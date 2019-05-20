package sebastian.ing.jyc2.Sockets;

import java.util.ArrayList;

import sebastian.ing.jyc2.Estructuras.Cliente;
import sebastian.ing.jyc2.Estructuras.Producto;
import sebastian.ing.jyc2.Estructuras.Vendedor;

public class Paquete_envio_BDD 
{
	private ArrayList<Vendedor> vendedor;
	private ArrayList<Cliente> cliente;
	private ArrayList<Producto> producto;
	
	public Paquete_envio_BDD(ArrayList<Vendedor> vendedor, ArrayList<Cliente> cliente, ArrayList<Producto> producto)
	{
		this.vendedor = vendedor;
		this.cliente = cliente;
		this.producto = producto;
	}

	public ArrayList<Vendedor> getVendedor() {
		return vendedor;
	}

	public void setVendedor(ArrayList<Vendedor> vendedor) {
		this.vendedor = vendedor;
	}

	public ArrayList<Cliente> getCliente() {
		return cliente;
	}

	public void setCliente(ArrayList<Cliente> cliente) {
		this.cliente = cliente;
	}

	public ArrayList<Producto> getProducto() {
		return producto;
	}

	public void setProducto(ArrayList<Producto> producto) {
		this.producto = producto;
	}
	

}
