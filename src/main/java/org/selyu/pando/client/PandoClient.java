package org.selyu.pando.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.selyu.pando.client.exception.ClientException;
import org.selyu.pando.client.model.*;
import org.selyu.pando.client.service.ILookupService;
import org.selyu.pando.client.service.IPingService;
import org.selyu.pando.client.service.IRankService;
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
    private final ILookupService lookupService;
    private final IRankService rankService;

    public PandoClient(@NotNull String baseUrl, @NotNull String authToken) throws ClientException {
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
        lookupService = retrofit.create(ILookupService.class);
        rankService = retrofit.create(IRankService.class);

        ping();
    }

    public void ping() throws ClientException {
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
            Response<User> response = userService.create(new UserCreateRequest(uuid, username)).execute();
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

    @NotNull
    public Optional<Lookup> getLookupById(@NotNull UUID uuid) throws ClientException {
        requireNotNull(uuid);
        try {
            Response<Lookup> response = lookupService.getById(uuid).execute();
            if (response.code() == 404) {
                return Optional.empty();
            }

            if (!response.isSuccessful()) {
                throw new ClientException(String.format("Getting lookup by id '%s' un-successful, status code = %s", uuid, response.code()));
            }

            if (response.body() == null) {
                throw new ClientException(String.format("Getting lookup by id '%s' un-successful, parsed body is null.", uuid));
            }

            return Optional.of(response.body());
        } catch (IOException e) {
            throw new ClientException(String.format("Caught exception getting lookup by id '%s'", uuid), e);
        }
    }

    @NotNull
    public Optional<Lookup> getLookupByUsername(@NotNull String username) throws ClientException {
        requireNotNull(username);
        try {
            Response<Lookup> response = lookupService.getByUsername(username).execute();
            if (response.code() == 404) {
                return Optional.empty();
            }

            if (!response.isSuccessful()) {
                throw new ClientException(String.format("Getting lookup by username '%s' un-successful, status code = %s", username, response.code()));
            }

            if (response.body() == null) {
                throw new ClientException(String.format("Getting lookup by username '%s' un-successful, parsed body is null.", username));
            }

            return Optional.of(response.body());
        } catch (IOException e) {
            throw new ClientException(String.format("Caught exception getting lookup by username '%s'", username), e);
        }
    }

    @NotNull
    public List<Rank> getAllRanks() {
        try {
            Response<List<Rank>> response = rankService.getAll().execute();
            if (!response.isSuccessful()) {
                throw new ClientException("Getting all ranks un-successful, status code = " + response.code());
            }

            if (response.body() == null) {
                throw new ClientException("Getting all ranks un-successful, parsed body is null.");
            }

            return response.body();
        } catch (IOException e) {
            throw new ClientException("Caught exception getting all ranks", e);
        }
    }

    public boolean createRank(@NotNull String name) throws ClientException {
        try {
            Response<Void> response = rankService.createRank(new RankCreateRequest(name)).execute();
            if (response.code() == 400) {
                return false;
            }

            if (!response.isSuccessful()) {
                throw new ClientException(String.format("Creating rank by name '%s' un-successful, status code = %s", name, response.code()));
            }

            return true;
        } catch (IOException e) {
            throw new ClientException(String.format("Caught exception creating rank by name '%s'", name), e);
        }
    }

    public boolean deleteRank(@NotNull UUID uuid) throws ClientException {
        requireNotNull(uuid);
        try {
            Response<Void> response = rankService.delete(uuid).execute();
            if (response.code() == 404) {
                return false;
            }

            if (!response.isSuccessful()) {
                throw new ClientException(String.format("Deleting rank by id '%s' un-successful, status code = %s", uuid, response.code()));
            }

            return true;
        } catch (IOException e) {
            throw new ClientException(String.format("Caught exception deleting rank by id '%s'", uuid), e);
        }
    }

    @NotNull
    public Optional<Rank> getRankById(@NotNull UUID uuid) throws ClientException {
        requireNotNull(uuid);
        try {
            Response<Rank> response = rankService.getRank(uuid).execute();
            if (response.code() == 404) {
                return Optional.empty();
            }

            if (!response.isSuccessful()) {
                throw new ClientException(String.format("Getting rank by id '%s' un-successful, status code = %s", uuid, response.code()));
            }

            if (response.body() == null) {
                throw new ClientException(String.format("Getting rank by id '%s' un-successful, parsed body is null.", uuid));
            }

            return Optional.of(response.body());
        } catch (IOException e) {
            throw new ClientException(String.format("Caught exception getting rank by id '%s'", uuid), e);
        }
    }

    @NotNull
    public Optional<Rank> getRankByName(@NotNull String name) throws ClientException {
        requireNotNull(name);
        try {
            Response<Rank> response = rankService.getRank(name).execute();
            if (response.code() == 404) {
                return Optional.empty();
            }

            if (!response.isSuccessful()) {
                throw new ClientException(String.format("Getting rank by name '%s' un-successful, status code = %s", name, response.code()));
            }

            if (response.body() == null) {
                throw new ClientException(String.format("Getting rank by name '%s' un-successful, parsed body is null.", name));
            }

            return Optional.of(response.body());
        } catch (IOException e) {
            throw new ClientException(String.format("Caught exception getting rank by name '%s'", name), e);
        }
    }

    public boolean editRank(@NotNull UUID uuid, @NotNull String field, @NotNull Object newValue) {
        requireNotNull(uuid, field, newValue);
        try {
            Response<Void> response = rankService.editRankField(uuid, new RankEditFieldRequest(field, newValue)).execute();
            if (response.code() == 404) {
                return false;
            }

            if (!response.isSuccessful()) {
                throw new ClientException(String.format("Editing rank by id '%s' un-successful, status code = %s", uuid, response.code()));
            }

            return true;
        } catch (IOException e) {
            throw new ClientException(String.format("Caught exception editing rank by id '%s'", uuid), e);
        }
    }
}
