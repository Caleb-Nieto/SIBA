package mx.edu.utez.siba.models.libro.ubicacion;

public class BeanUbicacion {
    private Long id;
    private int pasillo;
    private int seccion;
    private String estante;

    public BeanUbicacion() {
    }

    public BeanUbicacion(Long id, int pasillo, int seccion, String estante) {
        this.id = id;
        this.pasillo = pasillo;
        this.seccion = seccion;
        this.estante = estante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPasillo() {
        return pasillo;
    }

    public void setPasillo(int pasillo) {
        this.pasillo = pasillo;
    }

    public int getSeccion() {
        return seccion;
    }

    public void setSeccion(int seccion) {
        this.seccion = seccion;
    }

    public String getEstante() {
        return estante;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }
}
