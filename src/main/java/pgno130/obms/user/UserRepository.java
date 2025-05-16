package pgno130.obms.user;

import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    private static final String USERS_FILE = "users.txt";

    public List<User> findAll() {
        try {
            if (!Files.exists(Paths.get(USERS_FILE))) {
                return new ArrayList<>();
            }

            return Files.lines(Paths.get(USERS_FILE))
                    .map(this::parseUser)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read users file", e);
        }
    }

    public User findByEmail(String email) {
        return findAll().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public User save(User user) {
        List<User> users = findAll();

        if (user.getId() == null) {
            long maxId = users.stream().mapToLong(x -> Long.parseLong(x.getId())).max().orElse(0);
            user.setId(String.valueOf(maxId + 1));
            users.add(user);
        } else {
            users = users.stream()
                    .map(u -> u.getId().equals(user.getId()) ? user : u)
                    .collect(Collectors.toList());
        }

        writeToFile(users);
        return user;
    }

    public void delete(long id) {
        List<User> users = findAll().stream()
                .filter(user -> !user.getId().equals(id))
                .collect(Collectors.toList());
        writeToFile(users);
    }

    private User parseUser(String line) {
        String[] parts = line.split("\\|");
        User user = new User();
        user.setId(String.valueOf(Long.parseLong(parts[0])));
        user.setName(parts[1]);
        user.setEmail(parts[2]);
        user.setPassword(parts[3]);
        user.setAddress(parts[4]);
        user.setPhone(parts[5]);
        user.setCreatedAt(LocalDateTime.parse(parts[6]));
        user.setUseOrAdmin(parts[7]);
        user.setRememberMe(parts.length > 8 ? parts[8] : null);
        return user;
    }

    public void writeToFile(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.println(String.join("|",
                        user.getId().toString(),
                        user.getName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getAddress(),
                        user.getPhone(),
                        user.getCreatedAt().toString(),
                        user.getUseOrAdmin(),
                        user.getRememberMe() != null ? user.getRememberMe() : ""
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to users file", e);
        }
    }

    public void delete(String id) {
        List<User> users = findAll().stream()
                .filter(user -> !user.getId().equals(id))
                .collect(Collectors.toList());
        writeToFile(users);
    }
}