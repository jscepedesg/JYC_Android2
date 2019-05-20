package sebastian.ing.jyc2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import sebastian.ing.jyc2.Utilidades.ConexionSQLiteHelper;
import sebastian.ing.jyc2.Utilidades.Utilidades;
import sebastian.ing.jyc2.crear_clientes.Cliente_Int;
import sebastian.ing.jyc2.pedidos.Pedido;
import sebastian.ing.jyc2.vistaBasededatos.Vista_Base_Datos;

/**
 * Created by Usuario on 3/02/2019.
 */

public class Inicio extends AppCompatActivity
{

    private ConexionSQLiteHelper conn1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        setConsultaInicioUsuario();
    }


    public void setPedido(View view)
    {
        Intent i = new Intent(this, Pedido.class);
        startActivity(i);
    }

    public void setEstadistica(View view)
    {
        Intent i = new Intent(this, Estadistica.class);
        startActivity(i);
    }

    public void setFacturar(View view)
    {
        Intent i = new Intent(this, Facturar.class);
        startActivity(i);
    }

    public void setCliente(View view)
    {
        Intent i = new Intent(this, Cliente_Int.class);
        startActivity(i);
    }

    public void setVistaBaseDatos(View view)
    {
        Intent i = new Intent(this, Vista_Base_Datos.class);
        startActivity(i);
    }

    public void alerta(String cadena, int num)
    {
        //se prepara la alerta creando nueva instancia
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        //seleccionamos la cadena a mostrar
        dialogBuilder.setMessage(cadena);
        //elegimo un titulo y configuramos para que se pueda quitar
        if (num == 1) {
            dialogBuilder.setCancelable(true).setTitle("¡Atención!");
        } else if (num == 2) {
            dialogBuilder.setCancelable(true).setTitle("Correcto");
        }

        //mostramos el dialogBuilder
        dialogBuilder.create().show();

    }

    public void setCerrarSesion(View view)
    {
        MainActivity.setCambiarEstadoBoton(Inicio.this,false);
        Intent i = new Intent(Inicio.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void setConsultaInicioUsuario()
    {
        conn1 = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);

        SQLiteDatabase db = conn1.getReadableDatabase();
        String[] parametros = {String.valueOf(MainActivity.setOptenerEstadoUsuario(Inicio.this))};
        String[] campos = {Utilidades.CAMPO_ID,Utilidades.CAMPO_NOMBRE};

        Cursor cursor = db.query(Utilidades.TABLA_VENDEDOR,campos,Utilidades.CAMPO_ID+"=?",parametros,null,null,null);
        cursor.moveToFirst();
        Toast.makeText(getApplicationContext(),"Bienvenido: "+cursor.getString(1), Toast.LENGTH_SHORT).show();
    }
}
