package pgno130.obms.user;

import org.springframework.stereotype.Service;

@Service
public class MockEmailService {
    public void sendPasswordResetEmail(String email, String token) {
        System.out.println("Sending password reset email to " + email);
        System.out.println("Use this reset link to reset your password ");
        System.out.println("Reset link: http://localhost:8080/auth/reset-password?token=" + token);
    }

    public void sendPasswordChangeNotification(String email) {
        System.out.println("Sending password change notification to " + email);
    }
}
