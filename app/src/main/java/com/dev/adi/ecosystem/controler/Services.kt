package com.dev.adi.ecosystem.controler

import com.dev.adi.ecosystem.model.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Services {
    //1
    @GET("/ecosystem/feeds")
    fun getHome(): Call<ResponseHome>

    //2
    @POST("/ecosystem/{species_id}/{feed_id}/reply")
    fun replyFeed(
        @Path("species_id") speciesId : Int,
        @Path("feed_id") feedId : Int,
        @Body content : JsonObject
    ): Call<ResponseTweat>

    //3
    @GET("/ecosystem/feeds/{id}")
    fun getFeedsDetail(
        @Path("id") id : Int
    ): Call<ResponseFeed>

    //4
    @POST("/ecosystem/{species_id}/feeds")
    fun tweat(
        @Path("species_id") id : Int,
        @Body target_id : JsonObject
    ): Call<ResponseTweat>

    //5
    @GET("/ecosystem/{species_id}")
    fun getProfile(
        @Path("species_id") id : Int
    ): Call<ResponseProfile>

    //6
    @GET("/ecosystem")
    fun getSpecies(): Call<ResponseSpecies>
}