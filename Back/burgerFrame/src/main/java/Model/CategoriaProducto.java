package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class CategoriaProducto {
    private int idCatProducto;
    private String nombreCategoria;

    public CategoriaProducto(int idCatProducto, String nombreCategoria) {
        this.idCatProducto = idCatProducto;
        this.nombreCategoria = nombreCategoria;
    }

    public CategoriaProducto() {}

    public int getIdCatProducto() {
        return idCatProducto;
    }

    public void setIdCatProducto(int idCatProducto) {
        this.idCatProducto = idCatProducto;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public static String toArrayJSon(ArrayList<CategoriaProducto> categoriaProducto) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        return gson.toJson(categoriaProducto);
    }
}
