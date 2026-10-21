package com.aikodelvalle.ownquizz.quiz.data.datasource

import android.content.Context
import com.aikodelvalle.ownquizz.R
import org.json.JSONArray

class PreguntasSemillaDataSource(private val context: Context) {

    fun cargar(): List<Pregunta> {
        val texto = context.resources.openRawResource(R.raw.preguntas_base)
            .bufferedReader().use { it.readText() }
        return JsonMapper.aLista(JSONArray(texto))
    }
}