package fi.cafetsumppi.app.Interfaces.Retrofit;

import fi.cafetsumppi.app.Docs.Products;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GSX2JSONApi {

    @GET("/api?id=1zEJPk3wzTGvW8O1d5mhx0j--AFAbSXLZ5PK8lS0lGfc&columns=false&integers=false")
    Call<Products> getProducts();
}