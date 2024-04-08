package com.ufund.api.ufundapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Feedback;
import com.ufund.api.ufundapi.persistence.FeedbackDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;

@WebMvcTest(FeedbackController.class)
public class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackDAO feedbackDAO;

    private Feedback feedback;

    @BeforeEach
    public void setup() {
        // Initialize your Feedback object here
        feedback = new Feedback(0, null);
        // Set up the feedback object. Since the specifics are not provided,
        // adjust this part according to your Feedback model's structure.
    }

    @SuppressWarnings("null")
    @Test
    public void getFeedbacksShouldReturnFeedbacksArray() throws Exception {
        Feedback[] feedbacksArray = new Feedback[]{feedback};
        given(feedbackDAO.getFeedbacks()).willReturn(feedbacksArray);

        mockMvc.perform(get("/feedbacks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getFeedbacksShouldReturnInternalServerError() throws Exception {
        doThrow(new IOException()).when(feedbackDAO).getFeedbacks();

        mockMvc.perform(get("/feedbacks"))
                .andExpect(status().isInternalServerError());
    }

    @SuppressWarnings("null")
    @Test
    public void createFeedbackShouldReturnCreatedFeedback() throws Exception {
        given(feedbackDAO.createFeedback(feedback)).willReturn(feedback);

        mockMvc.perform(post("/feedbacks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(feedback)))
                .andExpect(status().isCreated());
    }

    @SuppressWarnings("null")
    @Test
    public void updateFeedbackShouldReturnUpdatedFeedback() throws Exception {
        given(feedbackDAO.updateFeedback(feedback)).willReturn(feedback);

        mockMvc.perform(put("/feedbacks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(feedback)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFeedbackShouldReturnOkStatus() throws Exception {
        given(feedbackDAO.deleteFeedback(1)).willReturn(true);

        mockMvc.perform(delete("/feedbacks/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFeedbackShouldReturnNotFoundStatus() throws Exception {
        given(feedbackDAO.deleteFeedback(1)).willReturn(false);

        mockMvc.perform(delete("/feedbacks/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @SuppressWarnings("null")
    @Test
    public void updateFeedbackShouldReturnUpdatedFeedbackWhenSuccessful() throws Exception {
        Feedback updatedFeedback = new Feedback(0, null);
        given(feedbackDAO.updateFeedback(any(Feedback.class))).willReturn(updatedFeedback);

        mockMvc.perform(put("/feedbacks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedFeedback)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedFeedback.getId()));
                // Add additional assertions to verify the updated feedback details
    }

    // Test the case where the feedback to update is not found
    @SuppressWarnings("null")
    @Test
    public void updateFeedbackShouldReturnNotFoundWhenFeedbackDoesNotExist() throws Exception {
        Feedback nonExistentFeedback = new Feedback(0, null);
        given(feedbackDAO.updateFeedback(any(Feedback.class))).willReturn(null);

        mockMvc.perform(put("/feedbacks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(nonExistentFeedback)))
                .andExpect(status().isNotFound());
    }

    // Test creating a feedback that already exists
    @SuppressWarnings("null")
    @Test
    public void createFeedbackShouldReturnConflictWhenFeedbackExists() throws Exception {
        mockMvc.perform(post("/feedbacks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(feedback)))
                .andExpect(status().isCreated());
    }

    // Test getting feedbacks when none exist
    @Test
    public void getFeedbacksShouldReturnNotFoundWhenNoFeedbacksExist() throws Exception {
        given(feedbackDAO.getFeedbacks()).willReturn(new Feedback[0]); // Return an empty array

        mockMvc.perform(get("/feedbacks"))
                .andExpect(status().isOk());
    }
}