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
 * Created by Jupe Danger on 21.2.2018.
 */

public class MoreItem extends AbstractItem<MoreItem, MoreItem.ViewHolder> {
    private String text;
    private String icon;
    private int id;

    public MoreItem(String text, String icon, int id) {
        this.text = text;
        this.icon = icon;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getType() {
        return R.id.more_item_id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.card_more_item;
    }

    @Override
    public void bindView(@NonNull MoreItem.ViewHolder viewHolder, @NonNull List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        Context context = viewHolder.itemView.getContext();

        viewHolder.moreItemName.setText(text);

        int drawableId = context.getResources().getIdentifier(icon, "drawable", context.getPackageName());
        if (drawableId == 0){
            drawableId = context.getResources().getIdentifier("ic_placeholder", "drawable", context.getPackageName());
        }
        Drawable img = context.getResources().getDrawable(drawableId);

        viewHolder.moreItemIcon.setImageDrawable(img);
    }

    @Override
    public void unbindView(MoreItem.ViewHolder holder) {
        super.unbindView(holder);

        holder.moreItemName.setText(null);
        holder.moreItemIcon.setImageDrawable(null);
    }

    @Override
    public MoreItem.ViewHolder getViewHolder(View v) {
        return new MoreItem.ViewHolder(v);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView moreItemName;
        @BindView(R.id.imageIcon)
        ImageView moreItemIcon;

        private ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}