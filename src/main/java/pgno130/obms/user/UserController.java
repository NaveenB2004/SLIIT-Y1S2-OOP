package pgno130.obms.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        try {
            if (user.getRole().equals("ADMIN")) {
                List<User> users = userService.getAllUsers();
                model.addAttribute("users", users);
            }
        } catch (IOException e) {
            model.addAttribute("error", "Error loading user data");
        }
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute User user,
                                @RequestParam(value = "avatar", required = false) MultipartFile avatar,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            byte[] avatarData = (avatar != null && !avatar.isEmpty()) ? avatar.getBytes() : null;
            userService.updateUser(user, avatarData);
            session.setAttribute("user", user); // Update session
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully");
            return "redirect:/user/profile";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error updating profile");
            return "redirect:/user/profile";
        }
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam Long id,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes redirectAttributes) {
        try {
            userService.changePassword(id, currentPassword, newPassword, confirmPassword);
            redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully");
            return "redirect:/user/profile";
        } catch (UserException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/profile";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error changing password");
            return "redirect:/user/profile";
        }
    }

    @GetMapping("/admin/users/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin-create-user"; // Note: Create this JSP
    }

    @PostMapping("/admin/users/create")
    public String createUser(@ModelAttribute User user,
                             @RequestParam String confirmPassword,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.createAdminUser(user, confirmPassword);
            redirectAttributes.addFlashAttribute("successMessage", "User created successfully");
            return "redirect:/user/profile";
        } catch (UserException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/users/create";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error creating user");
            return "redirect:/admin/users/create";
        }
    }

    @GetMapping("/admin/users/edit")
    public String showEditUserForm(@RequestParam Long id, Model model) {
        try {
            Optional<User> user = userService.findUserByEmail(
                    userService.getAllUsers().stream()
                            .filter(u -> u.getId().equals(id))
                            .findFirst()
                            .map(User::getEmail)
                            .orElseThrow(() -> new UserException("User not found"))
            );
            model.addAttribute("user", user.get());
            return "admin-edit-user"; // Note: Create this JSP
        } catch (IOException | UserException e) {
            model.addAttribute("error", "Error loading user");
            return "profile";
        }
    }

    @PostMapping("/admin/users/edit")
    public String editUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(user, null); // No avatar update in admin edit
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully");
            return "redirect:/user/profile";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error updating user");
            return "redirect:/user/profile";
        }
    }

    @GetMapping("/admin/users/delete")
    public String deleteUser(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully");
            return "redirect:/user/profile";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting user");
            return "redirect:/user/profile";
        }
    }
}
