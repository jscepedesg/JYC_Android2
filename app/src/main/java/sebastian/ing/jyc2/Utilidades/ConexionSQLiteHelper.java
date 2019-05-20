package sebastian.ing.jyc2.Utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Usuario on 14/02/2019.
 */

public class ConexionSQLiteHelper extends SQLiteOpenHelper
{

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(Utilidades.CREAR_TABLA_BODEGA);
        db.execSQL(Utilidades.CREAR_TABLA_CLIENTE);
        db.execSQL(Utilidades.CREAR_TABLA_FACTURA);
        db.execSQL(Utilidades.CREAR_TABLA_PRODUCTO);
        db.execSQL(Utilidades.CREAR_TABLA_VENDEDOR);
        //Base de datos provicional
        db.execSQL(Utilidades.CREAR_TABLA_CLIENTE_PROVICIONAL);
        db.execSQL(Utilidades.CREAR_TABLA_REGISTRO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionantigua, int versionnueva)
    {
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_BODEGA);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_CLIENTE);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_FACTURA);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_PRODUCTO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_VENDEDOR);
        //Base de datos provicional
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_CLIENTE_PROVICIONAL);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_REGISTRO);
        onCreate(db);
    }
}
