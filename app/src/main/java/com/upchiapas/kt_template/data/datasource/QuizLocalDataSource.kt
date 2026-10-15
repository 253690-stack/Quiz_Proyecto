package com.upchiapas.kt_template.data.datasource

import androidx.compose.runtime.mutableStateOf
import com.upchiapas.kt_template.data.model.Pregunta

class QuizLocalDataSource {

    private val preguntasSemilla = mutableListOf(
        Pregunta(
            id = 1,
            enunciado = "Que lenguaje estamos utilizando principalmente para Android?",
            opciones = listOf("Java", "Kotlin", "Python", "C++"),
            respuestaCorrectaIndex = 1
        ),
        Pregunta(
            id = 2,
            enunciado = "Que componente de Compose se usa para mostrar texto?",
            opciones = listOf("Button", "Column", "Text", "TextField"),
            respuestaCorrectaIndex = 2
        ),
        Pregunta(
            id = 3,
            enunciado = "Que patrón de arquitectura separa la UI del ViewModel?",
            opciones = listOf("MVC", "MVVM", "MVP", "Monolito"),
            respuestaCorrectaIndex = 3
        )
    )

    private val preguntasUsuario = mutableListOf<Pregunta>()

    fun obtenerTodasLasPreguntas(): List<Pregunta> {
        return preguntasSemilla + preguntasUsuario
    }

    fun guardarNuevaPregunta(nuevaPregunta: Pregunta){
        preguntasUsuario.add(nuevaPregunta)
    }
}


