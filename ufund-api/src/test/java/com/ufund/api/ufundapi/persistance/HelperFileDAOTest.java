package com.ufund.api.ufundapi.persistance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Helper;
import com.ufund.api.ufundapi.persistence.HelperFileDAO;
import com.ufund.api.ufundapi.persistence.NeedDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class HelperFileDAOTest {
    private HelperFileDAO helperFileDAO;
    private ObjectMapper mockObjectMapper;
    private Helper[] storedHelpers;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void setup() throws IOException {
        mockObjectMapper = Mockito.mock(ObjectMapper.class);
        storedHelpers = new Helper[]{
            new Helper(1, "user1", "pass1", false, new ArrayList<>(), new ArrayList<>()),
            new Helper(2, "user2", "pass2", false, new ArrayList<>(), new ArrayList<>())
        };

        when(mockObjectMapper.readValue(any(File.class), any(Class.class)))
                .thenReturn(storedHelpers);

        NeedDAO mockNeedDao = Mockito.mock(NeedDAO.class);
        helperFileDAO = new HelperFileDAO("helpers.json", mockObjectMapper, mockNeedDao);
        helperFileDAO.load();
    }

    @Test
    public void loadMethod_ShouldCorrectlyLoadHelpers() {
        // Assuming the load method has been called in the setup.
        assertEquals(2, helperFileDAO.helpers.size());
    }

    @Test
    public void saveMethod_ShouldInvokeObjectMapperWriteValue() throws IOException {
        // Perform an operation that would trigger a save
        helperFileDAO.createHelper(new Helper(0, "newUser", "newPass", false, new ArrayList<>(), new ArrayList<>()));
        Mockito.verify(mockObjectMapper).writeValue(any(File.class), any());
    }

    @Test
    public void getHelpersArrayMethod_WithoutFilter_ShouldReturnAllHelpers() {
        Helper[] helperArray = helperFileDAO.getHelpersArray();
        assertArrayEquals(storedHelpers, helperArray);
    }

    @Test
    public void getHelpersArrayMethod_WithFilter_ShouldReturnFilteredHelpers() {
        // Add a helper with username "admin" to trigger the admin logic.
        Helper[] allHelpers = helperFileDAO.getHelpersArray();
        assertEquals(2, allHelpers.length); // No filter, should return all helpers.
        
        Helper[] filteredHelpers = helperFileDAO.getHelpersArray("user1");
        assertEquals(1, filteredHelpers.length); // Filter by username, should return 1 helper.
        assertEquals("user1", filteredHelpers[0].getUsername());
    }

    @Test
    public void getHelpersMethod_ShouldReturnAllHelpers() {
        Helper[] helpers = helperFileDAO.getHelpers();
        assertArrayEquals(storedHelpers, helpers);
    }

    @Test
    public void getHelperMethod_ShouldReturnSpecificHelper() {
        Helper foundHelper = helperFileDAO.getHelper("user1");
        assertNotNull(foundHelper);
        assertEquals("user1", foundHelper.getUsername());
    }

    @Test
    public void loginHelperMethod_SuccessfulLogin_ShouldReturnHelper() {
        Helper foundHelper = helperFileDAO.loginHelper("user1", "pass1");
        assertNotNull(foundHelper);
        assertEquals("user1", foundHelper.getUsername());
    }

    @Test
    public void loginHelperMethod_WrongPassword_ShouldReturnNull() {
        Helper foundHelper = helperFileDAO.loginHelper("user1", "wrongpass");
        assertNull(foundHelper);
    }

    @Test
    public void createHelperMethod_ShouldCreateHelper() throws IOException {
        Helper newHelper = new Helper(0, "newUser", "newPass", false, new ArrayList<>(), new ArrayList<>());
        Helper createdHelper = helperFileDAO.createHelper(newHelper);
        assertNotNull(createdHelper);
        assertEquals("newUser", createdHelper.getUsername());
    }

    @Test
    public void updateHelperMethod_ShouldUpdateHelper() throws IOException {
        Helper toUpdate = new Helper(1, "user1", "newpass1", false, new ArrayList<>(), new ArrayList<>());
        Helper updatedHelper = helperFileDAO.updateHelper(toUpdate);
        assertNotNull(updatedHelper);
        assertEquals("newpass1", updatedHelper.getPassword());
    }

    @Test
    public void deleteHelperMethod_ShouldDeleteHelper() throws IOException {
        assertTrue(helperFileDAO.deleteHelper("user1"));
        assertNull(helperFileDAO.getHelper("user1"));
    }

}