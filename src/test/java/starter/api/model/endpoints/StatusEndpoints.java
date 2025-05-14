package starter.api.model.endpoints;

/*
 * Enum of all Status Endpoints
 */

public enum StatusEndpoints implements Endpoint {
    STATUS("/status");

    private final String path;

    StatusEndpoints(String path) { this.path = path;}

    @Override
    public String getPath() { return path; }
}
