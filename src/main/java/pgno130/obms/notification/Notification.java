package pgno130.obms.notification;

import lombok.Data;

@Data
public final class Notification {
    private Long id;
    private String timestamp;
    private String title;
    private String message;
}
