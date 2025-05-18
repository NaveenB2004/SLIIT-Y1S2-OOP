package pgno130.obms.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendResetEmail(String to, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Password Reset Request - Book Store 130'");

        String resetUrl = "http://localhost:8080/bookstore130/auth/reset-password?token=" + token;

        String htmlContent = "<h3>Password Reset Request</h3>" +
                "<p>Click the link below to reset your password:</p>" +
                "<a href=\"" + resetUrl + "\">Reset Password</a>" +
                "<p>This link will expire in 1 hour.</p>";

        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    public void sendPasswordChangedEmail(String to) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Password Changed Successfully - Book Store 130'");

        String htmlContent = "<h3>Your Book Store 130' Password has been changed successfully</h3>" +
                "<p>If you did not make this change, please contact support immediately.</p>";

        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}
