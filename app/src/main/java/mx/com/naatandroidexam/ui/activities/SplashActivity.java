package mx.com.naatandroidexam.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import io.realm.RealmList;
import mx.com.naatandroidexam.R;
import mx.com.naatandroidexam.model.Elemento;
import mx.com.naatandroidexam.persistence.realm.RealmDBHelper;
import mx.com.naatandroidexam.persistence.sharedpreferences.Configurations;

public class SplashActivity extends AppCompatActivity {
    private Handler mTimerHandler = new Handler();
    private RealmList<Elemento> elementosClaro;
    private RealmList<Elemento> elementosTuenti;
    private RealmList<Elemento> elementosEntel;
    private RealmDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startAnimations();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mTimerHandler.post(() -> {
                    poblarDatosBD();
                    validaSesion();
                });
            }
        };

        Timer timer = new Timer();
        long splashDelay = 3500;
        timer.schedule(task, splashDelay);
    }

    private void poblarDatosBD() {
        SharedPreferences sharedPref = SplashActivity.this.getSharedPreferences("PRIMERA_VEZ", Context.MODE_PRIVATE);
        dbHelper = new RealmDBHelper();
        if (sharedPref.getBoolean("primera_vez", true)) {

            elementosNuevos();

            dbHelper.guardarElementosClaro(elementosClaro);
            dbHelper.guardarElementosTuenti(elementosTuenti);
            dbHelper.guardarElementosEntel(elementosEntel);

            Toast.makeText(this, "Primer acceso - Creando nuevos elementos.", Toast.LENGTH_SHORT).show();
        } else {
            elementosClaro = dbHelper.getElementosClaro();
            elementosTuenti = dbHelper.getElementosTuenti();
            elementosEntel = dbHelper.getElementosEntel();
            Toast.makeText(this, "Leyendo BD - Número de datos almacenados...", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Claro: " + elementosClaro.size(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Tuenti: " + elementosTuenti.size(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Entel: " + elementosEntel.size(), Toast.LENGTH_SHORT).show();
        }
    }

    private void elementosNuevos() {
        elementosClaro = new RealmList<>();
        elementosTuenti = new RealmList<>();
        elementosEntel = new RealmList<>();

        elementosClaro.add(new Elemento("Claro", "Tiempo Aire"));
        elementosClaro.add(new Elemento("Claro", "Megas"));
        elementosClaro.add(new Elemento("Claro", "Megas"));
//        elementosClaro.add(new Elemento("Claro", "Megas"));
//        elementosClaro.add(new Elemento("Claro", "Megas"));
//        elementosClaro.add(new Elemento("Claro", "Tiempo Aire"));

        elementosTuenti.add(new Elemento("Tuenti", "Megas"));
        elementosTuenti.add(new Elemento("Tuenti", "Tiempo Aire"));
        elementosTuenti.add(new Elemento("Tuenti", "Tiempo Aire"));
//        elementosTuenti.add(new Elemento("Tuenti", "Tiempo Aire"));
//        elementosTuenti.add(new Elemento("Tuenti", "Tiempo Aire"));
//        elementosTuenti.add(new Elemento("Tuenti", "Tiempo Aire"));

        elementosEntel.add(new Elemento("Entel", "Megas"));
        elementosEntel.add(new Elemento("Entel", "Megas"));
        elementosEntel.add(new Elemento("Entel", "Tiempo Aire"));
//        elementosEntel.add(new Elemento("Entel", "Megas"));
//        elementosEntel.add(new Elemento("Entel", "Megas"));
//        elementosEntel.add(new Elemento("Entel", "Megas"));
    }

    private void validaSesion() {
        String[] sesion = Configurations.getRememberedUser(SplashActivity.this);
        Intent intent;

        if (sesion[0].isEmpty() || sesion[2].isEmpty() || Long.parseLong(sesion[5]) == 0) {
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        } else
            intent = new Intent(SplashActivity.this, MainActivity.class);

        startActivity(intent);
        finish();
    }

    private void startAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = findViewById(R.id.logo_splash);
        iv.clearAnimation();
        iv.startAnimation(anim);
    }
}