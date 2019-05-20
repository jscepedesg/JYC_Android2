package sebastian.ing.jyc2.Estructuras;

/**
 * Created by Usuario on 11/02/2019.
 */

public class Vendedor {

    private int id;
    private String nom;
    private String apel;
    private String ruta;
    private String tele;
    private String correo;

    public Vendedor(int id, String nom, String apel, String ruta, String tele, String correo)
    {
        this.id=id;
        this.nom=nom;
        this.apel=apel;
        this.ruta=ruta;
        this.tele=tele;
        this.correo=correo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setApel(String apel) {
        this.apel = apel;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getApel() {
        return apel;
    }

    public String getRuta() {
        return ruta;
    }

    public String getTele() {
        return tele;
    }

    public String getCorreo() {
        return correo;
    }
}
