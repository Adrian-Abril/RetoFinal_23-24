package Controller;

import Controller.Actions.*;
import Model.Cliente;
import Model.DAO.ClienteDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strAction = request.getParameter("ACTION");
        if (strAction == null || strAction.isEmpty()) {
            throw new ServletException("Acción no especificada");
        }

        String[] arrayAction = strAction.split("\\.");
        if (arrayAction.length != 2) {
            throw new ServletException("Formato de acción no válido");
        }

        if (arrayAction[0].equalsIgnoreCase("CARRITO")) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("clienteId") == null) {
                response.sendRedirect("login.html");
                return;
            }
        }

        try {
            switch (arrayAction[0].toUpperCase()) {
                case "PRODUCTO":
                    new ProductoAction().execute(request, response, arrayAction[1]);
                    break;
                case "LOGIN":
                    login(request, response);
                    break;
                case "CLIENTE":
                    new ClienteAction().execute(request, response, arrayAction[1]);
                    break;
                case "DETALLE":
                    new DetalleAction().execute(request, response, arrayAction[1]);
                    break;
                case "EMPLEADO":
                    new EmpleadoAction().findAll(request, response); // Llama al método findAll directamente
                    break;
                default:
                    throw new ServletException("Acción " + arrayAction[0] + " no válida");
            }
        } catch (Exception e) {
            // Manejo de excepciones generales
            throw new ServletException("Error durante el procesamiento de la solicitud", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailOrPhone = request.getParameter("emailOrPhone");
        String password = request.getParameter("password");

        if (validarCredenciales(emailOrPhone, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("clienteId", obtenerIdCliente(emailOrPhone));

            response.sendRedirect("index.html");
        } else {
            request.setAttribute("errorMessage", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("login.html").forward(request, response);
        }
    }

    private boolean validarCredenciales(String emailOrPhone, String password) {
        ClienteDao clienteDao = new ClienteDao();
        Cliente cliente = clienteDao.findByEmailOrPhoneAndPassword(emailOrPhone, password);
        return cliente != null;
    }


    private int obtenerIdCliente(String emailOrPhone) {

        return 1;
    }
}
