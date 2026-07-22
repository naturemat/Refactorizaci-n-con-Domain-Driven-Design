package Laboratorios.domain.ports;

import Laboratorios.domain.entities.Novedad;

public interface NovedadRepository {

    void save(Novedad novedad);
}
