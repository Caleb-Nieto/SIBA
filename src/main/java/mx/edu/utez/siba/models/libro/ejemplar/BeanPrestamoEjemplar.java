package mx.edu.utez.siba.models.libro.ejemplar;

import mx.edu.utez.siba.models.usuario.BeanUsuario;

public class BeanPrestamoEjemplar {
    private Long id_prestamo_libro;
    private BeanEjemplar ejemplar;
    private BeanUsuario usuario;
    private String fecha_inicio;
    private String fecha_devolucion;
    private String estatus;

    public BeanPrestamoEjemplar() {
    }

    public BeanPrestamoEjemplar(Long id_prestamo_libro, BeanEjemplar ejemplar, BeanUsuario usuario, String fecha_inicio, String fecha_devolucion, String estatus) {
        this.id_prestamo_libro = id_prestamo_libro;
        this.ejemplar = ejemplar;
        this.usuario = usuario;
        this.fecha_inicio = fecha_inicio;
        this.fecha_devolucion = fecha_devolucion;
        this.estatus = estatus;
    }

    public Long getId_prestamo_libro() {
        return id_prestamo_libro;
    }

    public void setId_prestamo_libro(Long id_prestamo_libro) {
        this.id_prestamo_libro = id_prestamo_libro;
    }

    public BeanEjemplar getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(BeanEjemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    public BeanUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(BeanUsuario usuario) {
        this.usuario = usuario;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(String fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
