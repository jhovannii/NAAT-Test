package mx.com.naatandroidexam.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import mx.com.naatandroidexam.R;
import mx.com.naatandroidexam.listeners.LoginListener;
import mx.com.naatandroidexam.presenter.LoginPresenter;
import mx.com.naatandroidexam.ui.dialogs.MensajeEsperaProgressDialog;
import mx.com.naatandroidexam.utilities.Utils;

public class LoginActivity extends AppCompatActivity implements LoginListener {
    private TextInputEditText edtUserEmail;
    private TextInputEditText edtPassword;
    private TextInputLayout tilUserEmail;
    private TextInputLayout tilPassword;
    private Button btnLogin;
    private RelativeLayout rlLogin;
    private LoginPresenter loginPresenter;
    private MensajeEsperaProgressDialog mensajeEsperaProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        loginPresenter = new LoginPresenter(LoginActivity.this, this);

        btnLogin.setOnClickListener(view -> {
            if (validFields())
                doLogin();
        });

        rlLogin.setOnClickListener(view -> hideKeyboard());
    }

    private void doLogin() {
        if (Utils.hayConexionInternet(LoginActivity.this)) {
            mensajeEsperaProgressDialog = new MensajeEsperaProgressDialog(LoginActivity.this, "Iniciando Sesión...");
            mensajeEsperaProgressDialog.show();

            loginPresenter.initLogin(
                    edtUserEmail.getText().toString().trim(),
                    edtPassword.getText().toString().trim());
        } else {
            mensajeEsperaProgressDialog.hide();
            Snackbar.make(rlLogin, "Verificar la conexión a internet.", Snackbar.LENGTH_LONG)
                    .setTextColor(getResources().getColor(android.R.color.white)).show();
            btnLogin.setEnabled(true);
        }
    }

    private boolean validFields() {
        hideKeyboard();
        btnLogin.setEnabled(false);
        boolean valid = false;

        if (edtUserEmail != null && edtUserEmail.getText() != null && !edtUserEmail.getText().toString().isEmpty()) {
            tilUserEmail.setError(null);
            tilUserEmail.setErrorEnabled(false);
            valid = true;
        } else {
            tilUserEmail.setErrorEnabled(true);
            tilUserEmail.setError("Este campo no puede estar vacío");
            btnLogin.setEnabled(true);
            valid = false;
        }

        if (edtPassword != null && edtPassword.getText() != null && !edtPassword.getText().toString().isEmpty()) {
            tilPassword.setError(null);
            tilPassword.setErrorEnabled(false);
            valid = true;
        } else {
            tilPassword.setErrorEnabled(true);
            tilPassword.setError("Este campo no puede estar vacío");
            btnLogin.setEnabled(true);
            valid = false;
        }

        return valid;
    }

    private void initViews() {
        rlLogin = findViewById(R.id.rlLoginFields);
        tilUserEmail = findViewById(R.id.tiLUserLogin);
        edtUserEmail = findViewById(R.id.edtUser);
        tilPassword = findViewById(R.id.tiLPasswordLogin);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLoginEmail);
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(rlLogin.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void loginSucceeded() {
        btnLogin.setEnabled(true);

        if (mensajeEsperaProgressDialog != null && mensajeEsperaProgressDialog.isShowing())
            mensajeEsperaProgressDialog.dismiss();

        Toast.makeText(LoginActivity.this, "Información almacenada en Preferencias", Toast.LENGTH_SHORT).show();

        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void loginFailed(String message) {
        btnLogin.setEnabled(true);
        if (mensajeEsperaProgressDialog != null && mensajeEsperaProgressDialog.isShowing())
            mensajeEsperaProgressDialog.dismiss();

        Snackbar.make(rlLogin, message, Snackbar.LENGTH_LONG)
                .setTextColor(getResources().getColor(android.R.color.white)).show();
    }
}