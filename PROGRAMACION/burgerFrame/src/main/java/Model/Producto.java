package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private String imagen;
    private int idCatProducto;

    public Producto(int idProducto, String nombre, String descripcion, double precio, String imagen, int idCatProducto) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.idCatProducto = idCatProducto;
    }

    public Producto() {}

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getIdCatProducto() {
        return idCatProducto;
    }

    public void setIdCatProducto(int idCatProducto) {
        this.idCatProducto = idCatProducto;
    }

    public static String toArrayJSon(ArrayList<Producto> Producto) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        return gson.toJson(Producto);
    }
}
