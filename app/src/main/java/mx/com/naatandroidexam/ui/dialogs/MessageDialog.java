package mx.com.naatandroidexam.ui.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import mx.com.naatandroidexam.R;

public class MessageDialog extends DialogFragment implements View.OnClickListener {
    private RelativeLayout rlRecargaExitosa;
    private RelativeLayout rlProcesando;
    private RelativeLayout rlDialogRecarga;

    public static MessageDialog getInstance(String nombre,
                                            String tipo,
                                            String idUsuario,
                                            String hora,
                                            String fecha,
                                            String numero,
                                            String monto) {
        Bundle bundle = new Bundle();
        bundle.putString("nombre", nombre);
        bundle.putString("tipo", tipo);
        bundle.putString("idUsuario", idUsuario);
        bundle.putString("hora", hora);
        bundle.putString("fecha", fecha);
        bundle.putString("numero", numero);
        bundle.putString("monto", monto);
        MessageDialog fragment = new MessageDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            if (getDialog().getWindow() != null)
                getDialog().getWindow().setWindowAnimations(R.style.DialogAnimation);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_layout, container, false);

        if (getDialog() != null && getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        rlDialogRecarga = v.findViewById(R.id.rlDialogRecarga);
        rlProcesando = v.findViewById(R.id.rlProcesando);
        rlRecargaExitosa = v.findViewById(R.id.rlRecargaExitosa);

        TextView tvAvisoPago = v.findViewById(R.id.tvAvisoDialogo);
        TextView tvAvisoRecargaLogo = v.findViewById(R.id.tvAvisoRecarga);
        TextView tvNumTel = v.findViewById(R.id.tvNumTelefonico);
        TextView tvMonto = v.findViewById(R.id.tvMontoRecarga);
        tvAvisoPago.setSelected(true);
        TextView tvIdUsuario = v.findViewById(R.id.tvIdUser);
        tvIdUsuario.setSelected(true);
        TextView tvHora = v.findViewById(R.id.tvHora);
        tvHora.setSelected(true);
        TextView tvFecha = v.findViewById(R.id.tvFecha);
        tvFecha.setSelected(true);
        Button btnCancelar = v.findViewById(R.id.btnCancelar);
        Button btnAceptar = v.findViewById(R.id.btnAceptar);

        if (getArguments() != null) {
            tvIdUsuario.setText(getArguments().getString("idUsuario"));
            tvHora.setText(getArguments().getString("hora"));
            tvFecha.setText(getArguments().getString("fecha"));
            tvNumTel.setText(getArguments().getString("numero"));
            tvMonto.setText("$ ".concat(getArguments().getString("monto")));

            switch (getArguments().getString("nombre")) {
                case "Claro":
                    tvAvisoRecargaLogo.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.ic_claro));
                    break;
                case "Tuenti":
                    tvAvisoRecargaLogo.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.ic_tuenti));
                    break;
                case "Entel":
                    tvAvisoRecargaLogo.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.ic_entel));
                    break;
                default:
                    tvAvisoRecargaLogo.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.ic_logo));
                    break;
            }
        }

        btnAceptar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAceptar:
                rlRecargaExitosa.setVisibility(View.GONE);
                rlDialogRecarga.setVisibility(View.GONE);
                rlProcesando.setVisibility(View.VISIBLE);

                new Handler().postDelayed(() -> {
                    rlRecargaExitosa.setVisibility(View.VISIBLE);
                    rlDialogRecarga.setVisibility(View.GONE);
                    rlProcesando.setVisibility(View.GONE);
                }, 2000);

                new Handler().postDelayed(() -> {
                    if (getDialog() != null) getDialog().dismiss();

                    if (getActivity() != null)
                        getActivity().finish();
                }, 4000);
                break;
            case R.id.btnCancelar:
                if (getDialog() != null) getDialog().dismiss();
                Toast.makeText(getActivity(), "Recarga cancelada", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}