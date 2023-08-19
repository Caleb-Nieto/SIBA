package mx.edu.utez.siba.controllers.sala;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import mx.edu.utez.siba.models.sala.DaoSala;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@WebServlet(name="Servlet", urlPatterns = {"/api/iniciar/prestamo","/api/finalizar/prestamo"})
public class ServletPrestamoSala extends HttpServlet {

    String mensaje;
    private String redirect;
    private String id_sala, idu;
    private String action;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        action = req.getServletPath();

        switch (action){
            case "/api/iniciar/prestamo":

                id_sala = req.getParameter("id_sala");
                idu = req.getParameter("idu");

                if(idu != null && !idu.isEmpty()){
                    idu = idu.trim();
                    mensaje = new DaoSala().hacerPrestamo(Long.parseLong(id_sala), idu);

                    if (mensaje.contains("correctamente")){
                        redirect = "/api/sala/salas?result=true&message="+ URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                    }else{
                        redirect = "/api/sala/salas?result=false&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                    }

                }else{

                    redirect = "/api/sala/salas";
                }

                break;
            case "/api/finalizar/prestamo":

                id_sala = req.getParameter("id_sala");

                mensaje = new DaoSala().finalizarPrestamo(Long.parseLong(id_sala));

                if (mensaje.contains("correctamente")){
                    redirect = "/api/sala/salas?result=true&message="+ URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }else{
                    redirect = "/api/sala/salas?result=false&message="+URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
                }

                break;
        }
        resp.sendRedirect(req.getContextPath() + redirect);
    }

}