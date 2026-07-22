package adapters.in;

import application.dto.CrearReservacionRequest;
import application.dto.CrearReservacionResponse;
import application.useCase.CrearReservacionUseCase;

public class ReservacionController {

    private final CrearReservacionUseCase crearReservacionUseCase;

    public ReservacionController(CrearReservacionUseCase crearReservacionUseCase) {
        this.crearReservacionUseCase = crearReservacionUseCase;
    }

    public CrearReservacionResponse crearReservacion(Long userId, Long roomId, Integer nights) {
        CrearReservacionRequest request = new CrearReservacionRequest(userId, roomId, nights);
        return crearReservacionUseCase.execute(request);
    }
}
