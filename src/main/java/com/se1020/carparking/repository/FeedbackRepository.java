package com.se1020.carparking.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.se1020.carparking.model.Feedback;
import com.se1020.carparking.repository.support.JsonDataAccess;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FeedbackRepository {

    private static final String CLASSPATH = "data/feedbacks.json";
    private static final String FILE_NAME = "feedbacks.json";

    public List<Feedback> findAll() {
        return JsonDataAccess.readList(CLASSPATH, FILE_NAME, new TypeReference<List<Feedback>>() {});
    }

    public void saveAll(List<Feedback> feedbacks) {
        JsonDataAccess.writeList(FILE_NAME, feedbacks);
    }

    public Feedback findById(String feedbackId) {
        for (Feedback f : findAll()) {
            if (f.getFeedbackId().equals(feedbackId)) {
                return f;
            }
        }
        return null;
    }

    public List<Feedback> findByUserId(String userId) {
        List<Feedback> result = new ArrayList<>();
        for (Feedback f : findAll()) {
            if (f.getUserId().equals(userId)) {
                result.add(f);
            }
        }
        return result;
    }

    public void save(Feedback feedback) {
        List<Feedback> feedbacks = findAll();
        feedbacks.add(feedback);
        saveAll(feedbacks);
    }

    public void update(Feedback updatedFeedback) {
        List<Feedback> feedbacks = findAll();
        for (int i = 0; i < feedbacks.size(); i++) {
            if (feedbacks.get(i).getFeedbackId().equals(updatedFeedback.getFeedbackId())) {
                feedbacks.set(i, updatedFeedback);
                break;
            }
        }
        saveAll(feedbacks);
    }

    public void delete(String feedbackId) {
        List<Feedback> feedbacks = findAll();
        feedbacks.removeIf(f -> f.getFeedbackId().equals(feedbackId));
        saveAll(feedbacks);
    }
}
