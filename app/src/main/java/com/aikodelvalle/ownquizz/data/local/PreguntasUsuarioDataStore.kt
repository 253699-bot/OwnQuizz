package com.aikodelvalle.ownquizz.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.aikodelvalle.ownquizz.data.model.Pregunta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray

private val Context.dataStore by preferencesDataStore(name = "preguntas_usuario")

class PreguntasUsuarioDataStore(private val context: Context) {

    private val clave = stringPreferencesKey("lista_preguntas")

    val preguntas: Flow<List<Pregunta>> = context.dataStore.data.map { prefs ->
        prefs[clave]?.let { JsonMapper.aLista(JSONArray(it)) } ?: emptyList()
    }

    suspend fun guardar(nueva: Pregunta) {
        context.dataStore.edit { prefs ->
            val actuales = prefs[clave]?.let { JsonMapper.aLista(JSONArray(it)) } ?: emptyList()
            prefs[clave] = JsonMapper.aJson(actuales + nueva)
        }
    }
}