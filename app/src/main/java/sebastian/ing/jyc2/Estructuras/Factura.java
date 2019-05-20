package sebastian.ing.jyc2.Estructuras;

/**
 * Created by Usuario on 14/02/2019.
 */

public class Factura
{
    private int id_pro3;
    private int id_factura;
    private int id_ven1;
    private int id_clie1;
    private int cantidad;
    private String fecha_de_facturacion;

    public Factura(int id_pro3, int id_factura, int id_ven1, int id_clie1, int cantidad, String fecha_de_facturacion) {
        this.id_pro3 = id_pro3;
        this.id_factura = id_factura;
        this.id_ven1 = id_ven1;
        this.id_clie1 = id_clie1;
        this.cantidad = cantidad;
        this.fecha_de_facturacion = fecha_de_facturacion;
    }

    public void setId_pro3(int id_pro3) {
        this.id_pro3 = id_pro3;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public void setId_ven1(int id_ven1) {
        this.id_ven1 = id_ven1;
    }

    public void setId_clie1(int id_clie1) {
        this.id_clie1 = id_clie1;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFecha_de_facturacion(String fecha_de_facturacion) {
        this.fecha_de_facturacion = fecha_de_facturacion;
    }

    public int getId_pro3() {
        return id_pro3;
    }

    public int getId_factura() {
        return id_factura;
    }

    public int getId_ven1() {
        return id_ven1;
    }

    public int getId_clie1() {
        return id_clie1;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getFecha_de_facturacion() {
        return fecha_de_facturacion;
    }
}
