package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
    public BasketFileDAO(@Value("${baskets.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private boolean save() throws IOException {
        objectMapper.writeValue(new File(filename), needs.values());
        return true;
    }

    private boolean load() throws IOException {
        needs = new TreeMap<>();
        File file = new File(filename);

        if(!file.exists()){
            return false;
        }

        Basket[] baskets = objectMapper.readValue(file, Basket[].class);
        for(Basket basket: baskets){
            for(Need need: basket.getContents()){
                needs.put(need.getId(), need);
            }
        }

        return true;
    }



    @Override
    public boolean addToBasket(int id, Need need) throws IOException {
        Basket[] baskets = objectMapper.readValue(new File(filename), Basket[].class);
        boolean updated = false;

        for(Basket basket : baskets){
            if(basket.getId() == id){
                basket.getContents().add(need);
                updated = true;
                break;
            }
            
        }
        if(updated){
            objectMapper.writeValue(new File(filename), baskets);
        }

        return updated;
    }

    @Override
    public boolean clearBasket(int id) throws IOException {
        Basket[] baskets = objectMapper.readValue(new File(filename), Basket[].class);
        boolean updated = false;
    
        for(Basket basket : baskets){
            if(basket.getId() == id){
                basket.getContents().clear();
                updated = true;
                break;
            }
        }
    
        if(updated){
            objectMapper.writeValue(new File(filename), baskets);
        }
    
        return updated;
    }

    @Override
    public Need getBasketNeed(int id, int needid) {
        return needs.get(needid);
    }

    @Override
    public Need[] getContents(int id) throws IOException {
        Basket[] baskets = objectMapper.readValue(new File(filename), Basket[].class);
    
        for(Basket basket : baskets){
            if(basket.getId() == id){
                return basket.getContents().toArray(new Need[0]);
            }
        }
    
        return new Need[0];
    }

    @Override
    public boolean removeFromBasket(int id, int needid) throws IOException {
        Basket[] baskets = objectMapper.readValue(new File(filename), Basket[].class);
        boolean updated = false;
    
        for(Basket basket : baskets){
            if(basket.getId() == id){
                basket.getContents().removeIf(need -> need.getId() == needid);
                updated = true;
                break;
            }
        }
    
        if(updated){
            objectMapper.writeValue(new File(filename), baskets);
        }
    
        return updated;
    }

    @Override
    public Need[] searchBasket(int id, String name) throws IOException {
        Basket[] baskets = objectMapper.readValue(new File(filename), Basket[].class);
    
        for(Basket basket : baskets){
            if(basket.getId() == id){
                List<Need> foundNeeds = basket.getContents().stream()
                    .filter(need -> need.getName().equalsIgnoreCase(name))
                    .collect(Collectors.toList());
                return foundNeeds.toArray(new Need[0]);
            }
        }
    
        return new Need[0];
    }

    @Override
    public void updateBasket(int id) throws IOException {
        return;
    }
    
    
    
}
