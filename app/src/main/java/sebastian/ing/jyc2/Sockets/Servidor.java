package sebastian.ing.jyc2.Sockets;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import sebastian.ing.jyc2.Estructuras.*;

/**
 * Created by Usuario on 7/02/2019.
 */

public class Servidor implements Runnable {

    private static Gson gson;
    private ServerSocket servidor;
    private Socket socket;
    private BufferedReader rd;
    private static InputStreamReader in;
    private String mensaje_texto;
    private static ArrayList<Vendedor> vendedor;
    private static ArrayList<Cliente> cliente;
    private static ArrayList<Producto> producto;
    private static String mensaje_aux;
    private static boolean pruebaConexion;
    private Paquete_envio_BDD paquete_de_envio;

    public Servidor()
    {
        Thread hilo = new Thread(this);
        hilo.start();
        pruebaConexion=false;
    }
    public void run()
    {

            try
            {
                gson = new Gson();
                while (true)
                {
                    servidor = new ServerSocket(5002);
                    socket = servidor.accept();
                    in = new InputStreamReader(socket.getInputStream());
                    rd= new BufferedReader(in);
                    mensaje_texto = rd.readLine();
                    Log.d("Mensaje: ",mensaje_texto);
                    mensaje_aux=mensaje_texto;
                    in.close();
                    rd.close();
                    servidor.close();
                    socket.close();
                    if(mensaje_aux.equals("0"))
                    {
                        setPruebaConexion(true);
                    }
                    else
                    {
                        Log.d("Mensaje entro: ",mensaje_aux);
                        setGsonToPaqueteEnvio();
                        //getGsonToArray();
                        setPruebaConexion(false);
                    }

                }
            }
            catch(Exception e)
            {
                e.getMessage();
                setPruebaConexion(false);
            }


    }

    public void setGsonToPaqueteEnvio()
    {
        paquete_de_envio=gson.fromJson(mensaje_aux,Paquete_envio_BDD.class);
        vendedor=paquete_de_envio.getVendedor();
        cliente=paquete_de_envio.getCliente();
        producto=paquete_de_envio.getProducto();


    }

    public static ArrayList<Vendedor> getGsonToArray()
    {
        //vendedor = gson.fromJson(mensaje_aux, new TypeToken<ArrayList<Vendedor>>(){}.getType());

        return vendedor;
    }

    public static ArrayList<Cliente> getCliente() {
        return cliente;
    }

    public static ArrayList<Producto> getProducto() {
        return producto;
    }

    public static void setPruebaConexion(boolean pruebaCon)
    {
        pruebaConexion=pruebaCon;

    }
    public static  boolean getPruebaConexion()
    {return pruebaConexion;}


}
