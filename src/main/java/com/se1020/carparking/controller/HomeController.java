package com.se1020.carparking.controller;

import com.se1020.carparking.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user != null) {
            return "redirect:" + user.getDashboardPath();
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        return "dashboard/admin-dashboard";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !"USER".equals(user.getRole())) {
            return "redirect:/login";
        }
        return "dashboard/user-dashboard";
    }
}
