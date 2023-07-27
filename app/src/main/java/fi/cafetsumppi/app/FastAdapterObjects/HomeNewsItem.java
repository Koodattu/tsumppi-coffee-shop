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

import butterknife.BindView;
import butterknife.ButterKnife;
import fi.cafetsumppi.app.R;

/**
 * Created by Jupe Danger on 21.2.2018.
 */

public class HomeNewsItem extends AbstractItem<HomeNewsItem, HomeNewsItem.ViewHolder> {

    private String title;
    private String img;
    private String link;

    public HomeNewsItem(String title, String img, String link) {
        this.title = title;
        this.img = img;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int getType() {
        return R.id.home_news_item_id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.card_home_news;
    }

    @Override
    public void bindView(@NonNull HomeNewsItem.ViewHolder viewHolder, @NonNull List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        Context context = viewHolder.itemView.getContext();

        viewHolder.newsImage.setImageBitmap(null);
        viewHolder.newsTitle.setText(title);

        Glide.with(context).load(img).into(viewHolder.newsImage);
    }

    @Override
    public void unbindView(HomeNewsItem.ViewHolder holder) {
        super.unbindView(holder);

        holder.newsTitle.setText(null);
        holder.newsImage.setImageDrawable(null);
    }

    @Override
    public HomeNewsItem.ViewHolder getViewHolder(View v) {
        return new HomeNewsItem.ViewHolder(v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_news_title)
        TextView newsTitle;
        @BindView(R.id.iv_icon_background)
        ImageView newsImage;

        private ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
