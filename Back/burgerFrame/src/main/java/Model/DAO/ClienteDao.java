package Model.DAO;

import Model.Cliente;
import Model.IDao;
import Model.MotorSQL;

import java.sql.*;
import java.util.ArrayList;

public class ClienteDao implements IDao<Cliente, Integer> {
    private MotorSQL motorSQL;

    public ClienteDao() {
        this.motorSQL = new MotorSQL();
    }

    private final String SQL_FIND_ALL = "SELECT * FROM CLIENTES WHERE 1=1";

    @Override
    public int add(Cliente cliente) {
        int result = 0;
        PreparedStatement preparedStatement = null;
        try {
            motorSQL.connect();
            String sql = "INSERT INTO CLIENTES (ID_CLIENTES, NOMBRE, APELLIDOS, EMAIL, TELEFONO, PASSWORD) VALUES (SEQ_CLIENTES.NEXTVAL, ?, ?, ?, ?, ?)";
            preparedStatement = motorSQL.prepareStatement(sql);

            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getApellidos());
            preparedStatement.setString(3, cliente.getEmail());
            preparedStatement.setString(4, cliente.getTelefono());
            preparedStatement.setString(5, cliente.getPassword());

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
    public int delete(Integer id) {
        int result = 0;
        PreparedStatement preparedStatement = null;
        try {
            motorSQL.connect();
            String sql = "DELETE FROM CLIENTES WHERE ID_CLIENTES = ?";
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
    public int update(Cliente cliente) {
        int result = 0;
        PreparedStatement preparedStatement = null;
        try {
            motorSQL.connect();
            String sql = "UPDATE CLIENTES SET NOMBRE = ?, APELLIDOS = ?, EMAIL = ?, TELEFONO = ?, PASSWORD = ? WHERE ID_CLIENTES = ?";
            preparedStatement = motorSQL.prepareStatement(sql);

            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getApellidos());
            preparedStatement.setString(3, cliente.getEmail());
            preparedStatement.setString(4, cliente.getTelefono());
            preparedStatement.setString(5, cliente.getPassword());
            preparedStatement.setInt(6, cliente.getIdCliente());

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
    public ArrayList<Cliente> findAll(Cliente bean) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            motorSQL.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (bean.getIdCliente() != 0) {
                    sql += " AND ID_CLIENTES='" + bean.getIdCliente() + "'";
                }
                if (bean.getNombre() != null) {
                    sql += " AND NOMBRE='" + bean.getNombre() + "'";
                }
                if (bean.getApellidos() != null) {
                    sql += " AND APELLIDOS='" + bean.getApellidos() + "'";
                }
                if (bean.getEmail() != null) {
                    sql += " AND EMAIL='" + bean.getEmail() + "'";
                }
                if (bean.getTelefono() != null) {
                    sql += " AND TELEFONO='" + bean.getTelefono() + "'";
                }
                if (bean.getPassword() != null) {
                    sql += " AND PASSWORD='" + bean.getPassword() + "'";
                }
            }
            ResultSet rs = motorSQL.executeQuery(sql);

            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setIdCliente(rs.getInt("ID_CLIENTES"));
                cliente.setNombre(rs.getString("NOMBRE"));
                cliente.setApellidos(rs.getString("APELLIDOS"));
                cliente.setEmail(rs.getString("EMAIL"));
                cliente.setTelefono(rs.getString("TELEFONO"));
                cliente.setPassword(rs.getString("PASSWORD"));

                clientes.add(cliente);
            }

        } catch (Exception ex) {
            clientes.clear();
            ex.printStackTrace();
        } finally {
            motorSQL.disconnect();
        }
        return clientes;
    }
    public Cliente findByEmailOrPhoneAndPassword(String emailOrPhone, String password) {
        Cliente cliente = null;
        PreparedStatement preparedStatement = null;
        try {
            motorSQL.connect();
            String sql = "SELECT * FROM CLIENTES WHERE (EMAIL = ? OR TELEFONO = ?) AND PASSWORD = ?";
            preparedStatement = motorSQL.prepareStatement(sql);

            preparedStatement.setString(1, emailOrPhone);
            preparedStatement.setString(2, emailOrPhone);
            preparedStatement.setString(3, password);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("ID_CLIENTES"));
                cliente.setNombre(rs.getString("NOMBRE"));
                cliente.setApellidos(rs.getString("APELLIDOS"));
                cliente.setEmail(rs.getString("EMAIL"));
                cliente.setTelefono(rs.getString("TELEFONO"));
                cliente.setPassword(rs.getString("PASSWORD"));
            }
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
        return cliente;
    }

}
