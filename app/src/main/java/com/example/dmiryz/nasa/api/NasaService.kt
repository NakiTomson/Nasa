package com.example.dmiryz.nasa.api


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import okhttp3.Interceptor

import okhttp3.logging.HttpLoggingInterceptor


var KEY = "bvHFulvj4IYd35wfWWAb0qaWGamtavbSx7KgMVPc"

class NasaService() {

    var BASE_URL = "https://api.nasa.gov/EPIC/api/"
    var api: NasaApi

    init {
        val retrofit = createRetrofit()
        api = retrofit.create(NasaApi::class.java)
    }


    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", KEY)
                    .build()
                val requestBuilder = original.newBuilder()
                    .url(url)
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })


        val logging =  HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

}