package com.pakaking.sojun_final_project;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tlstk on 2017-10-10.
 */

public class ServiceGenerator {

    private static final String SW_API_ROOT_URL = "http://swopenAPI.seoul.go.kr/";

    private static Retrofit getStationListInstance() {
        return new Retrofit.Builder()
                .baseUrl(SW_API_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static SubwayApiService getListApiService() {
        return getStationListInstance().create(SubwayApiService.class);
    }




}
