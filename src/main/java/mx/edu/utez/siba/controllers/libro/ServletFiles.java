package mx.edu.utez.siba.controllers.libro;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.siba.models.libro.BeanLibro;
import mx.edu.utez.siba.models.libro.DaoLibro;

import java.io.*;

@WebServlet(name="Files", urlPatterns = {
        "/libro/loadfile"
})
public class ServletFiles extends HttpServlet{
    private String action;
    private BeanLibro libro;
    private String directory = "F:"+ File.separator + "integradora/siba";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        action = req.getServletPath();
        switch (action) {
            case "/libro/loadfile":
                int id = Integer.parseInt(
                        req.getParameter("file") != null ?
                                req.getParameter("file") : "0"
                );
                libro = new DaoLibro().findFile(id);
                //Folder
                /*
                File file = new File(directory + File.separator + libro.getFileName());
                try (InputStream input = new FileInputStream(file)) {
                    OutputStream outputStream = resp.getOutputStream();
                    byte[] buffer = new byte[1048];
                    int lengthBytesRead;
                    while ((lengthBytesRead = input.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, lengthBytesRead);
                    }
                }catch (IOException e){
                    Logger.getLogger(ServletFiles.class.getName())
                            .log(Level.SEVERE, "Error files " + e.getMessage());
                }*/

                //Base de datos
                if (resp != null && libro != null && libro.getFile() != null) {

                    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                    resp.setHeader("Pragma", "no-cache");
                    resp.setHeader("Expires", "0");

                    OutputStream outputStream = resp.getOutputStream();
                    outputStream.write(libro.getFile(), 0, libro.getFile().length);
                }
                //Consulta de la imagen
                break;
            default:
                req.getRequestDispatcher("/libro/libros").forward(req, resp);
        }
    }
}
