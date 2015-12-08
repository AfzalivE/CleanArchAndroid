package com.afzaln.cleanarch.interactors;

import com.afzaln.cleanarch.network.ApiaryApi;

/**
 * Created by afzal on 2015-12-07.
 */
public class Interactor {
    ApiaryApi mApiaryApi;

    protected ApiaryApi getApiaryApi() {
        if (mApiaryApi == null) {
            mApiaryApi = new ApiaryApi();
        }

        return mApiaryApi;
    }
}
