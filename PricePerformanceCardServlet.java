package au.com.cfs.winged.servlets;

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
        Constants.SERVICE_DESCRIPTION + "= Json Data in dynamic Dropdown",
        "sling.servlet.paths=" + "/bin/cfs/PricePerformanceCardRequest.json",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET
})
public class PricePerformanceCardServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(PricePerformanceCardServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

        try {
            Resource pathResource = request.getResource();
            String jsonDataPath = Objects.requireNonNull(pathResource.getChild("datasource")).getValueMap().get("fpjsonPath", String.class);

            if (jsonDataPath != null) {
                Resource jsonResource = request.getResourceResolver().getResource(jsonDataPath + "/" + JcrConstants.JCR_CONTENT);
                if (jsonResource != null) {
                    Node jsonNode = jsonResource.adaptTo(Node.class);
                    if (jsonNode != null) {
                        InputStream inputStream = jsonNode.getProperty("jcr:data").getBinary().getStream();
                        StringBuilder stringBuilder = new StringBuilder();
                        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                            String eachLine;
                            while ((eachLine = bufferedReader.readLine()) != null) {
                                stringBuilder.append(eachLine);
                            }
                        }

                        JSONObject jsonObject = new JSONObject(stringBuilder.toString());

                        // Populate mainGroup dropdown
                        populateDropdown(jsonObject.getJSONArray("mainGroup"), request, "mainGroup");

                        // Populate products dropdown
                        populateDropdown(jsonObject.getJSONArray("products"), request, "products");

                        // Populate years dropdown
                        populateDropdown(jsonObject.getJSONArray("years"), request, "years");
                    }
                }
            }
        } catch (JSONException | IOException | RepositoryException e) {
            LOGGER.error("Error in Json Data Exporting: ", e);
        }
    }

    private void populateDropdown(JSONArray jsonArray, SlingHttpServletRequest request, String attributeName) throws JSONException {
        List<Resource> resourceList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String key = jsonObject.getString("key");
            String value = jsonObject.getString("value");

            ValueMap valueMap = new ValueMapDecorator(new HashMap<>());
            valueMap.put("value", key);
            valueMap.put("text", value);
            resourceList.add(new ValueMapResource(request.getResourceResolver(), new ResourceMetadata(), "nt:unstructured", valueMap));
        }

        DataSource dataSource = new SimpleDataSource(resourceList.iterator());
        request.setAttribute(attributeName, dataSource);
    }
}
