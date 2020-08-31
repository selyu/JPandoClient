package org.selyu.pando.client.service;

import org.selyu.pando.client.model.Rank;
import org.selyu.pando.client.model.RankCreateRequest;
import org.selyu.pando.client.model.RankEditFieldRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.UUID;

public interface IRankService {
    @GET("rank")
    Call<List<Rank>> getAll();

    @POST("rank")
    Call<Void> createRank(@Body RankCreateRequest request);

    @GET("rank/id/{id}")
    Call<Rank> getRank(@Path("id") UUID uuid);

    @PUT("rank/id/{id}")
    Call<Void> editRankField(@Path("id") UUID uuid, @Body RankEditFieldRequest request);

    @DELETE("rank/id/{id}")
    Call<Void> delete(@Path("id") UUID uuid);

    @GET("rank/name/{name}")
    Call<Rank> getRank(@Path("name") String name);
}
