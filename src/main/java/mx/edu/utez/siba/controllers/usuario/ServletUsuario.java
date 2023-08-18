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

})
public class ServletUsuario extends HttpServlet {
    private String action;
    private String redirect;
    private String id_usuario;
    BeanAlumno alumno;
    BeanDocente docente;
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
                id_usuario = request.getParameter("id_sala");





                if (mensaje.contains("correctamente")){
                    redirect = "/api/usuario/usuarios?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/api/usuario/usuario-view-update?id_libro=" + id_usuario + "&result=false&message=" + URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
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