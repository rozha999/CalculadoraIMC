
package com.example.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Morado = Color(0xFF6336B5)
private val MoradoOscuro = Color(0xFF39206F)
private val Azul = Color(0xFF3185F5)
private val Fondo = Color(0xFFF5F3FC)
private val Texto = Color(0xFF252044)
private val Gris = Color(0xFF77738D)
private val Blanco = Color.White
private val Verde = Color(0xFF16A36A)
private val Naranja = Color(0xFFF59E0B)
private val Rojo = Color(0xFFE34D68)

data class RangoIMC(
    val minimo: Double,
    val maximo: Double?,
    val nombre: String,
    val descripcion: String
)

val rangosIMC = listOf(
    RangoIMC(0.0, 18.5, "Bajo peso", "Tu peso está por debajo de lo normal"),
    RangoIMC(18.5, 25.0, "Peso normal", "Tu peso está dentro del rango saludable"),
    RangoIMC(25.0, 30.0, "Sobrepeso", "Tu peso está por encima de lo normal"),
    RangoIMC(30.0, null, "Obesidad", "Tu peso está muy por encima de lo normal")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Morado,
                    secondary = Azul,
                    background = Fondo
                )
            ) {
                var mostrarInicio by remember { mutableStateOf(true) }

                if (mostrarInicio) {
                    WelcomeScreen {
                        mostrarInicio = false
                    }
                } else {
                    CalculadoraScreen()
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.bmi),
            contentDescription = "Fondo de bienvenida",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        MoradoOscuro.copy(alpha = 0.9f)
                    )
                )
            )
        )

        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Surface(
                color = Morado.copy(alpha = 0.88f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "¡Bienvenido a Calculadora IMC!",
                    modifier = Modifier.padding(
                        horizontal = 18.dp,
                        vertical = 12.dp
                    ),
                    color = Blanco,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(14.dp))

            Text(
                "Calcula tu índice de masa corporal de forma fácil y rápida.",
                fontSize = 16.sp,
                color = Blanco,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(25.dp))

            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Azul
                )
            ) {
                Text(
                    "Comenzar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(18.dp))
        }
    }
}

@Composable
fun CalculadoraScreen() {
    var nombre by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("Hombre") }
    var peso by remember { mutableStateOf("") }
    var estatura by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf<Double?>(null) }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Fondo)
            .verticalScroll(rememberScrollState())
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 20.dp,
                bottom = 10.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ENCABEZADO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(MoradoOscuro, Morado, Azul)
                    )
                )
                .padding(horizontal = 17.dp, vertical = 13.dp)
        ) {
            Column {
                Text(
                    "BMI HEALTH",
                    color = Color(0xFFE7DDFF),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )

                Text(
                    "Calculadora IMC",
                    color = Blanco,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Cuida tu salud, empieza hoy",
                    color = Blanco,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // FORMULARIO
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Blanco
            ),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(Modifier.padding(14.dp)) {

                Text(
                    "Datos personales",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Morado
                )

                Spacer(Modifier.height(8.dp))

                CampoTexto(
                    titulo = "Nombre",
                    valor = nombre,
                    alCambiar = {
                        nombre = it
                        resultado = null
                        error = ""
                    },
                    placeholder = "Escribe tu nombre",
                    tipo = KeyboardType.Text
                )

                Spacer(Modifier.height(9.dp))

                Text(
                    "Selecciona tu sexo",
                    color = Texto,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SexoCard(
                        "Hombre",
                        sexo == "Hombre",
                        Modifier.weight(1f)
                    ) {
                        sexo = "Hombre"
                        resultado = null
                    }

                    SexoCard(
                        "Mujer",
                        sexo == "Mujer",
                        Modifier.weight(1f)
                    ) {
                        sexo = "Mujer"
                        resultado = null
                    }
                }

                Spacer(Modifier.height(9.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    CampoTexto(
                        titulo = "Peso (kg)",
                        valor = peso,
                        alCambiar = {
                            peso = it
                            resultado = null
                            error = ""
                        },
                        placeholder = "70",
                        tipo = KeyboardType.Decimal,
                        modifier = Modifier.weight(1f)
                    )

                    CampoTexto(
                        titulo = "Estatura (cm)",
                        valor = estatura,
                        alCambiar = {
                            estatura = it
                            resultado = null
                            error = ""
                        },
                        placeholder = "175",
                        tipo = KeyboardType.Decimal,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(13.dp))

                Button(
                    onClick = {
                        val p = peso.replace(",", ".").toDoubleOrNull()
                        val e = estatura.replace(",", ".").toDoubleOrNull()

                        when {
                            nombre.isBlank() -> {
                                error = "Introduce tu nombre"
                                resultado = null
                            }

                            p == null || p !in 1.0..500.0 -> {
                                error = "Introduce un peso válido (1-500 kg)"
                                resultado = null
                            }

                            e == null || e !in 50.0..250.0 -> {
                                error = "Introduce una estatura válida (50-250 cm)"
                                resultado = null
                            }

                            else -> {
                                error = ""
                                val metros = e / 100.0
                                resultado = p / (metros * metros)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Morado
                    )
                ) {
                    Text(
                        "Calcular mi IMC",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (error.isNotEmpty()) {
                    Spacer(Modifier.height(7.dp))
                    Text(
                        error,
                        color = Rojo,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // RESULTADO
        resultado?.let { imc ->

            val rango = rangosIMC.lastOrNull {
                imc >= it.minimo &&
                        (it.maximo == null || imc < it.maximo)
            }

            val colorRango = when (rango?.nombre) {
                "Bajo peso" -> Azul
                "Peso normal" -> Verde
                "Sobrepeso" -> Naranja
                else -> Rojo
            }

            val imagen = when (sexo) {
                "Hombre" -> when (rango?.nombre) {
                    "Bajo peso" -> R.drawable.hombre_bajo_peso
                    "Peso normal" -> R.drawable.hombre_normal
                    "Sobrepeso" -> R.drawable.hombre_sobrepeso
                    else -> R.drawable.hombre_obesidad
                }

                else -> when (rango?.nombre) {
                    "Bajo peso" -> R.drawable.mujer_bajo_peso
                    "Peso normal" -> R.drawable.mujer_normal
                    "Sobrepeso" -> R.drawable.mujer_sobrepeso
                    else -> R.drawable.mujer_obesidad
                }
            }

            Spacer(Modifier.height(13.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Blanco
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(Modifier.padding(14.dp)) {

                    Text(
                        "TU RESULTADO",
                        color = Morado,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        fontSize = 12.sp
                    )

                    Spacer(Modifier.height(7.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // IZQUIERDA: RESULTADO Y NOMBRE
                        Column(
                            modifier = Modifier.weight(1.1f)
                        ) {
                            Text(
                                "Hola, $nombre",
                                color = Texto,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )

                            Spacer(Modifier.height(5.dp))

                            Text(
                                String.format("%.1f", imc),
                                color = Morado,
                                fontSize = 34.sp,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Text(
                                "Índice IMC",
                                color = Gris,
                                fontSize = 11.sp
                            )

                            Spacer(Modifier.height(8.dp))

                            Box(
                                Modifier
                                    .clip(RoundedCornerShape(30.dp))
                                    .background(colorRango.copy(alpha = 0.13f))
                                    .padding(
                                        horizontal = 10.dp,
                                        vertical = 6.dp
                                    )
                            ) {
                                Text(
                                    rango?.nombre ?: "",
                                    color = colorRango,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }

                            Spacer(Modifier.height(6.dp))

                            Text(
                                rango?.descripcion ?: "",
                                color = Gris,
                                fontSize = 10.sp,
                                lineHeight = 13.sp
                            )
                        }

                        Spacer(Modifier.width(4.dp))

                        // DERECHA: IMAGEN
                        Image(
                            painter = painterResource(imagen),
                            contentDescription = "Resultado corporal",
                            modifier = Modifier
                                .weight(0.9f)
                                .height(145.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    HorizontalDivider(color = Color(0xFFECE8F5))

                    Spacer(Modifier.height(9.dp))

                    Text(
                        "Indicador de IMC",
                        fontWeight = FontWeight.Bold,
                        color = Texto,
                        fontSize = 12.sp
                    )

                    Spacer(Modifier.height(7.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(20.dp))
                    ) {
                        Box(
                            Modifier.weight(1f).fillMaxHeight().background(Azul)
                        )
                        Box(
                            Modifier.weight(1f).fillMaxHeight().background(Verde)
                        )
                        Box(
                            Modifier.weight(1f).fillMaxHeight().background(Naranja)
                        )
                        Box(
                            Modifier.weight(1f).fillMaxHeight().background(Rojo)
                        )
                    }

                    Spacer(Modifier.height(5.dp))

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Bajo", color = Gris, fontSize = 9.sp)
                        Text("Normal", color = Gris, fontSize = 9.sp)
                        Text("Sobrepeso", color = Gris, fontSize = 9.sp)
                        Text("Obesidad", color = Gris, fontSize = 9.sp)
                    }
                }
            }
        }

        Spacer(Modifier.height(15.dp))
    }
}

@Composable
fun CampoTexto(
    titulo: String,
    valor: String,
    alCambiar: (String) -> Unit,
    placeholder: String,
    tipo: KeyboardType,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = valor,
            onValueChange = alCambiar,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    titulo,
                    fontSize = 12.sp
                )
            },
            placeholder = {
                Text(
                    placeholder,
                    fontSize = 12.sp
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = tipo
            ),
            shape = RoundedCornerShape(12.dp),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Morado,
                unfocusedBorderColor = Color(0xFFE2DDED),
                focusedLabelColor = Morado,
                unfocusedLabelColor = Gris,
                focusedContainerColor = Color(0xFFFCFBFF),
                unfocusedContainerColor = Color(0xFFFCFBFF),
                cursorColor = Morado
            )
        )
    }
}

@Composable
fun SexoCard(
    texto: String,
    seleccionado: Boolean,
    modifier: Modifier = Modifier,
    alClick: () -> Unit
) {
    val fondo = if (seleccionado) {
        Color(0xFFEDE5FF)
    } else {
        Color(0xFFFAF9FD)
    }

    val borde = if (seleccionado) {
        Morado
    } else {
        Color(0xFFE5E0ED)
    }

    Row(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(fondo)
            .border(1.5.dp, borde, RoundedCornerShape(12.dp))
            .clickable { alClick() }
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.Person,
            contentDescription = null,
            tint = if (seleccionado) Morado else Gris,
            modifier = Modifier.size(21.dp)
        )

        Spacer(Modifier.width(5.dp))

        Text(
            texto,
            color = if (seleccionado) Morado else Texto,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}
