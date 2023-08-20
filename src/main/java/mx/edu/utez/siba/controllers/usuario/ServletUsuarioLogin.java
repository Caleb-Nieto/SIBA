package mx.edu.utez.siba.controllers.usuario;


import mx.edu.utez.siba.models.usuario.BeanAlumno;
import mx.edu.utez.siba.models.usuario.BeanDocente;
import mx.edu.utez.siba.models.usuario.BeanUsuario;
import mx.edu.utez.siba.models.usuario.DaoUsuario;
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
        urlPatterns = {"/api/login", "/api/logout", "/api/register-view", "/api/user/save", "/send-email"})

public class ServletUsuarioLogin extends HttpServlet {
    private String action;
    private String urlRedirect;
    private String  nombre, ap, am, correo, contrasenia, telefono,
            no_trabajador, division,
            matricula, grado, grupo, carrera;
    private BeanUsuario usuario;
    private String mensaje;
    private BeanAlumno alumno;
    private BeanDocente docente;
    HttpSession session;
    UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        action = req.getServletPath();
        switch (action){
            case "/api/login":
                urlRedirect = "/index.jsp";
                break;
            case "/api/register-view":
                urlRedirect = "/registro.jsp";
                break;
            case "/api/logout":
                session = req.getSession();
                session.invalidate();
                urlRedirect = "/index.jsp";
                break;
        }
        req.getRequestDispatcher(urlRedirect).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        action =req.getServletPath();
        switch (action){
            case "/api/login":
                correo = req.getParameter("correo");
                contrasenia = req.getParameter("contrasenia");

                try{
                    usuario = usuarioService.login(correo, contrasenia);

                    if (usuario != null) {
                        session = req.getSession();
                        if(usuario instanceof BeanAlumno){
                            alumno = (BeanAlumno) usuario;
                            session.setAttribute("user", alumno);
                            session.setAttribute("rol", alumno.getRol());

                        }else if(usuario instanceof BeanDocente){
                            docente = (BeanDocente) usuario;
                            session.setAttribute("user", docente);
                            session.setAttribute("rol", docente.getRol());

                        }else{
                            session.setAttribute("user", usuario);
                            session.setAttribute("rol", usuario.getRol());
                        }
                        if (usuario.getRol() == 1) {
                            urlRedirect = "/api/libro/libros";
                        } else if (usuario.getRol() == 2) {
                            urlRedirect = "/api/libro/libros";
                        } else if (usuario.getRol() == 3) {
                            urlRedirect = "/api/libro/libros";
                        } else if (usuario.getRol() == 4) {
                            urlRedirect = "/api/libro/libros";
                        }
                    } else {
                        throw new Exception("Credentials mismatch");
                    }
                }catch(Exception e) {

                    urlRedirect = "/api/login?result=false&message=" + URLEncoder
                            .encode("Correo o contraseña incorrecta",
                                    StandardCharsets.UTF_8)+ "&correo="+URLEncoder.encode(correo, StandardCharsets.UTF_8);
                }
                break;
            case "/api/user/save":
                nombre = req.getParameter("nombre").trim();
                ap = req.getParameter("apellido_paterno").trim();
                am = req.getParameter("apellido_materno").trim();
                correo = req.getParameter("correo").trim();
                contrasenia = req.getParameter("contrasenia").trim();
                telefono = req.getParameter("telefono").trim();


                no_trabajador = req.getParameter("no_trabajador");
                division =  req.getParameter("division");

                matricula = req.getParameter("matricula");
                carrera = req.getParameter("carrera");
                grado = req.getParameter("grado");
                grupo = req.getParameter("grupo");

                if(no_trabajador != null && division != null){
                    docente = new BeanDocente(nombre, ap, am, correo, contrasenia, telefono, no_trabajador.trim(), division.trim());

                    mensaje = new DaoUsuario().saveDocente(docente);

                }else if(matricula != null && carrera != null && grado != null && grupo != null){
                    alumno = new BeanAlumno(nombre, ap, am, correo, contrasenia, telefono, matricula.trim(), carrera, Integer.parseInt(grado), grupo.trim());

                    mensaje = new DaoUsuario().saveAlumno(alumno);

                }else{
                    usuario = new BeanUsuario(nombre, ap, am , correo, contrasenia, telefono);

                    mensaje = new DaoUsuario().save(usuario);
                }

                session = req.getSession();

                int rol = 0;

                if(session.getAttribute("rol") instanceof Number){
                     rol = (((Number) session.getAttribute("rol")).intValue());
                }

                if(mensaje.contains("correctamente")){
                    if (rol == 1) {
                        urlRedirect = "/api/usuario/usuarios?result=true&message="+ URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                    } else if(rol == 2){
                        urlRedirect = "/api/libro/libros?result=true&message="+ URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                    }else{
                        urlRedirect = "/api/login?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                    }
                }else{
                    urlRedirect = "/api/register-view?result=false&message="+ URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
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