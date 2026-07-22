package application.useCase;

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


        return new CrearReservacionResponse(total, "Reserva creada");
    }
}
