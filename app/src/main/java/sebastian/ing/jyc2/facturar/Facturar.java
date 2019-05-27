package sebastian.ing.jyc2.facturar;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import sebastian.ing.jyc2.R;
import sebastian.ing.jyc2.Utilidades.ConexionSQLiteHelper;
import sebastian.ing.jyc2.Utilidades.Utilidades;

/**
 * Created by Usuario on 12/02/2019.
 */

public class Facturar extends AppCompatActivity
{

    private TextView numero_facturas;

    private ConexionSQLiteHelper conn;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facturar);

        conn = new ConexionSQLiteHelper(this, Utilidades.DATABASE_NAME,null,Utilidades.DATABASE_VERSION);

        numero_facturas = (TextView) findViewById(R.id.textnum);

        numero_facturas.setText(String.valueOf(getObtenerNumeroFactura()));


    }

    public int getObtenerNumeroFactura()
    {
        SQLiteDatabase dbe = conn.getReadableDatabase();
        int numRows = (int) DatabaseUtils.longForQuery(dbe, "SELECT COUNT(*) FROM "+ Utilidades.TABLA_REGISTRO, null);
        dbe.close();
        return numRows;
    }

}
