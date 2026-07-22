package Laboratorios.domain.entities;

public class Usuario {
    private Long UsuarioId;
    private String nombre;
    private String rol;
    private String cedula;
    private String carrera;

    public Usuario(Long usuarioId, String nombre, String rol, String carrera, String cedula) {
        UsuarioId = usuarioId;
        this.nombre = nombre;
        this.rol = rol;
        this.carrera = carrera;
        this.cedula = cedula;
    }

    public Long getUsuarioId() {
        return UsuarioId;
    }
    public String getNombre() {
        return nombre;
    }
    public String getRol() {
        return rol;
    }
    public String getCedula() {
        return cedula;
    }
    public String getCarrera() {
        return carrera;
    }

}

