
package com.example.calculadoraimc.screens

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
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.data.ColoresIMC
import com.example.calculadoraimc.data.ResultadoIMC
import com.example.calculadoraimc.data.Validation
import com.example.calculadoraimc.data.ValidationResult

// --------------------------------------------------
// PANTALLA PRINCIPAL
// --------------------------------------------------

@Composable
fun FormularioScreen(
    onResultado: (ResultadoIMC) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("Hombre") }
    var peso by remember { mutableStateOf("") }
    var estatura by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColoresIMC.Fondo)
            .verticalScroll(rememberScrollState())
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 20.dp,
                bottom = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Encabezado
        EncabezadoFormulario()

        Spacer(modifier = Modifier.height(16.dp))

        // Formulario principal
        FormularioDatosPersonales(
            nombre = nombre,
            sexo = sexo,
            peso = peso,
            estatura = estatura,
            error = error,
            alCambiarNombre = {
                nombre = it
                error = ""
            },
            alCambiarSexo = {
                sexo = it
                error = ""
            },
            alCambiarPeso = {
                peso = it
                error = ""
            },
            alCambiarEstatura = {
                estatura = it
                error = ""
            },
            alCalcular = {
                when (
                    val validacion = Validation.validarYCalcular(
                        nombre = nombre,
                        sexo = sexo,
                        peso = peso,
                        estatura = estatura
                    )
                ) {
                    is ValidationResult.Success -> {
                        error = ""
                        onResultado(validacion.resultado)
                    }

                    is ValidationResult.Error -> {
                        error = validacion.mensaje
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tarjeta de guía
        TarjetaGuia()

        Spacer(modifier = Modifier.height(14.dp))

        // Tarjeta de salud
        TarjetaSalud()

        Spacer(modifier = Modifier.height(18.dp))

        // Pie de página
        PieDePagina()

        Spacer(modifier = Modifier.height(8.dp))
    }
}

// --------------------------------------------------
// ENCABEZADO
// --------------------------------------------------

@Composable
private fun EncabezadoFormulario() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(
                        ColoresIMC.MoradoOscuro,
                        ColoresIMC.Morado,
                        ColoresIMC.Azul
                    )
                )
            )
            .padding(18.dp)
    ) {
        Column {

            Text(
                text = "BMI HEALTH",
                color = Color(0xFFE7DDFF),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Calculadora IMC",
                color = ColoresIMC.Blanco,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Cuida tu salud, empieza hoy",
                color = ColoresIMC.Blanco,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                color = Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "TU SALUD ES LO MÁS IMPORTANTE",
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 7.dp
                    )
                )
            }
        }
    }
}

// --------------------------------------------------
// FORMULARIO DE DATOS PERSONALES
// --------------------------------------------------

@Composable
private fun FormularioDatosPersonales(
    nombre: String,
    sexo: String,
    peso: String,
    estatura: String,
    error: String,
    alCambiarNombre: (String) -> Unit,
    alCambiarSexo: (String) -> Unit,
    alCambiarPeso: (String) -> Unit,
    alCambiarEstatura: (String) -> Unit,
    alCalcular: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = ColoresIMC.Blanco
        ),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Datos personales",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = ColoresIMC.Morado
            )

            Text(
                text = "Completa tus datos para calcular tu IMC",
                fontSize = 11.sp,
                color = ColoresIMC.Gris
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Nombre
            CampoTexto(
                titulo = "Nombre",
                valor = nombre,
                alCambiar = alCambiarNombre,
                placeholder = "Escribe tu nombre",
                tipo = KeyboardType.Text
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Selección de sexo
            SelectorSexo(
                sexo = sexo,
                alCambiarSexo = alCambiarSexo
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Peso y estatura
            CamposMedidas(
                peso = peso,
                estatura = estatura,
                alCambiarPeso = alCambiarPeso,
                alCambiarEstatura = alCambiarEstatura
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón y mensaje de error
            BotonCalcular(
                error = error,
                alCalcular = alCalcular
            )
        }
    }
}

// --------------------------------------------------
// SELECTOR DE SEXO
// --------------------------------------------------

@Composable
private fun SelectorSexo(
    sexo: String,
    alCambiarSexo: (String) -> Unit
) {

    Column {

        Text(
            text = "Selecciona tu sexo",
            color = ColoresIMC.Texto,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(7.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SexoCard(
                texto = "Hombre",
                seleccionado = sexo == "Hombre",
                modifier = Modifier.weight(1f)
            ) {
                alCambiarSexo("Hombre")
            }

            SexoCard(
                texto = "Mujer",
                seleccionado = sexo == "Mujer",
                modifier = Modifier.weight(1f)
            ) {
                alCambiarSexo("Mujer")
            }
        }
    }
}

// --------------------------------------------------
// CAMPOS DE PESO Y ESTATURA
// --------------------------------------------------

@Composable
private fun CamposMedidas(
    peso: String,
    estatura: String,
    alCambiarPeso: (String) -> Unit,
    alCambiarEstatura: (String) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CampoTexto(
            titulo = "Peso (kg)",
            valor = peso,
            alCambiar = alCambiarPeso,
            placeholder = "70",
            tipo = KeyboardType.Decimal,
            modifier = Modifier.weight(1f)
        )

        CampoTexto(
            titulo = "Estatura (cm)",
            valor = estatura,
            alCambiar = alCambiarEstatura,
            placeholder = "175",
            tipo = KeyboardType.Decimal,
            modifier = Modifier.weight(1f)
        )
    }
}

// --------------------------------------------------
// BOTÓN DE CÁLCULO Y MENSAJE DE ERROR
// --------------------------------------------------

@Composable
private fun BotonCalcular(
    error: String,
    alCalcular: () -> Unit
) {

    Column {

        Button(
            onClick = alCalcular,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ColoresIMC.Morado
            )
        ) {
            Text(
                text = "Calcular mi IMC",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = error,
                color = ColoresIMC.Rojo,
                fontSize = 12.sp
            )
        }
    }
}

// --------------------------------------------------
// CAMPO DE TEXTO REUTILIZABLE
// --------------------------------------------------

@Composable
private fun CampoTexto(
    titulo: String,
    valor: String,
    alCambiar: (String) -> Unit,
    placeholder: String,
    tipo: KeyboardType,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = valor,
        onValueChange = alCambiar,
        modifier = modifier.fillMaxWidth(),
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
            focusedBorderColor = ColoresIMC.Morado,
            unfocusedBorderColor = Color(0xFFE2DDED),
            focusedLabelColor = ColoresIMC.Morado,
            unfocusedLabelColor = ColoresIMC.Gris,
            focusedContainerColor = Color(0xFFFCFBFF),
            unfocusedContainerColor = Color(0xFFFCFBFF),
            cursorColor = ColoresIMC.Morado
        )
    )
}

// --------------------------------------------------
// TARJETA DE SELECCIÓN DE SEXO
// --------------------------------------------------

@Composable
private fun SexoCard(
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
        ColoresIMC.Morado
    } else {
        Color(0xFFE5E0ED)
    }

    Row(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(fondo)
            .border(
                1.5.dp,
                borde,
                RoundedCornerShape(12.dp)
            )
            .clickable {
                alClick()
            }
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            tint = if (seleccionado) {
                ColoresIMC.Morado
            } else {
                ColoresIMC.Gris
            },
            modifier = Modifier.size(21.dp)
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = texto,
            color = if (seleccionado) {
                ColoresIMC.Morado
            } else {
                ColoresIMC.Texto
            },
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

// --------------------------------------------------
// TARJETA DE GUÍA
// --------------------------------------------------

@Composable
private fun TarjetaGuia() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = ColoresIMC.Morado
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Antes de comenzar",
                    color = ColoresIMC.Texto,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            ElementoGuia(
                icono = Icons.Default.MonitorWeight,
                titulo = "Peso corporal",
                descripcion = "Introduce tu peso en kilogramos (kg)."
            )

            Spacer(modifier = Modifier.height(12.dp))

            ElementoGuia(
                icono = Icons.Default.Height,
                titulo = "Estatura",
                descripcion = "Introduce tu altura en centímetros (cm)."
            )
        }
    }
}

// --------------------------------------------------
// ELEMENTO DE LA TARJETA DE GUÍA
// --------------------------------------------------

@Composable
private fun ElementoGuia(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    titulo: String,
    descripcion: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = ColoresIMC.Azul,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column {

            Text(
                text = titulo,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = ColoresIMC.Texto
            )

            Text(
                text = descripcion,
                fontSize = 11.sp,
                color = ColoresIMC.Gris
            )
        }
    }
}

// --------------------------------------------------
// TARJETA DE SALUD
// --------------------------------------------------

@Composable
private fun TarjetaSalud() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEDE5FF)
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = ColoresIMC.Morado,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {

                Text(
                    text = "Un pequeño paso para tu salud",
                    color = ColoresIMC.MoradoOscuro,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Conocer tu IMC puede ayudarte a comprender mejor tu estado corporal.",
                    color = ColoresIMC.Texto,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

// --------------------------------------------------
// PIE DE PÁGINA
// --------------------------------------------------

@Composable
private fun PieDePagina() {

    Text(
        text = "BMI HEALTH  •  CUIDA TU BIENESTAR",
        color = ColoresIMC.Gris,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
    )
}
