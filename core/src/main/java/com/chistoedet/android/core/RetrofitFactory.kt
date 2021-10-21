package com.chistoedet.android.core


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    companion object {
        val baseUrl = "https://istu.ru/api/mobile/"

        private fun getOkHttpInstance(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC

            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .followRedirects(false)
                .build()
        }

        //@UnstableDefault
        private fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpInstance())
                //.addConverterFactory(Json.nonstrict.asConverterFactory(contentType = "application/json".toMediaType()))
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }

        fun getApiService() = getRetrofitInstance().create(ISTUService::class.java)
    }
}