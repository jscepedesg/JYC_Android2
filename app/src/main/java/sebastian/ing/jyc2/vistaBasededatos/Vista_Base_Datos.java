package sebastian.ing.jyc2.vistaBasededatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sebastian.ing.jyc2.R;

/**
 * Created by Usuario on 10/04/2019.
 */

public class Vista_Base_Datos extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_base_datos);
    }

    public void setVista_BD_Cliente(View view)
    {
        Intent i = new Intent(this, Vista_BD_Cliente.class);
        startActivity(i);
    }
    public void setVista_BD_Producto(View view)
    {
        Intent i = new Intent(this, Vista_BD_Producto.class);
        startActivity(i);
    }
}
