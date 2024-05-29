package Controller.Actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.*;
import Model.DAO.ProductoDao;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;

public class ProductoAction implements IAction {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String action) {
        String strReturn = "";
        switch (action.toUpperCase()) {
            case "FIND_FIRST":
                break;
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
            default:
                strReturn = "ERROR. Invalid Action";
        }
        return strReturn;
    }

    private String findAll(HttpServletResponse response) {
        ProductoDao productoDao = new ProductoDao();
        ArrayList<Producto> productos = productoDao.findAll(null);

        Gson gson = new Gson();
        String jsonProductos = gson.toJson(productos);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().write(jsonProductos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String add(HttpServletRequest request, HttpServletResponse response) {
        int idProducto = Integer.parseInt(request.getParameter("ID_PRODUCTO"));
        String nombre = request.getParameter("NOMBRE");
        String descripcion = request.getParameter("DESCRIPCION");
        double precio = Double.parseDouble(request.getParameter("PRECIO"));
        String imagen = request.getParameter("IMAGEN");
        int idCatProducto = Integer.parseInt(request.getParameter("ID_CATPRODUCTO"));

        Producto producto = new Producto(idProducto, nombre, descripcion, precio, imagen, idCatProducto);

        ProductoDao productoDao = new ProductoDao();
        int result = productoDao.add(producto);

        Gson gson = new Gson();
        String jsonResponse;
        if (result > 0) {
            jsonResponse = gson.toJson(new ResponseMessage("Product added successfully"));
        } else {
            jsonResponse = gson.toJson(new ResponseMessage("Failed to add product"));
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
        int idProducto = Integer.parseInt(request.getParameter("ID_PRODUCTO"));

        ProductoDao productoDao = new ProductoDao();
        int result = productoDao.delete(idProducto);

        Gson gson = new Gson();
        String jsonResponse;
        if (result > 0) {
            jsonResponse = gson.toJson(new ResponseMessage("Product deleted successfully"));
        } else {
            jsonResponse = gson.toJson(new ResponseMessage("Failed to delete product"));
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
        int idProducto = Integer.parseInt(request.getParameter("ID_PRODUCTO"));
        String nombre = request.getParameter("NOMBRE");
        String descripcion = request.getParameter("DESCRIPCION");
        double precio = Double.parseDouble(request.getParameter("PRECIO"));
        String imagen = request.getParameter("IMAGEN");
        int idCatProducto = Integer.parseInt(request.getParameter("ID_CATPRODUCTO"));

        Producto producto = new Producto(idProducto, nombre, descripcion, precio, imagen, idCatProducto);

        ProductoDao productoDao = new ProductoDao();
        int result = productoDao.update(producto);

        Gson gson = new Gson();
        String jsonResponse;
        if (result > 0) {
            jsonResponse = gson.toJson(new ResponseMessage("Product updated successfully"));
        } else {
            jsonResponse = gson.toJson(new ResponseMessage("Failed to update product"));
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
