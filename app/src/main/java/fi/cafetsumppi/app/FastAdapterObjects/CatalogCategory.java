package fi.cafetsumppi.app.FastAdapterObjects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fi.cafetsumppi.app.R;

/**
 * Created by Jupe Danger on 19.2.2018.
 */

public class CatalogCategory extends AbstractItem<CatalogCategory, CatalogCategory.ViewHolder> {

    private String name;
    private String icon;

    public CatalogCategory(String name, String icon) {
        this.name = name;
        this.icon = "ic_" + icon.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int getType() {
        return R.id.catalog_category_item_id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.card_catalog_category;
    }

    @Override
    public void bindView(@NonNull CatalogCategory.ViewHolder viewHolder, @NonNull List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        Context context = viewHolder.itemView.getContext();

        viewHolder.categoryName.setText(name);

        int drawableId = context.getResources().getIdentifier(icon, "drawable", context.getPackageName());
        if (drawableId == 0){
            drawableId = context.getResources().getIdentifier("ic_placeholder", "drawable", context.getPackageName());
        }
        Drawable img = context.getResources().getDrawable(drawableId);

        viewHolder.categoryIcon.setImageDrawable(img);
    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);

        holder.categoryName.setText(null);
        holder.categoryIcon.setImageDrawable(null);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text) TextView categoryName;
        @BindView(R.id.iv_icon_background) ImageView categoryIcon;

        private ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}