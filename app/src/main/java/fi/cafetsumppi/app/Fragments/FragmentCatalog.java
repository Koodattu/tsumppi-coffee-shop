package fi.cafetsumppi.app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.listeners.OnClickListener;
import com.mikepenz.itemanimators.*;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import fi.cafetsumppi.app.Docs.Products;
import fi.cafetsumppi.app.Docs.Row;
import fi.cafetsumppi.app.Events.CatalogStatusEvent;
import fi.cafetsumppi.app.Events.ProductsEvent;
import fi.cafetsumppi.app.FastAdapterObjects.CatalogCategory;
import fi.cafetsumppi.app.FastAdapterObjects.ProductItem;
import fi.cafetsumppi.app.R;

/**
 * Created by Jupe Danger on 4.12.2017.
 */

public class FragmentCatalog extends Fragment {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    List<CatalogCategory> catalogCategories;
    List<ProductItem> catalogItems;

    Products products;
    ItemAdapter itemAdapter;

    Map<String, List<CatalogCategory>> subCategories;
    Map<String, List<ProductItem>> subCatItems;

    Set<String> cats;
    Set<String> subCats;

    int statusCode = 1;
    String lastCategory;

    GridLayoutManager glm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_catalog, container, false);
        ButterKnife.bind(this, v);

        glm = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(glm);
        recyclerView.setItemAnimator(new AlphaInAnimator());
        //create the ItemAdapter holding your Items
        itemAdapter = new ItemAdapter();

        //create the managing FastAdapter, by passing in the itemAdapter
        FastAdapter fastAdapter = FastAdapter.with(itemAdapter);

        //set our adapters to the RecyclerView
        recyclerView.setAdapter(fastAdapter);

        if (catalogCategories.size() != 0){
            //set the items to your ItemAdapter
            if (itemAdapter.getAdapterItemCount() == 0) {
                itemAdapter.add(catalogCategories);
            }
        }

        fastAdapter.withSelectable(true);
        fastAdapter.withOnClickListener(new OnClickListener<IItem>() {
            @Override
            public boolean onClick(View v, @NonNull IAdapter<IItem> adapter, @NonNull IItem item, int position) {

                if (item instanceof CatalogCategory){
                    if (statusCode == 1){
                        getActivity().setTitle(((CatalogCategory)item).getName());
                        lastCategory = ((CatalogCategory)item).getName();
                        itemAdapter.clear();
                        itemAdapter.add(subCategories.get(((CatalogCategory)item).getName()));
                        statusCode = 2;
                        statePasser.statePasser(statusCode);
                        return true;
                    } else if (statusCode == 2) {
                        getActivity().setTitle(((CatalogCategory)item).getName());
                        itemAdapter.clear();
                        itemAdapter.add(subCatItems.get(((CatalogCategory)item).getName()));
                        statusCode = 3;
                        statePasser.statePasser(statusCode);
                        glm.setSpanCount(1);
                        return true;
                    }
                } else if (item instanceof ProductItem) {
                    return false;
                }

                return false;
            }
        });

        return v;
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

    private void productsToCatsAndItems(){

        String lang = Locale.getDefault().getDisplayLanguage();

        for (Row row : products.getRows()) {
            if (lang.equals("suomi")){

                if (subCategories.containsKey(row.getCategoryfi())){
                    if (!subCats.contains(row.getSubcategoryfi())){
                        subCategories.get(row.getCategoryfi()).add(new CatalogCategory(row.getSubcategoryfi(), row.getSubcategory()));
                    }
                } else {
                    subCategories.put(row.getCategoryfi(), new ArrayList<CatalogCategory>());
                    subCategories.get(row.getCategoryfi()).add(new CatalogCategory(row.getSubcategoryfi(), row.getSubcategory()));
                }

                cats.add(row.getCategoryfi());
                subCats.add(row.getSubcategoryfi());

                catalogItems.add(new ProductItem(row));
            } else {

                if (subCategories.containsKey(row.getCategory())){
                    if (!subCats.contains(row.getSubcategory())){
                        subCategories.get(row.getCategory()).add(new CatalogCategory(row.getSubcategory(), row.getSubcategory()));
                    }
                } else {
                    subCategories.put(row.getCategory(), new ArrayList<CatalogCategory>());
                    subCategories.get(row.getCategory()).add(new CatalogCategory(row.getSubcategory(), row.getSubcategory()));
                }

                cats.add(row.getCategory());
                subCats.add(row.getSubcategory());

                catalogItems.add(new ProductItem(row));
            }
        }

        if (lang.equals("suomi")){
            for (String s : cats) {
                for (Row row : products.getRows()){
                    if (row.getCategoryfi().equals(s)){
                        catalogCategories.add(new CatalogCategory(s, row.getCategory()));
                        break;
                    }
                }
            }
        } else {
            for (String s : cats) {
                catalogCategories.add(new CatalogCategory(s, s));
            }
        }

        Collections.sort(catalogCategories, new Comparator<CatalogCategory>(){
            public int compare(CatalogCategory obj1, CatalogCategory obj2) {
                return obj1.getName().compareToIgnoreCase(obj2.getName());
            }
        });

        if (lang.equals("suomi")){
            for (ProductItem catalogItem : catalogItems){
                if (subCatItems.containsKey(catalogItem.getSubcategoryfi())){
                    subCatItems.get(catalogItem.getSubcategoryfi()).add(catalogItem);
                } else {
                    subCatItems.put(catalogItem.getSubcategoryfi(), new ArrayList<ProductItem>());
                    subCatItems.get(catalogItem.getSubcategoryfi()).add(catalogItem);
                }
            }
        } else {
            for (ProductItem catalogItem : catalogItems){
                if (subCatItems.containsKey(catalogItem.getSubcategory())){
                    subCatItems.get(catalogItem.getSubcategory()).add(catalogItem);
                } else {
                    subCatItems.put(catalogItem.getSubcategory(), new ArrayList<ProductItem>());
                    subCatItems.get(catalogItem.getSubcategory()).add(catalogItem);
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catalogCategories = new ArrayList<>();
        catalogItems = new ArrayList<>();
        subCategories = new HashMap<>();

        cats = new HashSet<>();
        subCats = new HashSet<>();
        subCatItems = new HashMap<>();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            products = bundle.getParcelable("products");
            productsToCatsAndItems();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        statePasser.statePasser(0);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onProductsEvent(ProductsEvent productsEvent) {
        products = productsEvent.getProducts();

        productsToCatsAndItems();

        //set the items to your ItemAdapter
        if (itemAdapter.getAdapterItemCount() == 0) {
            itemAdapter.add(catalogCategories);
        }
    }

    @Subscribe
    public void onCatalogStatusEvent(CatalogStatusEvent catalogStatusEvent) {
        switch (catalogStatusEvent.getStatusCode()){
            case 2:
                statusCode = 1;
                statePasser.statePasser(statusCode);
                itemAdapter.clear();
                itemAdapter.add(catalogCategories);
                getActivity().setTitle(getResources().getString(R.string.nav_catalog));
                break;
            case 3:
                statusCode = 2;
                statePasser.statePasser(statusCode);
                itemAdapter.clear();
                itemAdapter.add(subCategories.get(lastCategory));
                getActivity().setTitle(lastCategory);
                glm.setSpanCount(2);
                break;
        }
    }
}
