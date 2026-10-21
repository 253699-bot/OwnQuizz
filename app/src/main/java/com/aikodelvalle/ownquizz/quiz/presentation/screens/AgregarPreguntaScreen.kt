package com.aikodelvalle.ownquizz.quiz.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aikodelvalle.ownquizz.R
import com.aikodelvalle.ownquizz.quiz.presentation.viewmodels.AgregarPreguntaUiState
import com.aikodelvalle.ownquizz.quiz.presentation.viewmodels.AgregarPreguntaViewModel

@Composable
fun AgregarPreguntaRoute(
    onVolver: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AgregarPreguntaViewModel = viewModel(factory = AgregarPreguntaViewModel.Factory)
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.guardada) {
        if (state.guardada) {
            viewModel.onGuardadaConsumida()
            onVolver()
        }
    }

    AgregarPreguntaScreen(
        state = state,
        onEnunciadoChange = viewModel::onEnunciadoChange,
        onOpcionChange = viewModel::onOpcionChange,
        onCorrectaChange = viewModel::onCorrectaChange,
        onCategoriaChange = viewModel::onCategoriaChange,
        onGuardar = viewModel::onGuardar,
        modifier = modifier
    )
}

@Composable
fun AgregarPreguntaScreen(
    state: AgregarPreguntaUiState,
    onEnunciadoChange: (String) -> Unit,
    onOpcionChange: (Int, String) -> Unit,
    onCorrectaChange: (Int) -> Unit,
    onCategoriaChange: (String) -> Unit,
    onGuardar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = state.enunciado,
            onValueChange = onEnunciadoChange,
            label = { Text(stringResource(R.string.enunciado)) },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.instruccion_correcta),
            style = MaterialTheme.typography.bodyMedium
        )

        state.opciones.forEachIndexed { indice, texto ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = state.indiceCorrecto == indice,
                    onClick = { onCorrectaChange(indice) }
                )
                OutlinedTextField(
                    value = texto,
                    onValueChange = { onOpcionChange(indice, it) },
                    label = { Text(stringResource(R.string.opcion_n, indice + 1)) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        OutlinedTextField(
            value = state.categoria,
            onValueChange = onCategoriaChange,
            label = { Text(stringResource(R.string.categoria)) },
            modifier = Modifier.fillMaxWidth()
        )

        state.error?.let { error ->
            Text(
                text = stringResource(error.mensaje),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(onClick = onGuardar, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.guardar))
        }
    }
}