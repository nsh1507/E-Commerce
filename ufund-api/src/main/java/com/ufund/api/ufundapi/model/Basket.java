package com.ufund.api.ufundapi.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Basket {
    //private static final Logger LOG = Logger.getLogger(Basket.class.getName());
    // Package private for tests
    static final String STRING_FORMAT = "Basket [id=%d, name=%s, price=%d, list=%s]";

    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private int price;
    @JsonProperty("list") private ArrayList<Need> list;


    /**
     * Create a basket with the given id, name and empty list
     * @param id The id of the basket
     * @param name The name of the basket
     * @param list The list of needs

     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Basket(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") int price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.list = new ArrayList<Need>();

    }

    /**
     * Retrieves the id of the basket
     * @return The id of the basket
     */
    public int getId() {return id;}

    /**
     * Sets the name of the basket- this should never really need to be used except maybe for basket creation.
     * @param name The name of the basket
     */
    public void setName(String name) {this.name = name;}

    /**
     * Retrieves the name of the basket
     * @return The name of the basket
     */
    public String getName() {return name;}


    /**
     * Retrieves the full shopping list
     * @return The whole list list
     */
    public ArrayList<Need> getList() {return list;}

    /**
     * adds an item to the end of the shopping list
     * @param quantity The need to add
     */
    public void addToList(Need needToAdd) {this.list.add(needToAdd);}

    /**
     * removes an item from shopping list. this assumes the given id is actually in the shopping list, does nothing otherwise
     * @param id The id of the need to remove
     */
    public void removeFromList(int id) {
        for (Need need : list) {
            if(need.getId()==id){
                list.remove(need);
                break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id, name,price,list);
    }

    public int getPrice() {
        return price;
    }
}
