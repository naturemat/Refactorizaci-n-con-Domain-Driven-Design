package adapters.out;

import domain.entities.Reservation;
import domain.ports.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public void save(Reservation reserva) {
        reservations.add(reserva);
    }
}
