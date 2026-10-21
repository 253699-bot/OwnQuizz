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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aikodelvalle.ownquizz.R
import com.aikodelvalle.ownquizz.quiz.presentation.components.Estadistica

@Composable
fun ResultadosScreen(
    aciertos: Int,
    errores: Int,
    porcentaje: Int,
    onReiniciar: () -> Unit,
    onAgregarPregunta: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = stringResource(R.string.titulo_resultados),
            style = MaterialTheme.typography.headlineMedium
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                text = stringResource(R.string.porcentaje, porcentaje),
                modifier = Modifier.padding(horizontal = 48.dp, vertical = 32.dp),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Estadistica(stringResource(R.string.aciertos, aciertos), Modifier.weight(1f))
            Estadistica(stringResource(R.string.errores, errores), Modifier.weight(1f))
        }
        Button(onClick = onReiniciar, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.reiniciar))
        }
        OutlinedButton(onClick = onAgregarPregunta, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.agregar_pregunta))
        }
    }
}