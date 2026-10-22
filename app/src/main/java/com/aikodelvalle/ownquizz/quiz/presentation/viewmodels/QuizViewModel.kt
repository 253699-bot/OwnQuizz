package com.aikodelvalle.ownquizz.quiz.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.aikodelvalle.ownquizz.quiz.data.datasource.PreguntasSemillaDataSource
import com.aikodelvalle.ownquizz.quiz.data.datasource.PreguntasUsuarioDataStore
import com.aikodelvalle.ownquizz.quiz.data.repository.PreguntasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
//contiene la lógica de la pantalla (validar respuesta, racha, avanzar, categ y reiniciar)
class QuizViewModel(private val repositorio: PreguntasRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repositorio.preguntas.collect { lista ->
                _uiState.update { it.copy(todas = lista) }
            }
        }
    }

    fun onResponder(indice: Int) {
        val estado = _uiState.value
        val pregunta = estado.preguntaActual ?: return
        if (estado.respuestaSeleccionada != null) return
        val acerto = indice == pregunta.indiceCorrecto
        _uiState.update {
            it.copy(
                respuestaSeleccionada = indice,
                aciertos = if (acerto) it.aciertos + 1 else it.aciertos,
                errores = if (acerto) it.errores else it.errores + 1,
                racha = if (acerto) it.racha + 1 else 0
            )
        }
    }

    fun onSiguiente() {
        _uiState.update {
            val siguiente = it.indiceActual + 1
            if (siguiente >= it.preguntas.size) {
                it.copy(terminado = true)
            } else {
                it.copy(indiceActual = siguiente, respuestaSeleccionada = null)
            }
        }
    }

    fun onCategoriaSeleccionada(categoria: String?) {
        _uiState.update {
            it.copy(
                categoriaFiltro = categoria,
                indiceActual = 0,
                respuestaSeleccionada = null,
                aciertos = 0,
                errores = 0,
                racha = 0,
                terminado = false
            )
        }
    }

    fun onReiniciar() = onCategoriaSeleccionada(_uiState.value.categoriaFiltro)

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as Application
                QuizViewModel(
                    PreguntasRepository(
                        PreguntasSemillaDataSource(app),
                        PreguntasUsuarioDataStore(app)
                    )
                )
            }
        }
    }
}