package Model;

import java.sql.*;
import java.util.ArrayList;

public class ProductoDao implements IDao<Producto, Integer> {
    private MotorSQL motorSQL;

    public ProductoDao() {
        this.motorSQL = new MotorSQL();
    }

    private final String SQL_FIND_ALL = "SELECT * FROM PRODUCTO WHERE 1=1";

    @Override
    public int add(Producto producto) {
        int result = 0;
        MotorSQL motor = new MotorSQL();
        PreparedStatement preparedStatement = null; // Declarar el objeto PreparedStatement
        try {
            motor.connect();
            String sql = "INSERT INTO PRODUCTO (ID_PRODUCTO, NOMBRE, DESCRIPCION, PRECIO, IMAGEN, ID_CATPRODUCTO) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = motor.prepareStatement(sql); // Obtener PreparedStatement desde MotorSQL

            preparedStatement.setInt(1, producto.getIdProducto());
            preparedStatement.setString(2, producto.getNombre());
            preparedStatement.setString(3, producto.getDescripcion());
            preparedStatement.setDouble(4, producto.getPrecio());
            preparedStatement.setString(5, producto.getImagen());
            preparedStatement.setInt(6, producto.getIdCatProducto());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar el PreparedStatement dentro del bloque finally para asegurarse de que se cierre correctamente
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            motor.disconnect();
        }
        return result;
    }


    @Override
    public int delete(Integer id) {
        // Implementación de la eliminación de un producto en la base de datos
        return 0;
    }

    @Override
    public int update(Producto producto) {
        // Implementación de la actualización de un producto en la base de datos
        return 0;
    }

    @Override
    public ArrayList<Producto> findAll(Producto bean) {
        ArrayList<Producto> productos = new ArrayList<>();
        MotorSQL motor = new MotorSQL();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (bean.getIdProducto() != 0) {
                    sql += " AND ID_PRODUCTO='" + bean.getIdProducto() + "'";
                }
                if (bean.getNombre() != null) {
                    sql += " AND NOMBRE='" + bean.getNombre() + "'";
                }
                if (bean.getDescripcion() != null) {
                    sql += " AND DESCRIPCION='" + bean.getDescripcion() + "'";
                }
                if (bean.getPrecio() != 0) {
                    sql += " AND PRECIO='" + bean.getPrecio() + "'";
                }
                if (bean.getImagen() != null) {
                    sql += " AND IMAGEN='" + bean.getImagen() + "'";
                }
                if (bean.getIdCatProducto() != 0) {
                    sql += " AND ID_CATPRODUCTO='" + bean.getIdCatProducto() + "'";
                }
            }
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next()) {
                Producto producto = new Producto();

                producto.setIdProducto(rs.getInt("ID_PRODUCTO"));
                producto.setNombre(rs.getString("NOMBRE"));
                producto.setDescripcion(rs.getString("DESCRIPCION"));
                producto.setPrecio(rs.getDouble("PRECIO"));
                producto.setImagen(rs.getString("IMAGEN"));
                producto.setIdCatProducto(rs.getInt("ID_CATPRODUCTO"));

                productos.add(producto);  // Corrección: agregar a la lista de productos
            }

        } catch (Exception ex) {
            productos.clear();
            ex.printStackTrace();
        } finally {
            motor.disconnect();
        }
        return productos;
    }
}
