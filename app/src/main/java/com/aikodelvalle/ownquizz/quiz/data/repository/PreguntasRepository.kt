package com.aikodelvalle.ownquizz.quiz.data.repository

import com.aikodelvalle.ownquizz.quiz.data.model.Pregunta
import com.aikodelvalle.ownquizz.quiz.data.datasource.PreguntasSemillaDataSource
import com.aikodelvalle.ownquizz.quiz.data.datasource.PreguntasUsuarioDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
//junta las dos fuentes (semilla y usuario) y entrega una sola lista al viewmodel
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