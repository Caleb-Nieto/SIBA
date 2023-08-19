package mx.edu.utez.siba.service;

import  mx.edu.utez.siba.models.usuario.BeanUsuario;
import  mx.edu.utez.siba.models.usuario.DaoUsuario;

public class UsuarioService {
    DaoUsuario usuario = new DaoUsuario();
    public BeanUsuario login(String correo, String contrasenia){

        return usuario.loadUserByEmailAndPassword(correo, contrasenia);
    }
}
