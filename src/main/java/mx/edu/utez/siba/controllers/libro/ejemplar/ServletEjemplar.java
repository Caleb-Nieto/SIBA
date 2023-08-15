package mx.edu.utez.siba.controllers.libro.ejemplar;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mx.edu.utez.siba.models.libro.ejemplar.BeanEjemplar;
import mx.edu.utez.siba.models.libro.ejemplar.DaoEjemplar;
import mx.edu.utez.siba.models.libro.BeanLibro;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(name="ServletEjemplar", urlPatterns = {
         "/ejemplar/ejemplar",
        "/ejemplar/ejemplar-view" , "/ejemplar/save",
        "/ejemplar/ejemplar-view-update", "/ejemplar/update",
        "/ejemplar/delete", "/ejemplar/search"

})
public class ServletEjemplar extends HttpServlet {
    private String action;
    private String redirect = "ejemplar/ejemplares/";
    private String id_ejemplar, observaciones;
    private BeanLibro libro;
    private String mensaje;
    private HttpSession session;
    private BeanEjemplar ejemplar;
    private List<BeanEjemplar> ejemplares;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        action = request.getServletPath();
        switch(action){
            case "/ejemplar/ejemplares":
                int pagina = 1;
                int limite = 12;
                if (request.getParameter("page") != null) {
                    pagina = Integer.parseInt(request.getParameter("page"));
                }
                int inicio = (pagina -1) * limite;

                ejemplares = new DaoEjemplar().findAll(inicio, limite);

                request.setAttribute("ejemplares", ejemplares);
                int totalRegistros = new DaoEjemplar().count();
                int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

                request.setAttribute("totalPaginas", totalPaginas);
                request.setAttribute("paginaActual", pagina);

                redirect= "/views/administrador/ejemplares/list_ejemplares.jsp";
                break;
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
            case "/ejemplar/save":
                id_ejemplar = request.getParameter("id_ejemplar");
                observaciones = request.getParameter("observaciones");

                libro=new BeanLibro();//declaro un nuevo libro

                libro.setId(Long.parseLong("id_libro"));
                ejemplar = new BeanEjemplar(Long.parseLong(id_ejemplar), observaciones,libro);
                mensaje = new DaoEjemplar().save(ejemplar);
                if (mensaje.contains("correctamente")){
                    redirect = "/ejemplar/ejemplar-view?result=true&message="+ URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/ejemplar/ejemplar-view?result=false&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }
                break;
            case "/ejemplar/update":
                id_ejemplar = request.getParameter("id_ejemplar");
                observaciones = request.getParameter("observaciones");

                libro=new BeanLibro();
                libro.setId(Long.parseLong("id_libro"));

                ejemplar = new BeanEjemplar(Long.parseLong(id_ejemplar),observaciones, libro);
                mensaje = new DaoEjemplar().update(ejemplar);
                if (mensaje.contains("correctamente")){
                    redirect = "/ejemplar/ejemplares?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/ejemplar/ejemplares?result=false&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }

                break;
            case "/ejemplar/delete":
                id_ejemplar = request.getParameter("id_ejemplar");
                mensaje = new DaoEjemplar().delete(Long.parseLong(id_ejemplar));
                if (mensaje.contains("correctamente")){
                    redirect = "/ejemplar/ejemplares?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/ejemplar/ejemplares?result=false&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }
                break;
        }
        response.sendRedirect(request.getContextPath() + redirect);
    }

}
