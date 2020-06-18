package com.shoppingbag.model.response.shopping;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoryDataItem implements Serializable {

    @SerializedName("section_title")
    private String sectionTitle;

    @SerializedName("subCategory")
    private List<SubCategoryItem> subCategory;

    @SerializedName("childCategory")
    private List<ChildCategoryItem> childCategory;

    @SerializedName("categoryGroupWise")
    private List<CategoryGroupWiseItem> categoryGroupWise;

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public List<SubCategoryItem> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<SubCategoryItem> subCategory) {
        this.subCategory = subCategory;
    }

    public List<ChildCategoryItem> getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(List<ChildCategoryItem> childCategory) {
        this.childCategory = childCategory;
    }

    public List<CategoryGroupWiseItem> getCategoryGroupWise() {
        return categoryGroupWise;
    }

    public void setCategoryGroupWise(List<CategoryGroupWiseItem> categoryGroupWise) {
        this.categoryGroupWise = categoryGroupWise;
    }

    @Override
    public String toString() {
        return
                "CategoryDataItem{" +
                        "section_title = '" + sectionTitle + '\'' +
                        ",subCategory = '" + subCategory + '\'' +
                        ",childCategory = '" + childCategory + '\'' +
                        ",categoryGroupWise = '" + categoryGroupWise + '\'' +
                        "}";
    }
}