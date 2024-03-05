package com.ufund.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.Helper;

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
        int expected_cart = 1;
        String expected_password = "password123";
        Boolean expected_admin = false;

        // Invoke
        Helper helper = new Helper(expected_id,expected_name, expected_cart, expected_password, expected_admin);

        // Analyze
        assertEquals(expected_id,helper.getId());
        assertEquals(expected_name,helper.getName());
    }

    @Test
    public void testName() {
        // Setup
        int expected_id = 99;
        String expected_name = "Bob";
        int expected_cart = 1;
        String expected_password = "password123";
        Boolean expected_admin = false;
        Helper helper = new Helper(expected_id,expected_name, expected_cart, expected_password, expected_admin);

        // Invoke
        helper.setName(expected_name);

        // Analyze
        assertEquals(expected_name,helper.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int expected_id = 99;
        String expected_name = "Bob";
        int expected_cart = 1;
        String expected_password = "password123";
        Boolean expected_admin = false;
        Helper helper = new Helper(expected_id,expected_name, expected_cart, expected_password, expected_admin);
        String expected_string = String.format(Helper.STRING_FORMAT,expected_id,expected_name);

        // Invoke
        String actual_string = helper.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}