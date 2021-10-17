package com.chistoedet.android.istustudents.di

import com.chistoedet.android.istustudents.BuildConfig
import com.chistoedet.android.istustudents.Config
import com.chistoedet.android.istustudents.ISTUService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
abstract class NetworkModule {

    companion object {

        @Provides
        fun provideOkHttp(): OkHttpClient {

            val loggingInterceptor = HttpLoggingInterceptor()

            // Только в режиме отладки

            if (BuildConfig.DEBUG) {

                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            }


            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor())
                //.addInterceptor(AccessInterceptor(Config.accessToken!!))
                .followRedirects(false)
                .addInterceptor(loggingInterceptor)
                .build()
        }

        @Provides
        fun provideRetrofit(client: OkHttpClient) : Retrofit {
            return Retrofit.Builder()
                .baseUrl(Config.BASE_API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                //.addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

        @Provides
        fun provideApiService(retrofit: Retrofit) : ISTUService {
            return retrofit.create(ISTUService::class.java)
        }
    }


}