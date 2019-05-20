package sebastian.ing.jyc2.pedidos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sebastian.ing.jyc2.Estructuras.FacturaRelacionalProducto;
import sebastian.ing.jyc2.R;
import sebastian.ing.jyc2.Utilidades.ConexionSQLiteHelper;
import sebastian.ing.jyc2.Utilidades.Utilidades;

/**
 * Created by Usuario on 30/04/2019.
 */

public class Lista_Productos_Factura extends AppCompatActivity
{

    private ListView listView3;
    private TextView total_precio;
    private ArrayList<String> listaInformacion3;
    private ArrayList<FacturaRelacionalProducto> listafactura;
    private ConexionSQLiteHelper conn;
    private final double iva = 1.19;
    private static double total=0;



    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_productos_factura);

        conn = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);
        listView3 = (ListView) findViewById(R.id.listView_lista_producto_factura);
        total_precio=(TextView) findViewById(R.id.textView_lista_producto_factura_total);
        total=0;
        setConsultarListaProductosFactura();
        total_precio.setText("Total: "+ String.valueOf(total));

        final ArrayAdapter adaptador= new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion3);
        listView3.setAdapter(adaptador);

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                /*String info= "Id: "+listaProducto.get(pos).getCod_p()+"\n";
                info+= "Nombre: "+listaProducto.get(pos).getNom_p()+"\n";
                info+="Precio: "+Math.round(listaProducto.get(pos).getPre_p()*iva)+"\n";
                info+="Linea: "+listaProducto.get(pos).getLinea()+"\n";
                info+="Casa: "+listaProducto.get(pos).getCasa()+"\n";

                Toast.makeText(getApplicationContext(),info,Toast.LENGTH_LONG).show();*/

                /*Cliente cliente = listaCliente.get(pos);
                //Modificar
                Intent intent = new Intent(Vista_BD_Cliente.this,Cliente_lista_clientes_detalle.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("cliente2",cliente);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();*/
                //id_pro_facturar = listaProducto.get(pos).getCod_p();
                //new CuadroDialogo(context,Hacer_pedido.this);
                createSimpleDialog();
            }
        });


    }

    private void setConsultarListaProductosFactura()
    {
        SQLiteDatabase db = conn.getReadableDatabase();
        listafactura = new ArrayList<FacturaRelacionalProducto>();
        //SELECT p.nom_Pro, p.Valor_sin_iva, f.cantidad
        //FROM factura f INNER JOIN producto p ON(p.Id_Pro=f.Id_Pro3)
        //WHERE f.id_factura= 1
        Cursor cursor = db.rawQuery("SELECT p." +Utilidades.CAMPO_NOMBRE_PRO+", p."+Utilidades.CAMPO_PRECIO_PRO+", f."+Utilidades.CAMPO_CANTIDAD_FAC+
                " FROM "+Utilidades.TABLA_FACTURA+" f INNER JOIN "+Utilidades.TABLA_PRODUCTO+" p ON(p."+Utilidades.CAMPO_ID_PRO+"=f."+Utilidades.CAMPO_ID_PRO3+
                ") WHERE f."+Utilidades.CAMPO_ID_FACTURA+" = 1" ,null);

        while (cursor.moveToNext())
        {
            listafactura.add(new FacturaRelacionalProducto(cursor.getString(0),cursor.getDouble(1),cursor.getInt(2),((Math.round(cursor.getDouble(1)*iva))*cursor.getInt(2))));
            total+=((Math.round(cursor.getDouble(1)*iva))*cursor.getInt(2));
        }

        setObtenerLista();
    }

    private void setObtenerLista()
    {
        listaInformacion3 = new ArrayList<String>();

        for (int i=0;i<listafactura.size();i++)
        {
            listaInformacion3.add(listafactura.get(i).getNombre_producto()+" - Precio UND: "+ Math.round(listafactura.get(i).getPrecio_unitario()*iva)+"$"+" - Cantidad: "+listafactura.get(i).getCantidad()+" - SUB Total: "+listafactura.get(i).getTotal_por_producto()+"$");
        }
    }

    public AlertDialog createSimpleDialog() {
        Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Eliminar")
                .setMessage("¿Seguro que quiere elimiar el producto?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //listener.onPossitiveButtonClick();
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //listener.onNegativeButtonClick();
                            }
                        });

        return builder.create();
    }
}
