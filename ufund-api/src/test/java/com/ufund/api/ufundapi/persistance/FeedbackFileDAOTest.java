package com.ufund.api.ufundapi.persistance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Feedback;
import com.ufund.api.ufundapi.persistence.FeedbackFileDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FeedbackFileDAOTest {
    private FeedbackFileDAO feedbackFileDAO;
    private ObjectMapper mockObjectMapper;
    private Feedback[] storedFeedbacks;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void setup() throws IOException {
        mockObjectMapper = Mockito.mock(ObjectMapper.class);
        storedFeedbacks = new Feedback[]{
                new Feedback(1, "First feedback"),
                new Feedback(2, "Second feedback"),
        };

        // Prepare the ObjectMapper mock to simulate the loading and saving of feedbacks.
        when(mockObjectMapper.readValue(any(File.class), any(Class.class)))
                .thenReturn(storedFeedbacks);
        doNothing().when(mockObjectMapper).writeValue(any(File.class), any());

        feedbackFileDAO = new FeedbackFileDAO("feedbacks.json", mockObjectMapper);
        // Since load is public, we invoke it directly in the test.
        feedbackFileDAO.load();
    }

    @Test
    public void whenLoad_thenReturnAllFeedbacks() {
        assertEquals(storedFeedbacks.length, feedbackFileDAO.feedbacks.size());
    }

    @Test
    public void whenSave_thenFeedbacksPersisted() throws IOException {
        feedbackFileDAO.save();
        verify(mockObjectMapper).writeValue(any(File.class), eq(storedFeedbacks));
    }

    @Test
    public void whenGetFeedbacksArray_thenCorrectArrayReturned() {
        Feedback[] feedbackArray = feedbackFileDAO.getFeedbacks();
        assertArrayEquals(storedFeedbacks, feedbackArray);
    }

    @Test
    public void whenGetFeedbacks_thenReturnAllFeedbacks() {
        Feedback[] feedbacks = feedbackFileDAO.getFeedbacks();
        assertArrayEquals(storedFeedbacks, feedbacks);
    }

    @Test
    public void whenCreateFeedback_thenFeedbackIsAdded() throws IOException {
        Feedback newFeedback = new Feedback(0, "New feedback");
        Feedback createdFeedback = feedbackFileDAO.createFeedback(newFeedback);
        assertNotNull(createdFeedback);
        assertEquals(3, feedbackFileDAO.getFeedbacks().length);
        assertTrue(feedbackFileDAO.feedbacks.containsValue(createdFeedback));
    }

    @Test
    public void whenUpdateFeedback_thenFeedbackIsUpdated() throws IOException {
        Feedback existingFeedback = storedFeedbacks[0];
        Feedback updatedFeedback = new Feedback(existingFeedback.getId(), "Updated feedback");
        Feedback result = feedbackFileDAO.updateFeedback(updatedFeedback);

        assertNotNull(result);
        assertEquals("Updated feedback", result.getFeedback());
        assertEquals(updatedFeedback.getFeedback(), feedbackFileDAO.feedbacks.get(existingFeedback.getId()).getFeedback());
    }

    @Test
    public void whenDeleteFeedback_thenFeedbackIsDeleted() throws IOException {
        int feedbackIdToDelete = storedFeedbacks[0].getId();
        assertTrue(feedbackFileDAO.deleteFeedback(feedbackIdToDelete));
        assertFalse(feedbackFileDAO.feedbacks.containsKey(feedbackIdToDelete));
    }

    @Test
    public void whenDeleteNonExistingFeedback_thenFalseReturned() throws IOException {
        int nonExistingFeedbackId = 999; // Assuming this ID does not exist
        assertFalse(feedbackFileDAO.deleteFeedback(nonExistingFeedbackId));
    }

    @Test
    public void whenUpdateNonExistingFeedback_thenNullReturned() throws IOException {
        Feedback nonExistingFeedback = new Feedback(999, "Non-existing feedback");
        assertNull(feedbackFileDAO.updateFeedback(nonExistingFeedback));
    }
    
}