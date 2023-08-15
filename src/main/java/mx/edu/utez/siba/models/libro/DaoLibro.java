package mx.edu.utez.siba.models.libro;


import mx.edu.utez.siba.models.libro.autor.BeanAutor;
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
            List<BeanAutor> autores = autores(libro.getId());
            libro.setAutores(autores);
        }

        return libros;
    }


    //Mostrar autores por libro, solo para CRUD de libros
    public List<BeanAutor> autores(Long id_libro) {
        List<BeanAutor> autores = new ArrayList<>();

        try {
            conn = new MySQLConnection().getConnection();
            String query = "call autores(?)";
            cstm = conn.prepareCall(query);
            if(id_libro == null){
                cstm.setNull(1, Types.INTEGER);
            }else{
                cstm.setLong(1, id_libro);
            }
            cstm.execute();
            rs = cstm.getResultSet();

            while (rs.next()) {
                BeanAutor autor = new BeanAutor();
                autor.setId_autor(rs.getLong("id_autor"));
                autor.setNombre(rs.getString("nombre"));
                autor.setApellido_materno(rs.getString("apellido_materno"));
                autor.setApellido_paterno(rs.getString("apellido_paterno"));

                autores.add(autor);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoLibro.class.getName())
                    .log(Level.SEVERE, "Error FindAutores" + e.getMessage());
        } finally {
            close();
        }
        return autores;
    }

    public  List<BeanUbicacion> ubicaciones(){
        List<BeanUbicacion> ubicaciones = new ArrayList<>();
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call ubicaciones();";
            cstm = conn.prepareCall(query);
            cstm.execute();

            rs = cstm.getResultSet();

            while(rs.next()){
                BeanUbicacion ubicacion = new BeanUbicacion();

                ubicacion.setId(rs.getLong("id"));
                ubicacion.setPasillo(rs.getInt("pasillo"));
                ubicacion.setSeccion(rs.getInt("seccion"));
                ubicacion.setEstante(rs.getString("estante"));

                ubicaciones.add(ubicacion);
            }

        }catch (SQLException e){
            Logger.getLogger(DaoLibro.class.getName())
                    .log(Level.SEVERE, "Error save " + e.getMessage());
        }

        return ubicaciones;
    }

    public int count(){
        int total = 0;
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call contar_libros()";
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
       return null;
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

    public String update(BeanLibro libro){
        return null;
    }

    public String delete(Long id){

        String mensaje = ":(";
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call eliminar_libro(?, ?)";
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
