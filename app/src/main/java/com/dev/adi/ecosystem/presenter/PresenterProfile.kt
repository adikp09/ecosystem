package com.dev.adi.ecosystem.presenter

import android.content.Context
import com.dev.adi.ecosystem.controler.DataRepository
import com.dev.adi.ecosystem.model.DataProfile
import com.dev.adi.ecosystem.model.ResponseProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PresenterProfile (val viewProfile : ProfileView, private val context: Context) {

    fun getProfile(id : Int) {
        val service = DataRepository.create(context)
        service.getProfile(id).enqueue(object : Callback<ResponseProfile> {
            override fun onFailure(call: Call<ResponseProfile>, t: Throwable) {
            }

            override fun onResponse(call: Call<ResponseProfile>, response: Response<ResponseProfile>) {
                if (response?.isSuccessful!!) {
                    val body = response.body()
                    if (body != null) {
                        viewProfile.successGetProfile(body.data)
                    }
                }
            }

        })
    }
}


interface ProfileView {
    fun successGetProfile(list: DataProfile)
}