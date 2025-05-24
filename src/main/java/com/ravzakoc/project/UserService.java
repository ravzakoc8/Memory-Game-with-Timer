package com.ravzakoc.project;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, String> users = new HashMap<>();

    public boolean register(String username, String password) {
        // Empty values are not accepted
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false; //Empty values are not accepted.
        }
        // User already registered
        if (users.containsKey(username)) {
            return false; //User already registered.
        }
        users.put(username, password);
        return true;
    }

    public boolean login(String username, String password) {
        // Return false if username does not exist
        if (!users.containsKey(username)) {
            return false;
        }
        return users.get(username).equals(password);
    }
}