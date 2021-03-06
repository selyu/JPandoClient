package org.selyu.pando.client.service;

import org.selyu.pando.client.model.User;
import org.selyu.pando.client.model.UserCreateRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    @GET("user")
    Call<List<User>> getAll();

    @POST("user")
    Call<User> create(@Body UserCreateRequest request);

    @GET("user/id/{id}")
    Call<User> getOne(@Path("id") UUID uuid);
}
