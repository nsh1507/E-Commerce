package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.model.Helper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.HelperDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
public class HelperControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HelperDAO helperDao;

    @InjectMocks
    private HelperController helperController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(helperController).build();
    }

    @SuppressWarnings("null")
    @Test
    public void addToBasket_ShouldAddNeedToBasket() throws Exception {
        // Set up a helper and need
        Helper existingHelper = new Helper(1, "existingUser", "pass", false, new ArrayList<>(), new ArrayList<>());
        // Mock the helperDao interactions
        when(helperDao.getHelper(anyString())).thenReturn(existingHelper);
        when(helperDao.updateHelper(any(Helper.class))).thenReturn(existingHelper);

        mockMvc.perform(put("/helpers/basket/existingUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"name\": \"Need 1\", \"amount\": 100, \"quantity\": 1, \"description\": \"Description\"}"))
                .andExpect(status().isOk());

        // Verify addToCart was called on the Helper object
        verify(helperDao, times(1)).updateHelper(any(Helper.class));
    }

    @Test
    public void getHelper_WhenIOException_ShouldReturnInternalServerError() throws Exception {
        when(helperDao.getHelper(anyString())).thenThrow(new IOException());

        mockMvc.perform(get("/helpers/testUser"))
                .andExpect(status().isInternalServerError());
    }

    public void addToHistory_WhenHelperNotFound_ShouldReturnNotFound() throws Exception {
        when(helperDao.getHelper(anyString())).thenReturn(null);

        mockMvc.perform(put("/helpers/history/nonExistingUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"name\": \"Need 1\", \"amount\": 100, \"quantity\": 1, \"description\": \"Description\"}"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void removeFromBasket_ShouldRemoveNeedFromBasket() throws Exception {
        // Set up a helper and add a need to the basket
        Helper existingHelper = new Helper(1, "existingUser", "pass", false, new ArrayList<>(), new ArrayList<>());
        Need needInBasket = new Need(1, "Need 1", 100, 1, "Description");
        existingHelper.addToCart(needInBasket);

        // Mock the helperDao interactions
        when(helperDao.getHelper(anyString())).thenReturn(existingHelper);
        when(helperDao.updateHelper(any(Helper.class))).thenReturn(existingHelper);

        mockMvc.perform(delete("/helpers/basket/existingUser/1"))
                .andExpect(status().isOk());

        // Verify removeFromCart was called on the Helper object
        verify(helperDao, times(1)).updateHelper(any(Helper.class));
    }

    @SuppressWarnings("null")
    @Test
    public void addToHistory_ShouldAddNeedToHistory() throws Exception {
        // Set up a helper
        Helper existingHelper = new Helper(1, "existingUser", "pass", false, new ArrayList<>(), new ArrayList<>());
        // Mock the helperDao interactions
        when(helperDao.getHelper(anyString())).thenReturn(existingHelper);
        when(helperDao.updateHelper(any(Helper.class))).thenReturn(existingHelper);

        mockMvc.perform(put("/helpers/history/existingUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"name\": \"Need 1\", \"amount\": 100, \"quantity\": 1, \"description\": \"Description\"}"))
                .andExpect(status().isOk());

        // Verify addToHistory was called on the Helper object
        verify(helperDao, times(1)).updateHelper(any(Helper.class));
    }

    @SuppressWarnings("null")
    @Test
    public void getHelper_ShouldReturnHelper() throws Exception {
        Helper mockHelper = new Helper(1, "testUser", "testPass", false, new ArrayList<>(), new ArrayList<>());
    when(helperDao.getHelper(anyString())).thenReturn(mockHelper);

    mockMvc.perform(get("/helpers/testUser"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("testUser"));
        when(helperDao.getHelper(any(String.class))).thenReturn(new Helper(1, "testUser", "testPass", false, null, null));
        mockMvc.perform(get("/helpers/testUser"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @SuppressWarnings("null")
    @Test
    public void getHelpers_ShouldReturnAllHelpers() throws Exception {
        Helper[] helpers = {
                new Helper(1, "user1", "pass1", false, null, null),
                new Helper(2, "user2", "pass2", false, null, null)
        };
        when(helperDao.getHelpers()).thenReturn(helpers);
        mockMvc.perform(get("/helpers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].username").value("user2"));
    }

    @SuppressWarnings("null")
    @Test
    public void loginHelper_ShouldAuthorizeAndReturnHelper() throws Exception {
        when(helperDao.loginHelper(any(String.class), any(String.class))).thenReturn(new Helper(1, "user1", "pass1", false, null, null));
        mockMvc.perform(get("/helpers/user1?password=pass1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    public void loginHelper_WhenUnauthorized_ShouldReturnUnauthorized() throws Exception {
        when(helperDao.loginHelper(eq("user1"), eq("wrongPassword"))).thenReturn(null);

        mockMvc.perform(get("/helpers/user1?password=wrongPassword"))
                .andExpect(status().isUnauthorized());
    }

    @SuppressWarnings("null")
    @Test
    public void createHelper_ShouldCreateHelper() throws Exception {
        when(helperDao.createHelper(any(Helper.class))).thenReturn(new Helper(1, "newUser", "newPass", false, null, null));
        when(helperDao.getHelper(any(String.class))).thenReturn(null);

        mockMvc.perform(post("/helpers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"newUser\", \"password\": \"newPass\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newUser"));
    }

    @Test
    public void createHelper_WhenHelperExists_ShouldReturnConflict() throws Exception {
        Helper existingHelper = new Helper(1, "existingUser", "pass", false, new ArrayList<>(), new ArrayList<>());
        when(helperDao.getHelper(eq("existingUser"))).thenReturn(existingHelper);

        mockMvc.perform(post("/helpers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"existingUser\", \"password\": \"pass\"}"))
                .andExpect(status().isConflict());
    }

    @SuppressWarnings("null")
    @Test
    public void updateHelper_ShouldUpdateHelper() throws Exception {
        when(helperDao.updateHelper(any(Helper.class))).thenReturn(new Helper(1, "existingUser", "newPass", false, null, null));
        mockMvc.perform(put("/helpers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"existingUser\", \"password\": \"newPass\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("existingUser"));
    }

    @Test
    public void deleteHelper_ShouldDeleteHelper() throws Exception {
        when(helperDao.deleteHelper(any(String.class))).thenReturn(true);
        mockMvc.perform(delete("/helpers/testUser"))
                .andExpect(status().isOk());
    }

    public void getHelper_WhenHelperIsNull_ShouldReturnNotFound() throws Exception {
        // Mock the helperDao interactions to return null
        when(helperDao.getHelper(anyString())).thenReturn(null);
    
        mockMvc.perform(get("/helpers/testUser"))
                .andExpect(status().isNotFound());
    }
}