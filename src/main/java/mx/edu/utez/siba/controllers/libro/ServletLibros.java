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



import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;


@WebServlet(name="Libros", urlPatterns = {
        "/libro/libros", "/libro/save",
        "/libro/libro_view", "/libro/delete"


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
    private String fileName, mime, id_libro;
    private String mensaje;
    private String directory = "F:" + File.separator + "integradora/siba";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        action = request.getServletPath();
        switch(action){
            case "/libro/libros":
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
            case "/libro/libro_view":
                List<BeanAutor> autores = new DaoLibro().autores(null);
                List<BeanUbicacion> ubicaciones = new DaoLibro().ubicaciones();

                request.setAttribute("autores", autores);
                request.setAttribute("ubicaciones", ubicaciones);
                redirect = "/views/administrador/libros/agregar_libro.jsp";
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
            case "/libro/save":
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

                libro.setTitulo(request.getParameter("titulo"));
                libro.setIsbn(request.getParameter("isbn"));
                libro.setEditorial(request.getParameter("editorial"));

                BeanEjemplar ejemplar = new BeanEjemplar();
                ejemplar.setId_ejemplar(Long.parseLong(request.getParameter("id_ejemplar")));
                ejemplar.setObservaciones(request.getParameter("observaciones"));


                BeanUbicacion ubicacion = new BeanUbicacion();
                ubicacion.setId(Long.parseLong(request.getParameter("ubicacion_id")));

                libro.setUbicacion(ubicacion);


                String[] arr = request.getParameterValues("autores_ids");
                StringBuilder idsBuilder = new StringBuilder();

                for (int i = 0; i < arr.length; i++) {
                    idsBuilder.append(arr[i]);
                    if (i < arr.length - 1) {
                        idsBuilder.append(",");
                    }
                }

                String ids = idsBuilder.toString();


                mensaje = new DaoLibro().save(libro, ejemplar, ids);

                if(mensaje.contains("correctamente")){
                    redirect = "/libro/libros?result=true&message=" + URLEncoder
                            .encode(mensaje,
                                    StandardCharsets.UTF_8);
                }else{
                    redirect = "/libro/libro_view?result=false&message=" + URLEncoder
                            .encode(mensaje,
                                    StandardCharsets.UTF_8);
                }
                break;
            case "/libro/delete":
                id_libro = request.getParameter("id_libro");


                mensaje = new DaoLibro().delete(Long.parseLong(id_libro));
                if (mensaje.contains("correctamente")){
                    redirect = "/libro/libros?result=true&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/libro/libros?result=false&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }
        }
        response.sendRedirect(request.getContextPath() + redirect);
    }
}