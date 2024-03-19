package com.ufund.api.ufundapi.model;

import java.util.HashMap;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Basket {
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    // All basket parameters
    @JsonProperty("id") private int id;
    // Maps need id to quantity
    @JsonProperty("inventory") private HashMap<Integer, NeedtoAdd> inventory;

    /**
     * Constructor for the basket, creates an empty basket
     */
    public Basket(@JsonProperty("id") int id) {
        this.id = id;
        this.inventory = new HashMap<>();
    }

    /**
     * Get the inventory of the basket
     * 
     * @return Inventory of the basket
     */
    public HashMap<Integer, NeedtoAdd> getInventory() {
        return inventory;
    }

    /**
     * Adds a need to the basket by calling the NeedtoAdd add method
     * 
     * Checks to see if it already exists and if we should just add on to the quantity
     * 
     * @param needId Id of the need to add
     * @param quantity Quantity of the need to add
     */
    public void addItem(int needId, int quantity) {
        if (inventory.containsKey(needId)) {
            int currentQuantity = inventory.get(needId).getQuantity();
            inventory.get(needId).setQuantity(currentQuantity + quantity);
        }
        else {
            inventory.put(needId, new NeedtoAdd(needId, quantity));
        }
    }

    /**
     * Edit the quantity of a need reference in the inventory
     * 
     * @param needId Id of the need to edit
     * @param quantity Quantity to set the need to, 0 will remove it
     */
    public void editQuantity(int needId, int quantity) {
        if (inventory.containsKey(needId)) {
            if (quantity == 0) {
                inventory.remove(needId);
            }
            else {
                inventory.get(needId).setQuantity(quantity);
            }
        }
    }

    /**
     * Remove an item from the basket
     * 
     * @param needId Id of the need to remove
     */
    public void removeItem(int needId) {
        editQuantity(needId, 0);
    }

    /**
     * Clears a basket, used when a user checks out
     */
    public void clear() {
        inventory.clear();
    }

    /**
     * Get the id of the basket
     * 
     * @return Id of the basket
     */
    public int getId() {
        return id;
    }
}
