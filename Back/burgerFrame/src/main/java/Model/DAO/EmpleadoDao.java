package Model.DAO;

import Model.Empleado;
import Model.MotorSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmpleadoDao {
    private MotorSQL motorSQL;

    public EmpleadoDao() {
        this.motorSQL = new MotorSQL();
    }

    public ArrayList<Empleado> findAll() {
        ArrayList<Empleado> empleados = new ArrayList<>();
        try {
            motorSQL.connect();
            String sql = "SELECT ID_EMPLEADOS, NOMBRE, APELLIDOS, EMAIL, TELEFONO, ID_CATEMPLEADO FROM EMPLEADOS";
            ResultSet rs = motorSQL.executeQuery(sql);

            while (rs.next()) {
                Empleado empleado = new Empleado();

                empleado.setIdEmpleado(rs.getInt("ID_EMPLEADOS"));
                empleado.setNombre(rs.getString("NOMBRE"));
                empleado.setApellidos(rs.getString("APELLIDOS"));
                empleado.setEmail(rs.getString("EMAIL"));
                empleado.setTelefono(rs.getString("TELEFONO"));
                empleado.setIdCatEmpleado(rs.getInt("ID_CATEMPLEADO"));

                empleados.add(empleado);
            }

        } catch (Exception ex) {
            empleados.clear();
            ex.printStackTrace();
        } finally {
            motorSQL.disconnect();
        }
        return empleados;
    }
}
