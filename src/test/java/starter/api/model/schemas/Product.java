package starter.api.model.schemas;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    public String productId;
    public String name;
    public double price;
    public String productType;
    public int quantity;
    public Date createdAt;
}
