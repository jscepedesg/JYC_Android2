package sebastian.ing.jyc2.estadistica;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import sebastian.ing.jyc2.Estructuras.FacturaRelacionalProducto;
import sebastian.ing.jyc2.R;
import sebastian.ing.jyc2.Utilidades.ConexionSQLiteHelper;
import sebastian.ing.jyc2.Utilidades.Utilidades;

/**
 * Created by Usuario on 12/02/2019.
 */

public class Estadistica extends AppCompatActivity
{

    private String dia;
    private TextView dia_fecha, numero_clientes_dia, numero_de_facturas, numero_clientes_cerrados, venta_total;
    private ConexionSQLiteHelper conn;
    private ArrayList<FacturaRelacionalProducto> listafactura;
    private final double iva = 1.19;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadistica);

        conn = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);

        Bundle objetoenviado = getIntent().getExtras();
        dia="";

        if (objetoenviado!=null) {
            dia = (String) objetoenviado.getSerializable("estadistica_dia");
        }

        dia_fecha = (TextView) findViewById(R.id.estadistica_dia_fecha);
        dia_fecha.setText(dia+": "+setFecha());
        numero_clientes_dia = (TextView) findViewById(R.id.texttotal);
        numero_clientes_dia.setText("Total clientes: "+getObtenerNumeroClientes());
        numero_de_facturas = (TextView) findViewById(R.id.textatendidos);
        numero_de_facturas.setText("Numero de facturas: "+getObtenerNumeroFactura());
        numero_clientes_cerrados = (TextView) findViewById(R.id.textcerrados);
        numero_clientes_cerrados.setText("Clientes cerrados: "+getObtenerNumeroCerrados());
        venta_total = (TextView) findViewById(R.id.textventa);
        venta_total.setText("Venta: "+getOptenerVentaTotal());
    }

    public String setFecha()
    {
        Date fecha1 = new Date();
        CharSequence s = DateFormat.format("yyyy-MM-dd", fecha1.getTime());

        String fecha = (String) s;
        return fecha;
    }

    public int getObtenerNumeroClientes()
    {
        SQLiteDatabase dbe = conn.getReadableDatabase();
        int numRows = (int) DatabaseUtils.longForQuery(dbe, "SELECT COUNT(*) FROM "+Utilidades.TABLA_CLIENTE+" WHERE "+Utilidades.CAMPO_DIA_ATENCION_CLIE+" = '"+dia+"'",
                null);
        dbe.close();
        return numRows;
    }

    public int getObtenerNumeroFactura()
    {
        SQLiteDatabase dbe = conn.getReadableDatabase();
        int numRows = (int) DatabaseUtils.longForQuery(dbe, "SELECT COUNT(*) FROM "+ Utilidades.TABLA_REGISTRO, null);
        dbe.close();
        return numRows;
    }

    public int getObtenerNumeroCerrados()
    {
        SQLiteDatabase dbe = conn.getReadableDatabase();
        int numRows = (int) DatabaseUtils.longForQuery(dbe, "SELECT COUNT(*) FROM "+ Utilidades.TABLA_CLIENTES_CERRADOS, null);
        dbe.close();
        return numRows;
    }

    public int getOptenerVentaTotal()
    {
        int total=0;

        SQLiteDatabase db = conn.getReadableDatabase();
        listafactura = new ArrayList<FacturaRelacionalProducto>();
        //SELECT p.nom_Pro, p.Valor_sin_iva, f.cantidad
        //FROM factura f INNER JOIN producto p ON(p.Id_Pro=f.Id_Pro3)
        //WHERE f.id_factura= 1
        Cursor cursor = db.rawQuery("SELECT p."+Utilidades.CAMPO_ID_PRO+", f."+Utilidades.CAMPO_ID_CLIE1+", p." +Utilidades.CAMPO_NOMBRE_PRO+", p."+Utilidades.CAMPO_PRECIO_PRO+", f."+Utilidades.CAMPO_CANTIDAD_FAC+
                " FROM "+Utilidades.TABLA_FACTURA+" f INNER JOIN "+Utilidades.TABLA_PRODUCTO+" p ON(p."+Utilidades.CAMPO_ID_PRO+"=f."+Utilidades.CAMPO_ID_PRO3+
                ")",null);

        while (cursor.moveToNext())
        {
            listafactura.add(new FacturaRelacionalProducto(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getDouble(3),cursor.getInt(4),((Math.round(cursor.getDouble(3)*iva))*cursor.getInt(4))));
            total+=((Math.round(cursor.getDouble(3)*iva))*cursor.getInt(4));
        }

        return total;
    }
}
