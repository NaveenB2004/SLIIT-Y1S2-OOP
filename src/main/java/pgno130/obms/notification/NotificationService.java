package pgno130.obms.notification;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Repository
public final class NotificationService {
    private static final String fileName = "Notifications.json";
    private static final Lock lock = new ReentrantLock();
    private static long notificationId;

    public List<Notification> getNotifications() throws IOException {
        lock.lock();
        List<Notification> notifications = new LinkedList<>();
        if (!(new File(fileName)).exists()) return notifications;
        Gson gson = new Gson();
        try (var reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Notification notification = gson.fromJson(line, Notification.class);
                notifications.add(notification);
                notificationId = Math.max(notificationId, notification.getId());
            }
        }
        lock.unlock();
        return notifications;
    }

    public Notification getNotification(@NonNull Long id) throws IOException {
        List<Notification> notifications = getNotifications();
        for (Notification notification : notifications) {
            if (notification.getId() == id) {
                return notification;
            }
        }
        return null;
    }

    public boolean addNotification(@NonNull Notification notification) throws IOException {
        List<Notification> notifications = getNotifications();
        notification.setId(notificationId++);
        notifications.add(notification);
        save(notifications);
        return true;
    }

    public boolean updateNotification(@NonNull Notification notification) throws IOException {
        List<Notification> notifications = getNotifications();
        Optional<Notification> tempNotification = notifications.stream().filter(n -> n.getId() == notification.getId()).findFirst();
        if (tempNotification.isPresent()) {
            tempNotification.get().setTitle(notification.getTitle());
            tempNotification.get().setMessage(notification.getMessage());
            tempNotification.get().setTimestamp(notification.getTimestamp());
            save(notifications);
            return true;
        }
        return addNotification(notification);
    }

    public boolean deleteNotification(@NonNull Long id) throws IOException {
        List<Notification> notifications = getNotifications();
        Optional<Notification> tempNotification = notifications.stream().filter(n -> n.getId() == id).findFirst();
        if (tempNotification.isPresent()) {
            notifications.remove(tempNotification.get());
            save(notifications);
            return true;
        }
        return false;
    }

    private void save(@NonNull List<Notification> notifications) throws IOException {
        lock.lock();
        Gson gson = new GsonBuilder().serializeNulls().create();
        try (var writer = new PrintWriter(fileName)) {
            for (Notification notification : notifications) {
                writer.println(gson.toJson(notification));
            }
        }
        lock.unlock();
    }
}
