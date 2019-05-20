package sebastian.ing.jyc2.Sockets;

/**
 * Created by Usuario on 24/02/2019.
 */

public class Paquete_envio_peticion
{
    private int peticion;
    private String BDD;

    public Paquete_envio_peticion(int peticion, String BDD)
    {
        this.peticion = peticion;
        this.BDD = BDD;
    }

    public int getPeticion() {
        return peticion;
    }

    public String getBDD() {
        return BDD;
    }

    public void setPeticion(int peticion) {
        this.peticion = peticion;
    }

    public void setBDD(String BDD) {
        this.BDD = BDD;
    }
}
