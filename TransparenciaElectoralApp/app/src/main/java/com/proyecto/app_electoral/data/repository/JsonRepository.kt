package com.proyecto.app_electoral.data.repository

import android.content.Context
import com.google.gson.Gson
import com.proyecto.app_electoral.data.model.DatosElectorales

class JsonRepository(private val context: Context) {

    fun cargarDatos(): DatosElectorales {
        val json = context.assets.open("app-electoral-datos.json")
            .bufferedReader().use { it.readText() }

        return Gson().fromJson(json, DatosElectorales::class.java)
    }
}
