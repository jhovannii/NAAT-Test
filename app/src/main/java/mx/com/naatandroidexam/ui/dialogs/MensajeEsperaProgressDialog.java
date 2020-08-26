package mx.com.naatandroidexam.ui.dialogs;

import android.app.ProgressDialog;
import android.content.Context;

public class MensajeEsperaProgressDialog extends ProgressDialog {

    public MensajeEsperaProgressDialog(Context context, String mensaje) {
        super(context);
        this.setCanceledOnTouchOutside(false);
        this.setMessage(mensaje);
        this.setCancelable(true);
    }

    public MensajeEsperaProgressDialog(Context context, int mensaje) {
        super(context);
        this.setCanceledOnTouchOutside(false);
        this.setMessage(context.getString(mensaje));
        this.setCancelable(false);
    }

    @Override
    public void cancel() {
        super.cancel();
    }

    @Override
    public void onBackPressed() {
    }
}