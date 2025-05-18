package pgno130.obms.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EmailService emailService;
    private final String userFilePath;
    private final String tokenFilePath;

    public UserService(EmailService emailService,
                       @Value("${app.data.users}") String userFilePath,
                       @Value("${app.data.reset-tokens}") String tokenFilePath) {
        this.emailService = emailService;
        this.userFilePath = userFilePath;
        this.tokenFilePath = tokenFilePath;
    }


    public User registerUser(User user) throws IOException {
        FileUtil.writeUser(userFilePath,user);
        return user;
    }

    public Optional<User> loginUser(String email, String password) throws IOException {
        List<User> users = FileUtil.readUsers(userFilePath);
        // Proceed with login check
        return users.stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst();
    }


    public Optional<User> findUserByEmail(String email) throws IOException {
        List<User> users = FileUtil.readUsers(userFilePath);
        return users.stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }

    public void updateUserByEmail(User updatedUser) throws IOException {

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(updatedUser.getPassword());
            updatedUser.setRole("USER");
        } else {
            Optional<User> existingUserOpt = findUserByEmail(updatedUser.getEmail());
            if (existingUserOpt.isPresent()) {
                updatedUser.setPassword(existingUserOpt.get().getPassword());
                updatedUser.setRole(existingUserOpt.get().getRole());
            } else {
                throw new UserException("User not found for email: " + updatedUser.getEmail());
            }
        }
        if (updatedUser.getCreatedAt() == null) {
            Optional<User> existingUserOpt = findUserByEmail(updatedUser.getEmail());
            existingUserOpt.ifPresent(user -> updatedUser.setCreatedAt(user.getCreatedAt()));
        }
        FileUtil.updateUserByEmail(userFilePath, updatedUser);
    }

        public void updateUser(User updatedUser, byte[] avatarData) throws IOException {
        List<User> users = FileUtil.readUsers(userFilePath);
        User existingUser = users.stream()
                .filter(u -> u.getId().equals(updatedUser.getId()))
                .findFirst()
                .orElseThrow(() -> new UserException("User not found"));
        existingUser.setName(updatedUser.getName());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setPhone(updatedUser.getPhone());
        if (avatarData != null && avatarData.length > 0) {
            String avatarPath = saveAvatar(updatedUser.getId(), avatarData);
            existingUser.setAvatarUrl(avatarPath);
        }
        FileUtil.writeUsers(userFilePath, users);
    }

    public void changePassword(Long userId, String currentPassword, String newPassword, String confirmPassword) throws IOException {
        if (!newPassword.equals(confirmPassword)) {
            throw new UserException("New passwords do not match");
        }
        List<User> users = FileUtil.readUsers(userFilePath);
        User user = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new UserException("User not found"));
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new UserException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        FileUtil.writeUsers(userFilePath, users);
    }

    public String generatePasswordResetToken(String email) throws IOException, MessagingException, jakarta.mail.MessagingException {
        Optional<User> user = findUserByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("Email not found");
        }
        String token = UUID.randomUUID().toString();
        ResetToken resetToken = new ResetToken();
        resetToken.setToken(token);
        resetToken.setEmail(email);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        List<ResetToken> tokens = FileUtil.readTokens(tokenFilePath);
        tokens.removeIf(t -> t.getEmail().equals(email));
        tokens.add(resetToken);
        FileUtil.writeTokens(tokenFilePath, tokens);
        emailService.sendResetEmail(email, token);
        return token;
    }

    public Optional<ResetToken> validateResetToken(String token) throws IOException {
        List<ResetToken> tokens = FileUtil.readTokens(tokenFilePath);
        return tokens.stream()
                .filter(t -> t.getToken().equals(token) && t.getExpiryDate().isAfter(LocalDateTime.now()))
                .findFirst();
    }

    public void resetPassword(String token, String newPassword, String confirmPassword) throws IOException, MessagingException, jakarta.mail.MessagingException {
        if (!newPassword.equals(confirmPassword)) {
            throw new UserException("Passwords do not match");
        }
        List<ResetToken> tokens = FileUtil.readTokens(tokenFilePath);
        ResetToken resetToken = tokens.stream()
                .filter(t -> t.getToken().equals(token))
                .findFirst()
                .orElseThrow(() -> new UserException("Invalid or expired token"));
        List<User> users = FileUtil.readUsers(userFilePath);
        User user = users.stream()
                .filter(u -> u.getEmail().equals(resetToken.getEmail()))
                .findFirst()
                .orElseThrow(() -> new UserException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        FileUtil.writeUsers(userFilePath, users);
        tokens.removeIf(t -> t.getToken().equals(token));
        FileUtil.writeTokens(tokenFilePath, tokens);
        emailService.sendPasswordChangedEmail(user.getEmail());
    }

    public List<User> getAllUsers() throws IOException {
        return FileUtil.readUsers(userFilePath);
    }

    public void deleteUser(Long id) throws IOException {
        List<User> users = FileUtil.readUsers(userFilePath);
        users.removeIf(u -> u.getId().equals(id));
        FileUtil.writeUsers(userFilePath, users);
    }

    public User createAdminUser(User user, String confirmPassword) throws IOException {
        if (!user.getPassword().equals(confirmPassword)) {
            throw new UserException("Passwords do not match");
        }
        if (findUserByEmail(user.getEmail()).isPresent()) {
            throw new UserException("Email already exists");
        }
        user.setId(generateUserId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ADMIN");
        user.setCreatedAt(LocalDateTime.now());
        List<User> users = FileUtil.readUsers(userFilePath);
        users.add(user);
        FileUtil.writeUsers(userFilePath, users);
        return user;
    }

    private Long generateUserId() throws IOException {
        List<User> users = FileUtil.readUsers(userFilePath);
        return users.stream().map(User::getId).max(Long::compareTo).orElse(0L) + 1;
    }

    private String saveAvatar(Long userId, byte[] avatarData) throws IOException {
        String avatarDir = "src/main/resources/static/images/avatars/";
        File dir = new File(avatarDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String avatarPath = avatarDir + "user_" + userId + ".png";
        try (FileOutputStream fos = new FileOutputStream(avatarPath)) {
            fos.write(avatarData);
        }
        return "/images/avatars/user_" + userId + ".png";
    }

    public void deleteUserByEmail(String email) throws IOException {
        List<User> users = FileUtil.readUsers(userFilePath);
        boolean removed = users.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
        if (!removed) {
            throw new UserException("User not found for email: " + email);
        }
        FileUtil.writeUsers(userFilePath, users);
    }

}
