package org.selyu.pando.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.selyu.pando.client.exception.ClientException;
import org.selyu.pando.client.service.IPingService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import static org.selyu.pando.client.util.Preconditions.requireNotNull;
import static org.selyu.pando.client.util.Preconditions.validateState;

public class PandoClient {
    private final IPingService pingService;

    public PandoClient(@NotNull String baseUrl, @NotNull String authToken) {
        requireNotNull(baseUrl, authToken);
        validateState(!baseUrl.isEmpty());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient okHttpClient = UnsafeOkHttpClient.GetBuilder()
                .authenticator((route, response) -> response.request().newBuilder().header("Authorization", authToken).build())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        pingService = retrofit.create(IPingService.class);
    }

    public String ping() {
        try {
            return pingService.ping().execute().body();
        } catch (IOException e) {
            throw new ClientException("Caught exception sending ping request", e);
        }
    }
}
