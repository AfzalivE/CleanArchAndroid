package com.afzaln.cleanarch.network;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by afzal on 2015-12-07.
 */
public abstract class Api {

    public static final String BASE_URL = "http://polls.apiblueprint.org/";

    // all API services
    ApiaryService mApiaryService;
    Retrofit mRetrofit;

    Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return mRetrofit;
    }

    ApiaryService getApiaryService() {
        if (mApiaryService == null) {
            mApiaryService = getRetrofit().create(ApiaryService.class);
        }

        return mApiaryService;
    }
}
