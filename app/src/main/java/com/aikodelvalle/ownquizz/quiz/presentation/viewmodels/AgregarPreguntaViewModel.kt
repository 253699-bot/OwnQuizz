package com.aikodelvalle.ownquizz.quiz.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.aikodelvalle.ownquizz.quiz.data.repository.PreguntasRepository
import com.aikodelvalle.ownquizz.quiz.di.QuizModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AgregarPreguntaViewModel(
    private val repositorio: PreguntasRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AgregarPreguntaUiState())
    val uiState: StateFlow<AgregarPreguntaUiState> = _uiState.asStateFlow()

    fun onEnunciadoChange(valor: String) =
        _uiState.update { it.copy(enunciado = valor, error = null) }

    fun onOpcionChange(indice: Int, valor: String) = _uiState.update {
        it.copy(
            opciones = it.opciones.mapIndexed { i, opcion -> if (i == indice) valor else opcion },
            error = null
        )
    }

    fun onCorrectaChange(indice: Int) = _uiState.update { it.copy(indiceCorrecto = indice) }

    fun onCategoriaChange(valor: String) =
        _uiState.update { it.copy(categoria = valor, error = null) }

    fun onGuardar() {
        val estado = _uiState.value
        val enunciado = estado.enunciado.trim()
        val categoria = estado.categoria.trim()
        val opciones = estado.opciones.map { it.trim() }

        when {
            enunciado.isEmpty() || categoria.isEmpty() || opciones.any { it.isEmpty() } ->
                _uiState.update { it.copy(error = ErrorFormulario.CAMPOS_VACIOS) }

            opciones.distinct().size != opciones.size ->
                _uiState.update { it.copy(error = ErrorFormulario.OPCIONES_REPETIDAS) }

            else -> viewModelScope.launch {
                repositorio.agregar(enunciado, opciones, estado.indiceCorrecto, categoria)
                _uiState.update { it.copy(guardada = true) }
            }
        }
    }

    // La pantalla ya navegó: limpiamos el formulario para la próxima vez
    fun onGuardadaConsumida() {
        _uiState.value = AgregarPreguntaUiState()
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as Application
                AgregarPreguntaViewModel(QuizModule.provideRepository(app))
            }
        }
    }
}