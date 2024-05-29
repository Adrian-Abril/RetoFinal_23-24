package Controller.Actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.Cliente;
import Model.DAO.ClienteDao;
import Model.LoginData;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;

public class ClienteAction implements IAction {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action.toUpperCase()) {
            case "FIND_ALL":
                strReturn = findAll(response);
                break;
            case "ADD":
                strReturn = add(request, response);
                break;
            case "DELETE":
                strReturn = delete(request, response);
                break;
            case "UPDATE":
                strReturn = update(request, response);
                break;
            case "LOGIN":
                strReturn = login(request, response);
                break;
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll(HttpServletResponse response) {
        ClienteDao clienteDao = new ClienteDao();
        ArrayList<Cliente> clientes = clienteDao.findAll(null);

        Gson gson = new Gson();
        String jsonClientes = gson.toJson(clientes);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().write(jsonClientes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String add(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Leer el JSON del cuerpo de la solicitud
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            Cliente cliente = gson.fromJson(reader, Cliente.class);

            // Validar que el cliente no sea nulo
            if (cliente == null) {
                throw new IllegalArgumentException("El objeto Cliente recibido es nulo");
            }

            // Verificar que se proporcionen al menos un email o un teléfono
            if ((cliente.getEmail() == null || cliente.getEmail().isEmpty()) &&
                    (cliente.getTelefono() == null || cliente.getTelefono().isEmpty())) {
                throw new IllegalArgumentException("Debe proporcionar al menos un email o un teléfono");
            }

            // Realizar operaciones para agregar el cliente a la base de datos
            ClienteDao clienteDao = new ClienteDao();
            int result = clienteDao.add(cliente);

            // Preparar la respuesta JSON
            String jsonResponse;
            if (result > 0) {
                jsonResponse = gson.toJson(new ResponseMessage("Cliente agregado exitosamente"));
            } else {
                jsonResponse = gson.toJson(new ResponseMessage("Error al agregar el cliente"));
            }

            // Establecer los encabezados de la respuesta
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Enviar la respuesta al cliente
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                // Manejar cualquier excepción enviando un error HTTP 400
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error al procesar la solicitud: " + e.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        return null;
    }




    private String delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idCliente = Integer.parseInt(request.getParameter("ID_CLIENTES"));

            ClienteDao clienteDao = new ClienteDao();
            int result = clienteDao.delete(idCliente);

            Gson gson = new Gson();
            String jsonResponse;
            if (result > 0) {
                jsonResponse = gson.toJson(new ResponseMessage("Client deleted successfully"));
            } else {
                jsonResponse = gson.toJson(new ResponseMessage("Failed to delete client"));
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error al procesar la solicitud: " + e.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        return null;
    }

    private String update(HttpServletRequest request, HttpServletResponse response) {
        try {
            int idCliente = Integer.parseInt(request.getParameter("ID_CLIENTES"));
            String nombre = request.getParameter("NOMBRE");
            String apellidos = request.getParameter("APELLIDOS");
            String email = request.getParameter("EMAIL");
            String telefono = request.getParameter("TELEFONO");
            String password = request.getParameter("PASSWORD");

            Cliente cliente = new Cliente(idCliente, nombre, apellidos, email, telefono, password);

            ClienteDao clienteDao = new ClienteDao();
            int result = clienteDao.update(cliente);

            Gson gson = new Gson();
            String jsonResponse;
            if (result > 0) {
                jsonResponse = gson.toJson(new ResponseMessage("Client updated successfully"));
            } else {
                jsonResponse = gson.toJson(new ResponseMessage("Failed to update client"));
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error al procesar la solicitud: " + e.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        return null;
    }

    class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
    private String login(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Leer el JSON del cuerpo de la solicitud
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            LoginData loginData = gson.fromJson(reader, LoginData.class);

            // Validar las credenciales del usuario
            ClienteDao clienteDao = new ClienteDao();
            Cliente cliente = clienteDao.findByEmailOrPhoneAndPassword(loginData.getEmailOrPhone(), loginData.getPassword());

            // Preparar la respuesta JSON
            String jsonResponse;
            if (cliente != null) {
                // Si el inicio de sesión es exitoso, establecemos el ID del cliente en la sesión
                HttpSession session = request.getSession(true); // Crear una nueva sesión si no existe
                session.setAttribute("clienteId", cliente.getIdCliente());

                jsonResponse = gson.toJson(new ResponseMessage("Login successful"));
            } else {
                jsonResponse = gson.toJson(new ResponseMessage("Login failed. Incorrect email/phone or password."));
            }

            // Establecer los encabezados de la respuesta
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Enviar la respuesta al cliente
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                // Manejar cualquier excepción enviando un error HTTP 400
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error al procesar la solicitud: " + e.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        return null;
    }


}
