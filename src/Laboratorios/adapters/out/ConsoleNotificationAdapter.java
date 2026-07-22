package adapters.out;

import domain.ports.NotificationPort;

public class ConsoleNotificationAdapter implements NotificationPort {

    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("=== EMAIL ENVIADO ===");
        System.out.println("Para: " + to);
        System.out.println("Asunto: " + subject);
        System.out.println("Mensaje: " + body);
        System.out.println("====================");
    }
}
