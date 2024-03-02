package com.ufund.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.ufund.api.ufundapi.model.Need;

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