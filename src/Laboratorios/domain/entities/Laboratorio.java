package Laboratorios.domain.entities;

import Laboratorios.domain.valueObjects.EstadoMaquina;

public class Laboratorio {
    private String codigo;
    private String nombreLaboratorio;
    private String edificio;
    private int piso;
    //private [String] maquinas;
    private EstadoMaquina estado;

    public Laboratorio(String codigo, String nombreLaboratorio, String edificio, int piso, EstadoMaquina estado) {
        this.codigo = codigo;
        this.nombreLaboratorio = nombreLaboratorio;
        this.edificio = edificio;
        this.estado = estado;
        this.piso = piso;
    }

    public String getCodigo() {
        return codigo;
    }
    public String getNombreLaboratorio() {
        return nombreLaboratorio;
    }
    public int getPiso() {
        return piso;
    }
    public String getEdificio() {
        return edificio;
    }
    public EstadoMaquina getEstado() {
        return estado;
    }

}
