package fi.cafetsumppi.app.Events;

/**
 * Created by Jupe Danger on 21.2.2018.
 */

public class CatalogStatusEvent {
    private int statusCode;

    public CatalogStatusEvent(int statusCode){
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
