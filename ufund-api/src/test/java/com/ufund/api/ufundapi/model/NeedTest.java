package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Need class
 * 
 * @author Nam Huynh
 */
@Tag("Model-tier")
public class NeedTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Wi-Fire";


        // Invoke
        Need need = new Need(expected_id,expected_name,0,0,"Men");

        // Analyze
        assertEquals(expected_id,need.getId());
        assertEquals(expected_name,need.getName());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        Need need = new Need(id,name,0,0,"Men");

        String expected_name = "Galactic Agent";

        // Invoke
        need.setName(expected_name);

        // Analyze
        assertEquals(expected_name,need.getName());
    }


    @Test
    public void testCost() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        Need need = new Need(id,name,0,0,"Men");

        Integer expected_cost = 5000;

        // Invoke
        need.setCost(expected_cost);

        // Analyze
        assertEquals(expected_cost,need.getCost());
    }


    @Test
    public void testQuantity() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        Need need = new Need(id,name,0,0,"Men");

        Integer expected_quantity = 5000;

        // Invoke
        need.setQuantity(expected_quantity);

        // Analyze
        assertEquals(expected_quantity,need.getQuantity());
    }


    @Test
    public void testType() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        Need need = new Need(id,name,0,0,"Men");

        String expected_type = "Galactic Agent";

        // Invoke
        need.setType(expected_type);

        // Analyze
        assertEquals(expected_type,need.getType());
    }


    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        String expected_string = String.format(Need.STRING_FORMAT,id,name,0,0,"Men");
        Need need = new Need(id,name,0,0,"Men");

        // Invoke
        String actual_string = need.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}