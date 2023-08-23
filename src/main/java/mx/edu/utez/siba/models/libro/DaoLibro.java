package mx.edu.utez.siba.models.libro;


import mx.edu.utez.siba.models.libro.autor.BeanAutor;
import mx.edu.utez.siba.models.libro.autor.DaoAutor;
import mx.edu.utez.siba.models.libro.ejemplar.BeanEjemplar;
import mx.edu.utez.siba.models.libro.ubicacion.BeanUbicacion;
import mx.edu.utez.siba.models.sala.DaoSala;
import mx.edu.utez.siba.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;


public class DaoLibro{
    private Connection conn;
    private CallableStatement cstm;
    private ResultSet rs;


    public List<BeanLibro> findAll(int inicio, int limite) {
        List<BeanLibro> libros = new ArrayList<>();
        try {
            conn = new MySQLConnection().getConnection();
            String query = "call ver_libros(?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setInt(1, inicio);
            cstm.setInt(2, limite);
            cstm.execute();
            rs = cstm.getResultSet();

            while (rs.next()) {
                BeanLibro libro = new BeanLibro();
                libro.setId(rs.getLong("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setEjemplares(rs.getInt("ejemplares"));

                BeanUbicacion ubicacion = new BeanUbicacion();
                ubicacion.setPasillo(rs.getInt("pasillo"));
                ubicacion.setSeccion(rs.getInt("seccion"));
                ubicacion.setEstante(rs.getString("estante"));

                libro.setUbicacion(ubicacion);



                libros.add(libro);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoLibro.class.getName())
                    .log(Level.SEVERE, "Error findAll" + e.getSQLState());
        } finally {
            close();
        }


        for(BeanLibro libro: libros){
            List<BeanAutor> autores = new DaoAutor().autores(libro.getId());
            libro.setAutores(autores);
        }

        return libros;
    }

    public int count(){
        int total = 0;
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call contar_libros();";
            cstm = conn.prepareCall(query);
            cstm.execute();
            rs = cstm.getResultSet();

            if(rs.next()){
                total = rs.getInt(1);
            }
        }catch(SQLException e){

        }finally {
            close();
        }
        return total;
    }

    public BeanLibro findOne(Long id){
        BeanLibro libro = new BeanLibro();
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call get_libro(?);";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);
            cstm.execute();
            rs = cstm.getResultSet();

            if (rs.next()){
                libro.setId(rs.getLong("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setIsbn(rs.getString("isbn"));

                BeanUbicacion ubicacion = new BeanUbicacion();
                ubicacion.setId(rs.getLong("id_ub"));
                ubicacion.setPasillo(rs.getInt("pasillo"));
                ubicacion.setSeccion(rs.getInt("seccion"));
                ubicacion.setEstante(rs.getString("estante"));

                libro.setUbicacion(ubicacion);

            }

        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error findOne" + e.getSQLState());
        }finally{
            close();
        }

        List<BeanAutor> autores = new DaoAutor().autores(libro.getId());
        libro.setAutores(autores);

        return libro;
    }

    public String  save(BeanLibro libro, BeanEjemplar ejemplar, String autoreIds){
        String mensaje = "algo falla";
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call Insertar_Libro(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setString(1, libro.getTitulo());
            cstm.setString(2, libro.getIsbn());
            cstm.setString(3, libro.getEditorial());
            cstm.setLong(4, libro.getUbicacion().getId());
            cstm.setLong(5, ejemplar.getId_ejemplar());
            cstm.setString(6, ejemplar.getObservaciones());
            cstm.setString(7, autoreIds);

            if(libro.getFile() != null && libro.getFileName() != null){
                cstm.setString(8, libro.getFileName());
                cstm.setBytes(9, libro.getFile());
            }else{
                cstm.setNull(8, Types.VARCHAR);
                cstm.setNull(9, Types.BLOB);
            }

            cstm.registerOutParameter(10, Types.VARCHAR);
            cstm.execute();


            mensaje = cstm.getString(10);


        }catch (SQLException e){
            Logger.getLogger(DaoLibro.class.getName())
                    .log(Level.SEVERE, "Error save " + e.getMessage());
        }finally {
            close();
        }
        return mensaje;
    }

    public String update(BeanLibro libro, BeanEjemplar ejemplar){
        String mensaje = "algo falla";
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call actualizar_libro(?, ?, ?, ?, ?, ?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, libro.getId());
            cstm.setString(2, libro.getTitulo());
            cstm.setString(3, libro.getEditorial());
            cstm.setLong(4, libro.getUbicacion().getId());


            if(libro.getFile() != null && !libro.getFileName().isEmpty()){
                cstm.setString(5, libro.getFileName());
                cstm.setBytes(6, libro.getFile());
            }else{
                cstm.setNull(5, Types.VARCHAR);
                cstm.setNull(6, Types.BLOB);
            }


            cstm.registerOutParameter(7, Types.VARCHAR);
            cstm.execute();


            mensaje = cstm.getString(7);


        }catch (SQLException e){
            Logger.getLogger(DaoLibro.class.getName())
                    .log(Level.SEVERE, "Error Update " + e.getMessage());
        }finally {
            close();
        }
        return mensaje;
    }

    public List<BeanLibro> search(String palabra){
        List<BeanLibro> libros = new ArrayList<>();
        try {
            conn = new MySQLConnection().getConnection();
            String query = "call buscar_libros(?);";
            cstm = conn.prepareCall(query);
            cstm.setString(1, palabra);
            cstm.execute();
            rs = cstm.getResultSet();

            while (rs.next()) {
                BeanLibro libro = new BeanLibro();
                libro.setId(rs.getLong("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setEjemplares(rs.getInt("ejemplares"));

                BeanUbicacion ubicacion = new BeanUbicacion();
                ubicacion.setPasillo(rs.getInt("pasillo"));
                ubicacion.setSeccion(rs.getInt("seccion"));
                ubicacion.setEstante(rs.getString("estante"));

                libro.setUbicacion(ubicacion);



                libros.add(libro);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoLibro.class.getName())
                    .log(Level.SEVERE, "Error search" + e.getSQLState());
        } finally {
            close();
        }


        for(BeanLibro libro: libros){
            List<BeanAutor> autores = new DaoAutor().autores(libro.getId());
            libro.setAutores(autores);
        }

        return libros;
    }

    public String delete(Long id){

        String mensaje = ":(";
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call eliminar_libro(?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);
            cstm.registerOutParameter(2, Types.VARCHAR);
            cstm.executeUpdate();

            mensaje = cstm.getString(2);

        }catch(SQLException e){
            Logger.getLogger(DaoLibro.class.getName())
                    .log(Level.SEVERE, "Error delete " + e.getMessage());
        }finally{
            close();
        }
        return mensaje;
    }

    public BeanLibro findFile(long id) {
        BeanLibro libro = null;
        try {
            conn = new MySQLConnection().getConnection();
            String query = "call findFile(?);";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);
            cstm.execute();
            rs = cstm.getResultSet();
            if (rs.next()) {
                libro = new BeanLibro();
                libro.setFileName(rs.getString("file_name"));
                libro.setFile(rs.getBytes("file"));
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoLibro.class.getName())
                    .log(Level.SEVERE, "ERROR findFile" + e.getMessage());
        } finally {
            close();
        }
        return libro;
    }

    //Mostar los libros de un autor
    public List<BeanLibro> libros(Long id_autor) {
        List<BeanLibro> libros = new ArrayList<>();

        try {
            conn = new MySQLConnection().getConnection();
            String query = "call libros(?);";
            cstm = conn.prepareCall(query);

            cstm.setLong(1, id_autor);

            cstm.execute();
            rs = cstm.getResultSet();

            while (rs.next()) {
                BeanLibro libro = new BeanLibro();

                libro.setTitulo(rs.getString("titulo"));

                libros.add(libro);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error FindAutores" + e.getMessage());
        } finally {
            close();
        }
        return libros;
    }


    public void close(){
        try {
            if (conn != null) conn.close();
            if (cstm != null) cstm.close();
            if (rs != null) rs.close();
        }catch (SQLException e){
            Logger.getLogger(DaoLibro.class.getName())
                    .log(Level.SEVERE, "Error closeConnection" + e.getMessage());
        }
    }
}
