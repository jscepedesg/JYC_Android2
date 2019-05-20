package sebastian.ing.jyc2.crear_clientes;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import sebastian.ing.jyc2.Estructuras.Cliente_provicional;
import sebastian.ing.jyc2.R;
import sebastian.ing.jyc2.Utilidades.ConexionSQLiteHelper;
import sebastian.ing.jyc2.Utilidades.Utilidades;

/**
 * Created by Usuario on 4/03/2019.
 */

public class Cliente_lista_clientes_detalle extends AppCompatActivity
{
    private TextView[] text;
    private ConexionSQLiteHelper conn1;
    private int id_cliente_provicional;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_lista_clientes_detalle);

        text = new TextView[8];

        text[0]=(TextView) findViewById(R.id.textViewdiares);
        text[1]=(TextView) findViewById(R.id.textViewnrsres);
        text[2]=(TextView) findViewById(R.id.textViewncres);
        text[3]=(TextView) findViewById(R.id.textViewapres);
        text[4]=(TextView) findViewById(R.id.textViewceres);
        text[5]=(TextView) findViewById(R.id.textViewdres);
        text[6]=(TextView) findViewById(R.id.textViewtres);
        text[7]=(TextView) findViewById(R.id.textViewnrres);

        conn1 = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);

        Bundle objetoenviado = getIntent().getExtras();
        Cliente_provicional cliente_provicional=null;

        if (objetoenviado!=null)
        {
            cliente_provicional= (Cliente_provicional) objetoenviado.getSerializable("cliente");

            text[0].setText(cliente_provicional.getDia());
            text[1].setText(cliente_provicional.getNom_r());
            text[2].setText(cliente_provicional.getNom_c());
            text[3].setText(cliente_provicional.getApel_c());
            text[4].setText(cliente_provicional.getCorreo());
            text[5].setText(cliente_provicional.getDire_c());
            text[6].setText(cliente_provicional.getTele());
            text[7].setText(String.valueOf(cliente_provicional.getNum_ruta_p()));
            id_cliente_provicional=cliente_provicional.getId_c_p();

            Log.d("Prueba 1080",cliente_provicional.getDia()+" "+cliente_provicional.getNom_r()+" "+cliente_provicional.getNom_c()
                    +" "+cliente_provicional.getApel_c()+" "+cliente_provicional.getCorreo()+" "+cliente_provicional.getCorreo()
                    +" "+cliente_provicional.getDire_c()+" "+cliente_provicional.getTele()+" "+cliente_provicional.getNum_ruta_p());



        }
    }

    public void setEliminarClienteProvicional(View view)
    {
       SQLiteDatabase dbe = conn1.getWritableDatabase();
        dbe.execSQL("delete from "+ Utilidades.TABLA_CLIENTE_PROVICIONAL+ " where "+Utilidades.CAMPO_ID_CLIE_PROVICIONAL+"="
                +id_cliente_provicional);
        dbe.close();
        setListaClientes_desde_detallado();
    }

    public void setListaClientes_desde_detallado()
    {
        Intent i = new Intent(this, Cliente_lista_clientes.class);
        startActivity(i);
        finish();
    }

    public void setVolverALista(View view)
    {
        setListaClientes_desde_detallado();
    }

}
