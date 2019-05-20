package sebastian.ing.jyc2.Estructuras;

/**
 * Created by Usuario on 14/02/2019.
 */

public class Bodega
{
    private int id_P;
    private int cantidad;
    private int id_B;

    public Bodega(int id_P, int cantidad, int id_B)
    {
        this.id_P = id_P;
        this.cantidad = cantidad;
        this.id_B = id_B;
    }

    public void setId_P(int id_P) {
        this.id_P = id_P;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setId_B(int id_B) {
        this.id_B = id_B;
    }

    public int getId_P() {
        return id_P;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getId_B() {
        return id_B;
    }
}
