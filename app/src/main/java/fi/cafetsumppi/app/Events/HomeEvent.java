package fi.cafetsumppi.app.Events;

import java.util.List;

import fi.cafetsumppi.app.Docs.Products;
import fi.cafetsumppi.app.Facebook.FacebookNews;
import fi.cafetsumppi.app.Youtube.YoutubeVideo;
import me.postaddict.instagram.scraper.domain.Media;

/**
 * Created by Jupe Danger on 21.2.2018.
 */

public class HomeEvent {

    private Products products;
    private YoutubeVideo youtubeVideo;
    private FacebookNews facebookNews;
    private List<Media> instagramPhotos;
    private int typeOfItem;

    public HomeEvent(FacebookNews facebookNews, int typeOfItem) {
        this.facebookNews = facebookNews;
        this.typeOfItem = typeOfItem;
    }

    public HomeEvent(List<Media> instagramPhotos, int typeOfItem) {
        this.instagramPhotos = instagramPhotos;
        this.typeOfItem = typeOfItem;
    }

    public HomeEvent(Products products, int typeOfItem) {
        this.products = products;
        this.typeOfItem = typeOfItem;
    }

    public HomeEvent(YoutubeVideo youtubeVideo, int typeOfItem) {
        this.youtubeVideo = youtubeVideo;
        this.typeOfItem = typeOfItem;
    }

    public Products getProducts() {
        return products;
    }

    public YoutubeVideo getYoutubeVideo() {
        return youtubeVideo;
    }

    public FacebookNews getFacebookNews() {
        return facebookNews;
    }

    public List<Media> getInstagramPhotos() {
        return instagramPhotos;
    }

    public int getTypeOfItem() {
        return typeOfItem;
    }
}
