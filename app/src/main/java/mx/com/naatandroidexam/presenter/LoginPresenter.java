package mx.com.naatandroidexam.presenter;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import mx.com.naatandroidexam.R;
import mx.com.naatandroidexam.listeners.LoginListener;
import mx.com.naatandroidexam.model.LoginResponse;
import mx.com.naatandroidexam.persistence.sharedpreferences.Configurations;
import mx.com.naatandroidexam.retrofit.base.RetrofitClient;
import mx.com.naatandroidexam.retrofit.services.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    private static final String TAG = "LoginPresenter";
    private Context context;
    private LoginListener listener;
    private RetrofitService retrofitService;

    public LoginPresenter(Context context, LoginListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void initLogin(String email, String password) {
        MessageDigest digest = null;
        String passSHA256 = "";
        retrofitService = RetrofitClient.getClient(context).create(RetrofitService.class);
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hash;
        if (digest != null) {
            hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));

            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }

            passSHA256 = hexString.toString();
            Log.d(TAG, "Password Hash256" + "\n" + passSHA256);
        }

        if (!passSHA256.isEmpty()) {
            retrofitService.doLogin(context.getString(R.string.AUTHENTICATION_HEADER),
                    "testnaat@na-at.com.mx", context.getString(R.string.GRANT_TYPE),
                    passSHA256.toLowerCase())
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                            Log.d(TAG, "onResponse() returned: " + response.body());
                            if (response.isSuccessful()
                                    && response.body() != null
                                    && response.body().getSuccess() == null) {
                                Configurations.rememberUser(context, response.body());
                                listener.loginSucceeded();
                            } else {
                                Log.e(TAG, "onResponse: Error" + response.message());
                                if (response.body() != null
                                        && response.body().getError() != null
                                        && !response.body().getError().isEmpty())
                                    if (response.body().getError().contains("Bad credentials"))
                                        listener.loginFailed("Credenciales inválidas, reintentar");
                                    else
                                        listener.loginFailed(response.body().getError());
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.e(TAG, "onFailure: Error Login", t);
                            listener.loginFailed("Error iniciando sesión");
                        }
                    });
        }
    }
}
