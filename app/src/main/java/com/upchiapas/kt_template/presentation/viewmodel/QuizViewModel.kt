package com.upchiapas.kt_template.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.upchiapas.kt_template.data.model.Pregunta
import com.upchiapas.kt_template.data.repository.QuizRepository

class QuizViewModel(
    private val repository: QuizRepository // Inyección del Repositorio
) : ViewModel() {

    // Variables de estado
    var listaPreguntas by mutableStateOf<List<Pregunta>>(emptyList())
    var indiceActual by mutableStateOf(0)
    var puntaje by mutableStateOf(0)
    var quizTerminado by mutableStateOf(false)

    init {
        cargarPreguntas()
    }

    fun cargarPreguntas() {
        listaPreguntas = repository.obtenerPreguntas()
    }

    // Validar respuesta seleccionada
    fun seleccionarRespuesta(indexOpcionSeleccionada: Int) {
        val preguntaActual = listaPreguntas.getOrNull(indiceActual) ?: return

        if (indexOpcionSeleccionada == preguntaActual.respuestaCorrectaIndex) {
            puntaje += 10 // Puntos por respuesta correcta
        }

        // Avanzar a la siguiente pregunta o finalizar
        if (indiceActual < listaPreguntas.size - 1) {
            indiceActual++
        } else {
            quizTerminado = true
        }
    }

    // Reiniciar el quiz
    fun reiniciarQuiz() {
        indiceActual = 0
        puntaje = 0
        quizTerminado = false
        cargarPreguntas()
    }

    // Registrar una nueva pregunta creada por el usuario
    fun agregarPreguntaUsuario(enunciado: String, opciones: List<String>, correcta: Int) {
        val nueva = Pregunta(
            id = listaPreguntas.size + 1,
            enunciado = enunciado,
            opciones = opciones,
            respuestaCorrectaIndex = correcta
        )
        repository.registrarPregunta(nueva)
        cargarPreguntas() // Recargamos para incluir la nueva pregunta
    }
}