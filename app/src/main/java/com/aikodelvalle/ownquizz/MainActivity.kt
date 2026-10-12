package com.aikodelvalle.ownquizz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.aikodelvalle.ownquizz.ui.quiz.QuizRoute
import com.aikodelvalle.ownquizz.ui.theme.OwnQuizzTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OwnQuizzTheme {
                Scaffold { padding ->
                    QuizRoute(
                        onAgregarPregunta = {},
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}