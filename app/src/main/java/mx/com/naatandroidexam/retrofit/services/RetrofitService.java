package mx.com.naatandroidexam.retrofit.services;

import mx.com.naatandroidexam.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitService {
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST("authorization-server/oauth/token")
    Call<LoginResponse> doLogin(@Header("Authorization") String key,
                                @Field("username") String username,
                                @Field("grant_type") String grant_type,
                                @Field("password") String password);
}
