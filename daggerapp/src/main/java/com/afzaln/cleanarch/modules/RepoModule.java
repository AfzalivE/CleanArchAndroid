package com.afzaln.cleanarch.modules;

import javax.inject.Singleton;

import com.afzaln.cleanarch.repo.ApiaryService;
import com.afzaln.cleanarch.repo.AppModel;
import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by afzal on 2015-12-13.
 */
@Module
public class RepoModule {
    public static final String BASE_URL = "http://polls.apiblueprint.org/";

    @Provides
    Retrofit providesRetrofit() {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    ApiaryService providesApiaryService(Retrofit retrofit) {
        return retrofit.create(ApiaryService.class);
    }

    @Provides
    @Singleton
    AppModel provideAppModel(ApiaryService apiaryService) {
        return new AppModel(apiaryService);
    }
}
