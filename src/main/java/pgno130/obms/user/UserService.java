package pgno130.obms.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private MockEmailService emailService;

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUseOrAdmin("User");
        return userRepository.save(user);
    }

    public User login(String email, String password, boolean rememberMe) {
        User user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (rememberMe) {
            String token = UUID.randomUUID().toString();
            user.setRememberMe(token);
            userRepository.save(user);
        }

        return user;
    }

    public User getUserById(String id) {
        return userRepository.findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        User existing = getUserById(user.getId());
        existing.setName(user.getName());
        existing.setAddress(user.getAddress());
        existing.setPhone(user.getPhone());
        return userRepository.save(existing);
    }

    public void changePassword(String userId, String currentPassword, String newPassword) {
        User user = getUserById(userId);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is not matched");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        emailService.sendPasswordChangeNotification(user.getEmail());
    }

    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Email not found");
        }

        String token = UUID.randomUUID().toString();
        user.setRememberMe(token);
        userRepository.save(user);

        emailService.sendPasswordResetEmail(user.getEmail(), token);
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findAll().stream()
                .filter(u -> token.equals(u.getRememberMe()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setRememberMe(null);
        userRepository.save(user);
        emailService.sendPasswordChangeNotification(user.getEmail());
    }

    public void deleteUser(String id) {
        User user = getUserById(id);
        userRepository.delete(id);
    }
}
