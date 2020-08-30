package org.selyu.pando.client.service;

import org.selyu.pando.client.model.Lookup;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.UUID;

public interface ILookupService {
    @GET("lookup/id/{id}")
    Call<Lookup> getById(@Path("id") UUID uuid);

    @GET("lookup/username/{username}")
    Call<Lookup> getByUsername(@Path("username") String username);
}
