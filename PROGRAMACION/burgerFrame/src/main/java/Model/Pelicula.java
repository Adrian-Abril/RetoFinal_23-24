package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Pelicula {
    private String titulo, trailer, sinopsis, fechaEstreno, url;
    private int duracion, nVotos, sPuntuacion, id;
    private Double precio;
    private Genero genero;
    private String year;

    public Pelicula(String titulo, Double precio, int duracion, String trailer, String descripcion, int nVotos, int sPuntuacion,
                    String year, String url){
        this.titulo = titulo;
        this.trailer = trailer;
        //this.sinopsis = sinopsis;
        //this.fechaEstreno = fechaEstreno;
        this.url = url;
        this.duracion = duracion;
        this.nVotos = nVotos;
        this.sPuntuacion = sPuntuacion;
        //this.id = id;
        this.year = year;
        this.precio = precio;
    }

    public Pelicula() {
    }

    //GETTERS & SETTERS
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getTrailer() {
        return trailer;
    }
    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
    public String getSinopsis() {
        return sinopsis;
    }
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
    public String getFechaEstreno() {
        return fechaEstreno;
    }
    public void setFechaEstreno(String fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }
    public int getDuracion() {
        return duracion;
    }
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    public int getnVotos() {
        return nVotos;
    }
    public void setnVotos(int nVotos) {
        this.nVotos = nVotos;
    }
    public int getsPuntuacion() {
        return sPuntuacion;
    }
    public void setsPuntuacion(int sPuntuacion) {
        this.sPuntuacion = sPuntuacion;
    }
    public int getId() {
        return id;
    }
    public void setId(int idPelicula) {
        this.id = idPelicula;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getYear() {
        return this.year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    public void setGenero (Genero genero)
    {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Pelicula{" + "titulo=" + titulo + ", trailer=" + trailer + ", sinopsis=" + sinopsis + ", fechaEstreno=" + fechaEstreno + ", duracion=" + duracion + ", nVotos=" + nVotos + ", sPuntuacion=" + sPuntuacion + ", id=" + id + ", precio=" + precio + '}';
    }

    public static String fromArrayToJson(ArrayList<Pelicula> peliculas){
        String resp = "[";
        for (Pelicula pelicula : peliculas) {
            resp+= "{" +
                    "'titulo':'" + pelicula.getTitulo() + "', "
                    + "'trailer':'" + pelicula.getTrailer() + "',"
                    + " 'sinopsis':'" + pelicula.getSinopsis() + "', "
                    + "'fechaEstreno':" + pelicula.getFechaEstreno() + ", "
                    + "'duracion':" + pelicula.getDuracion() +
                    ", 'nVotos':" + pelicula.getnVotos() + ", 'sPuntuacion':"
                    + pelicula.getnVotos() + ", 'id':" + pelicula.getId() + ", "
                    + "'precio':" + pelicula.getPrecio() + "}";
            resp+=",";
        }
        resp = resp.substring(0, resp.length()-1);
        resp+="]";
        return resp;
    }
    public static String toArrayJSon(ArrayList<Pelicula> peliculas) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        return gson.toJson(peliculas);
    }
}

