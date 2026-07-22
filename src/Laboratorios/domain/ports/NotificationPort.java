package domain.ports;

public interface NotificationPort {
    void sendEmail(String to, String subject, String body);
}
