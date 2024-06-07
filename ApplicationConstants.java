package au.com.cfs.winged.core.common.constants;

public final class ApplicationConstants {

  private ApplicationConstants() {
    throw new UnsupportedOperationException("This is a application constant class and cannot be instantiated");
  }

  // Dialog Box constants
  public static final String TITLE = "title";
  public static final String HIDE_BUTTON = "hideButton";
  public static final String MOBILE_TITLE = "mobileTitle";
  public static final String OVERVIEW = "Overview";
  public static final String JCR_TITLE = "jcr:title";
  public static final String JCR_DESCRIPTION = "jcr:description";
  public static final String CQ_TAGS = "cq:tags";
  public static final String NAME = "name";
  public static final String FEATURED_IMAGE = "featuredImage";
  public static final String DESCRIPTION = "description";
  public static final String HREF = "href";
  public static final String LINK = "link";
  public static final String URL = "url";
  public static final String ICON = "icon";
  public static final String LABEL = "label";
  public static final String SHORT_DESCRIPTION = "shortDescription";
  public static final String CARD_TAG = "cardTag";
  public static final String TARGET = "target";
  public static final String TYPE = "type";
  public static final String THEME = "theme";
  public static final String LINK_LABEL = "linkLabel";
  public static final String IMAGE_URL = "imageUrl";
  public static final String CTAS = "ctas";
  public static final String MEDIA_TYPE = "mediaType";
  public static final String MOBILE_IMAGE_URL = "mobileImageUrl";
  public static final String TEXT_POSITION = "textPosition";
  public static final String HEADING = "heading";
  public static final String SUPER_HEADING = "superHeading";
  public static final String HEADINGS = "headings";
  public static final String CONTENT = "content";
  public static final String DATE = "date";
  public static final String VIDEO_URL = "videoUrl";
  public static final String ITEMS = "items";
  public static final String SECONDARY_ITEMS = "secondaryItems";
  public static final String MAIN_TITLE = "mainTitle";
  public static final String BUTTON_LABEL = "buttonLabel";
  public static final String ICON_URL = "iconUrl";
  public static final String LAYOUT = "layout";
  public static final String HEADING_LINK = "headingLink";
  public static final String NUMBER = "number";
  public static final String CARDS = "cards";
  public static final String CARD_SIZE = "cardSize";
  public static final String ROWS = "rows";
  public static final String ROW_DATA = "rowData";
  public static final String DATA = "data";
  public static final String ACTIONS = "actions";
  public static final String MANUAL_CARDS = "manualCards";
  public static final String STEPS = "steps";
  public static final String COLUMN_WIDTH = "columnWidth";
  public static final String LINK_TYPE = "linkType";
  public static final String LINK_URL = "linkUrl";
  public static final String TRACKING_NAME = "trackingName";
  public static final String ROLE = "role";
  public static final String SOCIAL_ITEMS = "socialItems";
  public static final String ACTIONS_TEAM = "actionsTeam";
  public static final String ACTIONS_LEADERSHIP = "actionsLeadership";
  public static final String ICON_SIZE = "iconSize";
  public static final String HIDE_ON_MOBILE = "hideOnMobile";
  
  public static final String CONTACTINFO_ITEMS = "contactInfoItems";
  public static final String LINK_ITEMS = "linkItems";
  public static final String METADATA = "metadata";
  public static final String LOGIN_LABEL = "loginLabel";
  public static final String LINK_LOGIN = "linkLogin";
  public static final String LINK_DESCRIPTION = "linkDescription";

  // Card Type constants
  public static final String EDITORIAL_CARDS_TYPE = "editorialCards";
  public static final String AWARDS_CARDS_TYPE = "awardsCards";
  public static final String CENTER_CARDS_TYPE = "centerCards";
  public static final String FUND_CARDS_TYPE = "fundCards";
  public static final String LEFT_CARDS_TYPE = "leftCards";
  public static final String ROW_CARDS_TYPE = "rowCards";
  public static final String TEXT_CARDS_TYPE = "textCards";
  public static final String CARD_MODE_DYNAMIC = "dynamic";
  public static final String CARD_MODE_MANUAL = "manual";

	// Dynamic Fund Card Type constants
	public static final String EQUALS = "=";
	public static final String DATASOURCE = "datasource";
	public static final String DROPDOWN_SELECTOR = "dropdownSelector";
	public static final String MAIN_GROUP_LIST = "mainGroupList";
	public static final String MAIN_GROUP_LIST_PATH = "/content/dam/cfs-winged/fpjson/mainGroup.json";
	public static final String PRODUCTS_LIST = "productsList";
	public static final String PRODUCTS_LIST_PATH = "/content/dam/cfs-winged/fpjson/products.json";
	public static final String YEARS_LIST = "yearsList";
	public static final String YEARS_LIST_PATH = "/content/dam/cfs-winged/fpjson/years.json";
	public static final String END_POINT_LIST = "endPointList";
	public static final String END_POINT_LIST_PATH = "/content/dam/cfs-winged/fpjson/enpoint.json";

  // Configuration constans
  public static final String READ_SERVICE = "readService";

  public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
  public static final String DATE_FORMAT_DD_MMMM_YYYY = "dd MMMM yyyy";
  public static final String DATE_FORMAT_DD_MMM_YYYY = "dd MMM yyyy";
  public static final String REFERENCE_TO_NAVIGATIONAL_TAB = "referenceToANavigationalTab";
  public static final String CONTENT_FRAGMENT_MASTER = "/jcr:content/data/master";
  public static final String ROOT_PATH = "/content/cfs-winged/au/en";
  public static final String JCR_CONTENT = "jcr:content";

  // Template Name constants
  public static final String ARTICLE_TEMPLATE_NAME = "/conf/cfs-winged/settings/wcm/templates/article-page";

  // Page Property constants
  public static final String PUBLISH_DATE = "publishedDate";
  public static final String READING_TIME = "readingTime";
  public static final String ARTICLE_TAGS = "articleTags";
  public static final String TAG = "tag";
  public static final String PATH = "path";
  public static final String AUTHOR_DETAILS = "authorDetails";

  public static final String ADVISER = "adviser";
  public static final String MEMBER = "personal";
  public static final String EMPLOYER = "employer";
  public static final String ADVISER_SEARCH = "/content/cfs-winged/au/en/adviser/search-adviser.html";
  public static final String MEMBER_SEARCH = "/content/cfs-winged/au/en/personal/search-personal.html";
  public static final String EMPLOYER_SEARCH = "/content/cfs-winged/au/en/employer/search-employer.html";
  public static final String QUERY_PARAM = "query";
  
  // Form constants
  public static final String CATEGORY_FILTERS = "categoryFilters";
  public static final String PRODUCT_TYPE_FILTERS = "productTypeFilters";
  public static final String FORM_ITEMS = "formItems";

  // Form Item constants
  public static final String COUNT = "count";
  public static final String ITEM_NAME = "itemName";
  public static final String ITEM_DESCRIPTION = "itemDescription";
  public static final String FORM_TYPE = "formType";
  public static final String FORM_FILE = "formFile";
  public static final String FORM_FILE_REPEATER = "formFileRepeater";
  public static final String FORM_CATEGORIES = "formCategories";
  public static final String PRODUCT_TYPE = "productType";
  public static final String CONTACT_HEADING = "contactHeading";
  public static final String CONTACT_DETAILS = "contactDetails";
  public static final String PICKER_HEADING = "pickerHeading";
  public static final String DOWNLOAD_CTA_TEXT = "downloadCtaText";
  public static final String SHOW_DOCUMENT_ICON = "showDocumentIcon";
  public static final String SHOW_HELP = "showHelp";
  public static final String HELP_HEADING = "helpHeading";
  public static final String HELP_TEXT = "helpText";
}
