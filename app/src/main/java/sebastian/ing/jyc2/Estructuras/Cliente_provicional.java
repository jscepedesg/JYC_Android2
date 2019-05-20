package sebastian.ing.jyc2.Estructuras;

import java.io.Serializable;

/**
 * Created by Usuario on 18/02/2019.
 */

public class Cliente_provicional implements Serializable
{
    private int id_c_p;
    private String nom_r_p;
    private String nom_c_p;
    private String apel_c_p;
    private String dire_c_p;
    private String tele_p;
    private String correo_p;
    private String dia_p;
    private int num_ruta_p;

    public Cliente_provicional(int id_c_p, String nom_r_p, String nom_c_p , String apel_c_p, String dire_c_p, String tele_p, String correo_p, String dia_p, int num_ruta_p)
    {
        this.id_c_p=id_c_p;
        this.nom_r_p=nom_r_p;
        this.nom_c_p=nom_c_p;
        this.apel_c_p=apel_c_p;
        this.dire_c_p=dire_c_p;
        this.tele_p=tele_p;
        this.correo_p=correo_p;
        this.dia_p=dia_p;
        this.num_ruta_p=num_ruta_p;

    }

    public Cliente_provicional(String nom_r_p, String nom_c_p , String apel_c_p, String dire_c_p, String tele_p, String correo_p, String dia_p, int num_ruta_p)
    {
        this.nom_r_p=nom_r_p;
        this.nom_c_p=nom_c_p;
        this.apel_c_p=apel_c_p;
        this.dire_c_p=dire_c_p;
        this.tele_p=tele_p;
        this.correo_p=correo_p;
        this.dia_p=dia_p;
        this.num_ruta_p=num_ruta_p;

    }

    public void setNom_r(String nom_r_p) {
        this.nom_r_p = nom_r_p;
    }

    public void setNom_c(String nom_c_p) {
        this.nom_c_p = nom_c_p;
    }

    public void setApel_c(String apel_c_p) {
        this.apel_c_p = apel_c_p;
    }

    public void setDire_c(String dire_c_p) {
        this.dire_c_p = dire_c_p;
    }

    public void setTele(String tele_p) {
        this.tele_p = tele_p;
    }

    public void setCorreo(String correo_p) {
        this.correo_p = correo_p;
    }

    public void setDia(String dia_p) {
        this.dia_p = dia_p;
    }

    public void setNum_ruta_p(int num_ruta_p) {
        this.num_ruta_p = num_ruta_p;
    }

    public String getNom_r() {
        return nom_r_p;
    }

    public String getNom_c() {
        return nom_c_p;
    }

    public String getApel_c() {
        return apel_c_p;
    }

    public String getDire_c() {
        return dire_c_p;
    }

    public String getTele() {
        return tele_p;
    }

    public String getCorreo() {
        return correo_p;
    }

    public String getDia() {
        return dia_p;
    }

    public int getNum_ruta_p() {
        return num_ruta_p;
    }

    public int getId_c_p() {return id_c_p;}

    public void setId_c_p(int id_c_p) {this.id_c_p = id_c_p;}
}
