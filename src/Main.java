import adapters.in.ReservacionController;
import adapters.out.ConsoleNotificationAdapter;
import adapters.out.InMemoryReservationRepository;
import adapters.out.InMemoryUserRepository;
import application.dto.CrearReservacionResponse;
import application.useCase.CrearReservacionUseCase;

public class Main {
    public static void main(String[] args) {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        adapters.out.InMemoryLaboratorioRepository roomRepository = new adapters.out.InMemoryLaboratorioRepository();
        InMemoryReservationRepository reservationRepository = new InMemoryReservationRepository();
        ConsoleNotificationAdapter notificationPort = new ConsoleNotificationAdapter();

        CrearReservacionUseCase useCase = new CrearReservacionUseCase(
                userRepository,
                roomRepository,
                reservationRepository,
                notificationPort
        );

        ReservacionController controller = new ReservacionController(useCase);

        try {
            CrearReservacionResponse response = controller.crearReservacion(1L, 1L, 10);
            System.out.println("Resultado: " + response.getMessage() + " - Total: $" + response.getTotal());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
