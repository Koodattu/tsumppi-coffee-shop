package fi.cafetsumppi.app.Interfaces.Retrofit;

import fi.cafetsumppi.app.Youtube.YoutubeVideo;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jupe Danger on 17.2.2018.
 */

public interface YoutubeApi {
    @GET("/youtube/v3/search?part=snippet&channelId=UCiLNxnGQWEtFkd5N93SHCjg&maxResults=10&order=date&type=video&key=AIzaSyBtAM4R3AoOfYvo7IQeaZ_bVWZTx5TAnBo")
    Call<YoutubeVideo> getUploads();
}
