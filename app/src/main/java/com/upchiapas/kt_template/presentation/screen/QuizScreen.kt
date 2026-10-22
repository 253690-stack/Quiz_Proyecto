package com.upchiapas.kt_template.presentation.screen

import android.R
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upchiapas.kt_template.presentation.viewmodel.QuizViewModel

@Composable
fun QuizScreen(viewModel: QuizViewModel) {
    val preguntas = viewModel.listaPreguntas
    val indice = viewModel.indiceActual
    val terminado = viewModel.quizTerminado
    val puntaje = viewModel.puntaje

    // Estado local para controlar si se muestra el formulario de agregar pregunta
    var mostrarFormulario by remember { mutableStateOf(false) }

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
            if (mostrarFormulario) {
                // 1. Si el botón activó el formulario, se muestra aquí
                FormularioNuevaPregunta(
                    onGuardar = { enunciado, opciones, correcta ->
                        viewModel.agregarPreguntaUsuario(enunciado, opciones, correcta)
                        mostrarFormulario = false // Ocultamos el formulario al guardar
                    },
                    onCancelar = {
                        mostrarFormulario = false // Ocultamos si cancela
                    }
                )
            } else if (preguntas.isEmpty()) {
                Text(text = "Cargando preguntas...", fontSize = 18.sp)
            } else if (terminado) {
                // Pantalla de resultados
                Text(text = "¡Quiz Finalizado!", fontSize = 26.sp, color = Color.Blue)

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Tu Puntaje Final: $puntaje", fontSize = 22.sp)

                Spacer(modifier = Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        viewModel.reiniciarQuiz() })
                    {
                        Text(text = "Volver a Jugar")
                    }
                    // Botón para agregar pregunta al finalizar
                    OutlinedButton(onClick = {
                            mostrarFormulario = true })
                    {
                        Text("Nueva Pregunta")
                    }
                }
            } else {
                // Pantalla del Juego
                val preguntaActual = preguntas[indice]

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pregunta ${indice + 1} de ${preguntas.size}",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    // BOTÓN ONCLICK PARA MOSTRAR EL FORMULARIO
                    Button(onClick = {
                        mostrarFormulario = true
                    }) {
                        Text("Agregar Pregunta", fontSize = 10.sp)
                    }
                }

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
                        onClick = {
                            viewModel.seleccionarRespuesta(index) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(text = opcion, fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Puntaje actual: $puntaje", fontSize = 16.sp, color = Color.DarkGray, fontFamily = FontFamily.Monospace)
            }
        }
    }
}

@Composable
fun FormularioNuevaPregunta(
    onGuardar: (String, List<String>, Int) -> Unit,
    onCancelar: () -> Unit
) {
    var enunciado by remember { mutableStateOf("") }
    var opc1 by remember { mutableStateOf("") }
    var opc2 by remember { mutableStateOf("") }
    var opc3 by remember { mutableStateOf("") }
    var opc4 by remember { mutableStateOf("") }
    var correctaIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Agregar Nueva Pregunta", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = enunciado,
            onValueChange = { enunciado = it },
            label = { Text("Pregunta") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = opc1,
            onValueChange = { opc1 = it },
            label = { Text("Opción 1") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = opc2,
            onValueChange = { opc2 = it },
            label = { Text("Opción 2") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = opc3,
            onValueChange = { opc3 = it },
            label = { Text("Opción 3") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = opc4,
            onValueChange = { opc4 = it },
            label = { Text("Opción 4") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Pon la respuesta correcta en la primera opción")
            OutlinedTextField(
                value = correctaIndex.toString(),
                onValueChange = { correctaIndex = it.toIntOrNull() ?: 0 },
                modifier = Modifier.width(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onCancelar) {
                Text("Cancelar")
            }
            Button(
                onClick = {
                    if (enunciado.isNotBlank() && opc1.isNotBlank() && opc2.isNotBlank()) {
                        onGuardar(enunciado, listOf(opc1, opc2, opc3, opc4), correctaIndex)
                    }
                },

            ) {
                Text("Guardar")
            }
        }
    }
}