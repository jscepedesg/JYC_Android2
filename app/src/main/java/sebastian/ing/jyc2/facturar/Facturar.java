package sebastian.ing.jyc2.facturar;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import sebastian.ing.jyc2.Estructuras.Factura;
import sebastian.ing.jyc2.R;
import sebastian.ing.jyc2.Sockets.MensajeEnaviar;
import sebastian.ing.jyc2.Sockets.Paquete_envio_peticion;
import sebastian.ing.jyc2.Sockets.Servidor;
import sebastian.ing.jyc2.Utilidades.ConexionSQLiteHelper;
import sebastian.ing.jyc2.Utilidades.Utilidades;

/**
 * Created by Usuario on 12/02/2019.
 */

public class Facturar extends AppCompatActivity
{

    private TextView numero_facturas;
    private boolean prueba_conexion;

    private ConexionSQLiteHelper conn;

    private ArrayList<Factura> clienteArralist;

    public Facturar()
    {
        prueba_conexion=false;
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facturar);

        conn = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);

        numero_facturas = (TextView) findViewById(R.id.textnum);

        numero_facturas.setText(String.valueOf(getObtenerNumeroFactura()));


    }

    public int getObtenerNumeroFactura()
    {
        SQLiteDatabase dbe = conn.getReadableDatabase();
        int numRows = (int) DatabaseUtils.longForQuery(dbe, "SELECT COUNT(*) FROM "+ Utilidades.TABLA_REGISTRO, null);
        dbe.close();
        return numRows;
    }


    public void setEviarFacturas(View view)
    {
        SQLiteDatabase db = conn.getReadableDatabase();
        clienteArralist = new ArrayList<Factura>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Utilidades.TABLA_FACTURA,null);

        while (cursor.moveToNext())
        {
            clienteArralist.add(new Factura(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),
                    cursor.getInt(4),cursor.getString(5)));
            Log.d("Base de datos factura",cursor.getInt(0)+" "+cursor.getInt(1)+" "+cursor.getInt(2)+" "+cursor.getInt(3)
                    +" "+cursor.getInt(4)+" "+cursor.getString(5));
        }

        conn.close();

        setCovertirGson();
    }

    private void setCovertirGson()
    {
        if(getPrueba_conexion()) {
            Gson gson = new Gson();
            String bdd = gson.toJson(clienteArralist);
            Paquete_envio_peticion paqueteFinal = new Paquete_envio_peticion(3, bdd);
            String paquete = gson.toJson(paqueteFinal);

            Log.d("Paquete final", paquete);
            setSend(paquete);

            SQLiteDatabase dbe = conn.getWritableDatabase();
            dbe.execSQL("delete from " + Utilidades.TABLA_FACTURA);
            dbe.close();

            SQLiteDatabase dbe1 = conn.getWritableDatabase();
            dbe1.execSQL("delete from " + Utilidades.TABLA_REGISTRO);
            dbe1.close();

            SQLiteDatabase dbe2 = conn.getWritableDatabase();
            dbe2.execSQL("delete from " + Utilidades.TABLA_CLIENTES_CERRADOS);
            dbe2.close();



            numero_facturas.setText(String.valueOf(getObtenerNumeroFactura()));

            prueba_conexion=false;
            Servidor.setPruebaConexion(false);
            Toast.makeText(getApplicationContext(),"Sincronización exitosa", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No hay conexion con el servidor", Toast.LENGTH_SHORT).show();
        }
    }

    public void setSend(String peticion)
    {
        MensajeEnaviar mensajeEnaviar = new MensajeEnaviar();
        mensajeEnaviar.execute(peticion);
    }

    public boolean getPrueba_conexion()
    {
        setSend("0");
        try {
            Thread.sleep(1*1000);
        } catch (Exception e) {
            e.getMessage();
        }
        prueba_conexion=Servidor.getPruebaConexion();

        return prueba_conexion;
    }

}
