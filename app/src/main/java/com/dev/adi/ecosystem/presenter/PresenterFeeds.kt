package com.dev.adi.ecosystem.presenter

import android.content.Context
import com.dev.adi.ecosystem.Helper
import com.dev.adi.ecosystem.controler.DataRepository
import com.dev.adi.ecosystem.model.DataFeeds
import com.dev.adi.ecosystem.model.DataSpecies
import com.dev.adi.ecosystem.model.ResponseFeed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PresenterFeeds (val view: FeedsView, private val context: Context) {

    fun getDataSpecies(): ArrayList<DataSpecies> {
        return Helper(context).getArrayList("dataSpecies")
    }

    fun getDetailFeed(id: Int) {
        val service = DataRepository.create(context)
        service.getFeedsDetail(id).enqueue(object : Callback<ResponseFeed> {
            override fun onFailure(call: Call<ResponseFeed>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseFeed>, response: Response<ResponseFeed>) {
                when (response.code()) {
                    200 -> {
                        val body = response.body()
                        if (body != null) {
                            view.successGetDetailFeeds(body.data)
                        } else {
                        }
                    }
                    404 -> {
                    }
                    500 -> {
                    }
                    else -> {
                    }
                }
            }
        })
    }
}

interface FeedsView {
    fun successGetDetailFeeds(list: DataFeeds)
}