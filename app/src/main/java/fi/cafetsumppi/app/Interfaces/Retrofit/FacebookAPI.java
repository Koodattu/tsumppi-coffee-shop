package fi.cafetsumppi.app.Interfaces.Retrofit;

import fi.cafetsumppi.app.Facebook.FacebookNews;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jupe Danger on 17.2.2018.
 */

public interface FacebookAPI {

    @GET("/148675768614376/feed?access_token=192900914634989|Qi_ZJFNLkjcHyEtyg4yAJ_aF25k")
    Call<FacebookNews> getFBNews();
}
