package Laboratorios.domain.valueObjects;

public class Descripcion {
    private final String valor;

    public Descripcion(String valor) {
        if (valor==null || valor.length()<=10) {
            throw new IllegalArgumentException("ERROR: la descripcion debe tener al menos 10 caracteres");
        }
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
