package fi.cafetsumppi.app.FastAdapterObjects;

import android.content.Context;
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
 * Created by Jupe Danger on 21.2.2018.
 */

public class HomeDealsItem extends AbstractItem<HomeDealsItem, HomeDealsItem.ViewHolder> {

    private String title;
    private String desc;
    private String color;

    public HomeDealsItem(String title, String desc, String color) {
        this.title = title;
        this.desc = desc;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int getType() {
        return R.id.home_deals_item_id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.card_home_deal;
    }

    @Override
    public void bindView(@NonNull HomeDealsItem.ViewHolder viewHolder, @NonNull List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        Context context = viewHolder.itemView.getContext();

        viewHolder.dealsTitle.setText(title);
        viewHolder.dealsDesc.setText(desc);
        int colorId = context.getResources().getIdentifier(color, "color", context.getPackageName());
        viewHolder.background.setBackgroundColor(context.getResources().getColor(colorId));
    }

    @Override
    public void unbindView(HomeDealsItem.ViewHolder holder) {
        super.unbindView(holder);

        holder.dealsTitle.setText(null);
        holder.dealsDesc.setText(null);
    }

    @Override
    public HomeDealsItem.ViewHolder getViewHolder(View v) {
        return new HomeDealsItem.ViewHolder(v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_desc_deal)
        TextView dealsDesc;
        @BindView(R.id.text_title_deal)
        TextView dealsTitle;
        @BindView(R.id.iv_icon_background)
        ImageView background;

        private ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
