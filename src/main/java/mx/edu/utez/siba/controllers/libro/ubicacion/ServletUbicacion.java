package mx.edu.utez.siba.controllers.libro.ubicacion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.siba.models.libro.ubicacion.BeanUbicacion;
import mx.edu.utez.siba.models.libro.ubicacion.DaoUbicacion;



import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@WebServlet(name = "ServletUbicacion", urlPatterns = {
        "/api/ubicacion/ubicaciones",
        "/api/ubicacion/ubicacion-view-save" , "/api/ubicacion/save",
        "/api/ubicacion/ubicacion-view-update", "/api/ubicacion/update",
        "/api/ubicacion/delete", "/api/ubicacion/search"
})
public class ServletUbicacion extends HttpServlet {
    private String action;
    private String redirect;
    private String id, mensaje;
    private BeanUbicacion ubicacion;
    private List<BeanUbicacion> ubicaciones;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        action = request.getServletPath();
        switch(action){
            case "/api/ubicacion/ubicaciones":
                int pagina = 1;
                int limite = 12;
                if (request.getParameter("page") != null) {
                    pagina = Integer.parseInt(request.getParameter("page"));
                }
                int inicio = (pagina -1) * limite;

                ubicaciones = new DaoUbicacion().findAll(inicio, limite);

                request.setAttribute("ubicaciones", ubicaciones);
                int totalRegistros = new DaoUbicacion().count();
                int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

                request.setAttribute("totalPaginas", totalPaginas);
                request.setAttribute("paginaActual", pagina);

                redirect= "/views/administrador/libros/ubicaciones/list_ubicaciones.jsp";
                break;
            case "/api/ubicacion/ubicacion-view-save":
                redirect = "/views/administrador/libros/ubicaciones/agregar_ubicacion.jsp";
                break;
            case "/api/ubicacion/ubicacion-view-update":
                id = request.getParameter("id");

                ubicacion = new DaoUbicacion().findOne(Long.parseLong(id));

                if (ubicacion != null){
                    request.setAttribute("ubicacion", ubicacion);
                    redirect = "/views/administrador/libros/ubicaciones/editar_ubicacion.jsp";
                } else{
                    redirect = "/api/ubicacion/ubicaciones?result=false&message=¡Error! Acción no realizada correctamente";
                }
                break;
            case "/api/ubicacion/search":

                String palabra = request.getParameter("palabra").trim();


                if(!palabra.isEmpty()){
                    ubicaciones = new DaoUbicacion().search(palabra);
                }

                request.setAttribute("ubicaciones", ubicaciones);
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
            case "/api/ubicacion/save":
                ubicacion = new BeanUbicacion();


                if(request.getParameter("pasillo") != null && request.getParameter("seccion") != null && request.getParameter("estante") != null){
                    ubicacion.setPasillo(Integer.parseInt(request.getParameter("pasillo").trim()));
                    ubicacion.setSeccion(Integer.parseInt(request.getParameter("seccion").trim()));
                    ubicacion.setEstante(request.getParameter("estante"));
                }


                mensaje = new DaoUbicacion().save(ubicacion);

                if (mensaje.contains("correctamente")){
                    redirect = "/api/ubicacion/ubicaciones?result=true&message="+ URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/api/ubicacion/ubicacion-view-save?result=false&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }
                break;
            case "/api/ubicacion/update":

                if(request.getParameter("pasillo") != null && request.getParameter("seccion") != null && request.getParameter("estante") != null){
                    ubicacion.setPasillo(Integer.parseInt(request.getParameter("pasillo").trim()));
                    ubicacion.setSeccion(Integer.parseInt(request.getParameter("seccion").trim()));
                    ubicacion.setEstante(request.getParameter("estante"));
                }

                mensaje = new DaoUbicacion().update(ubicacion);
                if (mensaje.contains("correctamente")){
                    redirect = "/api/ubicacion/ubicaciones?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/api/ubicacion/ubicacion-view-update?id=" + id + "&result=false&message=" + URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }

                break;
            case "/api/ubicacion/delete":
                id = request.getParameter("id");


                mensaje = new DaoUbicacion().delete(Long.parseLong(id));
                if (mensaje.contains("correctamente")){
                    redirect = "/api/ubicacion/ubicaciones?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/api/ubicacion/ubicaciones?result=false&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }
                break;
        }
        response.sendRedirect(request.getContextPath() + redirect);
    }
}