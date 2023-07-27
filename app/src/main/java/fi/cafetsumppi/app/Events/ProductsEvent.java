package fi.cafetsumppi.app.Events;

import fi.cafetsumppi.app.Docs.Products;

/**
 * Created by Jupe Danger on 19.2.2018.
 */

public class ProductsEvent {

    private Products products;

    public ProductsEvent(Products products){
        this.products = products;
    }

    public Products getProducts() {
        return products;
    }
}
