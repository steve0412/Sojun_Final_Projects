package com.pakaking.sojun_final_project;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tlstk on 2017-10-10.
 */

public interface SubwayApiService {
    @GET("api/subway/{apiKey}/json/shortestRoute/0/5/{start_station}/{end_station}")
    Call<Station> getStationList(
            @Path("apiKey") String apiKey,
            @Path("start_station") String start_station,
            @Path("end_station") String end_station
    );
}