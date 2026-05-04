package com.se1020.carparking.controller;

import com.se1020.carparking.model.User;
import com.se1020.carparking.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*
CREATE - POST
read - GET
UPDATE - PUT
DELETE - DELETE
*/

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // READ - Login page
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    // READ - Login submit
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session, Model model) {
        User user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("loggedUser", user);
            return "redirect:" + user.getDashboardPath();
        }
        model.addAttribute("error", "Invalid credentials");
        return "auth/login";
    }

    // CREATE - Register page
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    // CREATE - Register submit
    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String phone,
                           @RequestParam String licenseNumber) {
        userService.registerRegularUser(name, email, password, phone, licenseNumber);
        return "redirect:/login";
    }

    // READ - Admin views all users
    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/admin-user-list";
    }

    // READ - View own profile
    @GetMapping("/user/profile")
    public String viewProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("user", user);
        return "user/profile";
    }

    // UPDATE - Edit profile page
    @GetMapping("/user/profile/edit")
    public String editProfilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("user", user);
        return "user/edit-profile";
    }

    // UPDATE - Edit profile submit
    @PostMapping("/user/profile/update")
    public String updateProfile(@RequestParam String userId,
                                @RequestParam String name,
                                @RequestParam String phone,
                                HttpSession session) {
        User user = userService.getUserById(userId);
        user.setName(name);
        user.setPhone(phone);
        userService.updateUser(user);
        session.setAttribute("loggedUser", user);
        return "redirect:/user/profile";
    }

    // DELETE - Admin deletes user
    @PostMapping("/admin/users/delete/{userId}")
    public String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/users";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}