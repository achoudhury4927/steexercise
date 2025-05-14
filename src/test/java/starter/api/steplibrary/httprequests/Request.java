package starter.api.steplibrary.httprequests;

import java.util.Arrays;
import java.util.List;

import starter.api.singleton.ApiTestContext;

public abstract class Request {
    protected final ApiTestContext apiTestContext;

    protected static final String USERNAME = "username";
    protected static final String PASSWORD = "password";
    protected static final String NAME = "name";
    protected static final String PRICE = "price";
    protected static final String PRODUCT_TYPE = "productType";
    protected static final String QUANTITY = "quantity";
    protected static final String ORDERTYPE = "orderType";
    protected static final String PRODUCTID = "productId";
    protected static List<String> productTypeList = Arrays.asList("games", "computer accessory", "laptops", "miscellaneous", "mobile");

    protected Request() { apiTestContext = ApiTestContext.getInstance(); }
}
