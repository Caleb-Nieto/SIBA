package mx.edu.utez.siba.models.usuario;

import mx.edu.utez.siba.models.repository.DaoRepository;
import mx.edu.utez.siba.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoUsuario implements DaoRepository<BeanUsuario> {
    private Connection conn;
    private CallableStatement cstm;
    private ResultSet rs;

    public BeanUsuario loadUserByEmailAndPassword (String correo, String contrasenia){
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call cargar_usuario(?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setString(1, correo);
            cstm.setString(2, contrasenia);
            cstm.execute();
            rs = cstm.getResultSet();
            if (rs.next()){
                Long id = rs.getLong("id_usuario");
                String nombre = rs.getString("nombre");
                String ap = rs.getString("apellido_paterno");
                String am = rs.getString("apellido_materno");
                String tel = rs.getString("telefono");
                int rol = rs.getInt("rol");


                if(rol == 4){
                    String matricula = rs.getString("matricula");
                    String carrera = rs.getString("carrera");
                    int grado = rs.getInt("grado");
                    String grupo = rs.getString("grupo");

                    return new BeanAlumno(id, nombre, ap, am, null, null, tel, rol, matricula, carrera, grado, grupo);
                }else if(rol == 3){
                    String no = rs.getString("no_trabajador");
                    String division = rs.getString("division");

                    return new BeanDocente(id, nombre, ap, am, null, null, tel, rol, no, division);
                }else{
                    return new BeanUsuario(id, nombre, ap, am, null, null, tel, rol);
                }
            }
            return null;
        }catch (SQLException e){
            Logger.getLogger(DaoUsuario.class.getName())
                    .log(Level.SEVERE, "Error LoadUser " + e.getMessage());
            return null;
        }finally{
            close();
        }
    }


    @Override
    public List<BeanUsuario> findAll(int inicio ,int limite) {
        List<BeanUsuario> usuarios = null;
        try {
            usuarios = new ArrayList<>();
            conn = new MySQLConnection().getConnection();
            String query = "call ver_usuarios(? , ?);";
            cstm = conn.prepareCall(query);
            cstm.setInt(1, inicio);
            cstm.setInt(2, limite);
            cstm.execute();
            rs = cstm.getResultSet();
            while (rs.next()) {
                BeanUsuario usuario = new BeanUsuario();
                usuario.setId_usuario(rs.getLong("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido_paterno(rs.getString("apellido_paterno"));
                usuario.setApellido_materno(rs.getString("apellido_materno"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setRol(rs.getInt("rol"));

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoUsuario.class.getName())
                    .log(Level.SEVERE, "Error findAll" + e.getMessage());
        } finally {
            close();
        }
        return usuarios;
    }

    @Override
    public BeanUsuario findOne(Long id) {
        try {
            conn = new MySQLConnection().getConnection();
            String query = "call encontrar_usuario(?);";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);
            cstm.execute();
            rs = cstm.getResultSet();
            BeanUsuario usuario = new BeanUsuario();
            if (rs.next()){
                usuario.setId_usuario(rs.getLong("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido_paterno(rs.getString("apellido_paterno"));
                usuario.setApellido_materno(rs.getString("apellido_materno"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContrasenia(rs.getString("contrasenia"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setRol(rs.getInt("rol"));
            }
            return usuario;
        }catch(SQLException e){
            Logger.getLogger(DaoUsuario.class.getName()).log(Level.SEVERE, "Error findOne" + e.getMessage());
        } finally {
            close();
        }
        return null;
    }

    @Override
    public String save(BeanUsuario object) {

        String mensaje = ":(";
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call insertar_bibliotecario(?, ?, ?, ?, ?, ?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setString(1, object.getNombre());
            cstm.setString(2, object.getApellido_paterno());
            cstm.setString(3, object.getApellido_materno());
            cstm.setString(4, object.getCorreo());
            cstm.setString(5, object.getContrasenia());
            cstm.setString(6, object.getTelefono());
            cstm.registerOutParameter(7, Types.VARCHAR);
            cstm.execute();

            mensaje = cstm.getString(7);
        }catch(SQLException e){
            Logger.getLogger(DaoUsuario.class.getName())
                    .log(Level.SEVERE, "Error saveBiblio " + e.getMessage());
        }finally{
            close();
        }
        return mensaje;
    }
    public String saveDocente(BeanDocente object) {

        String mensaje = ":(";
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call insertar_docente(?, ?, ?, ?, ?, ?, ?, ?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setString(1, object.getNombre());
            cstm.setString(2, object.getApellido_paterno());
            cstm.setString(3, object.getApellido_materno());
            cstm.setString(4, object.getCorreo());
            cstm.setString(5, object.getContrasenia());
            cstm.setString(6, object.getTelefono());
            cstm.setString(7, object.getNo_trabajador());
            cstm.setString(8, object.getDivision());


            cstm.registerOutParameter(9, Types.VARCHAR);
            cstm.execute();

            mensaje = cstm.getString(9);
        }catch(SQLException e){
            Logger.getLogger(DaoUsuario.class.getName())
                    .log(Level.SEVERE, "Error saveDocente " + e.getMessage());
        }finally{
            close();
        }
        return mensaje;
    }

    public String saveAlumno(BeanAlumno object) {

        String mensaje = ":(";
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call insertar_alumno(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            cstm = conn.prepareCall(query);
            cstm.setString(1, object.getNombre());
            cstm.setString(2, object.getApellido_paterno());
            cstm.setString(3, object.getApellido_materno());
            cstm.setString(4, object.getCorreo());
            cstm.setString(5, object.getContrasenia());
            cstm.setString(6, object.getTelefono());
            cstm.setString(7, object.getMatricula());
            cstm.setString(8, object.getCarrera());
            cstm.setInt(9, object.getGrado());
            cstm.setString(10, object.getGrupo());


            cstm.registerOutParameter(11, Types.VARCHAR);
            cstm.execute();

            mensaje = cstm.getString(11);
        }catch(SQLException e){
            Logger.getLogger(DaoUsuario.class.getName())
                    .log(Level.SEVERE, "Error saveAlumno " + e.getMessage());
        }finally{
            close();
        }
        return mensaje;
    }

    public List<BeanUsuario> search(String palabra){
        List<BeanUsuario> usuarios = new ArrayList<>();
        try {
            conn = new MySQLConnection().getConnection();
            String query = "call buscar_usuarios(?);";
            cstm = conn.prepareCall(query);
            cstm.setString(1, palabra);
            cstm.execute();
            rs = cstm.getResultSet();

           BeanUsuario usuario = new BeanUsuario();
            if (rs.next()){
                usuario.setId_usuario(rs.getLong("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido_paterno(rs.getString("apellido_paterno"));
                usuario.setApellido_materno(rs.getString("apellido_materno"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setRol(rs.getInt("rol"));

                usuarios.add(usuario);

            }

        }catch(SQLException e){
            Logger.getLogger(DaoUsuario.class.getName()).log(Level.SEVERE, "Error search" + e.getMessage());
        } finally {
            close();
        }
        return usuarios;
    }

    @Override
    public String update(BeanUsuario object) {
        return null;
    }

    @Override
    public String delete(Long id) {
        try{
            conn = new MySQLConnection().getConnection();
            String query = "call Eliminar_Usuario(?, ?)";
            cstm = conn.prepareCall(query);
            cstm.setLong(1, id);
            cstm.registerOutParameter(2, Types.VARCHAR);
            cstm.execute();

            String mensaje = cstm.getString(2);
            return mensaje;
        }catch(SQLException e){
            Logger.getLogger(DaoUsuario.class.getName())
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
            String query = "call contar_usuarios();";
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
            Logger.getLogger(DaoUsuario.class.getName())
                    .log(Level.SEVERE, "Error closeConnection" + e.getMessage());
        }
    }
}
