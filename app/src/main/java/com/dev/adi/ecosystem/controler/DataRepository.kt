package com.dev.adi.ecosystem.controler

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataRepository {
    fun create(context: Context): Services {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor( ChuckInterceptor(context))
            .addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl("https://little-ecosystem.glitch.me/")
                .build()
        return retrofit.create(Services::class.java)
    }
}