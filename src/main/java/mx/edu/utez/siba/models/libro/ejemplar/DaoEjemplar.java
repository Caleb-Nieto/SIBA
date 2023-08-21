package mx.edu.utez.siba.models.libro.ejemplar;

import mx.edu.utez.siba.models.libro.BeanLibro;
import mx.edu.utez.siba.models.repository.DaoRepository;
import mx.edu.utez.siba.models.sala.DaoSala;
import mx.edu.utez.siba.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DaoEjemplar{
    private Connection conn;
    private CallableStatement cstm;
    private ResultSet rs;


    public List<BeanEjemplar> findAll(Long id_libro, int inicio, int limite) {
        List<BeanEjemplar> ejemplares = new ArrayList<>();
        try {
            conn = new MySQLConnection().getConnection();
            String query = "call consultar_ejemplares(?, ?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id_libro);
            cstm.setInt(2, inicio);
            cstm.setInt(3, limite);
            cstm.execute();
            rs = cstm.getResultSet();

            while (rs.next()) {
                BeanEjemplar ejemplar = new BeanEjemplar();
                ejemplar.setId_ejemplar(rs.getLong("id_ejemplar"));
                ejemplar.setEjemplar(rs.getInt("ejemplar"));
                ejemplar.setObservaciones(rs.getString("observaciones"));

                BeanLibro l = new BeanLibro();
                l.setId(rs.getLong("id_libro"));

                ejemplar.setLibro(l);

                ejemplares.add(ejemplar);

            }
        } catch (SQLException e) {
            Logger.getLogger(DaoEjemplar.class.getName())
                    .log(Level.SEVERE, "Error findAll " + e.getSQLState());
        } finally {
            close();
        }
        return ejemplares;
    }


    public BeanEjemplar findOne(Long id) {
        try {
            conn = new MySQLConnection().getConnection();
            String query = "call get_ejemplar(?)";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);
            cstm.execute();
            rs = cstm.getResultSet();
            BeanEjemplar ejemplar = new BeanEjemplar();
            if (rs.next()) {
                ejemplar.setId_ejemplar(rs.getLong("id_ejemplar"));
                ejemplar.setObservaciones(rs.getString("observaciones"));
                BeanLibro libro = new BeanLibro();
                ejemplar.setLibro(libro);
            }
            return ejemplar;
        } catch (SQLException e) {
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error findOne" + e.getSQLState());
        } finally {
            close();
        }
        return null;
    }


    public String save(BeanEjemplar object) {
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call Insertar_ejemplar(?, ?, ?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, object.getId_ejemplar());
            cstm.setString(2, object.getObservaciones());
            cstm.setLong(3, object.getLibro().getId());
            cstm.registerOutParameter(4, Types.VARCHAR);
            cstm.execute();

            String mensaje = cstm.getString(4);
            return  mensaje;
        }catch(SQLException e){
            Logger.getLogger(DaoEjemplar.class.getName())
                    .log(Level.SEVERE, "Error save " + e.getMessage());
        }finally{
            close();
        }
        return null;
    }


    public String update(BeanEjemplar object) {

        try {
            conn = new MySQLConnection().getConnection();
            String query = "call actualizar_ejemplar(?, ?, ?)";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, object.getId_ejemplar());
            cstm.setString(2, object.getObservaciones());
            cstm.registerOutParameter(3, Types.VARCHAR);
            cstm.execute();
            String mensaje = cstm.getString(3);

            return mensaje;

        } catch (SQLException e) {
            Logger.getLogger(DaoEjemplar.class.getName())
                    .log(Level.SEVERE, "Error update " + e.getMessage());
        } finally {
            close();
        }
        return null;
    }


    public String delete(Long id) {
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call eliminar_ejemplar(?, ?)";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);
            cstm.registerOutParameter(2, Types.VARCHAR);
            cstm.executeUpdate();
            String mensaje = cstm.getString(2);
            return mensaje;
        }catch(SQLException e){
            Logger.getLogger(DaoEjemplar.class.getName())
                    .log(Level.SEVERE, "Error delete " + e.getMessage());
        }finally{
            close();
        }
        return null;
    }

    public int count(){
        int total = 0;
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call contar_ejemplares()";
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

    public List<BeanEjemplar> search(String palabra){
        List<BeanEjemplar> ejemplares = new ArrayList<>();
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call buscar_ejemplar(?)";
            cstm = conn.prepareCall(query);
            cstm.setString(1, palabra);

            cstm.execute();
            rs = cstm.getResultSet();
            while(rs.next()){
                BeanEjemplar ejemplar = new BeanEjemplar();
                ejemplar.setId_ejemplar(rs.getLong("id_ejemplar"));
                ejemplar.setObservaciones(rs.getString("observaciones"));

                BeanLibro libro = new BeanLibro();
                libro.setId(rs.getLong("id_libro"));//nombre columna
                ejemplar.setLibro(libro);
            }
            return ejemplares;
        }catch(SQLException e){
            Logger.getLogger(DaoEjemplar.class.getName())
                    .log(Level.SEVERE, "Error search" + e.getMessage());
        }finally {
            close();
        }
        return null;
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
