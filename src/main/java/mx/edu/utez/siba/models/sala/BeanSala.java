package mx.edu.utez.siba.models.sala;

public class BeanSala {
    private Long id_sala;
    private String nombre;
    private String capacidad;
    private String descripcion;
    private String prestamo;

    public BeanSala() {
    }

    public BeanSala(Long id_sala, String nombre, String capacidad, String descripcion) {
        this.id_sala = id_sala;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
    }

    public Long getId_sala() {
        return id_sala;
    }

    public void setId_sala(Long id_sala) {
        this.id_sala = id_sala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(String informacion_prestamo) {
        this.prestamo = informacion_prestamo;
    }
}