package sebastian.ing.jyc2.crear_clientes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import sebastian.ing.jyc2.Estructuras.Cliente_provicional;
import sebastian.ing.jyc2.R;
import sebastian.ing.jyc2.Sockets.MensajeEnaviar;
import sebastian.ing.jyc2.Sockets.Paquete_envio_peticion;
import sebastian.ing.jyc2.Sockets.Servidor;
import sebastian.ing.jyc2.Utilidades.ConexionSQLiteHelper;
import sebastian.ing.jyc2.Utilidades.Utilidades;

/**
 * Created by Usuario on 12/02/2019.
 */

public class Cliente_Int extends AppCompatActivity
{
    private Spinner combodias;
    private ConexionSQLiteHelper conn1;
    private EditText[] datos = new EditText[7];
    private TextView tamaño;
    private ArrayList<Cliente_provicional> clienteArralist;
    private boolean prueba_conexion;

    public Cliente_Int()
    {
        prueba_conexion=false;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente);

        combodias = (Spinner) findViewById(R.id.spinner);
        datos[0] = (EditText) findViewById(R.id.edit_nomrazonsocial);
        datos[1] = (EditText) findViewById(R.id.edit_nomcliente);
        datos[2] = (EditText) findViewById(R.id.editText8);
        datos[3] = (EditText) findViewById(R.id.edit_correo);
        datos[4] = (EditText) findViewById(R.id.editdireccion);
        datos[5] = (EditText) findViewById(R.id.editTel);
        datos[6] = (EditText) findViewById(R.id.edit_numRuta);
        tamaño = (TextView) findViewById(R.id.textresul);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.combo_dias,android.R.layout.simple_spinner_item);
        combodias.setAdapter(adapter);

        conn1 = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);

        SQLiteDatabase db1 = conn1.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db1, Utilidades.TABLA_CLIENTE_PROVICIONAL);
        tamaño.setText(String.valueOf(numRows));
        db1.close();

    }

    public void setGuardarClienteProvicional(View view)
    {
        if(!datos[0].getText().toString().equals("")&&!datos[1].getText().toString().equals("")
                &&!datos[2].getText().toString().equals("")&&!datos[3].getText().toString().equals("")
                &&!datos[4].getText().toString().equals("")&&!datos[5].getText().toString().equals("")
                &&!combodias.getSelectedItem().toString().equals("Seleccionar")&&!datos[6].getText().toString().equals(""))
        {
            try
            {
                Log.d("Mensaje en main",datos[0].getText().toString() + " " + datos[1].getText().toString()
                        + " " + datos[2].getText().toString() + " " + datos[3].getText().toString() + " "
                        + datos[4].getText().toString()+" "+datos[5].getText().toString()
                        +" "+combodias.getSelectedItem().toString()+" "+datos[6].getText().toString());

                SQLiteDatabase db= conn1.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Utilidades.CAMPO_NOM_RAZON_SOCIAL_PROVICIONAL,datos[0].getText().toString());
                values.put(Utilidades.CAMPO_NOMBRE_CLIE_PROVICIONAL,datos[1].getText().toString());
                values.put(Utilidades.CAMPO_APELLIDO_CLIE_PROVICIONAL,datos[2].getText().toString());
                values.put(Utilidades.CAMPO_DIRECCION_CLIE_PROVICIONAL,datos[4].getText().toString());
                values.put(Utilidades.CAMPO_TELEFONO_CLIE_PROVICIONAL,datos[5].getText().toString());
                values.put(Utilidades.CAMPO_CORREO_CLIE_PROVICIONAL,datos[3].getText().toString());
                values.put(Utilidades.CAMPO_DIA_ATENCION_CLIE_PROVICIONAL,combodias.getSelectedItem().toString());
                values.put(Utilidades.CAMPO_NUM_RUTA_CLIE_PROVICIONAL,datos[6].getText().toString());

                Long id_resultado = db.insert(Utilidades.TABLA_CLIENTE_PROVICIONAL,Utilidades.CAMPO_NUM_RUTA_CLIE_PROVICIONAL,values);
                Toast.makeText(getApplicationContext(),"Id resgistro: "+id_resultado, Toast.LENGTH_SHORT).show();
                db.close();

            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Error BDD", Toast.LENGTH_SHORT).show();
                e.getMessage();
            }
            SQLiteDatabase db1 = conn1.getReadableDatabase();
            int numRows = (int) DatabaseUtils.queryNumEntries(db1, Utilidades.TABLA_CLIENTE_PROVICIONAL);
            tamaño.setText(String.valueOf(numRows));
            db1.close();

        }
        else
        {
            Toast.makeText(getApplicationContext(),"No se han ingresado todos los datos", Toast.LENGTH_SHORT).show();
        }

    }

    public void setEviarClientesNuevos(View view)
    {
        SQLiteDatabase db = conn1.getReadableDatabase();
        clienteArralist = new ArrayList<Cliente_provicional>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Utilidades.TABLA_CLIENTE_PROVICIONAL,null);

        while (cursor.moveToNext())
        {
            clienteArralist.add(new Cliente_provicional(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                    cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getInt(8)));
            Log.d("Base de datos Cliente",cursor.getInt(0)+" "+cursor.getString(1)+" "+cursor.getInt(2)+" "+cursor.getString(3)
                    +" "+cursor.getString(4)+" "+cursor.getString(5)+" "+cursor.getString(6)+" "+cursor.getInt(7));
        }

        conn1.close();

        setCovertirGson();
    }

    private void setCovertirGson()
    {
        if(getPrueba_conexion()) {
            Gson gson = new Gson();
            String bdd = gson.toJson(clienteArralist);
            Paquete_envio_peticion paqueteFinal = new Paquete_envio_peticion(2, bdd);
            String paquete = gson.toJson(paqueteFinal);

            Log.d("Paquete final", paquete);
            setSend(paquete);

            SQLiteDatabase dbe = conn1.getWritableDatabase();
            dbe.execSQL("delete from " + Utilidades.TABLA_CLIENTE_PROVICIONAL);
            dbe.close();

            SQLiteDatabase db1 = conn1.getReadableDatabase();
            int numRows = (int) DatabaseUtils.queryNumEntries(db1, Utilidades.TABLA_CLIENTE_PROVICIONAL);
            tamaño.setText(String.valueOf(numRows));
            db1.close();
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

        /*Envia objecto Json
        //Vendedor usuario = new Vendedor(Integer.parseInt(contra.getText().toString()),nom.getText().toString(),null,null,null,null);
        //Gson gson = new Gson();
        //String json = gson.toJson(usuario);
        */
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

    public void setListaClientes(View view)
    {
        Intent i = new Intent(this, Cliente_lista_clientes.class);
        startActivity(i);
        finish();
    }

}
