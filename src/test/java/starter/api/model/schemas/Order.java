package starter.api.model.schemas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    public String productId;
    public int totalBuys;
    public int totalSells;
    public int currentStock;
    public int totalTransactions;
}
