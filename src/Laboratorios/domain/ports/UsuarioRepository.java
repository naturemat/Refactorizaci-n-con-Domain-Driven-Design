package Laboratorios.domain.ports;
import Laboratorios.domain.entities.Usuario;

public interface UsuarioRepository {
    Usuario findById(Long userId);

}
