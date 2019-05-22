package sebastian.ing.jyc2.pedidos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import sebastian.ing.jyc2.Estructuras.Cliente;
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
    private Cliente cliente;




    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_productos_factura);

        Bundle objetoenviado = getIntent().getExtras();
        cliente=null;

        if (objetoenviado!=null)
        {
            cliente = (Cliente) objetoenviado.getSerializable("cliente_factura_consulta");
        }

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
                Log.d("Codigos:", String.valueOf(listafactura.get(pos).getId_pro())+" "+String.valueOf(listafactura.get(pos).getId_clie()));
                createSimpleDialog(listafactura.get(pos).getId_pro(),listafactura.get(pos).getId_clie()).show();
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
        Cursor cursor = db.rawQuery("SELECT p."+Utilidades.CAMPO_ID_PRO+", f."+Utilidades.CAMPO_ID_CLIE1+", p." +Utilidades.CAMPO_NOMBRE_PRO+", p."+Utilidades.CAMPO_PRECIO_PRO+", f."+Utilidades.CAMPO_CANTIDAD_FAC+
                " FROM "+Utilidades.TABLA_FACTURA+" f INNER JOIN "+Utilidades.TABLA_PRODUCTO+" p ON(p."+Utilidades.CAMPO_ID_PRO+"=f."+Utilidades.CAMPO_ID_PRO3+
                ") WHERE f."+Utilidades.CAMPO_ID_CLIE1+" = "+cliente.getId_c() ,null);

        while (cursor.moveToNext())
        {
            listafactura.add(new FacturaRelacionalProducto(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getDouble(3),cursor.getInt(4),((Math.round(cursor.getDouble(3)*iva))*cursor.getInt(4))));
            total+=((Math.round(cursor.getDouble(3)*iva))*cursor.getInt(4));
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

    public AlertDialog createSimpleDialog(final int id_pro, final int id_cliete) {
        Context context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Eliminar")
                .setMessage("¿Seguro que quiere elimiar el producto?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setEliminarProductoFactura(id_pro,id_cliete);
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

    public void setEliminarProductoFactura(int id_pro, int id_cliete)
    {
        SQLiteDatabase dbe = conn.getWritableDatabase();
        dbe.execSQL("delete from "+ Utilidades.TABLA_FACTURA+ " where "+Utilidades.CAMPO_ID_PRO3+" = "
                +id_pro+" AND "+Utilidades.CAMPO_ID_CLIE1+" = "+id_cliete);
        dbe.close();
        setListaFactura();
    }

    public void setListaFactura()
    {
        Intent i = new Intent(this, Lista_Productos_Factura.class);
        Bundle bundle= new Bundle();
        bundle.putSerializable("cliente_factura_consulta",cliente);
        i.putExtras(bundle);
        startActivity(i);
        finish();
    }
}
