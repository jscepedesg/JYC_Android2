package sebastian.ing.jyc2.crear_clientes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import sebastian.ing.jyc2.R;
import sebastian.ing.jyc2.Estructuras.Cliente_provicional;
import sebastian.ing.jyc2.Utilidades.ConexionSQLiteHelper;
import sebastian.ing.jyc2.Utilidades.Utilidades;

/**
 * Created by Usuario on 1/03/2019.
 */

public class Cliente_lista_clientes extends AppCompatActivity
{
    private SearchView searchVieww;
    private ListView listView;
    private ArrayList<String> listaInformacion;
    private ArrayList<Cliente_provicional> listaCliente;
    private ConexionSQLiteHelper conn;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_lista_clientes);

        conn = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);
        searchVieww = (SearchView) findViewById(R.id.busquedadSearch);
        listView = (ListView) findViewById(R.id.listView_Clientes);


        setConsultarListaCLientes();
        final ArrayAdapter adaptador= new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                String info= "Nombre razon social: "+listaCliente.get(pos).getNom_r()+"\n";
                info+= "Nombre cliente: "+listaCliente.get(pos).getNom_c()+" "+listaCliente.get(pos).getApel_c()+"\n";
                info+="Telefono: "+listaCliente.get(pos).getTele()+"\n";

                Toast.makeText(getApplicationContext(),info, Toast.LENGTH_LONG).show();

                Cliente_provicional cliente_provicional = listaCliente.get(pos);

                Intent intent = new Intent(Cliente_lista_clientes.this,Cliente_lista_clientes_detalle.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("cliente",cliente_provicional);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();


            }
        });


        searchVieww.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adaptador.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void setConsultarListaCLientes()
    {
        SQLiteDatabase db = conn.getReadableDatabase();
        listaCliente = new ArrayList<Cliente_provicional>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Utilidades.TABLA_CLIENTE_PROVICIONAL,null);

        while (cursor.moveToNext())
        {
            listaCliente.add(new Cliente_provicional(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                    cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getInt(8)));

            Log.d("Base de datos Cliente",cursor.getInt(0)+" "+cursor.getString(1)+" "+cursor.getInt(2)+" "+cursor.getString(3)
                    +" "+cursor.getString(4)+" "+cursor.getString(5)+" "+cursor.getString(6)+" "+cursor.getInt(7));
        }

        setObtenerLista();


    }

    private void setObtenerLista()
    {
        listaInformacion = new ArrayList<String>();

        for (int i=0;i<listaCliente.size();i++)
        {
            listaInformacion.add(listaCliente.get(i).getNom_r()+" - "+listaCliente.get(i).getNom_c()+" "+listaCliente.get(i).getApel_c());
        }
    }

    public void setVolverACrearClientes(View view)
    {
        Intent i = new Intent(this, Cliente_Int.class);
        startActivity(i);
        finish();
    }
}
