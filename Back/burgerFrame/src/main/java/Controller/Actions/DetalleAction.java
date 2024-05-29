package Controller.Actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.Detalle;
import Model.DAO.DetalleDao;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;

public class DetalleAction implements IAction {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action.toUpperCase()) {
            case "FIND_ALL":
                findAll(response);
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
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private void findAll(HttpServletResponse response) {
        DetalleDao detalleDao = new DetalleDao();
        ArrayList<Detalle> detalles = detalleDao.findAll(null);

        if (detalles != null && !detalles.isEmpty()) {
            Gson gson = new Gson();
            String jsonDetalles = gson.toJson(detalles);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try {
                response.getWriter().write(jsonDetalles);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Gson gson = new Gson();
            String errorMessage = gson.toJson(new ResponseMessage("No details found"));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try {
                response.getWriter().write(errorMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





    private String add(HttpServletRequest request, HttpServletResponse response) {
        String jsonResponse;
        Gson gson = null;
        try {
            int idDetalle = request.getParameter("ID_DETALLE") != null ? Integer.parseInt(request.getParameter("ID_DETALLE")) : 0;
            int idPedido = request.getParameter("ID_PEDIDOS") != null ? Integer.parseInt(request.getParameter("ID_PEDIDOS")) : 0;
            int idProducto = request.getParameter("ID_PRODUCTO") != null ? Integer.parseInt(request.getParameter("ID_PRODUCTO")) : 0;
            int cantidad = request.getParameter("CANTIDAD") != null ? Integer.parseInt(request.getParameter("CANTIDAD")) : 0;
            double precio = request.getParameter("PRECIO") != null ? Double.parseDouble(request.getParameter("PRECIO")) : 0.0;

            Detalle detalle = new Detalle(idDetalle, idPedido, idProducto, cantidad, precio);

            DetalleDao detalleDao = new DetalleDao();
            int result = detalleDao.add(detalle);

            gson = new Gson();
            if (result > 0) {
                jsonResponse = gson.toJson(new ResponseMessage("Detalle agregado correctamente"));
            } else {
                jsonResponse = gson.toJson(new ResponseMessage("Error al agregar el detalle"));
            }
        } catch (NumberFormatException e) {
            // Manejar la excepción si hay un error al convertir los parámetros a números
            jsonResponse = gson.toJson(new ResponseMessage("Error al convertir parámetros a números"));
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private String delete(HttpServletRequest request, HttpServletResponse response) {
        int idDetalle = Integer.parseInt(request.getParameter("ID_DETALLE"));

        DetalleDao detalleDao = new DetalleDao();
        int result = detalleDao.delete(idDetalle);

        Gson gson = new Gson();
        String jsonResponse;
        if (result > 0) {
            jsonResponse = gson.toJson(new ResponseMessage("Detail deleted successfully"));
        } else {
            jsonResponse = gson.toJson(new ResponseMessage("Failed to delete detail"));
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String update(HttpServletRequest request, HttpServletResponse response) {
        int idDetalle = Integer.parseInt(request.getParameter("ID_DETALLE"));
        int idPedido = Integer.parseInt(request.getParameter("ID_PEDIDOS"));
        int idProducto = Integer.parseInt(request.getParameter("ID_PRODUCTO"));
        int cantidad = Integer.parseInt(request.getParameter("CANTIDAD"));
        double precio = Double.parseDouble(request.getParameter("PRECIO"));

        Detalle detalle = new Detalle(idDetalle, idPedido, idProducto, cantidad, precio);

        DetalleDao detalleDao = new DetalleDao();
        int result = detalleDao.update(detalle);

        Gson gson = new Gson();
        String jsonResponse;
        if (result > 0) {
            jsonResponse = gson.toJson(new ResponseMessage("Detail updated successfully"));
        } else {
            jsonResponse = gson.toJson(new ResponseMessage("Failed to update detail"));
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
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
}
