package com.se1020.carparking.controller;

import com.se1020.carparking.model.User;
import com.se1020.carparking.service.FeedbackService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // READ - User views own feedback
    @GetMapping("/user/feedbacks")
    public String myFeedbacks(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("feedbacks", feedbackService.getFeedbacksByUser(user.getUserId()));
        return "feedback/my-feedbacks";
    }

    // READ - Admin views all feedbacks
    @GetMapping("/admin/feedbacks")
    public String allFeedbacks(Model model) {
        model.addAttribute("feedbacks", feedbackService.getAllFeedbacks());
        return "feedback/admin-feedback-list";
    }

    // CREATE - Submit feedback page
    @GetMapping("/user/feedbacks/submit")
    public String submitPage() {
        return "feedback/submit-feedback";
    }

    // CREATE - Submit feedback
    @PostMapping("/user/feedbacks/submit")
    public String submitFeedback(@RequestParam String type,
                                 @RequestParam String message,
                                 @RequestParam int rating,
                                 HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        feedbackService.submitFeedback(user.getUserId(), user.getName(), type, message, rating);
        return "redirect:/user/feedbacks";
    }

    // UPDATE - User edits their feedback
    @GetMapping("/user/feedbacks/edit/{feedbackId}")
    public String editFeedbackPage(@PathVariable String feedbackId, Model model) {
        model.addAttribute("feedback", feedbackService.getFeedbackById(feedbackId));
        return "feedback/edit-feedback";
    }

    @PostMapping("/user/feedbacks/update")
    public String updateFeedback(@RequestParam String feedbackId,
                                 @RequestParam String type,
                                 @RequestParam String message,
                                 @RequestParam int rating) {
        feedbackService.updateFeedback(feedbackId, type, message, rating);
        return "redirect:/user/feedbacks";
    }

    // UPDATE - Admin resolves feedback
    @GetMapping("/admin/feedbacks/resolve/{feedbackId}")
    public String resolvePage(@PathVariable String feedbackId, Model model) {
        model.addAttribute("feedback", feedbackService.getFeedbackById(feedbackId));
        return "feedback/resolve-feedback";
    }

    @PostMapping("/admin/feedbacks/resolve")
    public String resolveFeedback(@RequestParam String feedbackId,
                                  @RequestParam String adminResponse) {
        feedbackService.resolveFeedback(feedbackId, adminResponse);
        return "redirect:/admin/feedbacks";
    }

    // DELETE - Admin deletes feedback
    @PostMapping("/admin/feedbacks/delete/{feedbackId}")
    public String deleteFeedback(@PathVariable String feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return "redirect:/admin/feedbacks";
    }
}