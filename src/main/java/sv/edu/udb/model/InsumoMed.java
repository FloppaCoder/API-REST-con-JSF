package sv.edu.udb.model;

public class InsumoMed {
    private int id;
    private String nombre;
    private int cantidad;
    private double precio;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }
}
