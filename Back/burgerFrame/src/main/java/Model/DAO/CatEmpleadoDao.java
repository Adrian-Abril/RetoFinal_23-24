package Model.DAO;

import Model.CatEmpleado;
import Model.IDao;
import Model.MotorSQL;

import java.sql.*;
import java.util.ArrayList;

public class CatEmpleadoDao implements IDao<CatEmpleado, Integer> {
    private MotorSQL motorSQL;

    public CatEmpleadoDao() {
        this.motorSQL = new MotorSQL();
    }

    private final String SQL_FIND_ALL = "SELECT * FROM CAT_EMPLEADO WHERE 1=1";

    @Override
    public int add(CatEmpleado catEmpleado) {
        int result = 0;
        PreparedStatement preparedStatement = null;
        try {
            motorSQL.connect();
            String sql = "INSERT INTO CAT_EMPLEADO (ID_CATEMPLEADO, NOMBRE_CATEGORIA) VALUES (?, ?)";
            preparedStatement = motorSQL.prepareStatement(sql);

            preparedStatement.setInt(1, catEmpleado.getIdCatEmpleado());
            preparedStatement.setString(2, catEmpleado.getNombreCategoria());

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
            String sql = "DELETE FROM CAT_EMPLEADO WHERE ID_CATEMPLEADO = ?";
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
    public int update(CatEmpleado catEmpleado) {
        int result = 0;
        PreparedStatement preparedStatement = null;
        try {
            motorSQL.connect();
            String sql = "UPDATE CAT_EMPLEADO SET NOMBRE_CATEGORIA = ? WHERE ID_CATEMPLEADO = ?";
            preparedStatement = motorSQL.prepareStatement(sql);

            preparedStatement.setString(1, catEmpleado.getNombreCategoria());
            preparedStatement.setInt(2, catEmpleado.getIdCatEmpleado());

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
    public ArrayList<CatEmpleado> findAll(CatEmpleado bean) {
        ArrayList<CatEmpleado> catEmpleados = new ArrayList<>();
        try {
            motorSQL.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (bean.getIdCatEmpleado() != 0) {
                    sql += " AND ID_CATEMPLEADO='" + bean.getIdCatEmpleado() + "'";
                }
                if (bean.getNombreCategoria() != null) {
                    sql += " AND NOMBRE_CATEGORIA='" + bean.getNombreCategoria() + "'";
                }
            }
            ResultSet rs = motorSQL.executeQuery(sql);

            while (rs.next()) {
                CatEmpleado catEmpleado = new CatEmpleado();

                catEmpleado.setIdCatEmpleado(rs.getInt("ID_CATEMPLEADO"));
                catEmpleado.setNombreCategoria(rs.getString("NOMBRE_CATEGORIA"));

                catEmpleados.add(catEmpleado);
            }

        } catch (Exception ex) {
            catEmpleados.clear();
            ex.printStackTrace();
        } finally {
            motorSQL.disconnect();
        }
        return catEmpleados;
    }
}
