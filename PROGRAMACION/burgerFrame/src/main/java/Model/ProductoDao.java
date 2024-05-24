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
        PreparedStatement preparedStatement = null;
        try {
            motor.connect();
            String sql = "INSERT INTO PRODUCTO (ID_PRODUCTO, NOMBRE, DESCRIPCION, PRECIO, IMAGEN, ID_CATPRODUCTO)";
            preparedStatement = motor.prepareStatement(sql);

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
        int result = 0;
        MotorSQL motor = new MotorSQL();
        PreparedStatement preparedStatement = null;
        try {
            motor.connect();
            String sql = "DELETE FROM PRODUCTO WHERE ID_PRODUCTO = ?";
            preparedStatement = motor.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
    public int update(Producto producto) {
        int result = 0;
        MotorSQL motor = new MotorSQL();
        PreparedStatement preparedStatement = null;
        try {
            motor.connect();
            String sql = "UPDATE PRODUCTO SET NOMBRE = ?, DESCRIPCION = ?, PRECIO = ?, IMAGEN = ?, ID_CATPRODUCTO = ? WHERE ID_PRODUCTO = ?";
            preparedStatement = motor.prepareStatement(sql);

            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setString(2, producto.getDescripcion());
            preparedStatement.setDouble(3, producto.getPrecio());
            preparedStatement.setString(4, producto.getImagen());
            preparedStatement.setInt(5, producto.getIdCatProducto());
            preparedStatement.setInt(6, producto.getIdProducto());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

                productos.add(producto);
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
