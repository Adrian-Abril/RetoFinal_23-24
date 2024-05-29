package Controller.Actions;

import Model.Empleado;
import Model.DAO.EmpleadoDao;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class EmpleadoAction {
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        EmpleadoDao empleadoDao = new EmpleadoDao();
        ArrayList<Empleado> empleados = empleadoDao.findAll();

        Gson gson = new Gson();
        String jsonEmpleados = gson.toJson(empleados);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().write(jsonEmpleados);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
