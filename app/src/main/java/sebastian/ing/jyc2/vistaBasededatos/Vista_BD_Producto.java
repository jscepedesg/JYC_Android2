package sebastian.ing.jyc2.vistaBasededatos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import sebastian.ing.jyc2.Estructuras.Producto;
import sebastian.ing.jyc2.R;
import sebastian.ing.jyc2.Utilidades.ConexionSQLiteHelper;
import sebastian.ing.jyc2.Utilidades.Utilidades;

/**
 * Created by Usuario on 10/04/2019.
 */

public class Vista_BD_Producto extends AppCompatActivity
{

    private SearchView searchView3;
    private ListView listView3;
    private ArrayList<String> listaInformacion3;
    private ArrayList<Producto> listaProducto;
    private ConexionSQLiteHelper conn;
    private final double iva = 1.19;

    public Vista_BD_Producto() {
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_bd_productos);

        conn = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);
        searchView3 = (SearchView) findViewById(R.id.search_vista_producto);
        listView3 = (ListView) findViewById(R.id.listview_vista_producto);

        setConsultarListaCLientes();
        final ArrayAdapter adaptador= new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion3);
        listView3.setAdapter(adaptador);

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                String info= "Id: "+listaProducto.get(pos).getCod_p()+"\n";
                info+= "Nombre: "+listaProducto.get(pos).getNom_p()+"\n";
                info+="Precio: "+ Math.round(listaProducto.get(pos).getPre_p()*iva)+"\n";
                info+="Linea: "+listaProducto.get(pos).getLinea()+"\n";
                info+="Casa: "+listaProducto.get(pos).getCasa()+"\n";

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

        searchView3.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        listaProducto = new ArrayList<Producto>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Utilidades.TABLA_PRODUCTO,null);

        while (cursor.moveToNext())
        {
            listaProducto.add(new Producto(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3)
                    ,cursor.getString(4)));
        }

        setObtenerLista();


    }

    private void setObtenerLista()
    {
        listaInformacion3 = new ArrayList<String>();

        for (int i=0;i<listaProducto.size();i++)
        {
            listaInformacion3.add(listaProducto.get(i).getNom_p()+" - "+ Math.round(listaProducto.get(i).getPre_p()*iva)+"$");
        }
    }
}
