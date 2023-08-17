package mx.edu.utez.siba.models.usuario;

public class BeanAlumno extends BeanUsuario {
    private String matricula;
    private int grado;
    private String grupo;

    public BeanAlumno() {
    }

    public BeanAlumno(Long id_usuario, String nombre, String apellido_paterno, String apellido_materno, String correo, String contrasenia, String telefono, int rol, String matricula, int grado, String grupo) {
        super(id_usuario, nombre, apellido_paterno, apellido_materno, correo, contrasenia, telefono, rol);
        this.matricula = matricula;
        this.grado = grado;
        this.grupo = grupo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
}
