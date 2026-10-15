package com.upchiapas.kt_template.data.model

data class Pregunta (
    val id: Int,
    val enunciado: String,
    val opciones: List<String>,
    val respuestaCorrectaIndex: Int
)






