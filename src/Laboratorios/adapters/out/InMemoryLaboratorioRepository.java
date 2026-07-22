package adapters.out;

import domain.entities.Room;

public class InMemoryLaboratorioRepository implements domain.ports.LaboratorioRepository {

    private final Room room1 = new Room(1L, 20.0, true);

    @Override
    public Room findById(Long roomId) {
        if (room1.getId().equals(roomId)) {
            return room1;
        }
        return null;
    }

    @Override
    public void save(Room cuarto) {
    }

    @Override
    public void update(Room cuarto) {
    }
}
