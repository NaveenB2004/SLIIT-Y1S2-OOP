package pgno130.obms.user;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", userService.getUserById(user.getId()));
        return "user/profile";
    }

    @PostMapping("/update")
    public String updateProfile(User user, HttpSession session) {
        User updateUser = userService.updateUser(user);
        session.setAttribute("user", updateUser);
        return "redirect:/user/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword, HttpSession session, Model model) {
        try {
            User user = (User) session.getAttribute("user");
            userService.changePassword(user.getId(), currentPassword, newPassword);
            model.addAttribute("success", "Password changed successfully");
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "user/profile";
    }
}
