package mx.edu.utez.siba.controllers.libro;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name="PrestamoEjemplares", urlPatterns = {
        "/api/iniciar/prestamo/ejemplar", "/api/finalizar/prestamo/ejemplar"
})
public class ServletPrestamoEjemplares extends HttpServlet {
    String mensaje;
    private String redirect;
    private String id_sala, idu;
    private String action;
    @Override
    protected void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException {

    }
}
