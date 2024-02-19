package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Need entity
 * 
 * @author Nam Huynh
 */
public class Needs {
    private static final Logger LOG = Logger.getLogger(Needs.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Need [id=%d, cost=%d, quantity=%d, type=%s, name=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("cost") private int cost;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("type") private String type;
    @JsonProperty("name") private String name;


    /**
     * Create a hero with the given id and name
     * @param id The id of the product
     * @param cost The cost of the product
     * @param quantity The quantity of the product
     * @param type The type of the product
     * @param name The name of the product
     * 
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Needs(@JsonProperty("id") int id, @JsonProperty("name") String name,@JsonProperty("cost") int cost,
                @JsonProperty("quantity") int quantity,@JsonProperty("type") String type) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.type = type;

    }

    /**
     * Retrieves the id of the product
     * @return The id of the product
     */
    public int getId() {return id;}

    /**
     * Sets the name of the product - necessary for JSON object to Java object deserialization
     * @param name The name of the product
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the product
     * @return The name of the product
     */
    public String getName() {return name;}

    /**
     * Sets the cost of the product - necessary for JSON object to Java object deserialization
     * @param cost The cost of the product
     */
    public void setCost(int cost) {this.cost = cost;}

    /**
     * Retrieves the cost of the product
     * @return The cost of the product
     */
    public int getCost() {return cost;}

    /**
     * Sets the quantity of the product - necessary for JSON object to Java object deserialization
     * @param quantity The quantity of the product
     */
    public void setQuantity(int quantity) {this.quantity = quantity;}

    /**
     * Retrieves the quantity of the product
     * @return The quantity of the product
     */
    public int getQuantity() {return quantity;}

    /**
     * Sets the type of the product - necessary for JSON object to Java object deserialization
     * @param type The type of the product
     */
    public void setType(String type) {this.type = type;}

    /**
     * Retrieves the name of the product
     * @return The name of the product
     */
    public String getType() {return type;}


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id, cost, type, quantity, name);
    }
}