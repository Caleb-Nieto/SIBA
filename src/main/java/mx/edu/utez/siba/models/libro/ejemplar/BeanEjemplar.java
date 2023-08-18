package mx.edu.utez.siba.models.libro.ejemplar;

import mx.edu.utez.siba.models.libro.BeanLibro;

public class BeanEjemplar {


    private Long id_ejemplar;

    private int ejemplar;
    private String observaciones;
    private BeanLibro libro;

    public BeanEjemplar() {
    }


    public BeanEjemplar(Long id_ejemplar, int ejemplar, String observaciones, BeanLibro libro) {
        this.id_ejemplar = id_ejemplar;
        this.ejemplar = ejemplar;
        this.observaciones = observaciones;
        this.libro = libro;
    }

    public int getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(int ejemplar) {
        this.ejemplar = ejemplar;
    }

    public Long getId_ejemplar() {
        return id_ejemplar;
    }

    public void setId_ejemplar(Long id_ejemplar) {
        this.id_ejemplar = id_ejemplar;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public BeanLibro getLibro() {return libro;}

    public void setLibro(BeanLibro libro) {
        this.libro = libro;
    }
}
