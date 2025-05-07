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

    @GetMapping("")
    public String getAllNotifications(Model model,
                                      @RequestParam Boolean isAdmin,
                                      @RequestParam(required = false) Boolean isSuccess) throws IOException {
        model.addAttribute("notifications", notificationService.getNotifications());
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isSuccess", isSuccess);
        return "notification/notification";
    }

    @GetMapping("/view-notification")
    public String getNotification(Model model,
                                  @RequestParam Boolean isAdmin,
                                  @RequestParam Long notificationId) throws IOException {
        model.addAttribute("notification", notificationService.getNotification(notificationId));
        model.addAttribute("isAdmin", isAdmin);
        return "notification/single-notification";
    }

    @GetMapping( "/update-notification")
    public String openNotificationEditor(Model model,
                                         @RequestParam(required = false) Long notificationId) throws IOException {
        model.addAttribute("notification", notificationId == null ?
                null : notificationService.getNotification(notificationId));
        return "notification/edit-notification";
    }

    @PostMapping("/update-notification")
    public String updateNotification(@RequestParam(required = false) Long id,
                                     @RequestParam(required = false) String timestamp,
                                     @RequestParam String title,
                                     @RequestParam String message) throws IOException {
        Notification notification = new Notification();
        notification.setId(id);
        notification.setTimestamp(timestamp);
        notification.setTitle(title);
        notification.setMessage(message);
        return "redirect:/notification?isAdmin=true&isSuccess=" + (id == null ?
                notificationService.addNotification(notification) :
                notificationService.updateNotification(notification));
    }

    @GetMapping("/delete-notification")
    public String deleteNotification(@RequestParam Long notificationId) throws IOException {
        return "redirect:/notification?isAdmin=true&isSuccess=" + notificationService.deleteNotification(notificationId);
    }
}
