package exepciones;

public class Excepcion extends RuntimeException {
    private String entidad;
    private int codigo;

    public Excepcion(String entidad, String mensaje, int codigo) {
        super(mensaje);
        this.entidad = entidad;
        this.codigo = codigo;
    }

    public String getEntidad() {
        return entidad;
    }

    public int getCodigo() {
        return codigo;
    }
}