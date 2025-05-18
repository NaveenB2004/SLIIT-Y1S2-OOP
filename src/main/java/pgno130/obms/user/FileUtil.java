package pgno130.obms.user;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<User> readUsers(String filePath) throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return users;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 8) {
                    User user = new User();
                    user.setName(parts[1]);
                    user.setEmail(parts[2]);
                    user.setPassword(parts[3]);
                    user.setAddress(parts[4]);
                    user.setPhone(parts[5]);
                    user.setRole(parts[6]);
                    user.setCreatedAt(LocalDateTime.parse(parts[7]));
                    user.setAvatarUrl(parts.length > 8 ? parts[8] : null);
                    users.add(user);
                }
            }
        }
        return users;
    }

    public static void writeUsers(String filePath, List<User> users) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (User user : users) {
                writer.write(String.format("%d|%s|%s|%s|%s|%s|%s|%s|%s\n",
                        user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                        user.getAddress() != null ? user.getAddress() : "",
                        user.getPhone() != null ? user.getPhone() : "",
                        user.getRole(), user.getCreatedAt(),
                        user.getAvatarUrl() != null ? user.getAvatarUrl() : ""));
            }
        }
    }

    public static void writeUser(String filePath, User user) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        // Use FileWriter with 'true' to append instead of overwrite
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(String.format("%d|%s|%s|%s|%s|%s|%s|%s|%s\n",
                    user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                    user.getAddress() != null ? user.getAddress() : "",
                    user.getPhone() != null ? user.getPhone() : "",
                    user.getRole(), user.getCreatedAt(),
                    user.getAvatarUrl() != null ? user.getAvatarUrl() : ""));
        }
    }

    public static void updateUserByEmail(String filePath, User updatedUser) throws IOException {
        List<User> users = readUsers(filePath);

        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getEmail().equalsIgnoreCase(updatedUser.getEmail())) {
                // Update fields (except email if you want to keep it immutable)
                user.setName(updatedUser.getName());
                user.setPassword(updatedUser.getPassword());
                user.setAddress(updatedUser.getAddress());
                user.setPhone(updatedUser.getPhone());
                user.setRole(updatedUser.getRole());
                user.setCreatedAt(updatedUser.getCreatedAt());
                user.setAvatarUrl(updatedUser.getAvatarUrl());

                users.set(i, user);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IOException("User with email " + updatedUser.getEmail() + " not found.");
        }

        // Write all users back to the file
        writeUsers(filePath, users);
    }


    public static List<ResetToken> readTokens(String filePath) throws IOException {
        List<ResetToken> tokens = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return tokens;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    ResetToken token = new ResetToken();
                    token.setToken(parts[0]);
                    token.setEmail(parts[1]);
                    token.setExpiryDate(LocalDateTime.parse(parts[2]));
                    tokens.add(token);
                }
            }
        }
        return tokens;
    }

    public static void writeTokens(String filePath, List<ResetToken> tokens) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (ResetToken token : tokens) {
                writer.write(String.format("%s|%s|%s\n",
                        token.getToken(), token.getEmail(), token.getExpiryDate()));
            }
        }
    }
}
