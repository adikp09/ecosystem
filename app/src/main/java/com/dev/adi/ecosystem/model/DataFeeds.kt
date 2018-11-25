package com.dev.adi.ecosystem.model

class DataFeeds(
        val id : Int,
        val content : String,
        val time : Long,
        val reference_id : Int,
        val species_id : Int,
        val replies : ArrayList<DataHome>
)