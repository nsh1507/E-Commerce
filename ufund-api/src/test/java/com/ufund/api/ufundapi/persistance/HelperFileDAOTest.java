package com.ufund.api.ufundapi.persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.persistence.HelperFileDAO;
import com.ufund.api.ufundapi.persistence.NeedDAO;
import com.ufund.api.ufundapi.persistence.NeedFileDAO;

/**
 * The unit test suite for the HelperFileDAO class
 * 
 * @author Saavan Tandon
 */
@Tag("Model-tier")
public class HelperFileDAOTest {
    @Test
    public void testCtor() throws IOException {
        // Setup
        String expected_file = "file.exe";
        ObjectMapper expected_objectMapper = new ObjectMapper();
        NeedDAO expected_needDao = (NeedDAO) new NeedFileDAO(expected_file, expected_objectMapper);
        // Invoke
        HelperFileDAO helperDAO = new HelperFileDAO(expected_file, expected_objectMapper, expected_needDao);

        // Analyze
        assertEquals(expected_file,helperDAO.filename);
        assertEquals(expected_objectMapper,helperDAO.objectMapper);
        assertEquals(expected_needDao, helperDAO.needDao);
    }

    @Test
    public void testGetHelpers() throws IOException {
        // Setup  
        String expected_file = "file.exe";
        ObjectMapper expected_objectMapper = new ObjectMapper();
        NeedDAO expected_needDao = (NeedDAO) new NeedFileDAO(expected_file, expected_objectMapper);
        HelperFileDAO helperDAO = new HelperFileDAO(expected_file, expected_objectMapper, expected_needDao);
        
        
        // Invoke
        

        // Analyze
        assertEquals(null,helperDAO.getHelpersArray());
    }

    @Test
    public void testLoginHelper() throws IOException {
        // Setup  
        String expected_file = "file.exe";
        ObjectMapper expected_objectMapper = new ObjectMapper();
        NeedDAO expected_needDao = (NeedDAO) new NeedFileDAO(expected_file, expected_objectMapper);
        HelperFileDAO helperDAO = new HelperFileDAO(expected_file, expected_objectMapper, expected_needDao);
        String username = "username";
        String password = "password";
        
        
        // Invoke
        

        // Analyze
        assertEquals(null,helperDAO.loginHelper(username, password));
    }
    
}