package ru.skillbox.presentation_patterns.utils.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest =
                chain.request().newBuilder()
                        .header("Content-Type","application/json; charset=UTF-8")
                        .build()
        return chain.proceed(newRequest)
    }
}