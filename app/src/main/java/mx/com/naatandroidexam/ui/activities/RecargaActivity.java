package mx.com.naatandroidexam.ui.activities;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

import mx.com.naatandroidexam.R;
import mx.com.naatandroidexam.persistence.sharedpreferences.Configurations;
import mx.com.naatandroidexam.ui.dialogs.MessageDialog;
import mx.com.naatandroidexam.utilities.Utils;

public class RecargaActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvTipo;
    private TextInputEditText edtMonto;
    private TextInputEditText edtCelular;
    private Button btnContinuarRecarga;
    private String nombre = "";
    private String tipo = "";
    private LinearLayout llRecarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga);

        initViews();

        if (getIntent() != null && getIntent().hasExtra("nombre") && getIntent().hasExtra("tipo")) {
            nombre = getIntent().getStringExtra("nombre");
            tipo = getIntent().getStringExtra("tipo");

            Toast.makeText(this, "Nombre: " + nombre, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Tipo: " + tipo, Toast.LENGTH_SHORT).show();
        }

        tvTipo.setText(tipo);

        switch (nombre) {
            case "Claro":
                tvTipo.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_claro), null, null);
                break;
            case "Tuenti":
                tvTipo.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_tuenti), null, null);
                break;
            case "Entel":
                tvTipo.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_entel), null, null);
                break;
            default:
                tvTipo.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_logo), null, null);
                break;
        }
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.tab_recargas);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        tvTipo = findViewById(R.id.tvLogoRecarga);
        edtMonto = findViewById(R.id.edtMontoRecarga);
        edtCelular = findViewById(R.id.edtNumCelular);
        btnContinuarRecarga = findViewById(R.id.btnContinuarRecarga);
        llRecarga = findViewById(R.id.llRecarga);

        btnContinuarRecarga.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnContinuarRecarga) {
            if (validFields()) {
                String idUsuario = Configurations.getRememberedUser(RecargaActivity.this)[5];
                String hora = Utils.getHora();
                String fecha = Utils.getFecha();
                String numero = edtCelular.getText().toString();
                String monto = edtMonto.getText().toString();

                MessageDialog dialogFragment = MessageDialog.getInstance(nombre, tipo, idUsuario, hora, fecha, numero, monto);

                if (getFragmentManager() != null)
                    dialogFragment.show(getSupportFragmentManager(), dialogFragment.getTag());
            }

        }
    }

    private boolean validFields() {
        hideKeyboard();
        if (edtCelular.getText() != null && !edtCelular.getText().toString().isEmpty()) {
            edtCelular.setError(null);
        } else {
            edtCelular.setError("Este campo es necesario.");
            return false;
        }

        if (edtMonto.getText() != null && !edtMonto.getText().toString().isEmpty())
            edtMonto.setError(null);
        else {
            edtMonto.setError("Este campo es necesario.");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) RecargaActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(llRecarga.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}