package Model;

public class Empleado {
    private int idEmpleado;
    private int idCatEmpleado;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;

    public Empleado(int idEmpleado, int idCatEmpleado, String nombre, String apellidos, String email, String telefono) {
        this.idEmpleado = idEmpleado;
        this.idCatEmpleado = idCatEmpleado;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
    }

    public Empleado() {}

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdCatEmpleado() {
        return idCatEmpleado;
    }

    public void setIdCatEmpleado(int idCatEmpleado) {
        this.idCatEmpleado = idCatEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
