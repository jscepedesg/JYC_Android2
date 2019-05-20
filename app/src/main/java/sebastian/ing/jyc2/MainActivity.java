package sebastian.ing.jyc2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

import sebastian.ing.jyc2.Estructuras.Cliente;
import sebastian.ing.jyc2.Estructuras.Producto;
import sebastian.ing.jyc2.Estructuras.Vendedor;
import sebastian.ing.jyc2.Sockets.MensajeEnaviar;
import sebastian.ing.jyc2.Sockets.Servidor;
import sebastian.ing.jyc2.Utilidades.ConexionSQLiteHelper;
import sebastian.ing.jyc2.Utilidades.Utilidades;
import sebastian.ing.jyc2.R;

public class MainActivity extends AppCompatActivity {

    private String usuario, contraseña;
    private EditText nom,contra;
    private ConexionSQLiteHelper conn1;
    private boolean prueba_conexion;
    private RadioButton RBSesion;
    private boolean estadoradioBtn;
    private static final String STRING_PREFERENCES="estado.verificacion";
    private static final String PREFERENCES_ESTADO_BOTON="estado.boton.sesion";
    //Usuario
    private static final String STRING_PREFERENCES_USUARIO="estado.verificacion.usuario";
    private static final String PREFERENCES_ESTADO_USUARIO="estado.usuario";



    public MainActivity()
    {
        usuario= "root";
        contraseña="1234";
        prueba_conexion=false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(setOptenerEstadoBoton())
        {
            Intent i = new Intent(this, Inicio.class);
            startActivity(i);
            finish();
        }
        nom=(EditText)findViewById(R.id.nombre);
        contra=(EditText)findViewById(R.id.contraseña);
        RBSesion=(RadioButton) findViewById(R.id.RBSesion);

        estadoradioBtn=RBSesion.isChecked();

        RBSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (estadoradioBtn)
                {
                    RBSesion.setChecked(false);
                }
                estadoradioBtn=RBSesion.isChecked();
            }
        });


        ConexionSQLiteHelper  conn = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);


        //Servidor
        Servidor servidor = new Servidor();

        conn1 = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);

    }

    public void InicioSeccion(View view)
    {
        if (!nom.getText().toString().equals("") && !contra.getText().toString().equals(""))
        {
            try
            {
                SQLiteDatabase db = conn1.getReadableDatabase();
                String[] parametros = {contra.getText().toString()};
                String[] campos = {Utilidades.CAMPO_ID,Utilidades.CAMPO_NOMBRE};

                Cursor cursor = db.query(Utilidades.TABLA_VENDEDOR,campos,Utilidades.CAMPO_ID+"=?",parametros,null,null,null);
                cursor.moveToFirst();
                Log.d("Consulta: ",cursor.getString(1));

                if (nom.getText().toString().equals(usuario) && contra.getText().toString().equals(contraseña)
                        || nom.getText().toString().equalsIgnoreCase(cursor.getString(1)) && contra.getText().toString().equalsIgnoreCase(cursor.getString(0)))
                {
                    setGuardarEstadoBoton();
                    setGuardarUsuario(cursor.getInt(0));
                    Intent i = new Intent(this, Inicio.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    alerta("Usuario incorrecto, no olvide sincronizar",1);
                }
                cursor.close();
                db.close();
            }
            catch (Exception e)
            {
                alerta("Usuario incorrecto, no olvide sincronizar",1);
            }

        }
        else
        {
            alerta("No se han ingresado todos los datos",1);
        }
    }

    public void setSincroniza(View view)
    {
        Toast.makeText(getApplicationContext(),"Verificando conexión.....",Toast.LENGTH_SHORT).show();
        if(getPrueba_conexion())
        {
            Toast.makeText(getApplicationContext(),"Conexión exitosa",Toast.LENGTH_SHORT).show();
            setSend("1");
            try {
                Thread.sleep(2*1000);
                Toast.makeText(getApplicationContext(),"Petición enviada, espere confirmación",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.getMessage();
            }
            setLlenarTablaVendedro(Servidor.getGsonToArray());
            setLlenarTablaCliente(Servidor.getCliente());
            setLlenarTablaProducto(Servidor.getProducto());
            prueba_conexion=false;

        }
        else
        {
            Toast.makeText(getApplicationContext(),"No hay conexion con el servidor",Toast.LENGTH_SHORT).show();
        }

    }

    private void setLlenarTablaVendedro(ArrayList<Vendedor> arrayVendedor)
    {

        SQLiteDatabase dbe = conn1.getWritableDatabase();
        dbe.execSQL("delete from "+ Utilidades.TABLA_VENDEDOR);
        dbe.close();
        try
        {
            for (Vendedor integer : arrayVendedor)
            {
                Log.d("Mensaje en main", integer.getId() + " " + integer.getNom() + " " + integer.getApel()
                        + " " + integer.getRuta() + " " + integer.getCorreo() + " " + integer.getTele());

                SQLiteDatabase db= conn1.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Utilidades.CAMPO_ID,integer.getId());
                values.put(Utilidades.CAMPO_NOMBRE,integer.getNom());
                values.put(Utilidades.CAMPO_APELLIDO,integer.getApel());
                values.put(Utilidades.CAMPO_RUTA,integer.getRuta());
                values.put(Utilidades.CAMPO_TELEFONO,integer.getTele());
                values.put(Utilidades.CAMPO_CORREO,integer.getCorreo());

                Long id_resultado = db.insert(Utilidades.TABLA_VENDEDOR,Utilidades.CAMPO_ID,values);
                Toast.makeText(getApplicationContext(),"Id resgistro: "+id_resultado,Toast.LENGTH_SHORT).show();
                db.close();
            }
            Toast.makeText(getApplicationContext(),"Sincronizacion finalizada, tabla 1",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            alerta("Error! Verifique la conexion",1);
            e.getMessage();
        }


    }

    public void alerta(String cadena, int num) {
        //se prepara la alerta creando nueva instancia
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        //seleccionamos la cadena a mostrar
        dialogBuilder.setMessage(cadena);
        //elegimo un titulo y configuramos para que se pueda quitar
        if(num==1)
        {
            dialogBuilder.setCancelable(true).setTitle("¡Atención!");
        }
        else if (num==2)
        {
            dialogBuilder.setCancelable(true).setTitle("Correcto");
        }

        //mostramos el dialogBuilder
        dialogBuilder.create().show();
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

    public void setLlenarTablaCliente(ArrayList<Cliente> arrayCliente)
    {
        SQLiteDatabase dbe = conn1.getWritableDatabase();
        dbe.execSQL("delete from "+ Utilidades.TABLA_CLIENTE);
        dbe.close();
        try
        {
            for (Cliente integer : arrayCliente)
            {
                Log.d("Mensaje en main", integer.getId_c() + " " + integer.getNom_r() + " " + integer.getNom_c()
                        + " " + integer.getApel_c() + " " + integer.getDire_c() + " " + integer.getTele()+" "+integer.getCorreo()
                        +" "+integer.getDia()+" "+integer.getNum_ruta());

                SQLiteDatabase db= conn1.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Utilidades.CAMPO_ID_CLIE,integer.getId_c());
                values.put(Utilidades.CAMPO_NOM_RAZON_SOCIAL,integer.getNom_r());
                values.put(Utilidades.CAMPO_NOMBRE_CLIE,integer.getNom_c());
                values.put(Utilidades.CAMPO_APELLIDO_CLIE,integer.getApel_c());
                values.put(Utilidades.CAMPO_DIRECCION_CLIE,integer.getDire_c());
                values.put(Utilidades.CAMPO_TELEFONO_CLIE,integer.getTele());
                values.put(Utilidades.CAMPO_CORREO_CLIE,integer.getCorreo());
                values.put(Utilidades.CAMPO_DIA_ATENCION_CLIE,integer.getDia());
                values.put(Utilidades.CAMPO_NUM_RUTA_CLIE,integer.getNum_ruta());

                db.insert(Utilidades.TABLA_CLIENTE,Utilidades.CAMPO_ID_CLIE,values);
                db.close();
            }
            Toast.makeText(getApplicationContext(),"Sincronizacion finalizada, tabla 2",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            alerta("Error! Verifique la conexion",1);
            e.getMessage();
        }
    }

    public void setLlenarTablaProducto(ArrayList<Producto> arrayProducto)
    {
        SQLiteDatabase dbe = conn1.getWritableDatabase();
        dbe.execSQL("delete from "+ Utilidades.TABLA_PRODUCTO);
        dbe.close();
        try
        {
            for (Producto integer : arrayProducto)
            {
                Log.d("Mensaje en main", integer.getCod_p() + " " + integer.getNom_p() + " " + integer.getPre_p()
                        + " " + integer.getLinea() + " " + integer.getCasa());

                SQLiteDatabase db= conn1.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Utilidades.CAMPO_ID_PRO,integer.getCod_p());
                values.put(Utilidades.CAMPO_NOMBRE_PRO,integer.getNom_p());
                values.put(Utilidades.CAMPO_PRECIO_PRO,integer.getPre_p());
                values.put(Utilidades.CAMPO_LINEA_PRO,integer.getLinea());
                values.put(Utilidades.CAMPO_CASA_EXPORTACION_PRO,integer.getCasa());

                db.insert(Utilidades.TABLA_PRODUCTO,Utilidades.CAMPO_ID_PRO,values);
                db.close();
            }
            Toast.makeText(getApplicationContext(),"Sincronizacion finalizada, tabla 3",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            alerta("Error! Verifique la conexion",1);
            e.getMessage();
        }
    }

    public void setGuardarEstadoBoton()
    {
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCES_ESTADO_BOTON,RBSesion.isChecked()).apply();
    }
    public boolean setOptenerEstadoBoton()
    {
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCES_ESTADO_BOTON,false);
    }

    public static void setCambiarEstadoBoton(Context c,boolean b)
    {
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCES_ESTADO_BOTON,b).apply();
    }

    public void setGuardarUsuario(int id)
    {
        Log.d("ID de bienvenida: ",String.valueOf(id));
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES_USUARIO,MODE_PRIVATE);
        preferences.edit().putInt(PREFERENCES_ESTADO_USUARIO,id).apply();
    }
    public static int setOptenerEstadoUsuario(Context c)
    {
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES_USUARIO,MODE_PRIVATE);
        return preferences.getInt(PREFERENCES_ESTADO_USUARIO,1);
    }

    public static void setCambiarEstadoUsuario(Context c,int b)
    {
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES_USUARIO,MODE_PRIVATE);
        preferences.edit().putInt(PREFERENCES_ESTADO_USUARIO,b).apply();
    }
}
