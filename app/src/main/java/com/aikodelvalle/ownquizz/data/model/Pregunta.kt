package com.aikodelvalle.ownquizz.data.model

data class Pregunta(
    val id: Int,
    val enunciado: String,
    val opciones: List<String>,
    val indiceCorrecto: Int,
    val categoria: String
)