package com.se1020.carparking.service;

import com.se1020.carparking.model.AdminUser;
import com.se1020.carparking.model.RegularUser;
import com.se1020.carparking.model.User;
import com.se1020.carparking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public User login(String email, String password) {
        if (email == null || password == null) {
            return null;
        }
        String e = email.trim();
        String p = password.trim();
        User user = userRepository.findByEmail(e);
        if (user != null && user.getPassword() != null && user.getPassword().trim().equals(p)) {
            return user;
        }
        return null;
    }

    public void registerRegularUser(String name, String email, String password, String phone, String licenseNumber) {
        String userId = UUID.randomUUID().toString();
        RegularUser user = new RegularUser(
                userId,
                name != null ? name.trim() : null,
                email != null ? email.trim() : null,
                password != null ? password.trim() : null,
                phone != null ? phone.trim() : null,
                licenseNumber != null ? licenseNumber.trim() : null
        );
        userRepository.save(user);
    }

    public void registerAdminUser(String name, String email, String password, String phone, String adminCode) {
        String userId = UUID.randomUUID().toString();
        AdminUser user = new AdminUser(
                userId,
                name != null ? name.trim() : null,
                email != null ? email.trim() : null,
                password != null ? password.trim() : null,
                phone != null ? phone.trim() : null,
                adminCode != null ? adminCode.trim() : null
        );
        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public void deleteUser(String userId) {
        userRepository.delete(userId);
    }
}