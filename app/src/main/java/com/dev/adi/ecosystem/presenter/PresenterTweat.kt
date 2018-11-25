package com.dev.adi.ecosystem.presenter

import android.content.Context
import com.dev.adi.ecosystem.controler.DataRepository
import com.dev.adi.ecosystem.model.ResponseTweat
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterTweat (val view : TweatView, val context: Context) {

    fun replyFeeds(idSpecies: Int, idTarget : Int, content : String) {
        val service = DataRepository.create(context)
        val jsonObject = JsonObject()
        jsonObject.addProperty("content", content)
        service.replyFeed(idSpecies, idTarget, jsonObject).enqueue(object : Callback<ResponseTweat> {
            override fun onFailure(call: Call<ResponseTweat>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseTweat>, response: Response<ResponseTweat>) {
                if (response?.isSuccessful!!) {
                    val body = response.body()
                    if (body != null) {
                        view.successReplyFeed(body.success)
                    }
                }
            }

        })
    }

    fun tweat(idSpecies : Int, idTarget : Int) {
        val service = DataRepository.create(context)
        val jsonObject = JsonObject()
        jsonObject.addProperty("target_id", idTarget)
        service.tweat(idSpecies, jsonObject).enqueue(object : Callback<ResponseTweat> {
            override fun onResponse(call: Call<ResponseTweat>, response: Response<ResponseTweat>) {
                if (response?.isSuccessful!!) {
                    val body = response.body()
                    if (body != null) {
                        view.successTweat(body.success)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseTweat>, t: Throwable) {
            }

        })
    }
}

interface TweatView {
    fun successTweat(status : Boolean)
    fun successReplyFeed(status: Boolean)
}