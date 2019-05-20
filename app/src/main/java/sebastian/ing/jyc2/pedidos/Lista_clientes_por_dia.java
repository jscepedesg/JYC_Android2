package sebastian.ing.jyc2.pedidos;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sebastian.ing.jyc2.Estructuras.Cliente;
import sebastian.ing.jyc2.R;
import sebastian.ing.jyc2.Utilidades.ConexionSQLiteHelper;
import sebastian.ing.jyc2.Utilidades.Utilidades;

/**
 * Created by Usuario on 15/04/2019.
 */

public class Lista_clientes_por_dia extends AppCompatActivity
{
    private TextView dia_text;
    private SearchView searchView2;
    private ListView listView2;
    private ArrayList<String> listaInformacion2;
    private ArrayList<Cliente> listaCliente;
    private ConexionSQLiteHelper conn;
    private String dia;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_clientes_por_dia);

        dia_text=(TextView) findViewById(R.id.text_dia_cliente);

        Bundle objetoenviado = getIntent().getExtras();
        dia=null;

        if (objetoenviado!=null) {
            dia = (String) objetoenviado.getSerializable("cliente_dia");
            dia_text.setText(dia);
        }


        conn = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);
        searchView2 = (SearchView) findViewById(R.id.search_lista_clientes_dia);
        listView2 = (ListView) findViewById(R.id.lista_clientes_dia);

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

                Cliente cliente = listaCliente.get(pos);
                //Modificar
                Intent intent = new Intent(Lista_clientes_por_dia.this,Hacer_pedido.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("cliente_factura",cliente);
                intent.putExtras(bundle);
                startActivity(intent);
                //finish();


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
        Cursor cursor = db.rawQuery("SELECT * FROM "+Utilidades.TABLA_CLIENTE+" WHERE "+Utilidades.CAMPO_DIA_ATENCION_CLIE+" = '"+dia+"'",null);
      while (cursor.moveToNext())
        {
            listaCliente.add(new Cliente(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),
                    cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getInt(8)));

            Log.d("Base de datos Cliente",cursor.getInt(0)+" "+cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3)
                    +" "+cursor.getString(4)+" "+cursor.getString(5)+" "+cursor.getString(6)+" "+cursor.getString(7)+" "+cursor.getInt(8));
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
