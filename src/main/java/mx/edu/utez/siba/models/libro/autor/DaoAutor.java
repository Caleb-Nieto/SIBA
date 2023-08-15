package mx.edu.utez.siba.models.libro.autor;

import mx.edu.utez.siba.models.libro.BeanLibro;
import mx.edu.utez.siba.models.repository.DaoRepository;
import mx.edu.utez.siba.models.sala.BeanSala;
import mx.edu.utez.siba.models.sala.DaoSala;
import mx.edu.utez.siba.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoAutor implements DaoRepository<BeanAutor> {

    private Connection conn;
    private CallableStatement cstm;
    private ResultSet rs;

    @Override
    public List<BeanAutor> findAll(int inicio, int limite) {
        List<BeanAutor> autores = new ArrayList<>();
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call ver_autores(?, ?)";
            cstm = conn.prepareCall(query);
            cstm.setInt(1, inicio);
            cstm.setInt(2, limite);

            cstm.execute();

            rs = cstm.getResultSet();

            while(rs.next()){
                BeanAutor autor = new BeanAutor();
                autor.setId_autor(rs.getLong("id_autor"));
                autor.setNombre(rs.getString("nombre"));
                autor.setApellido_paterno(rs.getString("apellido_paterno"));
                autor.setApellido_materno(rs.getString("apellido_materno"));

                autores.add(autor);

            }

        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error findAll  " + e.getMessage());
        } finally {
           close();
        }

        for(BeanAutor autor: autores){
            List<BeanLibro> libros = libros(autor.getId_autor());
            autor.setLibros(libros);
        }

        return autores;
    }

    public List<BeanLibro> libros(Long id_autor) {
        List<BeanLibro> libros = new ArrayList<>();

        try {
            conn = new MySQLConnection().getConnection();
            String query = "call libros(?)";
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

    @Override
    public BeanAutor findOne(Long id){
        BeanAutor autor = new BeanAutor();
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call get_autor(?)";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);
            cstm.execute();
            rs = cstm.getResultSet();

            if (rs.next()){
                autor.setId_autor(rs.getLong("id_autor"));
                autor.setNombre(rs.getString("nombre"));
                autor.setApellido_paterno(rs.getString("apellido_paterno"));
                autor.setApellido_materno(rs.getString("apellido_materno"));
            }

        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error findOne" + e.getSQLState());
        }finally{
            close();
        }
        return autor;
    }

    @Override
    public String save(BeanAutor object){
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call insertar_autor(?, ?, ?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setString(1, object.getNombre());
            cstm.setString(2, object.getApellido_paterno());
            cstm.setString(3, object.getApellido_materno());
            cstm.registerOutParameter(4, Types.VARCHAR);
            cstm.execute();

            String mensaje = cstm.getString(4);
            return  mensaje;
        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error save " + e.getMessage());
        }finally{
            close();
        }
        return null;
    }

    @Override
    public String update(BeanAutor object){
        String mensaje = "malo";

        try {
            conn = new MySQLConnection().getConnection();
            String query = "call actulizar_autor(?, ?, ?, ?, ?)";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, object.getId_autor());
            cstm.setString(2, object.getNombre());
            cstm.setString(3, object.getGetApellido_paterno());
            cstm.setString(4, object.getApellido_materno());
            cstm.registerOutParameter(5, Types.VARCHAR);
            cstm.execute();

            mensaje = cstm.getString(5);

        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error update " + e.getMessage());
        }finally {
            close();
        }
        return mensaje;
    }

    @Override
    public String delete(Long id){

        String mensaje = ":(";
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call eliminar_autor(?, ?)";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);
            cstm.registerOutParameter(2, Types.VARCHAR);
            cstm.executeUpdate();

            mensaje = cstm.getString(2);

        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error delete " + e.getMessage());
        }finally{
            close();
        }
        return mensaje;
    }

    public List<BeanAutor> search(String palabra){
        List<BeanAutor> autores = new ArrayList<>();
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call buscar_autores(?)";
            cstm = conn.prepareCall(query);
            cstm.setString(1, palabra);

            cstm.execute();
            rs = cstm.getResultSet();
            while(rs.next()){
                BeanAutor autor = new BeanAutor();
                autor.setId_autor(rs.getLong("id_autor"));
                autor.setNombre(rs.getString("nombre"));
                autor.setApellido_paterno(rs.getString("apellido_paterno"));
                autor.setApellido_materno(rs.getString("apellido_materno"));


                autores.add(autor);
            }
        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error search" + e.getMessage());
        }finally {
            close();
        }
        return autores;
    }

    public int count(){
        int total = 0;
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call contar_autores()";
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

    public void close(){
        try {
            if (conn != null) conn.close();
            if (cstm != null) cstm.close();
            if (rs != null) rs.close();
        }catch (SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error closeConnection" + e.getMessage());
        }
    }

}
