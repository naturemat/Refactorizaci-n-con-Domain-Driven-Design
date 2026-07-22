package Laboratorios.domain.entities;

import Laboratorios.domain.valueObjects.Descripcion;
import Laboratorios.domain.valueObjects.EstadoMaquina;
import Laboratorios.domain.valueObjects.TipoIncidente;

public class Novedad {
    private String idNovedad;
    private String ticket;
    private String nombre_Usuario;
    private String codigo_lab;
    private String numero_maquina;
    private TipoIncidente tipo_incidente;
    private Descripcion descripcion;
    private EstadoMaquina estado;
    private String prioridad;
    private String id_admin;
    private String admin_nombre;
    private String fecha;

    public Novedad(String idNovedad, String ticket, String nombre_Usuario, String codigo_lab, String numero_maquina, TipoIncidente tipo_incidente, Descripcion descripcion, EstadoMaquina estado, String prioridad, String id_admin, String admin_nombre, String fecha) {
        this.idNovedad = idNovedad;
        this.ticket = ticket;
        this.nombre_Usuario = nombre_Usuario;
        this.codigo_lab = codigo_lab;
        this.numero_maquina = numero_maquina;
        this.tipo_incidente = tipo_incidente;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.id_admin = id_admin;
        this.admin_nombre = admin_nombre;
        this.fecha = fecha;
    }

    public String getIdNovedad() {
        return idNovedad;
    }

    public String getNombre_Usuario() {
        return nombre_Usuario;
    }

    public String getTicket() {
        return ticket;
    }

    public String getCodigo_lab() {
        return codigo_lab;
    }

    public String getNumero_maquina() {
        return numero_maquina;
    }

    public TipoIncidente getTipo_incidente() {
        return tipo_incidente;
    }

    public Descripcion getDescripcion() {
        return descripcion;
    }

    public EstadoMaquina getEstado() {
        return estado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public String getId_admin() {
        return id_admin;
    }

    public String getFecha() {
        return fecha;
    }

    public String getAdmin_nombre() {
        return admin_nombre;
    }

}
