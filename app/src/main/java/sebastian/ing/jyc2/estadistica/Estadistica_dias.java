package sebastian.ing.jyc2.estadistica;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import sebastian.ing.jyc2.R;

public class Estadistica_dias extends AppCompatActivity
{
    private ListView listView;
    private ArrayList<String> listaInformacion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadistica_dias);

        listView = (ListView) findViewById(R.id.listViewDias_estadistica);


        setObtenerLista();
        final ArrayAdapter adaptador= new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                String dia = listaInformacion.get(pos);
                Log.d("Dia de busqueda: ",dia);
                Intent intent = new Intent(Estadistica_dias.this, Estadistica.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("estadistica_dia",dia);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();


            }
        });
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
