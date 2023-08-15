package mx.edu.utez.siba.models.usuario;

public class BeanDocente {
    private String no_trabajador;
    private String division;
    private BeanUsuario id_usuario;

    public BeanDocente() {
    }

    public BeanDocente(BeanUsuario id_usuario) {
        this.id_usuario = id_usuario;
    }

    public BeanDocente(String no_trabajador, String division, BeanUsuario id_usuario) {
        this.no_trabajador = no_trabajador;
        this.division = division;
        this.id_usuario = id_usuario;
    }

    public String getNo_trabajador() {
        return no_trabajador;
    }

    public void setNo_trabajador(String no_trabajador) {
        this.no_trabajador = no_trabajador;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public BeanUsuario getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(BeanUsuario id_usuario) {
        this.id_usuario = id_usuario;
    }
}
