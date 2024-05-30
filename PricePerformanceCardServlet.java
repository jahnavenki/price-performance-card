import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.crx.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component(service = Servlet.class, property = {
    Constants.SERVICE_DESCRIPTION + "= JSON Data in Dynamic Dropdown",
    "sling.servlet.paths=" + "/bin/cfs/PricePerformanceCardRequest.json",
    "sling.servlet.methods=" + HttpConstants.METHOD_GET
})
public class PricePerformanceCardServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(PricePerformanceCardServlet.class);
    private static final String JSON_PATH = "/content/dam/cfs-winged/fpjson/pricenperformance/PricePerformanceCardRequest.json";

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        ResourceResolver resourceResolver = request.getResourceResolver();
        String attributeName = request.getParameter("attributeName");
        List<Resource> resourceList = new ArrayList<>();

        try {
            Resource jsonResource = resourceResolver.getResource(JSON_PATH + "/" + JcrConstants.JCR_CONTENT);
            if (jsonResource == null) {
                throw new NullPointerException("JSON resource not found at path: " + JSON_PATH);
            }
            
            Node jsonNode = jsonResource.adaptTo(Node.class);
            if (jsonNode == null) {
                throw new NullPointerException("Failed to adapt JSON resource to node");
            }
            
            InputStream inputStream = jsonNode.getProperty("jcr:data").getBinary().getStream();
            StringBuilder stringBuilder = new StringBuilder();
            String eachLine;

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                while ((eachLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(eachLine);
                }
            }

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());

            // Fetch the appropriate JSON array based on attributeName
            JSONArray jsonArray = jsonObject.getJSONArray(attributeName);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String key = item.getString("key");
                String value = item.getString("value");

                ValueMap valueMap = new ValueMapDecorator(new HashMap<>());
                valueMap.put("value", key);
                valueMap.put("text", value);
                resourceList.add(new ValueMapResource(resourceResolver, new ResourceMetadata(), "nt:unstructured", valueMap));
            }

            DataSource dataSource = new SimpleDataSource(resourceList.iterator());
            request.setAttribute(DataSource.class.getName(), dataSource);

        } catch (JSONException | IOException | RepositoryException e) {
            LOGGER.error("Error in JSON Data Exporting: {}", e.getMessage(), e);
        }
    }
}
