package org.selyu.pando.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.selyu.pando.client.exception.ClientException;
import org.selyu.pando.client.model.User;
import org.selyu.pando.client.model.UserSchema;
import org.selyu.pando.client.service.IPingService;
import org.selyu.pando.client.service.IUserService;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.selyu.pando.client.util.Preconditions.requireNotNull;
import static org.selyu.pando.client.util.Preconditions.validateState;

public class PandoClient {
    private final IPingService pingService;
    private final IUserService userService;

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
        userService = retrofit.create(IUserService.class);

        try {
            Response<String> pingResponse = pingService.ping().execute();
            if (pingResponse.code() == 401) {
                throw new ClientException("Not authorized");
            }
        } catch (IOException e) {
            throw new ClientException("Caught exception sending ping request", e);
        }
    }

    @NotNull
    public List<User> getAllUsers() throws ClientException {
        try {
            Response<List<User>> response = userService.getAll().execute();
            if (!response.isSuccessful()) {
                throw new ClientException("Getting all users un-successful, status code = " + response.code());
            }

            if (response.body() == null) {
                throw new ClientException("Getting all users un-successful, parsed body is null.");
            }

            return response.body();
        } catch (IOException e) {
            throw new ClientException("Caught exception getting all users", e);
        }
    }

    @NotNull
    public Optional<User> getUserById(@NotNull UUID uuid) throws ClientException {
        requireNotNull(uuid);
        try {
            Response<User> response = userService.getOne(uuid).execute();
            if (response.code() == 404) {
                return Optional.empty();
            }

            if (!response.isSuccessful()) {
                throw new ClientException(String.format("Getting user by id '%s' un-successful, status code = %s", uuid, response.code()));
            }

            if (response.body() == null) {
                throw new ClientException(String.format("Getting user by id '%s' un-successful, parsed body is null.", uuid));
            }

            return Optional.of(response.body());
        } catch (IOException e) {
            throw new ClientException(String.format("Caught exception getting user by id '%s'", uuid), e);
        }
    }

    @NotNull
    public User createUser(@NotNull UUID uuid, @NotNull String username) throws ClientException {
        requireNotNull(uuid, username);
        try {
            Response<User> response = userService.create(new UserSchema(uuid, username)).execute();
            if (!response.isSuccessful()) {
                throw new ClientException(String.format("Creating user by id '%s' un-successful, status code = %s", uuid, response.code()));
            }

            if (response.body() == null) {
                throw new ClientException("Creating user by id '%s' un-successful, parsed body is null.");
            }

            return response.body();
        } catch (IOException e) {
            throw new ClientException("Caught exception creating user by id '%s'", e);
        }
    }
}
