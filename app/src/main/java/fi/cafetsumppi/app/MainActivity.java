package fi.cafetsumppi.app;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import fi.cafetsumppi.app.Docs.Products;
import fi.cafetsumppi.app.Events.CatalogStatusEvent;
import fi.cafetsumppi.app.Events.HomeEvent;
import fi.cafetsumppi.app.Events.ProductsEvent;
import fi.cafetsumppi.app.Facebook.FacebookNews;
import fi.cafetsumppi.app.Fragments.FragmentCatalog;
import fi.cafetsumppi.app.Fragments.FragmentHome;
import fi.cafetsumppi.app.Fragments.FragmentMore;
import fi.cafetsumppi.app.Interfaces.Retrofit.FacebookAPI;
import fi.cafetsumppi.app.Interfaces.Retrofit.GSX2JSONApi;
import fi.cafetsumppi.app.Interfaces.Retrofit.YoutubeApi;
import fi.cafetsumppi.app.ParcelObjects.BigData;
import fi.cafetsumppi.app.Youtube.YoutubeVideo;
import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.domain.Media;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements FragmentCatalog.StatePasser, FragmentHome.StatePasser{

    // https://www.youtube.com/channel/UCiLNxnGQWEtFkd5N93SHCjg
    // https://www.seinajoenopiskelijapalvelut.fi/
    // https://www.instagram.com/opiskelijapalvelut/
    // https://www.facebook.com/sopiskelijapalvelut/

    @BindView(R.id.navigation) BottomNavigationView navigationView;

    Products products;
    YoutubeVideo youtubeVideo;
    FacebookNews facebookNews;
    List<Media> instagramPhotos;

    boolean coldStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mOnNavigationItemSelectedListener.onNavigationItemSelected(navigationView.getMenu().getItem(0));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            int currentID = navigationView.getSelectedItemId();

            if (coldStart){
                Fragment fragment;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                setTitle(getString(R.string.nav_home));
                fragment = new FragmentHome();
                if (products != null && youtubeVideo != null && facebookNews != null && instagramPhotos.size() != 0) {
                    BigData bigData = new BigData(products, youtubeVideo, facebookNews, instagramPhotos);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("bigData", bigData);
                    fragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.frame, fragment, "HOME");
                fragmentTransaction.commit();
                coldStart = false;
            }

            if (id == currentID){
                return false;
            } else {
                Fragment fragment;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                int homeID = navigationView.getMenu().getItem(0).getItemId();
                int catalogID = navigationView.getMenu().getItem(1).getItemId();
                int moreID = navigationView.getMenu().getItem(2).getItemId();

                boolean goingRight = true;

                if (currentID == homeID) {
                    // ei voida mennä kuin oikealle
                    goingRight = true;
                } else if (currentID == catalogID) {
                    if (id == homeID) {
                        goingRight = false;
                    } else {
                        goingRight = true;
                    }
                } else if (currentID == moreID) {
                    goingRight = false;
                }

                if (goingRight) { // ollaan vasemmalla
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                } else { // ollaa oikealla
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                }

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        setTitle(getString(R.string.nav_home));
                        fragment = new FragmentHome();
                        if (products != null && youtubeVideo != null && facebookNews != null && instagramPhotos.size() != 0) {
                            BigData bigData = new BigData(products, youtubeVideo, facebookNews, instagramPhotos);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("bigData", bigData);
                            fragment.setArguments(bundle);
                        }
                        fragmentTransaction.replace(R.id.frame, fragment, "HOME");
                        break;
                    case R.id.navigation_catalog:
                        setTitle(getString(R.string.nav_catalog));
                        fragment = new FragmentCatalog();
                        if (products != null) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("products", products);
                            fragment.setArguments(bundle);
                        }
                        fragmentTransaction.replace(R.id.frame, fragment, "CATALOG");
                        break;
                    case R.id.navigation_more:
                        setTitle(getString(R.string.nav_more));
                        fragment = new FragmentMore();
                        fragmentTransaction.replace(R.id.frame, fragment, "MORE");
                        break;
                }
                //Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
                fragmentTransaction.commit();
                return true;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        // haetaan tuotteet google docs
        fetchProducts();

        //haetaan viimeisimmät youtube videot
        fetchYoutube();

        // haetaan viimeisimmät facebook julkaisut
        fetchFacebook();

        // haetaan viimeisimmät instagram julkaisut
        FetchInstagram fetchInstagram = new FetchInstagram();
        fetchInstagram.execute();


    }

    private class FetchInstagram extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            Instagram instagram = new Instagram(client);

            try {
                instagramPhotos = instagram.getMedias("opiskelijapalvelut", 4);
            } catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            FragmentHome fragmentHome = (FragmentHome) getSupportFragmentManager().findFragmentByTag("HOME");
            if (fragmentHome != null && fragmentHome.isVisible()) {
                EventBus.getDefault().post(new HomeEvent(instagramPhotos, 3));
            }
        }
    }

    private void fetchFacebook(){
        String API_BASE_URL = "https://graph.facebook.com/";
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        FacebookAPI client =  retrofit.create(FacebookAPI.class);
        Call<FacebookNews> call = client.getFBNews();
        call.enqueue(new Callback<FacebookNews>() {
            @Override
            public void onResponse(Call<FacebookNews> call, Response<FacebookNews> response) {
                // Kutsu onnistui ja saimme vastauksen
                // TODO: näytä tuotteet
                facebookNews = response.body();
                FragmentHome fragmentHome = (FragmentHome) getSupportFragmentManager().findFragmentByTag("HOME");
                if (fragmentHome != null && fragmentHome.isVisible()) {
                    EventBus.getDefault().post(new HomeEvent(facebookNews, 2));
                }
            }

            @Override
            public void onFailure(Call<FacebookNews> call, Throwable t) {
                // tapahtui virhe kutsua suorittaessa
                // TODO: hoida virhe
                t.printStackTrace();
            }
        });
    }

    private void fetchYoutube(){
        String API_BASE_URL = "https://www.googleapis.com";
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        YoutubeApi client =  retrofit.create(YoutubeApi.class);
        Call<YoutubeVideo> call = client.getUploads();
        call.enqueue(new Callback<YoutubeVideo>() {
            @Override
            public void onResponse(Call<YoutubeVideo> call, Response<YoutubeVideo> response) {
                // Kutsu onnistui ja saimme vastauksen
                // TODO: näytä tuotteet
                youtubeVideo = response.body();
                FragmentHome fragmentHome = (FragmentHome) getSupportFragmentManager().findFragmentByTag("HOME");
                if (fragmentHome != null && fragmentHome.isVisible()) {
                    EventBus.getDefault().post(new HomeEvent(youtubeVideo, 1));
                }
            }

            @Override
            public void onFailure(Call<YoutubeVideo> call, Throwable t) {
                // tapahtui virhe kutsua suorittaessa
                // TODO: hoida virhe
                t.printStackTrace();
            }
        });
    }

    private void fetchProducts(){
        String API_BASE_URL = "http://gsx2json.com";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        // Luodaan hyvin yksinkertainen REST adapteri joka yhdistää GSX2JSON rajapinnan kautta google docs
        GSX2JSONApi client =  retrofit.create(GSX2JSONApi.class);

        // Luodaan kutsu jolla haetaan tuotteet
        Call<Products> call = client.getProducts();

        // Suoritetaan kutsu asynkronisesti, saadaan positiivinen tai negatiivinen vastaus
        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                // Kutsu onnistui ja saimme vastauksen
                // TODO: näytä tuotteet
                products = response.body();
                FragmentCatalog fragmentCatalog = (FragmentCatalog) getSupportFragmentManager().findFragmentByTag("CATALOG");
                if (fragmentCatalog != null && fragmentCatalog.isVisible()) {
                    EventBus.getDefault().post(new ProductsEvent(products));
                }
                FragmentHome fragmentHome = (FragmentHome) getSupportFragmentManager().findFragmentByTag("HOME");
                if (fragmentHome != null && fragmentHome.isVisible()) {
                    EventBus.getDefault().post(new HomeEvent(products, 0));
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                // tapahtui virhe kutsua suorittaessa
                // TODO: hoida virhe
                t.printStackTrace();
            }
        });
    }

    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = super.getTheme();

        Random random = new Random();

        switch (0){
            case 0:
                theme.applyStyle(R.style.AppThemeGreen, true);
                break;
            case 1:
                theme.applyStyle(R.style.AppThemeYellow, true);
                break;
            case 2:
                theme.applyStyle(R.style.AppThemeBlue, true);
                break;
        }

        return theme;
    }

    int catalogStatusCode = 0;

    @Override
    public void statePasser(int statusCode) {
        catalogStatusCode = statusCode;
    }

    @Override
    public void onBackPressed() {
        FragmentCatalog fragmentCatalog = (FragmentCatalog) getSupportFragmentManager().findFragmentByTag("CATALOG");
        FragmentHome fragmentHome = (FragmentHome) getSupportFragmentManager().findFragmentByTag("HOME");
        if (fragmentCatalog != null && fragmentCatalog.isVisible()) {
            switch (catalogStatusCode){
                case 0:
                    // ei olla katalogissa
                    super.onBackPressed();
                    break;
                case 1:
                    // tarkastelaan pääkategorioita
                    super.onBackPressed();
                    break;
                case 2:
                    // tarkastellaan alakategorioita
                    EventBus.getDefault().post(new CatalogStatusEvent(2));
                    break;
                case 3:
                    // tarkastellaan tuotteita
                    EventBus.getDefault().post(new CatalogStatusEvent( 3));
                    break;
            }
        } else if (fragmentHome != null && fragmentHome.isVisible()){
            if (catalogStatusCode == 1){
                EventBus.getDefault().post(new CatalogStatusEvent(0));
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
}
