package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Basket {
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    @JsonProperty("id") private int id; // matches the id of the basket owner
    @JsonProperty("contents") private ArrayList<Need> contents;
    
    static final String STRING_FORMAT = "Basket [id=%d, contents=%s]";

    /**
     * 
     * @param id: the id of the user owning this cart
     * @param contents: the Needs in the cart
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Basket(@JsonProperty("id") int id, @JsonProperty("contents") ArrayList<Need> contents) {
        this.id = id;
        this.contents = contents;
    }

    
    public int getId() {
        return id;
    }

    public ArrayList<Need> getContents() {
        return contents;
    }

    public void setContents( ArrayList<Need> contents ) {
        this.contents = contents;
    }

    public void setId( int id ) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ("Basket [id="+id+"id" + " contents=" + contents.toString()) + "]";
    }
}
