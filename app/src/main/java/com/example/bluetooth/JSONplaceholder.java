package com.example.bluetooth;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JSONplaceholder {

    @GET("drdo")
    Call<List<ApiModel>> getApiData();
}
