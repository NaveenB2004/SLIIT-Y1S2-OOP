package pgno130.obms.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/notification")
@RequiredArgsConstructor
public final class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/")
    public String getAllNotifications(Model model) throws IOException {
        model.addAttribute("notifications", notificationService.getNotifications());
        model.addAttribute("isAdmin", true); // dynamic
        return "notification/notification";
    }

    @GetMapping("/{notificationId}")
    public String getNotification(Model model,
                                  @PathVariable Long notificationId) throws IOException {
        model.addAttribute("notification", notificationService.getNotification(notificationId));
        model.addAttribute("isAdmin", true); // dynamic
        return "notification/single-notification";
    }

    @GetMapping({"/update-notification/{notificationId}", "/update-notification"})
    public String openNotificationEditor(Model model,
                                         @PathVariable(required = false) Long notificationId) throws IOException {
        model.addAttribute("notification", notificationId == null ?
                null : notificationService.getNotification(notificationId));
        return "notification/edit-notification";
    }

    @PostMapping("/update-notification")
    public String updateNotification(Model model,
                                     @RequestParam(required = false) Long id,
                                     @RequestParam(required = false) String timestamp,
                                     @RequestParam String title,
                                     @RequestParam String message) throws IOException {
        Notification notification = new Notification();
        notification.setId(id);
        notification.setTimestamp(timestamp);
        notification.setTitle(title);
        notification.setMessage(message);
        model.addAttribute("isSuccess", id == null ?
                notificationService.addNotification(notification) : notificationService.updateNotification(notification));
        return "redirect:/notification/";
    }

    @GetMapping("/delete-notification/{notificationId}")
    public String deleteNotification(Model model,
                                     @PathVariable Long notificationId) throws IOException {
        model.addAttribute("isSuccess", notificationService.deleteNotification(notificationId));
        return "redirect:/notification/";
    }
}
