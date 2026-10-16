package com.upchiapas.kt_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.upchiapas.kt_template.data.datasource.QuizLocalDataSource
import com.upchiapas.kt_template.data.repository.QuizRepository
import com.upchiapas.kt_template.presentation.screen.QuizScreen
import com.upchiapas.kt_template.presentation.viewmodel.QuizViewModel
import com.upchiapas.kt_template.ui.theme.Kt_templateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ensamblamos las dependencias manualmente respetando la arquitectura
        val dataSource = QuizLocalDataSource()
        val repository = QuizRepository(dataSource)
        val viewModel = QuizViewModel(repository)

        setContent {
            Kt_templateTheme {
                QuizScreen(viewModel = viewModel)
            }
        }
    }
}