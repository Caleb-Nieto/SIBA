package mx.edu.utez.siba.controllers.usuario;

import mx.edu.utez.siba.models.usuario.BeanUsuario;
import mx.edu.utez.siba.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "ServletUsuarioLogin",
        urlPatterns = {"/login", "/logout", "/sigin", "/recover-password", "/send-email"})

public class ServletUsuarioLogin extends HttpServlet {
    String action;
    String urlRedirect = "/get-users";
    HttpSession session;
    UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        action = req.getServletPath();
        switch (action){
            case "/login":
                urlRedirect = "/index.jsp";
                break;
            case "/logout":
                session = req.getSession();
                session.invalidate();
                urlRedirect = "/index.jsp";
        }
        req.getRequestDispatcher(urlRedirect).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        action =req.getServletPath();
        switch (action){
            case "/login":
                String correo = req.getParameter("correo");
                String contrasenia = req.getParameter("contrasenia");
                try{
                    BeanUsuario usuario = usuarioService.login(correo, contrasenia);
                    if (usuario != null) {
                        session = req.getSession();
                        session.setAttribute("usuario", usuario);
                        session.setAttribute("rol", usuario.getRol());

                        if (usuario.getRol() == 1) {
                            urlRedirect = "/libro/libros";
                        } else if (usuario.getRol() == 2) {
                            urlRedirect = "/views/bibliotecario/libros.jsp";
                        } else if (usuario.getRol() == 3) {
                            urlRedirect = "/sala/salas";
                        } else if (usuario.getRol() == 4) {
                            urlRedirect = "/perfil-alumno";
                        } else {
                            urlRedirect = "/list_users.jsp";
                        }
                    } else {
                        throw new Exception("Credentials mismatch");
                    }
                }catch(Exception e) {
                    urlRedirect = "/login?result=false&message=" + URLEncoder
                            .encode("Correo y/o contraseña incorrecta",
                                    StandardCharsets.UTF_8);
                }
                break;
            default:
                session = req.getSession();
                session.invalidate();
                urlRedirect = "/";
                break;
        }
        resp.sendRedirect(req.getContextPath() + urlRedirect);
    }
}