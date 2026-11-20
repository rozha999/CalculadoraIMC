
package com.example.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculadoraimc.data.ResultadoIMC
import com.example.calculadoraimc.screens.FormularioScreen
import com.example.calculadoraimc.screens.ResultadoScreen
import com.example.calculadoraimc.screens.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalculadoraIMCApp()
        }
    }
}

@Composable
fun CalculadoraIMCApp() {

    var pantallaActual by remember {
        mutableStateOf("welcome")
    }

    var resultadoIMC by remember {
        mutableStateOf<ResultadoIMC?>(null)
    }

    when (pantallaActual) {

        "welcome" -> {
            WelcomeScreen(
                onComenzar = {
                    pantallaActual = "formulario"
                }
            )
        }

        "formulario" -> {
            FormularioScreen(
                onResultado = { resultado ->
                    resultadoIMC = resultado
                    pantallaActual = "resultado"
                }
            )
        }

        "resultado" -> {
            resultadoIMC?.let { resultado ->
                ResultadoScreen(
                    resultado = resultado,
                    onVolver = {
                        pantallaActual = "formulario"
                    }
                )
            }
        }
    }
}
