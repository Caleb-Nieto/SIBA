package mx.edu.utez.siba.models.libro.ubicacion;

import mx.edu.utez.siba.models.repository.DaoRepository;
import mx.edu.utez.siba.models.sala.DaoSala;
import mx.edu.utez.siba.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoUbicacion implements DaoRepository<BeanUbicacion> {

    private Connection conn;
    private CallableStatement cstm;
    private ResultSet rs;

    @Override
    public List<BeanUbicacion> findAll(int inicio, int limite){
        List<BeanUbicacion> ubicaciones = null;
        try{
            ubicaciones = new ArrayList<>();
            conn = new MySQLConnection().getConnection();
            String query = "call ver_ubicaciones(?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setInt(1, inicio);
            cstm.setInt(2,limite);
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
        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error findAll" + e.getSQLState());
        }finally{
            close();
        }
        return ubicaciones;
    }

    @Override
    public BeanUbicacion findOne(Long id){
        BeanUbicacion ubicacion = new BeanUbicacion();
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call get_ubicacion(?)";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);
            cstm.execute();

            rs = cstm.getResultSet();

            if (rs.next()){
                ubicacion.setId(rs.getLong("id"));
                ubicacion.setPasillo(rs.getInt("pasillo"));
                ubicacion.setSeccion(rs.getInt("seccion"));
                ubicacion.setEstante(rs.getString("estante"));
            }

        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error findOne" + e.getSQLState());
        }finally{
            close();
        }
        return ubicacion;
    }

    @Override
    public String save(BeanUbicacion object){
        String mensaje = ":(";
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call insertar_ubicacion(?, ?, ?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setInt(1, object.getPasillo());
            cstm.setInt(2, object.getSeccion());
            cstm.setString(3, object.getEstante());
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
    public String update(BeanUbicacion object){
        String mensaje = ":(";
        try {
            conn = new MySQLConnection().getConnection();
            String query = "call actualizar_ubicacion(?, ?, ?, ?, ?)";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, object.getId());
            cstm.setInt(2, object.getPasillo());
            cstm.setInt(3, object.getSeccion());
            cstm.setString(4, object.getEstante());
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
            String query = "call eliminar_ubicacion(?, ?)";
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

    public List<BeanUbicacion> search(String palabra){
        List<BeanUbicacion> ubicaciones = new ArrayList<>();
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call buscar_ubicaciones(?)";
            cstm = conn.prepareCall(query);
            cstm.setString(1, palabra);

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

        }catch(SQLException e){
            Logger.getLogger(DaoSala.class.getName())
                    .log(Level.SEVERE, "Error search" + e.getMessage());
        }finally {
            close();
        }
        return ubicaciones;
    }
    public int count(){
        int total = 0;
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call contar_ubicaciones();";
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
