package com.upchiapas.kt_template.data.repository

import com.upchiapas.kt_template.data.datasource.QuizLocalDataSource
import com.upchiapas.kt_template.data.model.Pregunta

class QuizRepository (
    private val dataSource: QuizLocalDataSource
) {
    fun obtenerPreguntas(): List<Pregunta> {
        return dataSource.obtenerTodasLasPreguntas()
    }

    fun registrarPregunta(pregunta: Pregunta) {
        dataSource.guardarNuevaPregunta(pregunta)
    }
}