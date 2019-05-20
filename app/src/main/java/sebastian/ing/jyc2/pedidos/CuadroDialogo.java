package sebastian.ing.jyc2.pedidos;

import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import sebastian.ing.jyc2.R;

/**
 * Created by Usuario on 15/04/2019.
 */

public class CuadroDialogo
{
    public interface FinalizoCuadroDialogo
    {
        void resultadocuadro(String cantidad);
    }

    private  FinalizoCuadroDialogo finalizoCuadroDialogo;
    public CuadroDialogo(Context context, FinalizoCuadroDialogo actividad)
    {
        finalizoCuadroDialogo=actividad;
        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        //dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.agregar_cantida_producto);

        final EditText cantida = (EditText) dialogo.findViewById(R.id.cantida_text_f);
        Button confirmar = (Button) dialogo.findViewById(R.id.btn_cantidad_aceptar);
        Button cancelar = (Button) dialogo.findViewById(R.id.btn_cantidad_cancelar);
        cantida.setInputType(InputType.TYPE_CLASS_NUMBER);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizoCuadroDialogo.resultadocuadro(cantida.getText().toString());
                dialogo.dismiss();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }
}
