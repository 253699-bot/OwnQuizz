package com.aikodelvalle.ownquizz.data.local

import android.content.Context
import com.aikodelvalle.ownquizz.R
import com.aikodelvalle.ownquizz.data.model.Pregunta
import org.json.JSONArray

class PreguntasSemillaDataSource(private val context: Context) {

    fun cargar(): List<Pregunta> {
        val texto = context.resources.openRawResource(R.raw.preguntas_base)
            .bufferedReader().use { it.readText() }
        return JsonMapper.aLista(JSONArray(texto))
    }
}