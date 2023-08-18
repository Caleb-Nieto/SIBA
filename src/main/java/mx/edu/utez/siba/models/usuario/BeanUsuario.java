package mx.edu.utez.siba.models.usuario;

public class BeanUsuario {
    private Long id_usuario;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String correo;
    private String contrasenia; //se que es varbinary pero no se como guardar ese tipo de dato en java
    private String telefono;

    private int rol;

    public BeanUsuario() {
    }


    public BeanUsuario(Long id_usuario, String nombre, String apellido_paterno, String apellido_materno, String telefono) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.telefono = telefono;
    }

    public BeanUsuario(Long id_usuario, String nombre, String apellido_paterno, String apellido_materno, String correo, String contrasenia, String telefono, int rol) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
        this.rol = rol;
    }

    public BeanUsuario(String nombre, String apellido_paterno, String apellido_materno, String correo, String contrasenia, String telefono) {
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }
}
