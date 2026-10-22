package com.aikodelvalle.ownquizz.quiz.presentation.viewmodels

import com.aikodelvalle.ownquizz.quiz.data.model.Pregunta
import com.aikodelvalle.ownquizz.quiz.presentation.components.EstadoOpcion
//Guarda todo lo que la pantalla necesita
data class QuizUiState(
    val todas: List<Pregunta> = emptyList(),
    val categoriaFiltro: String? = null,
    val indiceActual: Int = 0,
    val respuestaSeleccionada: Int? = null,
    val aciertos: Int = 0,
    val errores: Int = 0,
    val racha: Int = 0,
    val terminado: Boolean = false
) {
    val preguntas: List<Pregunta>
        get() = if (categoriaFiltro == null) todas else todas.filter { it.categoria == categoriaFiltro }

    val preguntaActual: Pregunta? get() = preguntas.getOrNull(indiceActual)

    val categorias: List<String> get() = todas.map { it.categoria }.distinct()

    val porcentaje: Int
        get() = (aciertos + errores).takeIf { it > 0 }?.let { aciertos * 100 / it } ?: 0

    val progreso: Float
        get() {
            if (preguntas.isEmpty()) return 0f
            val respondidas = indiceActual + if (respuestaSeleccionada != null) 1 else 0
            return respondidas.toFloat() / preguntas.size
        }

    // null = aún no responde; true/false = acertó o falló
    val acerto: Boolean?
        get() = respuestaSeleccionada?.let { it == preguntaActual?.indiceCorrecto }

    fun estadoOpcion(indice: Int): EstadoOpcion {
        val seleccion = respuestaSeleccionada ?: return EstadoOpcion.NEUTRA
        return when {
            indice == preguntaActual?.indiceCorrecto -> EstadoOpcion.CORRECTA
            indice == seleccion -> EstadoOpcion.INCORRECTA
            else -> EstadoOpcion.NEUTRA
        }
    }
}