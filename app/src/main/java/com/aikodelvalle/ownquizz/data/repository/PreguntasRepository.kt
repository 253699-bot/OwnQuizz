package com.aikodelvalle.ownquizz.data.repository

import com.aikodelvalle.ownquizz.data.local.PreguntasSemillaDataSource
import com.aikodelvalle.ownquizz.data.local.PreguntasUsuarioDataStore
import com.aikodelvalle.ownquizz.data.model.Pregunta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreguntasRepository(
    private val semilla: PreguntasSemillaDataSource,
    private val usuario: PreguntasUsuarioDataStore
) {
    val preguntas: Flow<List<Pregunta>> = usuario.preguntas.map { propias ->
        semilla.cargar() + propias
    }

    suspend fun agregar(
        enunciado: String,
        opciones: List<String>,
        indiceCorrecto: Int,
        categoria: String
    ) {
        // Los ids de usuario empiezan en 1000 para no chocar con los de la semilla
        val id = 1000 + (System.currentTimeMillis() % 100000).toInt()
        usuario.guardar(Pregunta(id, enunciado, opciones, indiceCorrecto, categoria))
    }
}