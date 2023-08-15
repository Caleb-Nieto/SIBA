package mx.edu.utez.siba.models.libro.autor;

import mx.edu.utez.siba.models.libro.BeanLibro;

import java.util.List;

public class BeanAutor {
    private Long id_autor;
    private String nombre;
    private String apellido_materno;
    private String getApellido_paterno;

    private List<BeanLibro> libros;


    public BeanAutor() {
    }

    public BeanAutor(Long id_autor, String nombre, String apellido_materno, String getApellido_paterno, List<BeanLibro> libros) {
        this.id_autor = id_autor;
        this.nombre = nombre;
        this.apellido_materno = apellido_materno;
        this.getApellido_paterno = getApellido_paterno;
        this.libros = libros;
    }

    public Long getId_autor() {
        return id_autor;
    }

    public void setId_autor(Long id_autor) {
        this.id_autor = id_autor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getApellido_paterno() {
        return getApellido_paterno;
    }

    public void setApellido_paterno(String getApellido_paterno) {
        this.getApellido_paterno = getApellido_paterno;
    }

    public String getGetApellido_paterno() {
        return getApellido_paterno;
    }

    public void setGetApellido_paterno(String getApellido_paterno) {
        this.getApellido_paterno = getApellido_paterno;
    }

    public List<BeanLibro> getLibros() {
        return libros;
    }

    public void setLibros(List<BeanLibro> libros) {
        this.libros = libros;
    }
}
