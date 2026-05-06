package com.se1020.carparking.service;

import com.se1020.carparking.model.Feedback;
import com.se1020.carparking.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> getFeedbacksByUser(String userId) {
        return feedbackRepository.findByUserId(userId);
    }

    public Feedback getFeedbackById(String feedbackId) {
        return feedbackRepository.findById(feedbackId);
    }

    public void submitFeedback(String userId, String userName, String type,
                               String message, int rating) {
        String feedbackId = UUID.randomUUID().toString();
        Feedback feedback = new Feedback(feedbackId, userId, userName, type,
                message, rating, "PENDING", "", LocalDate.now().toString());
        feedbackRepository.save(feedback);
    }

    public void updateFeedback(String feedbackId, String type, String message, int rating) {
        Feedback feedback = feedbackRepository.findById(feedbackId);
        feedback.setType(type);
        feedback.setMessage(message);
        feedback.setRating(rating);
        feedbackRepository.update(feedback);
    }

    public void resolveFeedback(String feedbackId, String adminResponse) {
        Feedback feedback = feedbackRepository.findById(feedbackId);
        feedback.setStatus("RESOLVED");
        feedback.setAdminResponse(adminResponse);
        feedbackRepository.update(feedback);
    }

    public void deleteFeedback(String feedbackId) {
        feedbackRepository.delete(feedbackId);
    }
}