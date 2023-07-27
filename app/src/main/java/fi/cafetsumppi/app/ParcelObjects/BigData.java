package fi.cafetsumppi.app.ParcelObjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import fi.cafetsumppi.app.Docs.Products;
import fi.cafetsumppi.app.Facebook.FacebookNews;
import fi.cafetsumppi.app.Youtube.YoutubeVideo;
import me.postaddict.instagram.scraper.domain.Media;

/**
 * Created by Jupe Danger on 21.2.2018.
 */

public class BigData implements Parcelable {

    private Products products;
    private YoutubeVideo youtubeVideo;
    private FacebookNews facebookNews;
    private List<Media> instagramPhotos;

    public BigData(Products products, YoutubeVideo youtubeVideo, FacebookNews facebookNews, List<Media> instagramPhotos) {
        this.products = products;
        this.youtubeVideo = youtubeVideo;
        this.facebookNews = facebookNews;
        this.instagramPhotos = instagramPhotos;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public YoutubeVideo getYoutubeVideo() {
        return youtubeVideo;
    }

    public void setYoutubeVideo(YoutubeVideo youtubeVideo) {
        this.youtubeVideo = youtubeVideo;
    }

    public FacebookNews getFacebookNews() {
        return facebookNews;
    }

    public void setFacebookNews(FacebookNews facebookNews) {
        this.facebookNews = facebookNews;
    }

    public List<Media> getInstagramPhotos() {
        return instagramPhotos;
    }

    public void setInstagramPhotos(List<Media> instagramPhotos) {
        this.instagramPhotos = instagramPhotos;
    }

    protected BigData(Parcel in) {
        products = (Products) in.readValue(Products.class.getClassLoader());
        youtubeVideo = (YoutubeVideo) in.readValue(YoutubeVideo.class.getClassLoader());
        facebookNews = (FacebookNews) in.readValue(FacebookNews.class.getClassLoader());
        if (in.readByte() == 0x01) {
            instagramPhotos = new ArrayList<Media>();
            in.readList(instagramPhotos, Media.class.getClassLoader());
        } else {
            instagramPhotos = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(products);
        dest.writeValue(youtubeVideo);
        dest.writeValue(facebookNews);
        if (instagramPhotos == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            try {
                dest.writeList(instagramPhotos);
            } catch (RuntimeException e){
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BigData> CREATOR = new Parcelable.Creator<BigData>() {
        @Override
        public BigData createFromParcel(Parcel in) {
            return new BigData(in);
        }

        @Override
        public BigData[] newArray(int size) {
            return new BigData[size];
        }
    };
}