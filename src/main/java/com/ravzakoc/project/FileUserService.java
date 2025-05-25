package com.ravzakoc.project;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileUserService {
    private static final String FILE_NAME = "users.txt";
    private Map<String, String> users;

    public FileUserService() {
        users = new HashMap<>();
        loadUsersFromFile();
    }

    private void loadUsersFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
    }

    private void saveUserToFile(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + ":" + password);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to users file: " + e.getMessage());
        }
    }

    public boolean register(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, password);
        saveUserToFile(username, password);
        return true;
    }

    public boolean login(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}