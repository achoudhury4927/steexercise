package starter.api.model.endpoints;

/*
 * Enum of All Product Management Endpoints
 */

public enum ProductEndpoints implements Endpoint{

    PRODUCTS("/products"),
    PRODUCTID("/products/{id}");
    
    private final String path;

    ProductEndpoints(String path) { this.path = path; }

    @Override
    public String getPath() { return path; }

    public String formatProductId(String id){
        String formattedPath = path.replace("{id}", id);
        return formattedPath;
    } 
}
