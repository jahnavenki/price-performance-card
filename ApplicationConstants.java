/**
 * This class represents a model for the form search component.
 * It is responsible for retrieving and organizing data related to the form search functionality.
 */
package au.com.cfs.winged.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.*;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

import au.com.cfs.winged.core.models.pojo.FormSearchTag;
import au.com.cfs.winged.core.models.pojo.FormSearchTaxonomy;
import au.com.cfs.winged.core.util.ObjectUtils;
import au.com.cfs.winged.helpers.LinkUtil;
import au.com.cfs.winged.core.common.constants.ApplicationConstants;
import au.com.cfs.winged.core.models.pojo.FormSearchFileRepeater;
import au.com.cfs.winged.core.models.pojo.FormSearchItem;


@Model(
  adaptables = SlingHttpServletRequest.class,
  adapters = FormSearchModel.class,
  defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class FormSearchModel {

  private String uuid;
  
  /**
   * The heading of the form search component.
   */
  @ValueMapValue
  private String heading;

  /**
   * The path to the form fragment resource.
   */
  @ValueMapValue
  private String formFragmentPath;

  /**
   * The number of items to display per page in the form search results.
   */
  @ValueMapValue
  private String itemsPerPage;

  /**
   * The resource resolver used for retrieving resources and resolving tags.
   */
  @Inject
  private ResourceResolver resourceResolver;

  /**
   * The list of category filters for the form search.
   */
  private ArrayList<FormSearchTag> categoryFilters;

  /**
   * The list of product type filters for the form search.
   */
  private ArrayList<FormSearchTag> productTypeFilters;

  /**
   * The list of filters for the form search.
   */
  private ArrayList<FormSearchTaxonomy> filters;

  /**
   * The list of form items for the form search.
   */
  private ArrayList<FormSearchItem> formItems;

  /**
   * Initializes the model by resolving the form content fragment.
   * This method is called after the model is constructed.
   */
  @PostConstruct
  public void initModel() {
    this.setUuid();
    this.resolveFormContentFragment();
    this.setFilters();
  }

  /**
   * Converts an array of serialized form search file repeaters into a list of FormSearchFileRepeater objects.
   *
   * @param serialisedList The array of serialized form search file repeaters.
   * @return The list of FormSearchFileRepeater objects.
   */
  private ArrayList<FormSearchFileRepeater> formatSerialisedDataToFileRepeaterList(String[] serialisedList) {
    ArrayList<FormSearchFileRepeater> formSearchFileRepeaterList = new ArrayList<>();

    if (serialisedList != null && serialisedList.length > 0) {
      for (String serialisedItem : serialisedList) {
        FormSearchFileRepeater formSearchFileRepeater = ObjectUtils.parseJson(
            serialisedItem,
            FormSearchFileRepeater.class
        );

        if (formSearchFileRepeater != null && !formSearchFileRepeater.getFormName().isEmpty() && !formSearchFileRepeater.getFormLink().isEmpty()) {
          formSearchFileRepeater.setFormLink(
            LinkUtil.getMappedURL(
              formSearchFileRepeater.getFormLink(),
              resourceResolver
            )
          );
  
          formSearchFileRepeaterList.add(formSearchFileRepeater);
        }
      }
    }

    return formSearchFileRepeaterList;
  }

  /**
   * Formats a Tag object as a FormSearchTag filter.
   *
   * @param tag The Tag object to format.
   * @return The formatted FormSearchTag filter.
   */
  private FormSearchTag formatTagAsFilter(Tag tag) {
    if (tag != null) {
      return new FormSearchTag(tag.getTitle(), tag.getName());
    }

    return null;
  }

  /**
   * Formats a list of Tag objects as a list of FormSearchTag filters.
   *
   * @param tags The list of Tag objects to format.
   * @return The formatted list of FormSearchTag filters.
   */
  private ArrayList<FormSearchTag> formatTagListAsFilterList(ArrayList<Tag> tags) {
    ArrayList<FormSearchTag> filters = new ArrayList<>();

    if (tags != null && tags.size() > 0) {
      for (Tag tag : tags) {
        FormSearchTag filter = this.formatTagAsFilter(tag);
        filters.add(filter);
      }
    }

    return filters;
  }

  /**
   * Resolves a Tag object from the given tag path.
   *
   * @param tagPath The path of the Tag to resolve.
   * @return The resolved Tag object, or null if not found or if the resource resolver is not available.
   */
  private Tag resolveTag(String tagPath) {
    if (resourceResolver != null) {
      TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
      Tag tag = tagManager.resolve(tagPath);
      return tag;
    }

    return null;
  }

  /**
   * Resolves a list of Tag objects from the given array of tag paths.
   *
   * @param tagPaths The array of tag paths to resolve.
   * @return The list of resolved Tag objects.
   */
  private ArrayList<Tag> resolveTagList(String[] tagPaths) {
    ArrayList<Tag> tags = new ArrayList<>();

    if (tagPaths != null && tagPaths.length > 0) {
      for (String tagPath : tagPaths) {
        Tag tag = this.resolveTag(tagPath);
        tags.add(tag);
      }
    }

    return tags;
  }

  /**
   * Resolves the content fragment of the specified path and retrieves its properties.
   *
   * @param fragmentPath The path to the content fragment.
   * @return The ValueMap object containing the properties of the content fragment, or null if not found or if the resource resolver is not available.
   */
  public ValueMap resolveContentFragment(String fragmentPath) {
    if (resourceResolver != null) {
      Resource fragmentResource = resourceResolver.getResource(fragmentPath + ApplicationConstants.CONTENT_FRAGMENT_MASTER);

      if (Objects.nonNull(fragmentResource)) {
        ValueMap properties = fragmentResource.getValueMap();
        return properties;
      }
    }

    return null;
  }

  /**
   * Resolves the form content fragment and initializes the category filters, product type filters, and form items.
   */
  private void resolveFormContentFragment() {
    if (this.formFragmentPath != null) {
      ValueMap formProperties = resolveContentFragment(formFragmentPath);

      if (!formProperties.isEmpty()) {
        setCategoryFilters(formProperties.get(ApplicationConstants.CATEGORY_FILTERS, String[].class));
        setProductTypeFilters(formProperties.get(ApplicationConstants.PRODUCT_TYPE_FILTERS, String[].class));
        setFormItems(formProperties.get(ApplicationConstants.FORM_ITEMS, String[].class));
      }
    }
  }

  /**
   * Resolves the content fragment of the specified form item path and creates a FormSearchItem object with its properties.
   *
   * @param formItemFragmentPath The path to the form item content fragment.
   * @return The FormSearchItem object representing the form item.
   */
  private FormSearchItem resolveFormItemContentFragment(String formItemFragmentPath) {
    ValueMap formItemProperties = resolveContentFragment(formItemFragmentPath);

    if (formItemProperties == null || formItemProperties.isEmpty()) {
      return null;
    }

    FormSearchItem formSearchItem = new FormSearchItem();
    formSearchItem.setItemName(formItemProperties.get(ApplicationConstants.ITEM_NAME, String.class));
    formSearchItem.setItemDescription(formItemProperties.get(ApplicationConstants.ITEM_DESCRIPTION, String.class));
    formSearchItem.setFormType(formItemProperties.get(ApplicationConstants.FORM_TYPE, String.class));
    formSearchItem.setContactHeading(formItemProperties.get(ApplicationConstants.CONTACT_HEADING, String.class));
    formSearchItem.setContactDetails(formItemProperties.get(ApplicationConstants.CONTACT_DETAILS, String.class));
    formSearchItem.setPickerHeading(formItemProperties.get(ApplicationConstants.PICKER_HEADING, String.class));
    formSearchItem.setDownloadCtaText(formItemProperties.get(ApplicationConstants.DOWNLOAD_CTA_TEXT, String.class));
    formSearchItem.setShowDocumentIcon(formItemProperties.get(ApplicationConstants.SHOW_DOCUMENT_ICON, Boolean.class));
    formSearchItem.setShowHelp(formItemProperties.get(ApplicationConstants.SHOW_HELP, Boolean.class));
    formSearchItem.setHelpHeading(formItemProperties.get(ApplicationConstants.HELP_HEADING, String.class));
    formSearchItem.setHelpText(formItemProperties.get(ApplicationConstants.HELP_TEXT, String.class));

    formSearchItem.setFormFile(
      LinkUtil.getMappedURL(
        formItemProperties.get(ApplicationConstants.FORM_FILE, String.class),
        resourceResolver
      )
    );

    formSearchItem.setFormFileRepeater(
      this.formatSerialisedDataToFileRepeaterList(
        formItemProperties.get(ApplicationConstants.FORM_FILE_REPEATER, String[].class)
      )
    );

    formSearchItem.setFormCategories(
      formatTagListAsFilterList(
        resolveTagList(
          formItemProperties.get(ApplicationConstants.FORM_CATEGORIES, String[].class)
        )
      )
    );

    formSearchItem.setProductType(
      formatTagListAsFilterList(
        resolveTagList(
          formItemProperties.get(ApplicationConstants.PRODUCT_TYPE, String[].class)
        )
      )
    );

    return formSearchItem;
  }

  // Setters
  public void setUuid() {
    UUID uuidObj = UUID.randomUUID();
    uuid = uuidObj.toString();
  }
  public void setCategoryFilters(String[] categoryFilterPaths) {
    this.categoryFilters = formatTagListAsFilterList(
        resolveTagList(categoryFilterPaths)
    );
  }
  public void setProductTypeFilters(String[] productTypeFilterPaths) {
    this.productTypeFilters = formatTagListAsFilterList(
        resolveTagList(productTypeFilterPaths)
    );
  }
  public void setFilters() {
    ArrayList<FormSearchTaxonomy> newFilters = new ArrayList<>();

    if (this.categoryFilters != null) {
      newFilters.add(
        new FormSearchTaxonomy(
          "Category",
          ApplicationConstants.FORM_CATEGORIES,
          this.categoryFilters
        )
      );
    }

    if (this.productTypeFilters != null) {
      newFilters.add(
        new FormSearchTaxonomy(
          "Product",
          ApplicationConstants.PRODUCT_TYPE,
          this.productTypeFilters
        )
      );
    }

    this.filters = newFilters;
  }
  public void setFormItems(String[] formItemPaths) {
    ArrayList<FormSearchItem> items = new ArrayList<>();

    if (formItemPaths != null && formItemPaths.length > 0) {
      for (String formItemPath : formItemPaths) {
        FormSearchItem formItem = resolveFormItemContentFragment(formItemPath);

        if (formItem != null) {
          items.add(formItem);
        }
      }
    }

    this.formItems = items;
  }

  // Getters
  public String getUuid() {
		return uuid;
	}
  public String getHeading() {
    return heading;
  }
  public String getFormFragmentPath() {
    return formFragmentPath;
  }
  public String getItemsPerPage() {
    return itemsPerPage;
  }
  public ArrayList<FormSearchTaxonomy> getFilters() {
    return filters;
  }
  public String getJsonPayload() {
    Map<String, Object> formData = new HashMap<>();

    if (formItems.size() > 0) {
      formData.put(ApplicationConstants.ITEMS, formItems);
      formData.put(ApplicationConstants.COUNT, formItems.size());
      return ObjectUtils.objectToJsonString(formData);
    }

    return StringUtils.EMPTY;
  }
}
