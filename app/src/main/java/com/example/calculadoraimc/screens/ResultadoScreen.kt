
package com.example.calculadoraimc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.R
import com.example.calculadoraimc.data.ColoresIMC
import com.example.calculadoraimc.data.ResultadoIMC

// --------------------------------------------------
// PANTALLA PRINCIPAL
// --------------------------------------------------

@Composable
fun ResultadoScreen(
    resultado: ResultadoIMC,
    onVolver: () -> Unit
) {
    val esHombre = resultado.datos.sexo
        .lowercase()
        .contains("hombre")

    val imagenResultado = obtenerImagenResultado(
        imc = resultado.imc,
        esHombre = esHombre
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColoresIMC.Fondo)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        EncabezadoResultado()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TarjetaResultado(
                resultado = resultado,
                imagenResultado = imagenResultado
            )

            TarjetaRangosIMC(
                imc = resultado.imc
            )

            AvisoSalud()

            BotonVolver(
                onVolver = onVolver
            )

            PieDePaginaResultado()
        }
    }
}

// --------------------------------------------------
// SELECCIÓN DE IMAGEN
// --------------------------------------------------

private fun obtenerImagenResultado(
    imc: Double,
    esHombre: Boolean
): Int {
    return when {
        imc < 18.5 -> {
            if (esHombre) {
                R.drawable.hombre_bajo_peso
            } else {
                R.drawable.mujer_bajo_peso
            }
        }

        imc < 25.0 -> {
            if (esHombre) {
                R.drawable.hombre_normal
            } else {
                R.drawable.mujer_normal
            }
        }

        imc < 30.0 -> {
            if (esHombre) {
                R.drawable.hombre_sobrepeso
            } else {
                R.drawable.mujer_sobrepeso
            }
        }

        else -> {
            if (esHombre) {
                R.drawable.hombre_obesidad
            } else {
                R.drawable.mujer_obesidad
            }
        }
    }
}

// --------------------------------------------------
// ENCABEZADO
// --------------------------------------------------

@Composable
private fun EncabezadoResultado() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        ColoresIMC.Morado,
                        ColoresIMC.MoradoOscuro
                    )
                )
            )
            .padding(vertical = 17.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )

            Spacer(Modifier.height(5.dp))

            Text(
                "BMI HEALTH",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Text(
                "Tu resultado IMC",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// --------------------------------------------------
// TARJETA PRINCIPAL DEL RESULTADO
// --------------------------------------------------

@Composable
private fun TarjetaResultado(
    resultado: ResultadoIMC,
    imagenResultado: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                text = "Hola, ${resultado.datos.nombre}",
                color = ColoresIMC.Texto,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DatosVisualesResultado(
                    resultado = resultado
                )

                ImagenResultado(
                    imagenResultado = imagenResultado
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                resultado.rango.descripcion,
                color = ColoresIMC.Gris,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            HorizontalDivider(
                color = ColoresIMC.Fondo
            )

            Spacer(Modifier.height(10.dp))

            DatosPersonalesResultado(
                resultado = resultado
            )
        }
    }
}

// --------------------------------------------------
// DATOS VISUALES DEL RESULTADO
// --------------------------------------------------

@Composable
private fun RowScope.DatosVisualesResultado(
    resultado: ResultadoIMC
) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(
                    resultado.rango.color.copy(alpha = 0.12f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    String.format("%.1f", resultado.imc),
                    color = resultado.rango.color,
                    fontSize = 29.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    "IMC",
                    color = ColoresIMC.Gris,
                    fontSize = 11.sp
                )
            }
        }

        Spacer(Modifier.height(9.dp))

        Surface(
            color = resultado.rango.color.copy(alpha = 0.13f),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text(
                resultado.rango.nombre,
                color = resultado.rango.color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    horizontal = 10.dp,
                    vertical = 7.dp
                )
            )
        }
    }
}

// --------------------------------------------------
// IMAGEN DEL RESULTADO
// --------------------------------------------------

@Composable
private fun RowScope.ImagenResultado(
    imagenResultado: Int
) {
    Image(
        painter = painterResource(id = imagenResultado),
        contentDescription = "Resultado del IMC",
        modifier = Modifier
            .weight(1f)
            .height(145.dp),
        contentScale = ContentScale.Fit
    )
}

// --------------------------------------------------
// PESO Y ESTATURA
// --------------------------------------------------

@Composable
private fun DatosPersonalesResultado(
    resultado: ResultadoIMC
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DatoResultado(
            "Peso",
            "${resultado.datos.peso} kg"
        )

        DatoResultado(
            "Estatura",
            "${resultado.datos.estatura} cm"
        )
    }
}

// --------------------------------------------------
// TARJETA DE LOS 8 RANGOS
// --------------------------------------------------

@Composable
private fun TarjetaRangosIMC(
    imc: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            BarraRangoIMC(imc)
        }
    }
}

// --------------------------------------------------
// BARRA DE RANGOS IMC
// --------------------------------------------------

@Composable
private fun BarraRangoIMC(
    imc: Double
) {
    val colores = listOf(
        Color(0xFF2563EB),
        Color(0xFF3185F5),
        Color(0xFF38BDF8),
        Color(0xFF16A36A),
        Color(0xFFEAB308),
        Color(0xFFF59E0B),
        Color(0xFFEA580C),
        Color(0xFFBE123C)
    )

    val nombres = listOf(
        "Severa",
        "Moderada",
        "Leve",
        "Normal",
        "Sobrepeso",
        "Obesidad I",
        "Obesidad II",
        "Obesidad III"
    )

    // Escala visual de 15 a 45
    val posicion = ((imc - 15.0) / 30.0)
        .coerceIn(0.0, 1.0)

    Text(
        "Tu posición en la escala del IMC",
        color = ColoresIMC.Texto,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(5.dp))

    Text(
        "Clasificación en 8 rangos",
        color = ColoresIMC.Gris,
        fontSize = 11.sp
    )

    Spacer(Modifier.height(14.dp))

    BarraColores(
        colores = colores,
        posicion = posicion
    )

    Spacer(Modifier.height(10.dp))

    EtiquetasRangos(
        nombres = nombres.take(4),
        colores = colores.take(4)
    )

    Spacer(Modifier.height(8.dp))

    EtiquetasRangos(
        nombres = nombres.drop(4),
        colores = colores.drop(4)
    )

    Spacer(Modifier.height(10.dp))

    Text(
        "Tu IMC: ${String.format("%.1f", imc)}",
        color = ColoresIMC.Morado,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

// --------------------------------------------------
// BARRA DE COLORES Y MARCADOR
// --------------------------------------------------

@Composable
private fun BarraColores(
    colores: List<Color>,
    posicion: Double
) {
    val pesos = listOf(
        1f, 1f, 1.5f, 6.5f,
        5f, 5f, 5f, 5f
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(29.dp)
    ) {
        val ancho = maxWidth

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            colores.forEachIndexed { index, color ->
                Box(
                    modifier = Modifier
                        .weight(pesos[index])
                        .fillMaxHeight()
                        .background(
                            color,
                            RoundedCornerShape(4.dp)
                        )
                )
            }
        }

        Box(
            modifier = Modifier
                .offset(
                    x = (ancho - 4.dp) * posicion.toFloat()
                )
                .width(4.dp)
                .height(27.dp)
                .background(
                    ColoresIMC.Texto,
                    RoundedCornerShape(4.dp)
                )
        )
    }
}

// --------------------------------------------------
// ETIQUETAS DE LOS RANGOS
// --------------------------------------------------

@Composable
private fun EtiquetasRangos(
    nombres: List<String>,
    colores: List<Color>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        nombres.forEachIndexed { index, nombre ->
            EtiquetaRango(
                nombre = nombre,
                color = colores[index],
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// --------------------------------------------------
// ETIQUETA INDIVIDUAL
// --------------------------------------------------

@Composable
private fun RowScope.EtiquetaRango(
    nombre: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )

        Spacer(Modifier.height(3.dp))

        Text(
            text = nombre,
            color = color,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 10.sp
        )
    }
}

// --------------------------------------------------
// AVISO DE SALUD
// --------------------------------------------------

@Composable
private fun AvisoSalud() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.Favorite,
            contentDescription = null,
            tint = ColoresIMC.Morado,
            modifier = Modifier.size(22.dp)
        )

        Spacer(Modifier.width(10.dp))

        Text(
            "El IMC es una referencia general. " +
                    "Consulta con un profesional para una evaluación completa.",
            color = ColoresIMC.Gris,
            fontSize = 11.sp,
            lineHeight = 16.sp
        )
    }
}

// --------------------------------------------------
// BOTÓN PARA VOLVER
// --------------------------------------------------

@Composable
private fun BotonVolver(
    onVolver: () -> Unit
) {
    Button(
        onClick = onVolver,
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ColoresIMC.Morado
        )
    ) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = null
        )

        Spacer(Modifier.width(8.dp))

        Text(
            "Volver a calcular",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// --------------------------------------------------
// PIE DE PÁGINA
// --------------------------------------------------

@Composable
private fun PieDePaginaResultado() {
    Text(
        "BMI HEALTH • Cuida tu salud",
        color = ColoresIMC.Gris,
        fontSize = 10.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
    )
}

// --------------------------------------------------
// DATO INDIVIDUAL
// --------------------------------------------------

@Composable
private fun DatoResultado(
    titulo: String,
    valor: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            titulo,
            color = ColoresIMC.Gris,
            fontSize = 11.sp
        )

        Spacer(Modifier.height(3.dp))

        Text(
            valor,
            color = ColoresIMC.Texto,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
