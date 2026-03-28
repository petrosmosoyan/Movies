package com.example.movies.data.remote.interceptor

import com.example.movies.common.Constants.APY_KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestWithAuth = originalRequest
            .newBuilder()
            .addHeader(
                "Authorization",
                "Bearer $APY_KEY"
            )
            .build()


        return chain.proceed(requestWithAuth)
    }

}