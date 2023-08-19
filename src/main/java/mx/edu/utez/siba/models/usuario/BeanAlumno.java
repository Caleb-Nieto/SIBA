package mx.edu.utez.siba.models.usuario;

public class BeanAlumno extends BeanUsuario {
    private String matricula;
    private String carrera;
    private int grado;
    private String grupo;

    public BeanAlumno() {
    }

    public BeanAlumno(Long id_usuario, String nombre, String apellido_paterno, String apellido_materno, String telefono, String carrera, int grado, String grupo) {
        super(id_usuario, nombre, apellido_paterno, apellido_materno, telefono);
        this.carrera = carrera;
        this.grado = grado;
        this.grupo = grupo;
    }

    public BeanAlumno(Long id_usuario, String nombre, String apellido_paterno, String apellido_materno, String correo, String contrasenia, String telefono, int rol, String matricula, String carrera, int grado, String grupo) {
        super(id_usuario, nombre, apellido_paterno, apellido_materno, correo, contrasenia, telefono, rol);
        this.matricula = matricula;
        this.carrera = carrera;
        this.grado = grado;
        this.grupo = grupo;
    }

    public BeanAlumno(String nombre, String apellido_paterno, String apellido_materno, String correo, String contrasenia, String telefono, String matricula, String carrera, int grado, String grupo) {
        super(nombre, apellido_paterno, apellido_materno, correo, contrasenia, telefono);
        this.matricula = matricula;
        this.carrera = carrera;
        this.grado = grado;
        this.grupo = grupo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
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
