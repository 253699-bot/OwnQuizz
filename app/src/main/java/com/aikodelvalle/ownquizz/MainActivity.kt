package com.aikodelvalle.ownquizz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aikodelvalle.ownquizz.scaffold.screens.OwnQuizzApp
import com.aikodelvalle.ownquizz.ui.theme.OwnQuizzTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OwnQuizzTheme {
                OwnQuizzApp()
            }
        }
    }
}