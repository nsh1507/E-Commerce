package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-Tier")
public class HelperTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_cart_size = 2;
        String expected_helper_name = "helperName";
        String expected_first_item_name = "Pizza";
        int expected_first_item_cost = 15;
        String expected_second_item_name = "Burger";
        int expected_second_item_cost = 10;

        Need pizza = new Need(1, "Pizza", 15, 2, "Food");
        Need burger = new Need(2, "Burger", 10, 5, "Food");
        ArrayList<Need> helperCart = new ArrayList<>();
        helperCart.add(pizza);
        helperCart.add(burger);

        // Invoke
        Helper helper = new Helper(expected_second_item_cost, "helperName", "pass123", false, helperCart, new ArrayList<Need>());

        // Analyze
        assertEquals(expected_cart_size, helper.getCart().size());
        assertEquals(expected_helper_name, helper.getUsername());
        assertEquals(expected_first_item_name, helper.getCart().get(0).getName());
        assertEquals(expected_first_item_cost, helper.getCart().get(0).getCost());
        assertEquals(expected_second_item_name, helper.getCart().get(1).getName());
        assertEquals(expected_second_item_cost, helper.getCart().get(1).getCost());
    }

    @Test
    public void testRemoveFromCart() {
        // Setup
        int expected_cart_size_before_removal = 2;
        int expected_cart_size_after_removal = 1;

        Need pizza = new Need(1, "Pizza", 15, 2, "Food");
        Need burger = new Need(2, "Burger", 10, 5, "Food");
        ArrayList<Need> helperCart = new ArrayList<>();
        helperCart.add(pizza);
        helperCart.add(burger);

        Helper helper = new Helper(expected_cart_size_after_removal, "helperName", "test123", false, helperCart, new ArrayList<Need>());

        // Analyze before removal
        assertEquals(expected_cart_size_before_removal, helper.getCart().size());

        // Perform removal
        helper.removeFromCart(2); // Assuming removeFromCart works by need ID

        // Verify after removal
        assertEquals(expected_cart_size_after_removal, helper.getCart().size());
    }

    @Test
    public void testEquals() {
        Need pizza1 = new Need(1, "pizza1", 1, 1, "Pizza");
        @SuppressWarnings("unused")
        Need pizza2 = new Need(1, "pizza2", 2, 2, "Pizza");

        assertTrue(pizza1.equals(pizza1));
    }

    @Test
    public void testNotEquals() {
        Need pizza = new Need(1, "Pizza", 15, 2, "Food");
        Need burger = new Need(2, "Burger", 10, 5, "Food");

        assertFalse(pizza.equals(burger));
    }


    @Test
    public void testSetter(){
        // Setup
        Helper helper = new Helper(99,"Galactic Agent","Hell",false,new ArrayList<Need>(), new ArrayList<Need>());
        // When the same id is passed in, our mock Need DAO will return the Need object

        String expected_password = "Jell";
        helper.setPassword(expected_password);

        Boolean expected_admin = false;

        String expected_username = "Jack";
        helper.setUserName(expected_username);

        assertEquals(expected_admin, helper.isAdmin());
        assertEquals(expected_password, helper.getPassword());
        assertEquals(expected_username, helper.getUsername());
        
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        String expected_string = String.format(Helper.STRING_FORMAT,name,"Hell",new ArrayList<Need>());
        Helper helper = new Helper(id, name,"Hell",false, new ArrayList<Need>(), new ArrayList<Need>());

        // Invoke
        String actual_string = helper.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }

}