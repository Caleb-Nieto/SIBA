package mx.edu.utez.siba.models.sala;

import mx.edu.utez.siba.models.usuario.BeanUsuario;

public class BeanPrestamoSala {
    private Long id_prestamo_sala;
    private BeanSala id_sala;
    private BeanUsuario id_usuario;
    private String fecha_inicio;
    private String feca_devolucion;
    private String estatus;


    public BeanPrestamoSala(){

    }
    public BeanPrestamoSala(Long id_prestamo_sala, BeanSala id_sala, BeanUsuario id_usuario, String fecha_inicio, String feca_devolucion, String estatus) {
        this.id_prestamo_sala = id_prestamo_sala;
        this.id_sala = id_sala;
        this.id_usuario = id_usuario;
        this.fecha_inicio = fecha_inicio;
        this.feca_devolucion = feca_devolucion;
        this.estatus = estatus;
    }

    public Long getId_prestamo_sala() {
        return id_prestamo_sala;
    }

    public void setId_prestamo_sala(Long id_prestamo_sala) {
        this.id_prestamo_sala = id_prestamo_sala;
    }

    public BeanSala getId_sala() {
        return id_sala;
    }

    public void setId_sala(BeanSala id_sala) {
        this.id_sala = id_sala;
    }

    public BeanUsuario getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(BeanUsuario id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFeca_devolucion() {
        return feca_devolucion;
    }

    public void setFeca_devolucion(String feca_devolucion) {
        this.feca_devolucion = feca_devolucion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
