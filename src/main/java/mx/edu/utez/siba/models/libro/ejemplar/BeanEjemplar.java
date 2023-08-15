package mx.edu.utez.siba.models.libro.ejemplar;

import mx.edu.utez.siba.models.libro.BeanLibro;

public class BeanEjemplar {
    private Long id_ejemplar;
    private String observaciones;
    private BeanLibro libro;

    public BeanEjemplar() {
    }

    public BeanEjemplar(Long id_ejemplar, String observaciones, BeanLibro id_libro) {
        this.id_ejemplar = id_ejemplar;
        this.observaciones = observaciones;
        this.libro = id_libro;
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
