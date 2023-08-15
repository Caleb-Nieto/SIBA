package mx.edu.utez.siba.models.libro;

import mx.edu.utez.siba.models.libro.autor.BeanAutor;
import mx.edu.utez.siba.models.libro.ubicacion.BeanUbicacion;

import java.util.List;

public class BeanLibro {
        private Long id;
        private String titulo;
        private String isbn;
        private String editorial;
        private BeanUbicacion ubicacion;
        private List<BeanAutor> autores;

        //Datos adicionales
        private int ejemplares;
        private String fileName;

        private byte[] file;

        public BeanLibro(Long id, String titulo, String isbn, String editorial, BeanUbicacion ubicacion, List<BeanAutor> autores, int ejemplares, byte[] file) {
                this.id = id;
                this.titulo = titulo;
                this.isbn = isbn;
                this.editorial = editorial;
                this.ubicacion = ubicacion;
                this.autores = autores;
                this.ejemplares = ejemplares;
                this.file = file;
        }

        public BeanLibro() {
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getTitulo() {
                return titulo;
        }

        public void setTitulo(String titulo) {
                this.titulo = titulo;
        }

        public String getIsbn() {
                return isbn;
        }

        public void setIsbn(String isbn) {
                this.isbn = isbn;
        }

        public String getEditorial() {
                return editorial;
        }

        public void setEditorial(String editorial) {
                this.editorial = editorial;
        }

        public BeanUbicacion getUbicacion() {
                return ubicacion;
        }

        public void setUbicacion(BeanUbicacion ubicacion) {
                this.ubicacion = ubicacion;
        }

        public List<BeanAutor> getAutores() {
                return autores;
        }

        public void setAutores(List<BeanAutor> autores) {
                this.autores = autores;
        }
        public byte[] getFile() {
                return file;
        }

        public void setFile(byte[] file) {
                this.file = file;
        }

        public int getEjemplares() {
                return ejemplares;
        }

        public void setEjemplares(int ejemplares) {
                this.ejemplares = ejemplares;
        }

        public String getFileName() {
                return fileName;
        }

        public void setFileName(String fileName) {
                this.fileName = fileName;
        }
}
