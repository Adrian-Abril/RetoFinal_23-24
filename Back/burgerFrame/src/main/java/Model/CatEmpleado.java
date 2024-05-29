package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class CatEmpleado {
    private int idCatEmpleado;
    private String nombreCategoria;

    public CatEmpleado(int idCatEmpleado, String nombreCategoria) {
        this.idCatEmpleado = idCatEmpleado;
        this.nombreCategoria = nombreCategoria;
    }

    public CatEmpleado() {}

    public int getIdCatEmpleado() {
        return idCatEmpleado;
    }

    public void setIdCatEmpleado(int idCatEmpleado) {
        this.idCatEmpleado = idCatEmpleado;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public static String toArrayJSon(ArrayList<CatEmpleado> catEmpleado) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        return gson.toJson(catEmpleado);
    }
}

