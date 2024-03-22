package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import com.ufund.api.ufundapi.persistence.HelperDAO;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("HelperControllerTest")
public class HelperControllerTest {
    private HelperController helperController;
    private HelperDAO mockHelperDAO;

    @BeforeEach
    public void setupHelperController() {
        mockHelperDAO = mock(HelperDAO.class);
        helperController = new HelperController(mockHelperDAO);
    }

    @SuppressWarnings("null")
    @Test
    public void testGetHelper() throws IOException {
        Helper testHelper = new Helper(0, "testHelperName", "pass123", false, null);
        when(mockHelperDAO.getHelper("testHelperName")).thenReturn(testHelper);
        ResponseEntity<Helper> response = helperController.getHelper("testHelperName");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("testHelperName", response.getBody().getUsername());
        assertEquals(null, response.getBody().getCart());
    }

    @Test
    public void testAddToBasket() throws IOException {
        Helper testHelper = new Helper(0, "testHelperName", "test123", false, new ArrayList<Need>());
        Need testNeed = new Need(1, "Need1", 10, 2, "Description");
        when(mockHelperDAO.getHelper("testHelperName")).thenReturn(testHelper);
        ResponseEntity<Helper> response = helperController.addToBasket("testHelperName", testNeed);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRemoveFromBasket() throws IOException {
        Helper testHelper = new Helper(0, "testHelperName", "test321", false, new ArrayList<Need>());
        Need testNeed = new Need(1, "Need1", 10, 2, "Description");
        when(mockHelperDAO.getHelper("testHelperName")).thenReturn(testHelper);
        ResponseEntity<Helper> response = helperController.addToBasket("testHelperName", testNeed);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ResponseEntity<Helper> response2 = helperController.removeFromBasket("testHelperName", testNeed.getId());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
    }



    @Test
    public void testGetHelpers() throws IOException {
        Helper testHelper = new Helper(0, "HELPER", "Hell",false, new ArrayList<>());
        Helper testHelperNull = null;

        ResponseEntity<Helper> found = new ResponseEntity<Helper>(testHelper,HttpStatus.OK);
        ResponseEntity<Helper> missing = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ResponseEntity<Helper> serverError = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        Helper[] helperArray = {testHelper};
        HelperDAO helperDAOMock = mock(HelperDAO.class);
        when(helperDAOMock.getHelper("HELPER")).thenReturn(testHelper);
        when(helperDAOMock.getHelper("FAKEHELPER")).thenReturn(testHelperNull);

        when(helperDAOMock.getHelpers()).thenReturn(helperArray);

        HelperController helperController = new HelperController(helperDAOMock);

        ResponseEntity<Helper> testResponse = helperController.getHelper("HELPER");
        ResponseEntity<Helper> testNullResponse = helperController.getHelper("FAKEHELPER");


        assertEquals(found, testResponse);
        assertEquals(missing, testNullResponse);

        ResponseEntity<Helper[]> foundArray = new ResponseEntity<Helper[]>(helperArray,HttpStatus.OK);

        ResponseEntity<Helper[]> testResponses = helperController.getHelpers();
        assertEquals(foundArray, testResponses);

        Helper[] nullHelperArray = new Helper[1];
        when(helperDAOMock.getHelpers()).thenReturn(nullHelperArray);


        when(helperDAOMock.getHelpers()).thenThrow(new IOException("Error"));
        ResponseEntity<Helper[]> testErrorResponses = helperController.getHelpers();
        assertEquals(serverError, testErrorResponses);
    }


    @Test
    public void testUpdateHelper() throws IOException {

        ResponseEntity<Helper> notFound = new ResponseEntity<Helper>(HttpStatus.NOT_FOUND);

        HelperDAO helperDAOMock = mock(HelperDAO.class);

        HelperController helperController= new HelperController(helperDAOMock);
        assertEquals(notFound, helperController.updateHelper(null));

    }
}