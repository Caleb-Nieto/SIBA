package mx.edu.utez.siba.controllers.libro;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import mx.edu.utez.siba.models.libro.*;
import mx.edu.utez.siba.models.libro.autor.BeanAutor;
import mx.edu.utez.siba.models.libro.autor.DaoAutor;
import mx.edu.utez.siba.models.libro.ejemplar.BeanEjemplar;
import mx.edu.utez.siba.models.libro.ubicacion.BeanUbicacion;
import mx.edu.utez.siba.models.libro.ubicacion.DaoUbicacion;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@WebServlet(name="Libros", urlPatterns = {
        "/api/libro/libros", "/api/libro/save",
        "/api/libro/libro-view-save", "/api/libro/delete",
        "/api/libro/update", "/api/libro/libro-view-update",
        "/api/libro/search"


})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 100
)
public class ServletLibros extends HttpServlet {
    private String action;
    private String redirect;
    private List<BeanLibro> libros;
    private BeanLibro libro;
    private BeanEjemplar ejemplar;
    private BeanUbicacion ubicacion;
    private String[] autores_ids;
    private String fileName, mime, id_libro;
    private String mensaje;
    private  List<BeanAutor> autores;
    List<BeanUbicacion> ubicaciones;
    private StringBuilder idsBuilder;
    private String ids;
    private String directory = "F:" + File.separator + "integradora/siba";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        action = request.getServletPath();
        switch(action){
            case "/api/libro/libros":
                int pagina = 1;
                int limite = 12;
                if (request.getParameter("page") != null) {
                    pagina = Integer.parseInt(request.getParameter("page"));
                }
                int inicio = (pagina -1) * limite;

                libros = new DaoLibro().findAll(inicio, limite);

                request.setAttribute("libros", libros);
                int totalRegistros = new DaoLibro().count();
                int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

                request.setAttribute("totalPaginas", totalPaginas);
                request.setAttribute("paginaActual", pagina);

                redirect= "/views/administrador/libros/list_libros.jsp";
                break;
            case "/api/libro/libro-view-save":
                autores = new DaoAutor().autores(null);
                ubicaciones = new DaoUbicacion().ubicaciones();

                request.setAttribute("autores", autores);
                request.setAttribute("ubicaciones", ubicaciones);
                redirect = "/views/administrador/libros/agregar_libro.jsp";
                break;
            case "/api/libro/libro-view-update":
                id_libro = request.getParameter("id_libro");

                ubicaciones = new DaoUbicacion().ubicaciones();

                libro = new DaoLibro().findOne(Long.parseLong(id_libro));
                if (libro != null){
                    request.setAttribute("ubicaciones", ubicaciones);
                    request.setAttribute("libro", libro);
                    redirect = "/views/administrador/libros/editar_libro.jsp";
                } else{
                    redirect = "/api/libro/libros?result=false&message=¡Error! Acción no realizada correctamente";
                }
                break;
            case "/api/libro/search":

                String palabra = request.getParameter("palabra").trim();

                if(!palabra.isEmpty()){
                    libros = new DaoLibro().search(palabra);
                }else{
                    redirect = "/api/libro/libros";
                }

                request.setAttribute("libros", libros);
                break;
        }
        request.getRequestDispatcher(redirect).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        action = request.getServletPath();
        switch (action){
            case "/api/libro/save":
                libro = new BeanLibro();

                for (Part part : request.getParts()) {
                    fileName = part.getSubmittedFileName();
                    if (fileName != null) {
                        mime = part.getContentType().split("/")[1];
                        String uid = UUID.randomUUID().toString();
                        libro.setFileName(uid + "." + mime);
                        //part.write(directory + File.separator + uid + "." + mime);
                        InputStream stream = part.getInputStream();
                        byte[] arr = stream.readAllBytes();
                        libro.setFile(arr);
                    }
                }

                libro.setTitulo(request.getParameter("titulo").trim());
                libro.setIsbn(request.getParameter("isbn").trim());
                libro.setEditorial(request.getParameter("editorial").trim());

                ejemplar = new BeanEjemplar();
                ejemplar.setId_ejemplar(Long.parseLong(request.getParameter("id_ejemplar").trim()));
                ejemplar.setObservaciones(request.getParameter("observaciones").trim());


                ubicacion = new BeanUbicacion();
                ubicacion.setId(Long.parseLong(request.getParameter("ubicacion_id")));

                libro.setUbicacion(ubicacion);


                autores_ids = request.getParameterValues("autores_ids");
                idsBuilder = new StringBuilder();

                for (int i = 0; i < autores_ids.length; i++) {
                    idsBuilder.append(autores_ids[i]);
                    if (i < autores_ids.length - 1) {
                        idsBuilder.append(",");
                    }
                }

                ids = idsBuilder.toString();


                mensaje = new DaoLibro().save(libro, ejemplar, ids);

                if(mensaje.contains("correctamente")){
                    redirect = "/api/libro/libros?result=true&message=" + URLEncoder
                            .encode(mensaje,
                                    StandardCharsets.UTF_8);
                }else{
                    redirect = "/api/libro/libro-view-save?result=false&message=" + URLEncoder
                            .encode(mensaje,
                                    StandardCharsets.UTF_8);
                }
                break;
            case "/api/libro/update":
                libro = new BeanLibro();

                for (Part part : request.getParts()) {
                    fileName = part.getSubmittedFileName();
                    if (fileName != null && !fileName.isEmpty()) {
                        mime = part.getContentType().split("/")[1];
                        String uid = UUID.randomUUID().toString();
                        libro.setFileName(uid + "." + mime);
                        //part.write(directory + File.separator + uid + "." + mime);
                        InputStream stream = part.getInputStream();
                        byte[] arr = stream.readAllBytes();
                        libro.setFile(arr);
                    }
                }



                libro.setId(Long.parseLong(request.getParameter("id_libro").trim()));

                libro.setTitulo(request.getParameter("titulo").trim());
                libro.setEditorial(request.getParameter("editorial").trim());

                ubicacion = new BeanUbicacion();
                ubicacion.setId(Long.parseLong(request.getParameter("ubicacion_id")));

                libro.setUbicacion(ubicacion);



                mensaje = new DaoLibro().update(libro, ejemplar);

                if(mensaje.contains("correctamente")){
                    redirect = "/api/libro/libros?result=true&message=" + URLEncoder
                            .encode(mensaje,
                                    StandardCharsets.UTF_8);
                }else{
                    redirect = "/libro/libro_view-update?id_libro="+libro.getId()+"result=false&message=" + URLEncoder
                            .encode(mensaje,
                                    StandardCharsets.UTF_8);
                }
                break;
            case "/api/libro/delete":
                id_libro = request.getParameter("id_libro");


                mensaje = new DaoLibro().delete(Long.parseLong(id_libro));
                if (mensaje.contains("correctamente")){
                    redirect = "/api/libro/libros?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/api/libro/libros?result=false&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }
        }
        response.sendRedirect(request.getContextPath() + redirect);
    }
}
