package com.proyecto.app_electoral.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.proyecto.app_electoral.data.model.Denuncia
import com.proyecto.app_electoral.data.model.HistorialCargo
import com.proyecto.app_electoral.data.model.Propuesta

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromPropuestaList(value: List<Propuesta>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPropuestaList(value: String): List<Propuesta>? {
        val listType = object : TypeToken<List<Propuesta>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromDenunciaList(value: List<Denuncia>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toDenunciaList(value: String): List<Denuncia>? {
        val listType = object : TypeToken<List<Denuncia>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromHistorialCargoList(value: List<HistorialCargo>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toHistorialCargoList(value: String): List<HistorialCargo>? {
        val listType = object : TypeToken<List<HistorialCargo>>() {}.type
        return gson.fromJson(value, listType)
    }
}
