package fi.cafetsumppi.app.Docs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Row implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("namefi")
    @Expose
    private String namefi;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("descfi")
    @Expose
    private String descfi;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("categoryfi")
    @Expose
    private String categoryfi;
    @SerializedName("subcategory")
    @Expose
    private String subcategory;
    @SerializedName("subcategoryfi")
    @Expose
    private String subcategoryfi;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("pricesamo")
    @Expose
    private String pricesamo;
    @SerializedName("pricesale")
    @Expose
    private String pricesale;
    @SerializedName("onsale")
    @Expose
    private String onsale;
    @SerializedName("newitem")
    @Expose
    private String newitem;
    @SerializedName("imageurl")
    @Expose
    private String imageurl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamefi() {
        return namefi;
    }

    public void setNamefi(String namefi) {
        this.namefi = namefi;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescfi() {
        return descfi;
    }

    public void setDescfi(String descfi) {
        this.descfi = descfi;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryfi() {
        return categoryfi;
    }

    public void setCategoryfi(String categoryfi) {
        this.categoryfi = categoryfi;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getSubcategoryfi() {
        return subcategoryfi;
    }

    public void setSubcategoryfi(String subcategoryfi) {
        this.subcategoryfi = subcategoryfi;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPricesamo() {
        return pricesamo;
    }

    public void setPricesamo(String pricesamo) {
        this.pricesamo = pricesamo;
    }

    public String getPricesale() {
        return pricesale;
    }

    public void setPricesale(String pricesale) {
        this.pricesale = pricesale;
    }

    public String getOnsale() {
        return onsale;
    }

    public void setOnsale(String onsale) { this.onsale = onsale; }

    public String getNewitem() {
        return newitem;
    }

    public void setNewitem(String newitem) { this.newitem = newitem; }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}