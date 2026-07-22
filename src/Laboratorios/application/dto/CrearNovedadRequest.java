package application.dto;

public class CrearNovedadRequest {
    private Long id_usuario;
    private Long codigo_lab;
    private int numero_maquina;
    private TipoIncidente tipoIncidente;
    private Descripcion descripcion;

    public CrearNovedadRequest(Long id_usuario, Long codigo_lab, int numero_maquina, TipoIncidente tipoIncidente, Descripcion descripcion) {
        this.id_usuario = id_usuario;
        this.codigo_lab = codigo_lab;
        this.numero_maquina = numero_maquina;
        this.tipoIncidente = tipoIncidente;
        this.descripcion = descripcion;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public Long getCodigo_lab() {
        return codigo_lab;
    }

    public int getNumero_maquina() {
        return numero_maquina;
    }

    public TipoIncidente getTipoIncidente() {
        return tipoIncidente;
    }

    public Descripcion getDescripcion() {
        return descripcion;
    }
}
