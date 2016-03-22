package ru.eastbanctech.careeersday.android;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebService {

    private final WebApi webApi;

    public WebService(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webApi = retrofit.create(WebApi.class);
    }

    public WebApi getWebApi() {
        return webApi;
    }
}
