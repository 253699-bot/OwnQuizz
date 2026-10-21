package com.aikodelvalle.ownquizz.quiz.presentation.viewmodels

import androidx.annotation.StringRes
import com.aikodelvalle.ownquizz.R

enum class ErrorFormulario(@StringRes val mensaje: Int) {
    CAMPOS_VACIOS(R.string.error_campos_vacios),
    OPCIONES_REPETIDAS(R.string.error_opciones_repetidas)
}

data class AgregarPreguntaUiState(
    val enunciado: String = "",
    val opciones: List<String> = List(4) { "" },
    val indiceCorrecto: Int = 0,
    val categoria: String = "",
    val error: ErrorFormulario? = null,
    val guardada: Boolean = false
)