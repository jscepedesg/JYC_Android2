package sebastian.ing.jyc2.vistaBasededatos;

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

import sebastian.ing.jyc2.Estructuras.Cliente;
import sebastian.ing.jyc2.R;
import sebastian.ing.jyc2.Utilidades.ConexionSQLiteHelper;
import sebastian.ing.jyc2.Utilidades.Utilidades;

/**
 * Created by Usuario on 10/04/2019.
 */

public class Vista_BD_Cliente extends AppCompatActivity
{
    private SearchView searchView2;
    private ListView listView2;
    private ArrayList<String> listaInformacion2;
    private ArrayList<Cliente> listaCliente;
    private ConexionSQLiteHelper conn;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_bd_clientes);

        conn = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);
        searchView2 = (SearchView) findViewById(R.id.search_vista_cliente);
        listView2 = (ListView) findViewById(R.id.listview_vista_cliente);

        setConsultarListaCLientes();
        final ArrayAdapter adaptador= new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion2);
        listView2.setAdapter(adaptador);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                String info= "Nombre razon social: "+listaCliente.get(pos).getNom_r()+"\n";
                info+= "Nombre cliente: "+listaCliente.get(pos).getNom_c()+" "+listaCliente.get(pos).getApel_c()+"\n";
                info+="Telefono: "+listaCliente.get(pos).getTele()+"\n";
                info+="Dirección: "+listaCliente.get(pos).getDire_c()+"\n";
                info+="Dia de atención: "+listaCliente.get(pos).getDia()+"\n";
                info+="Id: "+listaCliente.get(pos).getId_c()+"\n";

                Toast.makeText(getApplicationContext(),info, Toast.LENGTH_LONG).show();

                /*Cliente cliente = listaCliente.get(pos);
                //Modificar
                Intent intent = new Intent(Vista_BD_Cliente.this,Cliente_lista_clientes_detalle.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("cliente2",cliente);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();*/


            }
        });

        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        listaCliente = new ArrayList<Cliente>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Utilidades.TABLA_CLIENTE,null);

        while (cursor.moveToNext())
        {
            listaCliente.add(new Cliente(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                    cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getInt(8)));

            Log.d("Base de datos Cliente",cursor.getInt(0)+" "+cursor.getString(1)+" "+cursor.getInt(2)+" "+cursor.getString(3)
                    +" "+cursor.getString(4)+" "+cursor.getString(5)+" "+cursor.getString(6)+" "+cursor.getInt(7));
        }

        setObtenerLista();


    }

    private void setObtenerLista()
    {
        listaInformacion2 = new ArrayList<String>();

        for (int i=0;i<listaCliente.size();i++)
        {
            listaInformacion2.add(listaCliente.get(i).getNom_r()+" - "+listaCliente.get(i).getNom_c()+" "+listaCliente.get(i).getApel_c());
        }
    }
}
