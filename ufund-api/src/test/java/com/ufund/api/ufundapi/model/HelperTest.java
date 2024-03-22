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
        Helper helper = new Helper(expected_second_item_cost, "helperName", "pass123", false, helperCart);

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

        Helper helper = new Helper(expected_cart_size_after_removal, "helperName", "test123", false, helperCart);

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
}
