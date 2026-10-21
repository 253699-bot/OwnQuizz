package com.aikodelvalle.ownquizz.quiz.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aikodelvalle.ownquizz.R
import com.aikodelvalle.ownquizz.quiz.presentation.components.Estadistica
import com.aikodelvalle.ownquizz.ui.theme.RespuestaCorrecta
import com.aikodelvalle.ownquizz.ui.theme.RespuestaIncorrecta

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
            modifier = Modifier.size(width = 220.dp, height = 120.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.porcentaje, porcentaje),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Estadistica(
                texto = stringResource(R.string.aciertos, aciertos),
                modifier = Modifier.weight(1f),
                color = RespuestaCorrecta
            )
            Estadistica(
                texto = stringResource(R.string.errores, errores),
                modifier = Modifier.weight(1f),
                color = RespuestaIncorrecta
            )
        }

        Button(
            onClick = onReiniciar,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = stringResource(R.string.reiniciar),
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedButton(
            onClick = onAgregarPregunta,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(R.string.agregar_pregunta),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}