package com.donnut.yesorno;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("api")
    Single<YesNoImage> loadYesNoImage();
}
