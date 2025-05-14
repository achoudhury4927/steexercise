package starter.api.model.endpoints;

/*
 * Enum of All Stock Management Endpoints
 */

public enum StockEndpoints implements Endpoint{

    ORDERS("/orders"),
    ORDERSID("/orders/product/{productId}");
    
    private final String path;

    StockEndpoints(String path) { this.path = path; }

    @Override
    public String getPath() { return path; }

    public String formatProductId(String id){
        String formattedPath = path.replace("{productId}", id);
        return formattedPath;
    } 
}
