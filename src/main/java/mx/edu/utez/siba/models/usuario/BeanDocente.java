package mx.edu.utez.siba.models.usuario;

public class BeanDocente extends BeanUsuario {
    private String no_trabajador;
    private String division;

    public BeanDocente() {
    }

    public BeanDocente(Long id_usuario, String nombre, String apellido_paterno, String apellido_materno, String correo, String contrasenia, String telefono, int rol, String no_trabajador, String division) {
        super(id_usuario, nombre, apellido_paterno, apellido_materno, correo, contrasenia, telefono, rol);
        this.no_trabajador = no_trabajador;
        this.division = division;
    }

    public BeanDocente(String nombre, String apellido_paterno, String apellido_materno, String correo, String contrasenia, String telefono, String no_trabajador, String division) {
        super(nombre, apellido_paterno, apellido_materno, correo, contrasenia, telefono);
        this.no_trabajador = no_trabajador;
        this.division = division;
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
}
