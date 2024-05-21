package Model;

import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PeliculaDao implements IDao < Pelicula, Integer> {
    private final String SQL_FIND_ALL = "SELECT * FROM PELICULAS WHERE 1=1 ";

    @Override
    public int add(Pelicula bean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int delete(Integer e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int update(Pelicula bean) {


        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Pelicula> findAll(Pelicula bean) {
        ArrayList<Pelicula> peliculas = new ArrayList<>();
        MotorSQL motor = new MotorSQL();
        try {
            motor.connect();
            String sql = SQL_FIND_ALL;
            if (bean != null) {
                if (((Pelicula)bean).getId() != 0) {
                    sql += " AND ID_PELICULA='" + ((Pelicula)bean).getId() + "'";
                }
                if (((Pelicula)bean).getTitulo() != null) {
                    sql += " AND TITULO='" + ((Pelicula)bean).getTitulo() + "'";
                }
                if (((Pelicula)bean).getPrecio() > 0) {
                    sql += " AND PRECIO=" + ((Pelicula)bean).getPrecio() + "";
                }
                if (((Pelicula)bean).getTrailer() != null) {
                    sql += " AND TRAILER='" + ((Pelicula)bean).getTrailer() + "'";
                }
                if (((Pelicula)bean).getSinopsis() != null) {
                    sql += " AND SINOPSIS LIKE('%" + ((Pelicula)bean).getSinopsis() + "%')";
                }
                if (((Pelicula)bean).getnVotos() != 0) {
                    sql += " AND N_VOTOS='" + ((Pelicula)bean).getnVotos() + "'";
                }
                if (((Pelicula)bean).getsPuntuacion() != 0) {
                    sql += " AND S_PUNTUACION='" + ((Pelicula)bean).getsPuntuacion() + "'";
                }
                if (((Pelicula)bean).getFechaEstreno() != null) {
                    sql += " AND FECHA_ESTRENO='" + ((Pelicula)bean).getFechaEstreno() + "'";
                }
                if (((Pelicula)bean).getUrl()!= null) {
                    sql += " AND URL='" + ((Pelicula)bean).getUrl() + "'";
                }
            }
            ResultSet rs = motor.executeQuery(sql);

            while (rs.next())
            {
                Pelicula pelicula = new Pelicula();
                pelicula.setId(rs.getInt("Id"));
                pelicula.setTitulo(rs.getString("Titulo"));
                pelicula.setPrecio(rs.getDouble("Precio"));
                pelicula.setTrailer(rs.getString("Trailer"));
                pelicula.setSinopsis(rs.getString("Sinopsis"));
                pelicula.setnVotos(rs.getInt("Votos"));
                pelicula.setsPuntuacion(rs.getInt("Puntuacion"));
                pelicula.setFechaEstreno(rs.getString("FechaEstreno"));
                pelicula.setUrl(rs.getString("Url"));
                Genero genero = new Genero();
                genero.setIdGenero(rs.getInt("IdGenero"));
                pelicula.setGenero(genero);

                peliculas.add(pelicula);
            }

        }catch (Exception ex)
        {
            peliculas.clear();
        }
        finally {
            motor.disconnect();
        }
        return peliculas;
    }
}
