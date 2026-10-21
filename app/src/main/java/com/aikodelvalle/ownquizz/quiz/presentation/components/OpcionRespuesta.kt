package com.aikodelvalle.ownquizz.quiz.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aikodelvalle.ownquizz.ui.theme.RespuestaCorrecta
import com.aikodelvalle.ownquizz.ui.theme.RespuestaIncorrecta

enum class EstadoOpcion { NEUTRA, CORRECTA, INCORRECTA }

@Composable
fun OpcionRespuesta(
    texto: String,
    estado: EstadoOpcion,
    habilitada: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val fondo = when (estado) {
        EstadoOpcion.CORRECTA -> RespuestaCorrecta
        EstadoOpcion.INCORRECTA -> RespuestaIncorrecta
        EstadoOpcion.NEUTRA -> Color.Transparent
    }
    val textoDeshabilitado = if (estado == EstadoOpcion.NEUTRA) {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    } else {
        Color.Black
    }
    OutlinedButton(
        onClick = onClick,
        enabled = habilitada,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = fondo,
            disabledContainerColor = fondo,
            disabledContentColor = textoDeshabilitado
        )
    ) {
        Text(texto, style = MaterialTheme.typography.bodyLarge)
    }
}