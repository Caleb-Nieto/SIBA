package mx.edu.utez.siba.models.sala;

import mx.edu.utez.siba.models.repository.DaoRepository;
import mx.edu.utez.siba.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoSala implements DaoRepository<BeanSala>{
    private Connection conn;
    private CallableStatement cstm;
    private ResultSet rs;

    @Override
    public List<BeanSala> findAll(int inicio, int limite){
        List<BeanSala> salas = null;
        try{
            salas = new ArrayList<>();
            conn = new MySQLConnection().getConnection();
            String query = "call ver_salas(?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setInt(1, inicio);
            cstm.setInt(2,limite);
            cstm.execute();
            rs = cstm.getResultSet();

            while(rs.next()){
                BeanSala sala = new BeanSala();
                sala.setId_sala(rs.getLong("id_sala"));
                sala.setNombre(rs.getString("nombre"));
                sala.setCapacidad(rs.getString("capacidad"));
                sala.setDescripcion(rs.getString("descripcion"));
                if (rs.getString("prestamo") != null && rs.getString("prestamo").equals("Activo") && !rs.getString("prestamo").equals("Finalizado")){
                    sala.setPrestamo("En prestamo");
                }
                salas.add(sala);
            }
        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error findAll" + e.getSQLState());
        }finally{
            close();
        }
        return salas;
    }
    @Override
    public BeanSala findOne(Long id){
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call get_sala(?)";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);
            cstm.execute();
            rs = cstm.getResultSet();
            BeanSala sala = new BeanSala();
            if (rs.next()){
                sala.setId_sala(rs.getLong("id_sala"));
                sala.setNombre(rs.getString("nombre"));
                sala.setCapacidad(rs.getString("capacidad"));
                sala.setDescripcion(rs.getString("descripcion"));
            }
            return sala;
        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error findOne" + e.getSQLState());
        }finally{
            close();
        }
        return null;
    }
    @Override
    public String save(BeanSala object){
        String mensaje = ":'(";
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call insertar_sala(?, ?, ?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setString(1, object.getNombre());
            cstm.setString(2, object.getCapacidad());
            cstm.setString(3, object.getDescripcion());
            cstm.registerOutParameter(4, Types.VARCHAR);
            cstm.execute();

            mensaje = cstm.getString(4);

        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error save " + e.getMessage());
        }finally{
            close();
        }
        return  mensaje;
    }
    @Override
    public String update(BeanSala object){
        try {
            conn = new MySQLConnection().getConnection();
            String query = "call actualizar_sala(?, ?, ?, ?, ?)";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, object.getId_sala());
            cstm.setString(2, object.getNombre());
            cstm.setString(3, object.getCapacidad());
            cstm.setString(4, object.getDescripcion());
            cstm.registerOutParameter(5, Types.VARCHAR);
            cstm.execute();

            return cstm.getString(5);
        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error update " + e.getMessage());
        }finally {
            close();
        }
        return null;
    }
    @Override
    public String delete(Long id){
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call eliminar_sala(?, ?)";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);
            cstm.registerOutParameter(2, Types.VARCHAR);
            cstm.executeUpdate();
            return cstm.getString(2);
        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error delete " + e.getMessage());
        }finally{
            close();
        }
        return null;
    }

    public List<BeanSala> search(String palabra, String numero){
        List<BeanSala> salas = new ArrayList<>();
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call buscar_salas(?, ?)";
            cstm = conn.prepareCall(query);
            cstm.setString(1, palabra);
            cstm.setString(2, numero);
            cstm.execute();
            rs = cstm.getResultSet();
            while(rs.next()){
                BeanSala sala = new BeanSala();
                sala.setId_sala(rs.getLong("id_sala"));
                sala.setNombre(rs.getString("nombre"));
                sala.setDescripcion(rs.getString("descripcion"));
                sala.setCapacidad(rs.getString("capacidad"));
                if (rs.getString("prestamo") != null && rs.getString("prestamo").equals("Activo") && !rs.getString("prestamo").equals("Finalizado")){
                    sala.setPrestamo("En prestamo");
                }
                salas.add(sala);
            }
            return salas;
        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error search" + e.getMessage());
        }finally {
            close();
        }
        return null;
    }

    public int count(){
        int total = 0;
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call contar_salas();";
            cstm = conn.prepareCall(query);
            cstm.execute();
            rs = cstm.getResultSet();

            if(rs.next()){
                total = rs.getInt(1);
            }


        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error count" + e.getMessage());
        }finally{
            close();
        }
        return total;
    }


    public String hacerPrestamo(Long id, String idu){
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call iniciar_prestamo_sala(?, ?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);
            cstm.setString(2, idu);

            cstm.registerOutParameter(3, Types.VARCHAR);
            cstm.execute();

            return cstm.getString(3);

        }catch (SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error hacer prestamo" + e.getMessage());
        }finally {
            close();
        }
        return "error";
    }
    public String finalizarPrestamo(Long id){
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call finalizar_prestamo_sala(?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);

            cstm.registerOutParameter(2, Types.VARCHAR);
            cstm.execute();

            return cstm.getString(2);

        }catch (SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error finalizar prestamo " + e.getMessage());
        }finally {
            close();
        }

        return "error";
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
