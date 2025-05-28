package pgno130.obms.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "user/profile";  // profile.jsp
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        try {
            Optional<User> userOpt = userService.loginUser(email, password);
            if (userOpt.isPresent()) {
                // Store user in session
                session.setAttribute("loggedInUser", userOpt.get());
                redirectAttributes.addFlashAttribute("success", "Login successful!");
                return "redirect:/profile";
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid email or password");
                return "redirect:/login";
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred. Please try again.");
            return "redirect:/login";
        }
    }


    @PostMapping("/register")
    public String register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) boolean terms,
            RedirectAttributes redirectAttributes) {

        // Example validation
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match");
            return "redirect:/register";
        }
        if (!terms) {
            redirectAttributes.addFlashAttribute("error", "You must accept the terms");
            return "redirect:/register";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);  // Remember to hash password before saving!
        user.setAddress(address);
        user.setPhone(phone);
        user.setRole("USER");  // Default role; adjust as needed
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setAvatarUrl(null);


        try {
            userService.registerUser(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        redirectAttributes.addFlashAttribute("success", "Registration successful!");
        return "redirect:/login";
    }


    @GetMapping("/register")
    public String showRegisterForm() {
        return "user/register";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute User updatedUser,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                return "redirect:/login";
            }

            userService.updateUserByEmail(updatedUser);
            session.setAttribute("loggedInUser", updatedUser);

            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
            return "redirect:/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update profile: " + e.getMessage());
            return "redirect:/profile";
        }
    }

    @PostMapping("/delete")
    public String deleteProfile(HttpSession session, RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        try {
            userService.deleteUserByEmail(loggedInUser.getEmail());
            session.invalidate();
            redirectAttributes.addFlashAttribute("success", "Your account has been deleted.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete account: " + e.getMessage());
            return "redirect:/profile";
        }
    }


    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/auth/forgot-password")
    public String forgotPassword(@RequestParam String email,
                                 RedirectAttributes redirectAttributes) {
        try {
            userService.generatePasswordResetToken(email);
            redirectAttributes.addFlashAttribute("success", "Password reset link sent to your email.");
            return "redirect:/forgot-password";
        } catch (UserException | jakarta.mail.MessagingException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/forgot-password";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred. Please try again.");
            return "redirect:/forgot-password";
        }
    }

    @GetMapping("/auth/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        try {
            Optional<ResetToken> resetToken = userService.validateResetToken(token);
            if (resetToken.isPresent()) {
                model.addAttribute("token", token);
                return "reset-password";
            } else {
                model.addAttribute("error", "Invalid or expired reset token");
                return "reset-password";
            }
        } catch (IOException e) {
            model.addAttribute("error", "An error occurred. Please try again.");
            return "reset-password";
        }
    }

    @PostMapping("/auth/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password,
                                @RequestParam String confirmPassword,
                                RedirectAttributes redirectAttributes) {
        try {
            userService.resetPassword(token, password, confirmPassword);
            redirectAttributes.addFlashAttribute("success", "Password reset successfully. Please login.");
            return "redirect:/login";
        } catch (UserException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/auth/reset-password?token=" + token;
        } catch (IOException | MessagingException e) {
            redirectAttributes.addFlashAttribute("error", "An error occurred. Please try again.");
            return "redirect:/auth/reset-password?token=" + token;
        }
    }

    @GetMapping("/terms")
    public String showTerms() {
        return "terms";
    }
}
