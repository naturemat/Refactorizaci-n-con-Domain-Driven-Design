package application.useCase;

import domain.*;
import application/dto.*;


public class CrearReservacionUseCase {

    private final UsuarioRepository UsuarioRepository;
    private final LaboratorioRepository LaboratorioRepository;
    private final NovedadRepository NovedadRepository;


    public CrearReservacionUseCase(UsuarioRepository usuarioRepository, LaboratorioRepository laboratorioRepository, NovedadRepository novedadRepository) {
        UsuarioRepository = usuarioRepository;
        LaboratorioRepository = laboratorioRepository;
        NovedadRepository = novedadRepository;
    }


    public CrearNovedadResponse execute(CrearNovedadRequest request) {

        Usuario usuario = usuarioRepository.findByID(request.getId_usuario());
        Laboratorio laboratorio = laboratorioRepository.findById(request.getCodigo_lab());

        new Descripcion descripcion = request.getDescripcion();


        return new CrearReservacionResponse("Reserva creada");
    }
}
