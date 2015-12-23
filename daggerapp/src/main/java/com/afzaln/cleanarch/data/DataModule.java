package com.afzaln.cleanarch.data;

import javax.inject.Singleton;

import com.github.aurae.retrofit.LoganSquareConverterFactory;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.squareup.okhttp.logging.HttpLoggingInterceptor.Level;
import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by afzal on 2015-12-13.
 */
@Module
public class DataModule {
    public static final String BASE_URL = "http://polls.apiblueprint.org/";

    @Provides
    Retrofit providesRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(Level.BODY);

        OkHttpClient httpClient = new OkHttpClient();
        // add your other interceptors …

        // add logging as last interceptor
        httpClient.interceptors().add(logging);

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    ApiaryService providesApiaryService(Retrofit retrofit) {
        return retrofit.create(ApiaryService.class);
    }

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper() {
        return new DatabaseHelper();
    }

    @Provides
    @Singleton
    AppModel provideAppModel(ApiaryService apiaryService, DatabaseHelper databaseHelper) {
        return new AppModel(apiaryService, databaseHelper);
    }
}
