package sebastian.ing.jyc2.pedidos;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import sebastian.ing.jyc2.Estructuras.Cliente;
import sebastian.ing.jyc2.Estructuras.Factura;
import sebastian.ing.jyc2.Estructuras.Producto;
import sebastian.ing.jyc2.MainActivity;
import sebastian.ing.jyc2.R;
import sebastian.ing.jyc2.Utilidades.ConexionSQLiteHelper;
import sebastian.ing.jyc2.Utilidades.Utilidades;

/**
 * Created by Usuario on 15/04/2019.
 */

public class Hacer_pedido extends AppCompatActivity implements CuadroDialogo.FinalizoCuadroDialogo
{
    private String nombre_cliente;
    private TextView nombre_cliente_hacer;
    private TextView numero_de_factura;

    private SearchView searchView3;
    private ListView listView3;
    private ArrayList<String> listaInformacion3;
    private ArrayList<Producto> listaProducto;
    private ConexionSQLiteHelper conn;
    private final double iva = 1.19;
    private Context context;
    private int id_pro_facturar, id_cliente_factura;

    private Factura factura;
    private Cliente cliente;

    private RadioButton RBfacturado,RBcerrado;
    private boolean estadoradioBtnfactura,estadoradioBtncerrado;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hacer_pedido);

        nombre_cliente_hacer=(TextView) findViewById(R.id.nombre_cliente_hacer_factura);
        numero_de_factura=(TextView) findViewById(R.id.texto_numero_hacer_factura);
        RBfacturado = (RadioButton) findViewById(R.id.radioButton);
        estadoradioBtnfactura=RBfacturado.isChecked();
        RBcerrado = (RadioButton) findViewById(R.id.radioButton2);
        estadoradioBtncerrado = RBcerrado.isChecked();
        context = this;
        Bundle objetoenviado = getIntent().getExtras();
        cliente=null;

        if (objetoenviado!=null)
        {
            cliente = (Cliente) objetoenviado.getSerializable("cliente_factura");
            nombre_cliente=cliente.getNom_r();
            nombre_cliente_hacer.setText(nombre_cliente);
            id_cliente_factura=cliente.getId_c();
        }


        conn = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);
        searchView3 = (SearchView) findViewById(R.id.search_hacer_pedido_lista_productos);
        listView3 = (ListView) findViewById(R.id.lista_pedidos_hacer_factura);

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
                id_pro_facturar = listaProducto.get(pos).getCod_p();
                new CuadroDialogo(context,Hacer_pedido.this);

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


        Log.d("Cantidad registros re: ", String.valueOf(getObtenerNumeroFactura()));
        int numero_de_facturas1 = getObtenerNumeroFactura()+1;
        numero_de_factura.setText("Factura numero: "+numero_de_facturas1);

        RBfacturado.setChecked(true);

    }

    private void setConsultarListaCLientes()
    {
        SQLiteDatabase db = conn.getReadableDatabase();
        listaProducto = new ArrayList<Producto>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Utilidades.TABLA_PRODUCTO,null);
        int cantidad_total = cursor.getCount();
        Log.d("Cantidad registros", String.valueOf(cantidad_total));
        while (cursor.moveToNext())
        {
            listaProducto.add(new Producto(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3)
                    ,cursor.getString(4)));
        }

        setObtenerLista();
        db.close();

    }

    private void setObtenerLista()
    {
        listaInformacion3 = new ArrayList<String>();

        for (int i=0;i<listaProducto.size();i++)
        {
            listaInformacion3.add(listaProducto.get(i).getNom_p()+" - "+ Math.round(listaProducto.get(i).getPre_p()*iva)+"$");
        }
    }


    @Override
    public void resultadocuadro(String cantidad)
    {
        Log.d("Cantidad: ",cantidad);
        factura = new Factura(id_pro_facturar,(getObtenerNumeroFactura()+1), MainActivity.setOptenerEstadoUsuario(Hacer_pedido.this),id_cliente_factura, Integer.parseInt(cantidad),setFecha());
        Log.d("ID Producto: ", String.valueOf(factura.getId_pro3()));
        Log.d("ID Factura: ", String.valueOf(factura.getId_factura()));
        Log.d("ID Vendedor: ", String.valueOf(factura.getId_ven1()));
        Log.d("ID Cliente: ", String.valueOf(factura.getId_clie1()));
        Log.d("Cantidad 2: ", String.valueOf(factura.getCantidad()));
        Log.d("Fecha: ",factura.getFecha_de_facturacion());

        setFacturarProducto();

    }

    public int getObtenerNumeroFactura()
{
    SQLiteDatabase dbe = conn.getReadableDatabase();
    int numRows = (int) DatabaseUtils.longForQuery(dbe, "SELECT COUNT(*) FROM "+Utilidades.TABLA_REGISTRO, null);
    dbe.close();
    return numRows;
}

    public String setFecha()
    {
        Date fecha1 = new Date();
        CharSequence s = DateFormat.format("yyyy-MM-dd", fecha1.getTime());

        String fecha = (String) s;
        return fecha;
    }

    public void setFacturarProducto()
    {
        try
        {

            SQLiteDatabase db= conn.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_ID_PRO3,factura.getId_pro3());
            values.put(Utilidades.CAMPO_ID_FACTURA,factura.getId_factura());
            values.put(Utilidades.CAMPO_ID_VEN1,factura.getId_ven1());
            values.put(Utilidades.CAMPO_ID_CLIE1,factura.getId_clie1());
            values.put(Utilidades.CAMPO_CANTIDAD_FAC,factura.getCantidad());
            values.put(Utilidades.CAMPO_FECHA_FAC,factura.getFecha_de_facturacion());

            Long id_resultado = db.insert(Utilidades.TABLA_FACTURA,Utilidades.CAMPO_ID_FACTURA,values);
            Toast.makeText(getApplicationContext(),"Id resgistro: "+id_resultado, Toast.LENGTH_SHORT).show();
            db.close();

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error BDD", Toast.LENGTH_SHORT).show();
            e.getMessage();
        }
    }

    public void setLista_Productos_Factura_Layout(View view)
    {
        Intent i = new Intent(this, Lista_Productos_Factura.class);
        Bundle bundle= new Bundle();
        bundle.putSerializable("cliente_factura_consulta",cliente);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void setTerminarFactura(View view)
    {
        if (RBfacturado.isChecked())
        {
            Log.d("Activo: ", "Esta activo facturado");

            Log.d("Numero de registros", String.valueOf(optenerEstadoFactura()));

            if(optenerEstadoFactura()>0)
            {
                Log.d("Hacer el:","Registro");
                Log.d("Inserto: ", String.valueOf(id_cliente_factura)+" "+ MainActivity.setOptenerEstadoUsuario(Hacer_pedido.this));
                try
                {

                    SQLiteDatabase db= conn.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(Utilidades.CAMPO_ID_CLIENTE_REGISTRO,id_cliente_factura);
                    values.put(Utilidades.CAMPO_ID_VENDEDOR_REGISTRO,MainActivity.setOptenerEstadoUsuario(Hacer_pedido.this));

                    Long id_resultado = db.insert(Utilidades.TABLA_REGISTRO,Utilidades.CAMPO_ID_CLIENTE_REGISTRO,values);
                    Toast.makeText(getApplicationContext(),"Id resgistro: "+id_resultado, Toast.LENGTH_SHORT).show();
                    db.close();

                    Intent i = new Intent(this, Pedido.class);
                    startActivity(i);
                    finish();

                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Error BDD", Toast.LENGTH_SHORT).show();
                    e.getMessage();
                }
            }
            else
            {
                Log.d(" No Hace el:","Registro");
                Toast.makeText(getApplicationContext(),"No ha facturado ningun producto", Toast.LENGTH_SHORT).show();
            }



        }
        else
        {
            Log.d("Activo: ","Esta activo cerrado");
            try
            {

                SQLiteDatabase db= conn.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Utilidades.CAMPO_ID_CLIENTE_CERRADO,id_cliente_factura);

                Long id_resultado = db.insert(Utilidades.TABLA_CLIENTES_CERRADOS,Utilidades.CAMPO_ID_CLIENTE_CERRADO,values);
                Toast.makeText(getApplicationContext(),"Id resgistro: "+id_resultado, Toast.LENGTH_SHORT).show();
                db.close();

                Intent i = new Intent(this, Pedido.class);
                startActivity(i);
                finish();

            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Error BDD", Toast.LENGTH_SHORT).show();
                e.getMessage();
            }

        }
    }

    public int optenerEstadoFactura()
    {
        int numFactura = (getObtenerNumeroFactura()+1);
        SQLiteDatabase dbe = conn.getReadableDatabase();
        int numRows = (int) DatabaseUtils.longForQuery(dbe, "SELECT COUNT(*) FROM "+Utilidades.TABLA_FACTURA+" WHERE "+Utilidades.CAMPO_ID_CLIE1+" = "+id_cliente_factura+ " AND "+Utilidades.CAMPO_ID_FACTURA
                +" = "+ numFactura , null);
        dbe.close();
        return numRows;
    }
}
