package com.guilhermecardoso.buscapontos.domain.network.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by guilhermecardoso on 11/12/17.
 */

public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();
        final HttpUrl originalHttpUrl = original.url();

        final HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("access_token", authToken)
                .build();

        final Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

}

