package com.ufund.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Helper;
import com.ufund.api.ufundapi.model.Need;

/**
 * The unit test suite for the Helper class
 * 
 * @author Saavan Tandon
 */
@Tag("Model-tier")
public class HelperTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Bob";
        String expected_password = "password123";
        Boolean expected_admin = false;

        Need cookies = new Need(1, "cookies", 5, 1, "solid food");
        Need cake = new Need(2, "cookies", 10, 1, "solid food");
        ArrayList<Need> expected_cart = new ArrayList<Need>();
        expected_cart.add(cookies);
        expected_cart.add(cake);

        // Invoke
        Helper helper = new Helper(expected_id,expected_name, expected_password, expected_admin, expected_cart);

        // Analyze
        assertEquals(expected_id,helper.getId());
        assertEquals(expected_name,helper.getUsername());
    }

    @Test
    public void testName() {
        // Setup
        int expected_id = 99;
        String expected_name = "Bob";
        String expected_password = "password123";
        Boolean expected_admin = false;

        Need cookies = new Need(1, "cookies", 5, 1, "solid food");
        Need cake = new Need(2, "cookies", 10, 1, "solid food");
        ArrayList<Need> expected_cart = new ArrayList<Need>();
        expected_cart.add(cookies);
        expected_cart.add(cake);

        Helper helper = new Helper(expected_id,expected_name, expected_password, expected_admin, expected_cart);
        
        // Invoke
        helper.setUserName(expected_name);

        // Analyze
        assertEquals(expected_name,helper.getUsername());
    }

    @Test
    public void testPassword() {
        // Setup
        int expected_id = 99;
        String expected_name = "Bob";
        String expected_password = "password123";
        Boolean expected_admin = false;

        Need cookies = new Need(1, "cookies", 5, 1, "solid food");
        Need cake = new Need(2, "cookies", 10, 1, "solid food");
        ArrayList<Need> expected_cart = new ArrayList<Need>();
        expected_cart.add(cookies);
        expected_cart.add(cake);

        Helper helper = new Helper(expected_id,expected_name, expected_password, expected_admin, expected_cart);
        
        // Invoke
        helper.setUserName(expected_password);

        // Analyze
        assertEquals(expected_password,helper.getPassword());
    }

    @Test
    public void testCart() {
        // Setup
        int expected_id = 99;
        String expected_name = "Bob";
        String expected_password = "password123";
        Boolean expected_admin = false;

        Need cookies = new Need(1, "cookies", 5, 1, "solid food");
        Need cake = new Need(2, "cookies", 10, 1, "solid food");
        ArrayList<Need> expected_cart = new ArrayList<Need>();
        expected_cart.add(cookies);
        expected_cart.add(cake);

        Helper helper = new Helper(expected_id,expected_name, expected_password, expected_admin, expected_cart);
        
        // Invoke
        

        // Analyze
        assertEquals(expected_cart,helper.getCart());
    }

    @Test
    public void testAdmin() {
        // Setup
        int expected_id = 99;
        String expected_name = "Bob";
        String expected_password = "password123";
        Boolean expected_admin = false;

        Need cookies = new Need(1, "cookies", 5, 1, "solid food");
        Need cake = new Need(2, "cookies", 10, 1, "solid food");
        ArrayList<Need> expected_cart = new ArrayList<Need>();
        expected_cart.add(cookies);
        expected_cart.add(cake);

        Helper helper = new Helper(expected_id,expected_name, expected_password, expected_admin, expected_cart);
        
        // Invoke
        

        // Analyze
        assertEquals(expected_admin,helper.isAdmin());
    }

    @Test
    public void testToString() {
        // Setup
        int expected_id = 99;
        String expected_name = "Bob";
        String expected_password = "password123";
        Boolean expected_admin = false;

        Need cookies = new Need(1, "cookies", 5, 1, "solid food");
        Need cake = new Need(2, "cookies", 10, 1, "solid food");
        ArrayList<Need> expected_cart = new ArrayList<Need>();
        expected_cart.add(cookies);
        expected_cart.add(cake);

        Helper helper = new Helper(expected_id,expected_name, expected_password, expected_admin, expected_cart);
        String expected_string = String.format(Helper.STRING_FORMAT,expected_id,expected_name);

        // Invoke
        String actual_string = helper.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}