package com.adriandp.marvel.config.module

import com.adriandp.marvel.BuildConfig
import com.adriandp.marvel.config.Constants
import com.adriandp.marvel.core.service.APIService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    private var mRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(OkHttpClient.Builder()
                    .readTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(Interceptor())
                    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }).build())
            .build()

    private inner class Interceptor : okhttp3.Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: okhttp3.Interceptor.Chain): Response {

            var request = chain.request()
            val httpUrl = request.url().newBuilder()
                    .addQueryParameter("hash", BuildConfig.HASH)
                    .addQueryParameter("apikey", BuildConfig.APIKEY)
                    .addQueryParameter("ts", BuildConfig.TS)
                    .build()

            BuildConfig.APPLICATION_ID
            request = request.newBuilder().url(httpUrl).build()

            return chain.proceed(request)
        }
    }


    @Singleton
    @Provides
    internal fun provideApiService(): APIService {
        return mRetrofit.create(APIService::class.java)
    }
}