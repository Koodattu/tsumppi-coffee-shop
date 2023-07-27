package fi.cafetsumppi.app.FastAdapterObjects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import fi.cafetsumppi.app.Docs.Row;
import fi.cafetsumppi.app.R;

/**
 * Created by Jupe Danger on 19.2.2018.
 */

public class ProductItem extends AbstractItem<ProductItem, ProductItem.ViewHolder> {

    private String name;
    private String namefi;
    private String desc;
    private String descfi;
    private String category;
    private String categoryfi;
    private String subcategory;
    private String subcategoryfi;
    private String price;
    private String pricesamo;
    private String pricesale;
    private String onsale;
    private String newitem;
    private String imageurl;

    public ProductItem(Row row){
        this.name = row.getName();
        this.namefi = row.getNamefi();
        this.desc = row.getDesc();
        this.descfi = row.getDescfi();
        this.category = row.getCategory();
        this.categoryfi = row.getCategoryfi();
        this.subcategory = row.getSubcategory();
        this.subcategoryfi = row.getSubcategoryfi();
        this.price = row.getPrice();
        this.pricesamo = row.getPricesamo();
        this.pricesale = row.getPricesale();
        this.onsale = row.getOnsale();
        this.newitem = row.getNewitem();
        this.imageurl = row.getImageurl();
    }

    public ProductItem(String name, String namefi, String desc, String descfi, String category, String categoryfi, String subcategory, String subcategoryfi, String price, String pricesamo, String pricesale, String onsale, String newitem, String imageurl) {
        this.name = name;
        this.namefi = namefi;
        this.desc = desc;
        this.descfi = descfi;
        this.category = category;
        this.categoryfi = categoryfi;
        this.subcategory = subcategory;
        this.subcategoryfi = subcategoryfi;
        this.price = price;
        this.pricesamo = pricesamo;
        this.pricesale = pricesale;
        this.onsale = onsale;
        this.newitem = newitem;
        this.imageurl = imageurl;
    }

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

    public void setOnsale(String onsale) {
        this.onsale = onsale;
    }

    public String getNewitem() {
        return newitem;
    }

    public void setNewitem(String newitem) {
        this.newitem = newitem;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public int getType() {
        return R.id.product_item_id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.card_product_item;
    }

    @Override
    public void bindView(@NonNull ProductItem.ViewHolder viewHolder, @NonNull List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        Context context = viewHolder.itemView.getContext();
        String lang = Locale.getDefault().getDisplayLanguage();

        viewHolder.itemImg.setImageBitmap(null);

        if (lang.equals("suomi")) {
            viewHolder.itemName.setText(getNamefi());
            viewHolder.itemDesc.setText(getDescfi());
        } else {
            viewHolder.itemName.setText(getName());
            viewHolder.itemDesc.setText(getDesc());
        }

        if (getOnsale().equals("T")){
            viewHolder.itemPrice.setText(getPricesale() + "€");
        } else {
            viewHolder.itemPrice.setText(getPrice() + "€");
        }

        if (!getImageurl().equals("")) {
            Glide.with(context).load(getImageurl()).into(viewHolder.itemImg);
        } else {
            int drawableId = context.getResources().getIdentifier("ic_placeholder", "drawable", context.getPackageName());
            Drawable img = context.getResources().getDrawable(drawableId);
            viewHolder.itemImg.setImageDrawable(img);
        }
    }

    @Override
    public void unbindView(ProductItem.ViewHolder holder) {
        super.unbindView(holder);

        holder.itemName.setText(null);
        holder.itemPrice.setText(null);
        holder.itemDesc.setText(null);
        holder.itemImg.setImageDrawable(null);
    }

    @Override
    public ProductItem.ViewHolder getViewHolder(View v) {
        return new ProductItem.ViewHolder(v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.item_desc)
        TextView itemDesc;
        @BindView(R.id.item_image)
        ImageView itemImg;

        private ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
