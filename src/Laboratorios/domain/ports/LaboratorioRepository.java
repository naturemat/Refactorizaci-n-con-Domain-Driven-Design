package Laboratorios.domain.ports;

import Laboratorios.domain.entities.Laboratorio;

public interface LaboratorioRepository {

    Laboratorio findById(Long laboratorioId);
    void save(Laboratorio laboratorio);
    void update (Laboratorio laboratorio);


}
