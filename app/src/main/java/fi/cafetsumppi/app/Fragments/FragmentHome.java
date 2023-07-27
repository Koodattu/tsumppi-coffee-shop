package fi.cafetsumppi.app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.listeners.OnClickListener;
import com.mikepenz.itemanimators.AlphaInAnimator;
import com.mikepenz.itemanimators.SlideDownAlphaAnimator;
import com.mikepenz.itemanimators.SlideUpAlphaAnimator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fi.cafetsumppi.app.Docs.Products;
import fi.cafetsumppi.app.Docs.Row;
import fi.cafetsumppi.app.Events.CatalogStatusEvent;
import fi.cafetsumppi.app.Events.HomeEvent;
import fi.cafetsumppi.app.Facebook.FacebookNews;
import fi.cafetsumppi.app.FastAdapterObjects.CatalogCategory;
import fi.cafetsumppi.app.FastAdapterObjects.HomeDealsItem;
import fi.cafetsumppi.app.FastAdapterObjects.HomeNewsItem;
import fi.cafetsumppi.app.FastAdapterObjects.ProductItem;
import fi.cafetsumppi.app.ParcelObjects.BigData;
import fi.cafetsumppi.app.R;
import fi.cafetsumppi.app.Youtube.YoutubeVideo;
import me.postaddict.instagram.scraper.domain.Media;

/**
 * Created by Jupe Danger on 4.12.2017.
 */

public class FragmentHome extends Fragment {

    @BindView(R.id.iv_website_link)
    ImageView ivWebsite;
    @BindView(R.id.iv_instagram_link)
    ImageView ivInstagram;
    @BindView(R.id.iv_facebook_link)
    ImageView ivFacebook;
    @BindView(R.id.iv_youtube_link)
    ImageView ivYoutube;
    @BindView(R.id.rv_deals)
    RecyclerView rvDeals;
    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    @BindView(R.id.rv_products)
    RecyclerView rvProducts;

    ItemAdapter itemAdapterNews;
    ItemAdapter itemAdapterDeals;
    ItemAdapter itemAdapterProducts;

    Products products;
    YoutubeVideo youtubeVideo;
    FacebookNews facebookNews;
    List<Media> instagramPhotos;

    List<HomeNewsItem> homeNewsItems;
    List<HomeDealsItem> homeDealsItems;
    List<ProductItem> productItems;

    FastAdapter fastAdapterNews;
    FastAdapter fastAdapterDeals;
    FastAdapter fastAdapterProducts;

    BigData bigData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);

        GridLayoutManager glmProducts = new GridLayoutManager(getContext(), 1);
        rvProducts.setLayoutManager(glmProducts);
        //rvDeals.setHasFixedSize(true);
        rvProducts.setItemAnimator(new AlphaInAnimator());
        //create the ItemAdapter holding your Items
        itemAdapterProducts = new ItemAdapter();

        //create the managing FastAdapter, by passing in the itemAdapter
        fastAdapterProducts = FastAdapter.with(itemAdapterProducts);

        //set our adapters to the RecyclerView
        rvProducts.setAdapter(fastAdapterProducts);

        //set the items to your ItemAdapter
        itemAdapterProducts.add(productItems);

        rvProducts.setVisibility(View.GONE);

        GridLayoutManager glmNews = new GridLayoutManager(getContext(), 2);
        rvNews.setLayoutManager(glmNews);
        //rvNews.setHasFixedSize(true);
        rvNews.setItemAnimator(new AlphaInAnimator());
        //create the ItemAdapter holding your Items
        itemAdapterNews = new ItemAdapter();

        //create the managing FastAdapter, by passing in the itemAdapter
        fastAdapterNews = FastAdapter.with(itemAdapterNews);

        //set our adapters to the RecyclerView
        rvNews.setAdapter(fastAdapterNews);

        //set the items to your ItemAdapter
        itemAdapterNews.add(homeNewsItems);

        fastAdapterNews.withSelectable(true);
        fastAdapterNews.withOnClickListener(new OnClickListener<HomeNewsItem>() {
            @Override
            public boolean onClick(View v, @NonNull IAdapter<HomeNewsItem> adapter, @NonNull HomeNewsItem item, int position) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(item.getLink()));
                startActivity(intent);
                return true;
            }
        });

        GridLayoutManager glmDeals = new GridLayoutManager(getContext(), 1);
        rvDeals.setLayoutManager(glmDeals);
        //rvDeals.setHasFixedSize(true);
        rvDeals.setItemAnimator(new AlphaInAnimator());
        //create the ItemAdapter holding your Items
        itemAdapterDeals = new ItemAdapter();

        //create the managing FastAdapter, by passing in the itemAdapter
        fastAdapterDeals = FastAdapter.with(itemAdapterDeals);

        //set our adapters to the RecyclerView
        rvDeals.setAdapter(fastAdapterDeals);

        //set the items to your ItemAdapter
        itemAdapterDeals.add(homeDealsItems);

        fastAdapterDeals.withSelectable(true);
        fastAdapterDeals.withOnClickListener(new OnClickListener<HomeDealsItem>() {
            @Override
            public boolean onClick(View v, @NonNull IAdapter<HomeDealsItem> adapter, @NonNull HomeDealsItem item, int position) {
                linearLayout.setVisibility(View.GONE);
                rvProducts.setVisibility(View.VISIBLE);

                switch (item.getTitle()){
                    case "New Items":
                        newAndSaleItems(0);
                        break;
                    case "Sale Items":
                        newAndSaleItems(1);
                        break;
                    case "Tarjoukset":
                        newAndSaleItems(1);
                        break;
                    case "Uutuudet":
                        newAndSaleItems(0);
                        break;
                }

                return true;
            }
        });

        ivWebsite.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.seinajoenopiskelijapalvelut.fi/"));
                startActivity(intent);
            }
        });

        ivInstagram.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.instagram.com/opiskelijapalvelut/"));
                startActivity(intent);
            }
        });

        ivFacebook.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.facebook.com/sopiskelijapalvelut/"));
                startActivity(intent);
            }
        });

        ivYoutube.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.youtube.com/channel/UCiLNxnGQWEtFkd5N93SHCjg/"));
                startActivity(intent);
            }
        });

        if (fromActivity){
            onProductsDownloaded();
            onNewsDownloaded();
        }

        return v;
    }

    void newAndSaleItems(int type){
        itemAdapterProducts.clear();
        productItems = new ArrayList<>();

        switch (type){
            case 1:
                for (Row row : products.getRows()){
                    if (row.getOnsale().equals("T")){
                        productItems.add(new ProductItem(row));
                    }
                }
                break;
            case 0:
                for (Row row : products.getRows()){
                    if (row.getNewitem().equals("T")){
                        productItems.add(new ProductItem(row));
                    }
                }
                break;
        }

        statePasser.statePasser(1);
        itemAdapterProducts.add(productItems);
    }

    public interface StatePasser {
        public void statePasser(int statusCode);
    }

    StatePasser statePasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        statePasser = (StatePasser) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeNewsItems = new ArrayList<>();
        homeDealsItems = new ArrayList<>();
        productItems = new ArrayList<>();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            bigData = bundle.getParcelable("bigData");
            products = bigData.getProducts();
            instagramPhotos = bigData.getInstagramPhotos();
            facebookNews = bigData.getFacebookNews();
            youtubeVideo = bigData.getYoutubeVideo();
            fromActivity = true;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    boolean youtubeDL = false;
    boolean facebookDL = false;
    boolean instagramDL = false;

    boolean fromActivity = false;

    @Subscribe
    public void onHomeEvent(HomeEvent homeEvent) {

        switch(homeEvent.getTypeOfItem()){
            case 0:
                products = homeEvent.getProducts();
                if (products != null)
                    onProductsDownloaded();
                break;
            case 1:
                youtubeVideo = homeEvent.getYoutubeVideo();
                youtubeDL = true;
                break;
            case 2:
                facebookNews = homeEvent.getFacebookNews();
                facebookDL = true;
                break;
            case 3:
                instagramPhotos = homeEvent.getInstagramPhotos();
                instagramDL = true;
                break;
        }

        if (youtubeDL && facebookDL && instagramDL){
            onNewsDownloaded();
        }
    }

    void onNewsDownloaded(){
        homeNewsItems.add(new HomeNewsItem(instagramPhotos.get(0).caption, instagramPhotos.get(0).imageUrls.thumbnail, instagramPhotos.get(0).link));
        homeNewsItems.add(new HomeNewsItem(instagramPhotos.get(1).caption, instagramPhotos.get(1).imageUrls.thumbnail, instagramPhotos.get(1).link));
        homeNewsItems.add(new HomeNewsItem(instagramPhotos.get(2).caption, instagramPhotos.get(2).imageUrls.thumbnail, instagramPhotos.get(2).link));
        homeNewsItems.add(new HomeNewsItem(instagramPhotos.get(3).caption, instagramPhotos.get(3).imageUrls.thumbnail, instagramPhotos.get(3).link));
        if (itemAdapterNews.getAdapterItemCount() == 0) {
            itemAdapterNews.clear();
            itemAdapterNews.add(homeNewsItems);
        }
    }

    void onProductsDownloaded(){
        for (Row row : products.getRows()){
            if (row.getOnsale().equals("T")){
                homeDealsItems.add(new HomeDealsItem(getResources().getString(R.string.home_sale_items_title), getResources().getString(R.string.home_sale_items_desc), "holo_red_dark"));
                break;
            }
        }
        for (Row row : products.getRows()){
            if (row.getNewitem().equals("T")){
                homeDealsItems.add(new HomeDealsItem(getResources().getString(R.string.home_new_items_title), getResources().getString(R.string.home_new_items_desc), "holo_blue_dark"));
                break;
            }
        }

        if (itemAdapterDeals.getAdapterItemCount() == 0) {
            itemAdapterDeals.clear();
            itemAdapterDeals.add(homeDealsItems);
        }
    }

    @Subscribe
    public void onCatalogStatusEvent(CatalogStatusEvent catalogStatusEvent) {
        linearLayout.setVisibility(View.VISIBLE);
        rvProducts.setVisibility(View.GONE);
        productItems.clear();
        itemAdapterProducts.clear();
        itemAdapterProducts.add(productItems);
    }

}
