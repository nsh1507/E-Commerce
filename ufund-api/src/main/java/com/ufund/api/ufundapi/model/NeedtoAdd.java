package com.ufund.api.ufundapi.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NeedtoAdd {
    /** Id of which product this is representing */
    @JsonProperty("id") private int id;
    /** Number of items which the user is trying to purchase */
    @JsonProperty("quantity") private int quantity;

    /**
     * Constructor for the product reference
     * 
     * @param id Id of the product
     * @param quantity Number of items which the user is trying to purchase
     */
    public NeedtoAdd(@JsonProperty("id") int id, @JsonProperty("quantity") int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    /**
     * Get the id of the product
     * 
     * @return Id of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the product
     * 
     * @param id Id of the product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the quantity of the product
     * 
     * @return Quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity of the product
     * 
     * @param quantity Quantity of the product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
