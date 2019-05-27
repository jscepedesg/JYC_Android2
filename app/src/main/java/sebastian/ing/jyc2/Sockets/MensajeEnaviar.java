package sebastian.ing.jyc2.Sockets;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Usuario on 12/02/2019.
 */

public class MensajeEnaviar extends AsyncTask<String, Void, Void>
{

    private Socket socketc;
    private DataOutputStream flujo;
    private PrintWriter pw;
    private String ip = "192.168.1.70";

    @Override
    protected Void doInBackground(String... voids)
    {
        String mensaje = voids[0];
        try
        {
            socketc = new Socket(ip,5000);
            pw = new PrintWriter(socketc.getOutputStream());
            pw.write(mensaje);
            pw.flush();
            pw.close();
            socketc.close();
        }
        catch (Exception e)
        {
            e.getMessage();
            Log.d("No hay conexion jajaja",e.getMessage());
        }

        return null;
    }
}
