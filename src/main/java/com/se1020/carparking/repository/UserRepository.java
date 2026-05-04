package com.se1020.carparking.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.se1020.carparking.model.User;
import com.se1020.carparking.repository.support.JsonDataAccess;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private static final String CLASSPATH = "data/users.json";
    private static final String FILE_NAME = "users.json";

    public List<User> findAll() {
        List<User> fromClasspath = JsonDataAccess.readClasspathList(CLASSPATH, new TypeReference<List<User>>() {});
        List<User> fromExternal = JsonDataAccess.readExternalList(FILE_NAME, new TypeReference<List<User>>() {});
        if (fromExternal.isEmpty()) {
            return fromClasspath;
        }
        // Merge: seed users from classpath stay available; ~/.online-carparking overlay wins by userId
        // (fixes truncated external files after saves that dropped bundled accounts).
        Map<String, User> byId = new LinkedHashMap<>();
        for (User u : fromClasspath) {
            if (u.getUserId() != null) {
                byId.put(u.getUserId(), u);
            }
        }
        for (User u : fromExternal) {
            if (u.getUserId() != null) {
                byId.put(u.getUserId(), u);
            }
        }
        return new ArrayList<>(byId.values());
    }

    public void saveAll(List<User> users) {
        JsonDataAccess.writeList(FILE_NAME, users);
    }

    public User findById(String userId) {
        for (User user : findAll()) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public User findByEmail(String email) {
        if (email == null) {
            return null;
        }
        String normalized = email.trim();
        for (User user : findAll()) {
            if (user.getEmail() != null && user.getEmail().trim().equalsIgnoreCase(normalized)) {
                return user;
            }
        }
        return null;
    }

    public void save(User user) {
        List<User> users = findAll();
        users.add(user);
        saveAll(users);
    }

    public void update(User updatedUser) {
        List<User> users = findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(updatedUser.getUserId())) {
                users.set(i, updatedUser);
                break;
            }
        }
        saveAll(users);
    }

    public void delete(String userId) {
        List<User> users = findAll();
        users.removeIf(u -> u.getUserId().equals(userId));
        saveAll(users);
    }
}
