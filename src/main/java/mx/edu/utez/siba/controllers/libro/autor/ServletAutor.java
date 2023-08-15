package mx.edu.utez.siba.controllers.libro.autor;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import mx.edu.utez.siba.models.libro.autor.BeanAutor;
import mx.edu.utez.siba.models.libro.autor.DaoAutor;


import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@WebServlet(name="auotores", urlPatterns = {
        "/autor/autores", "/autor/autor-view",
        "/autor/save", "/autor/autor-view-update",
        "/autor/update", "/autor/delete", "/autor/search"
})
public class ServletAutor extends HttpServlet {
    private String action;
    private String redirect;
    private String id_autor, mensaje;
    private BeanAutor autor;
    private List<BeanAutor> autores;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        action = request.getServletPath();
        switch (action){
            case "/autor/autores":
                int pagina = 1;
                int limite = 12;
                if (request.getParameter("page") != null) {
                    pagina = Integer.parseInt(request.getParameter("page"));
                }
                int inicio = (pagina -1) * limite;

                autores = new DaoAutor().findAll(inicio, limite);

                request.setAttribute("autores", autores);
                int totalRegistros = new DaoAutor().count();
                int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

                request.setAttribute("totalPaginas", totalPaginas);
                request.setAttribute("paginaActual", pagina);

                redirect= "/views/administrador/libros/autores/list_autores.jsp";
                break;
            case "/autor/autor-view":


                redirect = "/views/administrador/libros/autores/agregar_autores.jsp";
                break;
            case "/autor/autor-view-update":
                id_autor = request.getParameter("id_autor");

                autor = new DaoAutor().findOne(Long.parseLong(id_autor));
                if (autor != null){
                    request.setAttribute("autor", autor);
                    redirect = "/views/administrador/libros/autores/editar_autores.jsp";
                } else{
                    redirect = "/autor/autores?result=false&message=¡Error! Acción no realizada correctamente";
                }
                break;
            case "/autor/search":

                String palabra = request.getParameter("palabra").trim();

                if(!palabra.isEmpty()){
                    autores = new DaoAutor().search(palabra);
                }else{
                    redirect = "/autor/autores";
                }

                request.setAttribute("autores", autores);
                break;
        }
        request.getRequestDispatcher(redirect).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        action = request.getServletPath();
        switch(action){
            case "/autor/save":
                autor = new BeanAutor();

                autor.setNombre(request.getParameter("nombre").trim());
                autor.setApellido_paterno(request.getParameter("apellido_paterno").trim());
                autor.setApellido_materno(request.getParameter("apellido_materno").trim());


                mensaje = new DaoAutor().save(autor);

                if(mensaje.contains("correctamente")){
                    redirect = "/autor/autores?result=true&message=" + URLEncoder
                            .encode(mensaje,
                                    StandardCharsets.UTF_8);
                }else{
                    redirect = "/autor/autor-view?result=false&message=" + URLEncoder
                            .encode(mensaje,
                                    StandardCharsets.UTF_8);
                }
                break;
            case "/autor/update":
                autor = new BeanAutor();

                autor.setId_autor(Long.parseLong(request.getParameter("id_autor")));
                autor.setNombre(request.getParameter("nombre").trim());
                autor.setApellido_paterno(request.getParameter("apellido_paterno").trim());
                autor.setApellido_materno(request.getParameter("apellido_materno").trim());

                mensaje = new DaoAutor().update(autor);


                if (mensaje.contains("correctamente")){
                    redirect = "/autor/autores?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/autor/autor-view-update?id_autor=" + autor.getId_autor() + "&result=false&message=" + URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }
                break;
            case "/autor/delete":
                id_autor = request.getParameter("id_autor");


                mensaje = new DaoAutor().delete(Long.parseLong(id_autor));
                if (mensaje.contains("correctamente")){
                    redirect = "/autor/autores?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/autor/autores?result=false&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }
                break;
        }

        response.sendRedirect(request.getContextPath() + redirect);
    }


}
