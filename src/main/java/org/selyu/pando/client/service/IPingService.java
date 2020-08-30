package org.selyu.pando.client.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IPingService {
    @GET("ping")
    Call<String> ping();
}
