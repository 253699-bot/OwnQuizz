package com.aikodelvalle.ownquizz.quiz.di

import android.content.Context
import com.aikodelvalle.ownquizz.quiz.data.datasource.PreguntasSemillaDataSource
import com.aikodelvalle.ownquizz.quiz.data.datasource.PreguntasUsuarioDataStore
import com.aikodelvalle.ownquizz.quiz.data.repository.PreguntasRepository

object QuizModule {

    fun provideRepository(context: Context): PreguntasRepository {
        val appContext = context.applicationContext
        return PreguntasRepository(
            semilla = PreguntasSemillaDataSource(appContext),
            usuario = PreguntasUsuarioDataStore(appContext)
        )
    }
}