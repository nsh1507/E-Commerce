package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents an Helper entity
 */
public class Helper {
    private static final Logger LOG = Logger.getLogger(Helper.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Helper [username=%s, password=%s, cart=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;
    @JsonProperty("admin") private boolean admin;
    @JsonProperty("cart") private ArrayList<Need> cart;

    /**
     * Create an helper with the given username and password
     * @param id The id of the helper
     * @param username The username of the helper
     * @param password The password of the helper
     * @param admin whether user is admin
     * @param cart list of Needs
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Helper(@JsonProperty("id") int id, @JsonProperty("username") String username, @JsonProperty("password") String password,
                  @JsonProperty("admin") Boolean admin, @JsonProperty("cart") ArrayList<Need> cart ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cart = cart;
        if (this.username.equals("admin") && this.password.equals("admin")) {this.admin = true;}
        else{ this.admin = false;}
    }

     /**
     * Retrieves the id of the helper
     * @return The id of the helper
     */
    public int getId() {return id;}

    /**
     * Sets the name of the helper - necessary for JSON object to Java object deserialization
     * @param name The name of the helper
     */
    public void setUserName(String name) {this.username = name;}

    /**
     * Retrieves the name of the helper
     * @return The name of the helper
     */
    public String getUsername() {return username;}

    /**
     * Sets the password of the helper - necessary for JSON object to Java object deserialization
     * @param password The password of the helper
     */

    public void setPassword(String password) {this.password = password;}

    /**
     * Retrieves the password of the helper
     * @return The password of the helper
     */
    public String getPassword() {return this.password;}

    /**
     * adds an item to the end of the shopping cart
     * @param quantity The product to add
     */
    public void addToCart(Need prod) {this.cart.add(prod);}

    /**
     * Retrieves the full shopping cart
     * @return The cart
     */
    public ArrayList<Need> getCart() {return cart;}

    /**
     * Retrieves the role of the user
     * @return The role of the user
     */
    public Boolean isAdmin() {return admin;}

    /**
     * removes an item from shopping cart. this assumes the given id is actually in the shopping cart, does nothing otherwise
     * @param id The id of the product to remove
     */
    public void removeFromCart(int id) {
        for (Need need : cart) {
            if(need.getId()==id){
                cart.remove(need);
                break;
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,username, password, cart);
    }

}