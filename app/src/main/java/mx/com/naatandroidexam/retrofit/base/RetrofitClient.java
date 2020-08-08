package mx.com.naatandroidexam.retrofit.base;

import android.content.Context;

import mx.com.naatandroidexam.R;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit getClient(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.URL_LOGIN))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().addInterceptor(logging).build())
                .build();
    }
}
