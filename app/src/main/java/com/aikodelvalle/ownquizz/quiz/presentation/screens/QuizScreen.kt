package com.aikodelvalle.ownquizz.quiz.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import com.aikodelvalle.ownquizz.ui.theme.RespuestaCorrecta
import com.aikodelvalle.ownquizz.ui.theme.RespuestaIncorrecta

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
                ChipCategoria(
                    texto = stringResource(R.string.todas),
                    seleccionado = state.categoriaFiltro == null,
                    onClick = { onCategoriaSeleccionada(null) }
                )
            }
            items(state.categorias) { categoria ->
                ChipCategoria(
                    texto = categoria,
                    seleccionado = state.categoriaFiltro == categoria,
                    onClick = { onCategoriaSeleccionada(categoria) }
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
            Estadistica(
                texto = stringResource(R.string.aciertos, state.aciertos),
                modifier = Modifier.weight(1f),
                color = RespuestaCorrecta
            )
            Estadistica(
                texto = stringResource(R.string.errores, state.errores),
                modifier = Modifier.weight(1f),
                color = RespuestaIncorrecta
            )
            Estadistica(
                texto = stringResource(R.string.racha, state.racha),
                modifier = Modifier.weight(1f)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
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
                text = stringResource(
                    if (acerto) R.string.correcto else R.string.incorrecto
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium,
                color = if (acerto) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.error
            )

            Button(
                onClick = onSiguiente,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.siguiente))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChipCategoria(
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = seleccionado,
        onClick = onClick,
        label = { Text(texto) },
        modifier = modifier,
        shape = RoundedCornerShape(50),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}