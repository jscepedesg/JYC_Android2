package sebastian.ing.jyc2.pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import sebastian.ing.jyc2.R;

/**
 * Created by Usuario on 12/02/2019.
 */

public class Pedido extends AppCompatActivity
{
    private ListView listView;
    private ArrayList<String> listaInformacion;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido);

        listView = (ListView) findViewById(R.id.listViewDias);


        setObtenerLista();
        final ArrayAdapter adaptador= new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listView.setAdapter(adaptador);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                String dia = listaInformacion.get(pos);
                Log.d("Dia de busqueda: ",dia);
                Intent intent = new Intent(Pedido.this,Lista_clientes_por_dia.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("cliente_dia",dia);
                intent.putExtras(bundle);
                startActivity(intent);
                //finish();


            }
        });
    }

    public void setFecha()
    {
        Date fecha1 = new Date();
        CharSequence s = DateFormat.format("yyyy-MM-dd", fecha1.getTime());
    }

    private void setObtenerLista()
    {
        listaInformacion = new ArrayList<String>();


        listaInformacion.add("Lunes");
        listaInformacion.add("Martes");
        listaInformacion.add("Miercoles");
        listaInformacion.add("Jueves");
        listaInformacion.add("Viernes");
        listaInformacion.add("Sabado");
    }
}
