package sebastian.ing.jyc2.Estructuras;

import java.io.Serializable;

/**
 * Created by Usuario on 2/05/2019.
 */

public class FacturaRelacionalProducto implements Serializable
{
    private String nombre_producto;
    private double precio_unitario;
    private  int cantidad;
    private double total_por_producto;

    public FacturaRelacionalProducto(String nombre_producto, double precio_unitario, int cantidad, double total_por_producto)
    {
        this.nombre_producto = nombre_producto;
        this.precio_unitario = precio_unitario;
        this.cantidad = cantidad;
        this.total_por_producto = total_por_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal_por_producto() {
        return total_por_producto;
    }

    public void setTotal_por_producto(double total_por_producto) {
        this.total_por_producto = total_por_producto;
    }
}
