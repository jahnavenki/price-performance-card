/*
		Author Venkat
*/
package au.com.cfs.winged.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;

@Model(
adaptables = SlingHttpServletRequest.class,
adapters = FundPerformanceModel.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class FundPerformanceModel {

	@ValueMapValue
	private String layout;

	@ValueMapValue
	private String cardSize;

	@ValueMapValue
	private String apir;

	@ValueMapValue
	private String imageUrl;

	@ValueMapValue
	private String horizontalLine;

	@ValueMapValue
	private String verticalLine;

	@Inject
	private ResourceResolver resourceResolver;

	public String getLayout() {
		return layout;
	}

	public String getCardSize() {
		return cardSize;
	}

	public String getApir() {
		return apir;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getHorizontalLine() {
		return horizontalLine;
	}

	public String getVerticalLine() {
		return verticalLine;
	}

}
