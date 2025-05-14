package starter.api.model.endpoints;

/*
 * Enum of All Login Endpoints
 */

public enum LoginEndpoints implements Endpoint {
    LOGIN("/auth/login");

    private final String path;

    LoginEndpoints(String path) { this.path = path;}

    @Override
    public String getPath() { return path; }
}
