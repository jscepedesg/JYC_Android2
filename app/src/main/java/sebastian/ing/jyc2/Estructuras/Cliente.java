package sebastian.ing.jyc2.Estructuras;

import java.io.Serializable;

public class Cliente implements Serializable {

	private int id_c;
	private String nom_r;
	private String nom_c;
	private String apel_c;
	private String dire_c;
	private String tele;
	private String correo;
	private String dia;
	private int num_ruta;
	
	public Cliente(int id_c, String nom_r, String nom_c , String apel_c, String dire_c, String tele, String correo, String dia, int num_ruta)
	{
		this.id_c=id_c;
		this.nom_r=nom_r;
		this.nom_c=nom_c;
		this.apel_c=apel_c;
		this.dire_c=dire_c;
		this.tele=tele;
		this.correo=correo;
		this.dia=dia;
		this.num_ruta=num_ruta;
		
	}

	
	public int getNum_ruta() {
		return num_ruta;
	}
	
	public int getId_c() {
		return id_c;
	}

	public String getNom_r() {
		return nom_r;
	}

	public String getNom_c() {
		return nom_c;
	}

	public String getApel_c() {
		return apel_c;
	}

	public String getDire_c() {
		return dire_c;
	}

	public String getTele() {
		return tele;
	}

	public String getCorreo() {
		return correo;
	}

	public String getDia() {
		return dia;
	}
	
}
