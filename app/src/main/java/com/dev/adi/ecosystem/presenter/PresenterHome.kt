package com.dev.adi.ecosystem.presenter

import android.content.Context
import com.dev.adi.ecosystem.controler.DataRepository
import com.dev.adi.ecosystem.model.DataHome
import com.dev.adi.ecosystem.model.DataSpecies
import com.dev.adi.ecosystem.model.ResponseHome
import com.dev.adi.ecosystem.model.ResponseSpecies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterHome (val viewHome : HomeView, val context: Context) {
    fun getHomeFeeds() {
        val service = DataRepository.create(context)
        service.getHome().enqueue(object : Callback<ResponseHome> {
            override fun onFailure(call: Call<ResponseHome>, t: Throwable) {
                viewHome.failedGetHome(t.message.toString())
            }

            override fun onResponse(call: Call<ResponseHome>, response: Response<ResponseHome>) {
                when (response.code()) {
                    200 -> {
                        val body = response.body()
                        if (body != null) {
                            if (body.data.isNotEmpty()) {
                                viewHome.successGetHome(body?.data)
                            } else {
                                viewHome.emptyFeeds("Empty Feeds")
                            }
                        }
                    }
                    404 -> {
                        viewHome.emptyFeeds("Error 404")
                    }
                    500 -> {
                        viewHome.emptyFeeds("Server Error")
                    }
                    else -> {
                        viewHome.emptyFeeds("System Error")
                    }
                }
            }
        })
    }

    fun getSpecies() {
        val service = DataRepository.create(context)
        service.getSpecies().enqueue(object : Callback<ResponseSpecies> {
            override fun onResponse(call: Call<ResponseSpecies>, response: Response<ResponseSpecies>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        viewHome.successGetSpecies(body.data)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseSpecies>, t: Throwable) {
            }

        })
    }
}

interface HomeView {
    fun successGetHome(list: ArrayList<DataHome>)
    fun successGetSpecies(list: ArrayList<DataSpecies>)
    fun failedGetHome(message : String)
    fun emptyFeeds(message : String)
}