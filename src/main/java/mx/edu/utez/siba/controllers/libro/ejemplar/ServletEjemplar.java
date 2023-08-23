package mx.edu.utez.siba.controllers.libro.ejemplar;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mx.edu.utez.siba.models.libro.DaoLibro;
import mx.edu.utez.siba.models.libro.ejemplar.BeanEjemplar;
import mx.edu.utez.siba.models.libro.ejemplar.DaoEjemplar;
import mx.edu.utez.siba.models.libro.BeanLibro;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="ServletEjemplar", urlPatterns = {
         "/api/ejemplar/ejemplares",
        "/api/ejemplar/ejemplar-view-save" , "/api/ejemplar/save",
        "/api/ejemplar/ejemplar-view-update", "/api/ejemplar/update",
        "/api/ejemplar/delete", "/api/ejemplar/search"

})
public class ServletEjemplar extends HttpServlet {
    private String action;
    private String redirect;
    private String id_ejemplar, observaciones, id_libro, titulo;
    private BeanLibro libro;
    private String mensaje;
    private BeanEjemplar ejemplar;
    private List<BeanEjemplar> ejemplares;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        action = request.getServletPath();
        switch(action){
            case "/api/ejemplar/ejemplares":
                int pagina = 1;
                int limite = 12;
                if (request.getParameter("page") != null) {
                    pagina = Integer.parseInt(request.getParameter("page"));
                }

                int inicio = (pagina -1) * limite;

                id_libro = request.getParameter("id_libro");



                ejemplares = new DaoEjemplar().findAll(Long.parseLong(id_libro), inicio, limite);

                request.setAttribute("ejemplares", ejemplares);


                int totalRegistros = new DaoLibro().count();
                int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

                request.setAttribute("totalPaginas", totalPaginas);
                request.setAttribute("paginaActual", pagina);

                redirect = "/views/administrador/libros/ejemplares/list_ejemplares.jsp";
                break;

            case "/api/ejemplar/ejemplar-view-save":

                id_libro = request.getParameter("id_libro");
                titulo = request.getParameter("titulo");

                request.setAttribute("id_libro",id_libro);
                request.setAttribute("titulo", titulo);

                redirect = "/views/administrador/libros/ejemplares/agregar_ejemplar.jsp";
                break;
            case "/api/ejemplar/ejemplar-view-update":

                id_ejemplar = request.getParameter("id_ejemplar");


                ejemplar = new DaoEjemplar().findOne(Long.parseLong(id_ejemplar));

                request.setAttribute("ejemplar", ejemplar);

                redirect = "/views/administrador/libros/ejemplares/editar_ejemplar.jsp";
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
            case "/api/ejemplar/save":
                id_libro = request.getParameter("id_libro");
                id_ejemplar = request.getParameter("id_ejemplar");
                observaciones = request.getParameter("observaciones");


                libro=new BeanLibro();//declaro un nuevo libro

                libro.setId(Long.parseLong(id_libro));

                ejemplar = new BeanEjemplar(0L,Integer.parseInt(id_ejemplar), observaciones,libro);
                mensaje = new DaoEjemplar().save(ejemplar);
                if (mensaje.contains("correctamente")){
                    redirect = "/api/ejemplar/ejemplares?result=true&id_libro="+id_libro+"&message="+ URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/api/ejemplar/ejemplar-view-save?result=false&id_libro="+id_libro+"&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }
                break;
            case "/api/ejemplar/update":
                id_ejemplar = request.getParameter("id_ejemplar");
                String num = request.getParameter("ejemplar");
                observaciones = request.getParameter("observaciones");
                id_libro = request.getParameter("id_libro");

                libro=new BeanLibro();
                libro.setId(Long.parseLong(id_libro));

                ejemplar = new BeanEjemplar(Long.parseLong(id_ejemplar),Integer.parseInt(num), observaciones,libro);
                mensaje = new DaoEjemplar().update(ejemplar);
                if (mensaje.contains("correctamente")){
                    redirect = "/api/ejemplar/ejemplares?result=true&id_libro="+id_libro+"&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/api/ejemplar/ejemplares?result=false&id_libro="+id_libro+"&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }

                break;
            case "/api/ejemplar/delete":
                id_ejemplar = request.getParameter("id_ejemplar");

                mensaje = new DaoEjemplar().delete(Long.parseLong(id_ejemplar));
                if (mensaje.contains("correctamente")){
                    redirect = "/api/ejemplar/ejemplares?result=true&id_libro="+id_libro+"&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/api/ejemplar/ejemplares?result=false&id_libro="+id_libro+"&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }
                break;
        }
        response.sendRedirect(request.getContextPath() + redirect);
    }

}
