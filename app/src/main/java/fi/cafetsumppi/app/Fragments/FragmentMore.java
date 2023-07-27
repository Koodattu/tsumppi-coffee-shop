package fi.cafetsumppi.app.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.listeners.OnClickListener;
import com.mikepenz.itemanimators.SlideUpAlphaAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fi.cafetsumppi.app.FastAdapterObjects.MoreItem;
import fi.cafetsumppi.app.R;

/**
 * Created by Jupe Danger on 4.12.2017.
 */

public class FragmentMore extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    List<MoreItem> moreItems;

    ItemAdapter itemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_catalog, container, false);
        ButterKnife.bind(this, v);

        GridLayoutManager glm = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(glm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideUpAlphaAnimator());
        itemAdapter = new ItemAdapter();
        FastAdapter fastAdapter = FastAdapter.with(itemAdapter);
        recyclerView.setAdapter(fastAdapter);
        itemAdapter.add(moreItems);

        fastAdapter.withSelectable(true);
        fastAdapter.withOnClickListener(new OnClickListener<MoreItem>() {
            @Override
            public boolean onClick(View v, @NonNull IAdapter<MoreItem> adapter, @NonNull MoreItem item, int position) {

                switch(item.getId()){
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:
                        new LibsBuilder()
                                //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
                                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                .withAboutIconShown(true)
                                .withAboutVersionShown(true)
                                .withAboutDescription("Tsumppi sovellus. Tehnyt Juha Ala-Rantala ja Mika Jäntti")
                                //start the activity
                                .start(getActivity());
                    break;
                }

                return true;
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moreItems = new ArrayList<>();

        moreItems.add(new MoreItem(getResources().getString(R.string.settings), "ic_settings", 0));
        moreItems.add(new MoreItem(getResources().getString(R.string.send_feedback), "ic_feedback", 1));
        moreItems.add(new MoreItem(getResources().getString(R.string.about), "ic_about", 2));
    }
}
