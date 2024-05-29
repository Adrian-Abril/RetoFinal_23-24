package Model.DAO;

import Model.Detalle;
import Model.IDao;
import Model.MotorSQL;

import java.sql.*;
import java.util.ArrayList;

public class DetalleDao implements IDao<Detalle, Integer> {
    private MotorSQL motorSQL;

    public DetalleDao() {
        this.motorSQL = new MotorSQL();
    }

    private final String SQL_FIND_ALL = "SELECT * FROM DETALLE WHERE 1=1";

    @Override
    public int add(Detalle detalle) {
        int result = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            motorSQL.connect();

            // Validar si el ID_PEDIDOS existe en la tabla PEDIDOS
            String queryPedido = "SELECT COUNT(*) AS count FROM PEDIDOS WHERE ID_PEDIDOS = ?";
            preparedStatement = motorSQL.prepareStatement(queryPedido);
            preparedStatement.setInt(1, detalle.getIdPedido());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next() || resultSet.getInt("count") == 0) {
                // El ID_PEDIDOS especificado no existe en la tabla PEDIDOS
                System.out.println("Error: El ID_PEDIDOS especificado no existe en la tabla PEDIDOS.");
                return 0; // Opcional: Podrías lanzar una excepción o manejarlo de otra manera
            }

            // Validar si el ID_PRODUCTO existe en la tabla PRODUCTO
            String queryProducto = "SELECT COUNT(*) AS count FROM PRODUCTO WHERE ID_PRODUCTO = ?";
            preparedStatement = motorSQL.prepareStatement(queryProducto);
            preparedStatement.setInt(1, detalle.getIdProducto());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next() || resultSet.getInt("count") == 0) {
                // El ID_PRODUCTO especificado no existe en la tabla PRODUCTO
                System.out.println("Error: El ID_PRODUCTO especificado no existe en la tabla PRODUCTO.");
                return 0; // Opcional: Podrías lanzar una excepción o manejarlo de otra manera
            }

            // Insertar el detalle una vez que las claves foráneas han sido validadas
            String sql = "INSERT INTO DETALLE (ID_DETALLE, ID_PEDIDOS, ID_PRODUCTO, CANTIDAD, PRECIO) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = motorSQL.prepareStatement(sql);
            preparedStatement.setInt(1, detalle.getIdDetalle());
            preparedStatement.setInt(2, detalle.getIdPedido());
            preparedStatement.setInt(3, detalle.getIdProducto());
            preparedStatement.setInt(4, detalle.getCantidad());
            preparedStatement.setDouble(5, detalle.getPrecio());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            motorSQL.disconnect();
        }
        return result;
    }


    @Override
    public int delete(Integer id) {
        int result = 0;
        PreparedStatement preparedStatement = null;
        try {
            motorSQL.connect();
            String sql = "DELETE FROM DETALLE WHERE ID_DETALLE = ?";
            preparedStatement = motorSQL.prepareStatement(sql);

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
            motorSQL.disconnect();
        }
        return result;
    }

    @Override
    public int update(Detalle detalle) {
        int result = 0;
        PreparedStatement preparedStatement = null;
        try {
            motorSQL.connect();
            String sql = "UPDATE DETALLE SET ID_PEDIDOS = ?, ID_PRODUCTO = ?, CANTIDAD = ?, PRECIO = ? WHERE ID_DETALLE = ?";
            preparedStatement = motorSQL.prepareStatement(sql);

            preparedStatement.setInt(1, detalle.getIdPedido());
            preparedStatement.setInt(2, detalle.getIdProducto());
            preparedStatement.setInt(3, detalle.getCantidad());
            preparedStatement.setDouble(4, detalle.getPrecio());
            preparedStatement.setInt(5, detalle.getIdDetalle());

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
            motorSQL.disconnect();
        }
        return result;
    }

    @Override
    public ArrayList<Detalle> findAll(Detalle bean) {
        ArrayList<Detalle> detalles = new ArrayList<>();
        try {
            motorSQL.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (bean.getIdDetalle() != 0) {
                    sql += " AND ID_DETALLE='" + bean.getIdDetalle() + "'";
                }
                if (bean.getIdPedido() != 0) {
                    sql += " AND ID_PEDIDOS='" + bean.getIdPedido() + "'";
                }
                if (bean.getIdProducto() != 0) {
                    sql += " AND ID_PRODUCTO='" + bean.getIdProducto() + "'";
                }
                if (bean.getCantidad() != 0) {
                    sql += " AND CANTIDAD='" + bean.getCantidad() + "'";
                }
                if (bean.getPrecio() != 0) {
                    sql += " AND PRECIO='" + bean.getPrecio() + "'";
                }
            }
            ResultSet rs = motorSQL.executeQuery(sql);

            while (rs.next()) {
                Detalle detalle = new Detalle();

                detalle.setIdDetalle(rs.getInt("ID_DETALLE"));
                detalle.setIdPedido(rs.getInt("ID_PEDIDOS"));
                detalle.setIdProducto(rs.getInt("ID_PRODUCTO"));
                detalle.setCantidad(rs.getInt("CANTIDAD"));
                detalle.setPrecio(rs.getDouble("PRECIO"));

                detalles.add(detalle);
            }

        } catch (Exception ex) {
            detalles.clear();
            ex.printStackTrace();
        } finally {
            motorSQL.disconnect();
        }

        System.out.println("Detalles recuperados: " + detalles);

        return detalles;
    }

}
