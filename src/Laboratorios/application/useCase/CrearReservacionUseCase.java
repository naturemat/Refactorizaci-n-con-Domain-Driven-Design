package application.useCase;

import application.dto.CrearReservacionRequest;
import application.dto.CrearReservacionResponse;
import domain.entities.Reservation;
import domain.entities.Room;
import domain.entities.User;
import domain.ports.NotificationPort;
import domain.ports.ReservationRepository;
import domain.ports.UserRepository;

public class CrearReservacionUseCase {

    private final UsuarioRepository UsuarioRepository;
    private final domain.ports.LaboratorioRepository laboratorioRepository;
    private final ReservationRepository reservationRepository;
    private final NotificationPort notificationPort;

    public CrearReservacionUseCase(UserRepository userRepository, domain.ports.LaboratorioRepository laboratorioRepository, ReservationRepository reservationRepository, NotificationPort notificationPort) {
        this.userRepository = userRepository;
        this.laboratorioRepository = laboratorioRepository;
        this.reservationRepository = reservationRepository;
        this.notificationPort = notificationPort;
    }

    public CrearReservacionResponse execute(CrearReservacionRequest request) {
        Long userId = request.getUserId();
        Long roomId = request.getRoomId();
        Integer nights = request.getNights();

        if (userId == null || userId <= 0) {
            throw new RuntimeException("Usuario inválido");
        }

        if (roomId == null || roomId <= 0) {
            throw new RuntimeException("Habitación inválida");
        }

        if (nights == null || nights <= 0) {
            throw new RuntimeException("Cantidad de noches inválida");
        }

        User user = userRepository.findById(userId);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Room room = laboratorioRepository.findById(roomId);
        if (room == null) {
            throw new RuntimeException("Habitación no encontrada");
        }

        room.validateAvailability();

        Reservation reservation = new Reservation(1L, userId, roomId, nights);
        double total = reservation.calculateTotal(room.getPrice());

        reservationRepository.save(reservation);

        room.setAvailable(false);
        laboratorioRepository.update(room);

        String email = user.getEmail().getValor();
        notificationPort.sendEmail(
                email,
                "Reserva confirmada",
                "Su reserva fue creada. Total: $" + total
        );

        return new CrearReservacionResponse(total, "Reserva creada");
    }
}
