package mx.edu.utez.siba.models.usuario;

public class BeanAlumno {
    private String matricula;
    private int grado;
    private String grupo;
    private BeanUsuario id_usuario;

    public BeanAlumno() {
    }

    public BeanAlumno(BeanUsuario id_usuario) {
        this.id_usuario = id_usuario;
    }

    public BeanAlumno(String matricula, int grado, String grupo, BeanUsuario id_usuario) {
        this.matricula = matricula;
        this.grado = grado;
        this.grupo = grupo;
        this.id_usuario = id_usuario;
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

    public BeanUsuario getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(BeanUsuario id_usuario) {
        this.id_usuario = id_usuario;
    }
}
