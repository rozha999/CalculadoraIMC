
package com.example.calculadoraimc.data

import androidx.compose.ui.graphics.Color

// Información del usuario
data class DatosPersonales(
    val nombre: String,
    val sexo: String,
    val peso: Double,
    val estatura: Double
)

// Rangos del índice de masa corporal
data class RangoIMC(
    val minimo: Double,
    val maximo: Double?,
    val nombre: String,
    val descripcion: String,
    val color: Color
)

// Resultado del cálculo del IMC
data class ResultadoIMC(
    val datos: DatosPersonales,
    val imc: Double,
    val rango: RangoIMC
)

// Colores de la aplicación
object ColoresIMC {
    val Morado = Color(0xFF6336B5)
    val MoradoOscuro = Color(0xFF39206F)
    val Azul = Color(0xFF3185F5)
    val Fondo = Color(0xFFF5F3FC)
    val Texto = Color(0xFF252044)
    val Gris = Color(0xFF77738D)
    val Blanco = Color.White
    val Verde = Color(0xFF16A36A)
    val Naranja = Color(0xFFF59E0B)
    val Rojo = Color(0xFFE34D68)

    val AzulOscuro = Color(0xFF2563EB)
    val Celeste = Color(0xFF38BDF8)
    val VerdeClaro = Color(0xFF65A30D)
    val Amarillo = Color(0xFFEAB308)
    val NaranjaOscuro = Color(0xFFEA580C)
    val RojoOscuro = Color(0xFFBE123C)
}

// Lista de los 8 rangos del IMC
val rangosIMC = listOf(
    RangoIMC(
        0.0, 16.0,
        "Delgadez severa",
        "Tu peso está muy por debajo del rango saludable",
        ColoresIMC.AzulOscuro
    ),
    RangoIMC(
        16.0, 17.0,
        "Delgadez moderada",
        "Tu peso está por debajo del rango recomendado",
        ColoresIMC.Azul
    ),
    RangoIMC(
        17.0, 18.5,
        "Delgadez leve",
        "Tu peso está ligeramente por debajo del rango normal",
        ColoresIMC.Celeste
    ),
    RangoIMC(
        18.5, 25.0,
        "Peso normal",
        "Tu peso está dentro del rango saludable",
        ColoresIMC.Verde
    ),
    RangoIMC(
        25.0, 30.0,
        "Sobrepeso",
        "Tu peso está por encima del rango saludable",
        ColoresIMC.Amarillo
    ),
    RangoIMC(
        30.0, 35.0,
        "Obesidad grado I",
        "Tu IMC corresponde al primer grado de obesidad",
        ColoresIMC.Naranja
    ),
    RangoIMC(
        35.0, 40.0,
        "Obesidad grado II",
        "Tu IMC corresponde al segundo grado de obesidad",
        ColoresIMC.NaranjaOscuro
    ),
    RangoIMC(
        40.0, null,
        "Obesidad grado III",
        "Tu IMC corresponde al tercer grado de obesidad",
        ColoresIMC.Rojo
    )
)

// Buscar el rango correspondiente al IMC
fun obtenerRangoIMC(imc: Double): RangoIMC? {
    return rangosIMC.lastOrNull { rango ->
        imc >= rango.minimo &&
                (rango.maximo == null || imc < rango.maximo)
    }
}
