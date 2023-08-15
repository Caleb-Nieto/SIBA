package mx.edu.utez.siba.controllers.sala;

import mx.edu.utez.siba.models.sala.BeanSala;
import mx.edu.utez.siba.models.sala.DaoSala;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ServletSala", urlPatterns = {
        "/sala/salas", "/sala/sala",
        "/sala/sala-view" , "/sala/save",
        "/sala/sala-view-update", "/sala/update",
        "/sala/delete", "/sala/search"
})
public class ServletSala extends HttpServlet {
    private String action;
    private String redirect = "/sala/salas";
    private String id_sala, nombre, capacidad, descripcion;
    private String mensaje;
    private BeanSala sala;
    private List<BeanSala> salas;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        action = request.getServletPath();
        switch(action){
            case "/sala/salas":
                int pagina = 1;
                int limite = 12;
                if (request.getParameter("page") != null) {
                    pagina = Integer.parseInt(request.getParameter("page"));
                }
                int inicio = (pagina -1) * limite;

                salas = new DaoSala().findAll(inicio, limite);

                request.setAttribute("salas", salas);
                int totalRegistros = new DaoSala().count();
                int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

                request.setAttribute("totalPaginas", totalPaginas);
                request.setAttribute("paginaActual", pagina);

                redirect= "/views/administrador/salas/list_salas.jsp";
                break;
            case "/sala/sala-view":
                redirect = "/views/administrador/salas/agregar_sala.jsp";
                break;
            case "/sala/sala-view-update":
                id_sala = request.getParameter("id_sala");
                sala = new DaoSala().findOne(Long.parseLong(id_sala));
                if (sala != null){
                    request.setAttribute("sala", sala);
                    redirect = "/views/administrador/salas/editar_sala.jsp";
                } else{
                    redirect = "/sala/salas?result=false&message=¡Error! Acción no realizada correctamente";
                }
                break;
                case "/sala/search":
                    String palabra = request.getParameter("palabra").trim();
                    String num = request.getParameter("capacidad").trim();

                    if(palabra.isEmpty()){
                        salas = new DaoSala().search(null, num);
                    }else if(num.isEmpty()){
                        salas = new DaoSala().search(palabra, null);
                    }else{
                        salas = new DaoSala().search(palabra, num);
                    }
                    request.setAttribute("salas", salas);
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
            case "/sala/save":
                nombre = request.getParameter("nombre").trim();
                capacidad = request.getParameter("capacidad").trim();
                descripcion = request.getParameter("descripcion").trim();
                sala = new BeanSala(0L, nombre, capacidad, descripcion);
                mensaje = new DaoSala().save(sala);
                if (mensaje.contains("correctamente")){
                    redirect = "/sala/salas?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/sala/sala-view?result=false&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }
                break;
            case "/sala/update":
                id_sala = request.getParameter("id_sala");
                nombre = request.getParameter("nombre").trim();
                capacidad = request.getParameter("capacidad").trim();
                descripcion = request.getParameter("descripcion").trim();
                sala = new BeanSala(Long.parseLong(id_sala), nombre,capacidad, descripcion);
                mensaje = new DaoSala().update(sala);
                if (mensaje.contains("correctamente")){
                    redirect = "/sala/salas?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/sala/sala-view-update?id_sala=" + id_sala + "&result=false&message=" + URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }

                break;
            case "/sala/delete":
                id_sala = request.getParameter("id_sala");

                mensaje = new DaoSala().delete(Long.parseLong(id_sala));
                if (mensaje.contains("correctamente")){
                    redirect = "/sala/salas?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/sala/salas?result=false&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }
                break;
        }
        response.sendRedirect(request.getContextPath() + redirect);
    }
}
