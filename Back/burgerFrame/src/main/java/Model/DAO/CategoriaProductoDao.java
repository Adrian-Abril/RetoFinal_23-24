package Model.DAO;

import Model.CategoriaProducto;
import Model.IDao;
import Model.MotorSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaProductoDao implements IDao<CategoriaProducto, Integer> {
    private MotorSQL motorSQL;

    public CategoriaProductoDao() {
        this.motorSQL = new MotorSQL();
    }

    private final String SQL_FIND_ALL = "SELECT * FROM CATEGORIA_PRODUCTO WHERE 1=1";

    @Override
    public int add(CategoriaProducto categoriaProducto) {
        int result = 0;
        PreparedStatement preparedStatement = null;
        try {
            motorSQL.connect();
            String sql = "INSERT INTO CATEGORIA_PRODUCTO (ID_CATPRODUCTO, NOMBRE_CATEGORIA) VALUES (?, ?)";
            preparedStatement = motorSQL.prepareStatement(sql);

            preparedStatement.setInt(1, categoriaProducto.getIdCatProducto());
            preparedStatement.setString(2, categoriaProducto.getNombreCategoria());

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
            String sql = "DELETE FROM CATEGORIA_PRODUCTO WHERE ID_CATPRODUCTO = ?";
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
    public int update(CategoriaProducto categoriaProducto) {
        int result = 0;
        PreparedStatement preparedStatement = null;
        try {
            motorSQL.connect();
            String sql = "UPDATE CATEGORIA_PRODUCTO SET NOMBRE_CATEGORIA = ? WHERE ID_CATPRODUCTO = ?";
            preparedStatement = motorSQL.prepareStatement(sql);

            preparedStatement.setString(1, categoriaProducto.getNombreCategoria());
            preparedStatement.setInt(2, categoriaProducto.getIdCatProducto());

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
    public ArrayList<CategoriaProducto> findAll(CategoriaProducto bean) {
        ArrayList<CategoriaProducto> categoriaProductos = new ArrayList<>();
        try {
            motorSQL.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (bean.getIdCatProducto() != 0) {
                    sql += " AND ID_CATPRODUCTO='" + bean.getIdCatProducto() + "'";
                }
                if (bean.getNombreCategoria() != null) {
                    sql += " AND NOMBRE_CATEGORIA='" + bean.getNombreCategoria() + "'";
                }
            }
            ResultSet rs = motorSQL.executeQuery(sql);

            while (rs.next()) {
                CategoriaProducto categoriaProducto = new CategoriaProducto();

                categoriaProducto.setIdCatProducto(rs.getInt("ID_CATPRODUCTO"));
                categoriaProducto.setNombreCategoria(rs.getString("NOMBRE_CATEGORIA"));

                categoriaProductos.add(categoriaProducto);
            }

        } catch (Exception ex) {
            categoriaProductos.clear();
            ex.printStackTrace();
        } finally {
            motorSQL.disconnect();
        }
        return categoriaProductos;
    }
}
