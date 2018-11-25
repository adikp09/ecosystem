package com.dev.adi.ecosystem

import android.content.Context
import android.preference.PreferenceManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.dev.adi.ecosystem.model.DataSpecies
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*


class Helper(val context: Context) {

    fun getSpeciesName(listSpecies : ArrayList<DataSpecies>, speciesId : Int): String {
        var name = "Species Name"
        listSpecies.forEach {
            if(it.id == speciesId) {
                name = it.name
            }
        }
        return upperCaseFirst(name)
    }

    fun replaceIdContent(listSpecies : ArrayList<DataSpecies>, contentData : String): String {
        var conten = "Content"
        if (contentData.contains("@")) {
            var substring = contentData.substring(contentData.length -1)
            listSpecies.forEach {
                if(it.id == substring.toInt()) {
                    conten = contentData.replace("@$substring", "${upperCaseFirst(it.name)}")
                }
            }
        } else {
            conten = contentData
        }
        return conten
    }

    fun setGlideImage(imageData : Int, imageView: ImageView ) {
        Glide.with(context)
            .load(imageData)
            .into(imageView)
    }

    fun getDateTime(s: String): String? {
        return try {
            val sdf = SimpleDateFormat("dd-MMM-yyyy, HH:mm ")
            val netDate = Date(s.toLong() * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    fun upperCaseFirst(value : String): String {
        val array = value.toCharArray()
        array[0] = Character.toUpperCase(array[0])
        return String(array)
    }

    fun saveArrayList(list: ArrayList<DataSpecies>, key: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()     // This line is IMPORTANT !!!
        editor.commit()
    }

    fun saveSpeciesId(idSpecies : Int, key: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putInt(key, idSpecies)
        editor.apply()     // This line is IMPORTANT !!!
        editor.commit()
    }

    fun getSpeciesId(key : String): Int {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getInt(key, 0)
    }

    fun getArrayList(key: String): ArrayList<DataSpecies> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type = object : TypeToken<ArrayList<DataSpecies>>() {

        }.type
        return gson.fromJson(json, type)
    }

    fun removeIdSpecies(key: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.remove(key)
        editor.apply()     // This line is IMPORTANT !!!
        editor.commit()
    }
}