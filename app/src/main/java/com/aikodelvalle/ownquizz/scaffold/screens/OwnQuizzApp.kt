package com.aikodelvalle.ownquizz.scaffold.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.aikodelvalle.ownquizz.R
import com.aikodelvalle.ownquizz.quiz.presentation.screens.AgregarPreguntaRoute
import com.aikodelvalle.ownquizz.quiz.presentation.screens.QuizRoute

enum class Pantalla { QUIZ, AGREGAR }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnQuizzApp() {
    var pantalla by rememberSaveable { mutableStateOf(Pantalla.QUIZ) }

    BackHandler(enabled = pantalla == Pantalla.AGREGAR) { pantalla = Pantalla.QUIZ }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(
                            if (pantalla == Pantalla.QUIZ) R.string.titulo_quiz
                            else R.string.titulo_agregar
                        )
                    )
                },
                navigationIcon = {
                    if (pantalla == Pantalla.AGREGAR) {
                        IconButton(onClick = { pantalla = Pantalla.QUIZ }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.volver)
                            )
                        }
                    }
                },
                actions = {
                    if (pantalla == Pantalla.QUIZ) {
                        IconButton(onClick = { pantalla = Pantalla.AGREGAR }) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = stringResource(R.string.agregar_pregunta)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        when (pantalla) {
            Pantalla.QUIZ -> QuizRoute(
                onAgregarPregunta = { pantalla = Pantalla.AGREGAR },
                modifier = Modifier.padding(padding)
            )
            Pantalla.AGREGAR -> AgregarPreguntaRoute(
                onVolver = { pantalla = Pantalla.QUIZ },
                modifier = Modifier.padding(padding)
            )
        }
    }
}