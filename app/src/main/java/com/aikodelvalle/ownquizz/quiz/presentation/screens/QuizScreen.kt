package com.aikodelvalle.ownquizz.quiz.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aikodelvalle.ownquizz.R
import com.aikodelvalle.ownquizz.quiz.presentation.components.Estadistica
import com.aikodelvalle.ownquizz.quiz.presentation.components.OpcionRespuesta
import com.aikodelvalle.ownquizz.quiz.presentation.viewmodels.QuizUiState
import com.aikodelvalle.ownquizz.quiz.presentation.viewmodels.QuizViewModel

@Composable
fun QuizRoute(
    onAgregarPregunta: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel(factory = QuizViewModel.Factory)
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    if (state.terminado) {
        ResultadosScreen(
            aciertos = state.aciertos,
            errores = state.errores,
            porcentaje = state.porcentaje,
            onReiniciar = viewModel::onReiniciar,
            onAgregarPregunta = onAgregarPregunta,
            modifier = modifier
        )
    } else {
        QuizScreen(
            state = state,
            onResponder = viewModel::onResponder,
            onSiguiente = viewModel::onSiguiente,
            onCategoriaSeleccionada = viewModel::onCategoriaSeleccionada,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    state: QuizUiState,
    onResponder: (Int) -> Unit,
    onSiguiente: () -> Unit,
    onCategoriaSeleccionada: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                FilterChip(
                    selected = state.categoriaFiltro == null,
                    onClick = { onCategoriaSeleccionada(null) },
                    label = { Text(stringResource(R.string.todas)) }
                )
            }
            items(state.categorias) { categoria ->
                FilterChip(
                    selected = state.categoriaFiltro == categoria,
                    onClick = { onCategoriaSeleccionada(categoria) },
                    label = { Text(categoria) }
                )
            }
        }

        val pregunta = state.preguntaActual
        if (pregunta == null) {
            Text(stringResource(R.string.sin_preguntas))
            return@Column
        }

        Text(
            text = stringResource(
                R.string.progreso_pregunta,
                state.indiceActual + 1,
                state.preguntas.size
            ),
            style = MaterialTheme.typography.labelLarge
        )
        LinearProgressIndicator(
            progress = { state.progreso },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Estadistica(stringResource(R.string.aciertos, state.aciertos), Modifier.weight(1f))
            Estadistica(stringResource(R.string.errores, state.errores), Modifier.weight(1f))
            Estadistica(stringResource(R.string.racha, state.racha), Modifier.weight(1f))
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = pregunta.enunciado,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }

        pregunta.opciones.forEachIndexed { indice, texto ->
            OpcionRespuesta(
                texto = texto,
                estado = state.estadoOpcion(indice),
                habilitada = state.respuestaSeleccionada == null,
                onClick = { onResponder(indice) }
            )
        }

        state.acerto?.let { acerto ->
            Text(
                text = stringResource(if (acerto) R.string.correcto else R.string.incorrecto),
                style = MaterialTheme.typography.titleMedium,
                color = if (acerto) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.error
            )
            Button(onClick = onSiguiente, modifier = Modifier.align(Alignment.End)) {
                Text(stringResource(R.string.siguiente))
            }
        }
    }
}