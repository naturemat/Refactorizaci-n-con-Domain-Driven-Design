package adapters.out;

import domain.entities.User;
import domain.ports.UserRepository;

public class InMemoryUserRepository implements UserRepository {

    @Override
    public User findById(Long userId) {
        if (userId.equals(1L)) {
            return new User("Mateo", 1L, "mateo@mail.com");
        }
        return null;
    }
}
