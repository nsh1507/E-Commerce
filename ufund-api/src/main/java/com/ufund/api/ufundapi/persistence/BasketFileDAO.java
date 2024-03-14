package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Basket;

@Component
public class BasketFileDAO implements BasketDAO {

    Map<Integer, Need> needs; // Provides a local cache of the need objects
                              // so that we don't need to read from the file
                              // each time
    private ObjectMapper objectMapper; // Provides conversion between Need               
                                       // objects and JSON text format
                                       // written to the file
    private static int nextId;
    private String filename; // the path to read and write to


    //CONSTRUCTOR
    public CartFileDAO(@Value("${baskets.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private boolean save() throws IOException {

    }

    private boolean load() throws IOException {
        needs = new TreeMap<>();
        // Deserialize JSON objects into an array (may throw an IOException)
        Basket[] basketArray = objectMapper.readValue( new File(filename), Basket[].class );

        // Populate the tree map with the baskets
    }



    @Override
    public boolean addToBasket(int id, Need need) throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean clearBasket(int id) throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Need getBasketNeed(int id, int needid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Need[] getContents(int id) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeFromBasket(int id, int needid) throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Need[] searchBasket(int id, String name) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateBasket(int id) throws IOException {
        // TODO Auto-generated method stub
        
    }
    
    
    
}
