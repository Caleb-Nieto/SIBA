package mx.edu.utez.siba.controllers.usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.siba.models.usuario.BeanAlumno;
import mx.edu.utez.siba.models.usuario.BeanDocente;
import mx.edu.utez.siba.models.usuario.BeanUsuario;
import mx.edu.utez.siba.models.usuario.DaoUsuario;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(name = "ServletUsuario", urlPatterns = {
        "/api/usuario/usuarios", "/api/usuario/delete",
        "/api/usuario/search", "/api/usuario/usuario-view-update",
        "/api/usuario/update"

})
public class ServletUsuario extends HttpServlet {
    private String action;
    private String redirect;
    private String id_usuario;
    BeanAlumno alumno;
    BeanDocente docente;
    private String  nombre, ap, am, correo, contrasenia, telefono,
            no_trabajador, division,
            matricula, grado, grupo, carrera;
    int rol;
    private String mensaje;
    private BeanUsuario usuario;
    private List<BeanUsuario> usuarios;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        action = request.getServletPath();
        switch(action){
            case "/api/usuario/usuarios":
                int pagina = 1;
                int limite = 12;
                if (request.getParameter("page") != null) {
                    pagina = Integer.parseInt(request.getParameter("page"));
                }
                int inicio = (pagina -1) * limite;

                usuarios = new DaoUsuario().findAll(inicio, limite);

                request.setAttribute("usuarios", usuarios);

                int totalRegistros = new DaoUsuario().count();
                int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

                request.setAttribute("totalPaginas", totalPaginas);
                request.setAttribute("paginaActual", pagina);

                redirect= "/views/administrador/usuarios/list_usuarios.jsp";
                break;
            case "/api/usuario/usuario-view-update":
                usuario = new BeanUsuario();


                id_usuario = request.getParameter("id_usuario");

                usuario = new DaoUsuario().findOne(Long.parseLong(id_usuario));


                if (usuario != null){

                    if(usuario instanceof BeanAlumno){
                        alumno = (BeanAlumno) usuario;
                        request.setAttribute("usuario", alumno);

                    }else if(usuario instanceof BeanDocente){
                        docente = (BeanDocente) usuario;
                        request.setAttribute("usuario", docente);

                    }else {
                        request.setAttribute("usuario", usuario);
                    }

                    redirect = "/views/administrador/usuarios/editar_usuario.jsp";
                } else{
                    redirect = "/api/usuario/usuarios?result=false&message=¡Error! Acción no realizada correctamente";
                }
                break;
            case "/api/usuario/search":

                String palabra = request.getParameter("palabra").trim();

                usuarios = new DaoUsuario().search(palabra);

                request.setAttribute("usuarios", usuarios);
                break;
            default:
                System.out.println(action);
        }
        request.getRequestDispatcher(redirect).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        action = request.getServletPath();
        switch (action){
            case "/api/usuario/update":
                id_usuario = request.getParameter("id_usuario");

                nombre = request.getParameter("nombre").trim();
                ap = request.getParameter("apellido_paterno").trim();
                am = request.getParameter("apellido_materno").trim();
                telefono = request.getParameter("telefono").trim();
                rol = Integer.parseInt(request.getParameter("rol"));


                if(rol == 3){
                    no_trabajador = request.getParameter("no_trabajador");
                    division =  request.getParameter("division");

                    docente = new BeanDocente(Long.parseLong(id_usuario),nombre, ap, am, telefono, division.trim());

                    mensaje = new DaoUsuario().update(docente);

                }else if(rol == 4){
                    matricula = request.getParameter("matricula");
                    carrera = request.getParameter("carrera");
                    grado = request.getParameter("grado");
                    grupo = request.getParameter("grupo");

                    alumno = new BeanAlumno(Long.parseLong(id_usuario), nombre, ap, am, telefono, carrera, Integer.parseInt(grado), grupo.trim());

                    mensaje = new DaoUsuario().update(alumno);

                }else{
                    usuario = new BeanUsuario(Long.parseLong(id_usuario), nombre, ap, am, telefono);

                    mensaje = new DaoUsuario().update(usuario);
                }





                if (mensaje.contains("correctamente")){
                    redirect = "/api/usuario/usuarios?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/api/usuario/usuario-view-update?id_usuario=" + id_usuario + "&result=false&message=" + URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }

                break;
            case "/api/usuario/delete":
                id_usuario = request.getParameter("id_usuario");

                mensaje = new DaoUsuario().delete(Long.parseLong(id_usuario));

                if (mensaje.contains("correctamente")){
                    redirect = "/api/usuario/usuarios?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/api/usuario/usuarios?result=false&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }
                break;
        }
        response.sendRedirect(request.getContextPath() + redirect);
    }
}