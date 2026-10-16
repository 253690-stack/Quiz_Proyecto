package com.upchiapas.kt_template.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upchiapas.kt_template.presentation.viewmodel.QuizViewModel

@Composable
fun QuizScreen(viewModel: QuizViewModel) {
    val preguntas = viewModel.listaPreguntas
    val indice = viewModel.indiceActual
    val terminado = viewModel.quizTerminado
    val puntaje = viewModel.puntaje

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (preguntas.isEmpty()) {
                Text(text = "Cargando preguntas...", fontSize = 18.sp)
            } else if (terminado) {
                // Pantalla de resultados
                Text(text = "¡Quiz Finalizado!", fontSize = 26.sp, color = Color.Blue)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Tu Puntaje Final: $puntaje", fontSize = 22.sp)
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = { viewModel.reiniciarQuiz() }) {
                    Text(text = "Volver a Jugar")
                }
            } else {
                // Pantalla de Pregunta Actual
                val preguntaActual = preguntas[indice]

                Text(
                    text = "Pregunta ${indice + 1} de ${preguntas.size}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = preguntaActual.enunciado,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Opciones de respuesta
                preguntaActual.opciones.forEachIndexed { index, opcion ->
                    Button(
                        onClick = { viewModel.seleccionarRespuesta(index) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(text = opcion, fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Puntaje actual: $puntaje", fontSize = 16.sp, color = Color.DarkGray)
            }
        }
    }
}