
package com.example.calculadoraimc.data

sealed class ValidationResult {
    data class Success(
        val resultado: ResultadoIMC
    ) : ValidationResult()

    data class Error(
        val mensaje: String
    ) : ValidationResult()
}

object Validation {

    fun validarYCalcular(
        nombre: String,
        sexo: String,
        peso: String,
        estatura: String
    ): ValidationResult {

        // Validar nombre
        if (nombre.isBlank()) {
            return ValidationResult.Error(
                "Introduce tu nombre"
            )
        }

        // Convertir y validar peso
        val pesoDouble = peso
            .replace(",", ".")
            .toDoubleOrNull()

        if (pesoDouble == null || pesoDouble !in 1.0..500.0) {
            return ValidationResult.Error(
                "Introduce un peso válido (1-500 kg)"
            )
        }

        // Convertir y validar estatura
        val estaturaDouble = estatura
            .replace(",", ".")
            .toDoubleOrNull()

        if (estaturaDouble == null || estaturaDouble !in 50.0..250.0) {
            return ValidationResult.Error(
                "Introduce una estatura válida (50-250 cm)"
            )
        }

        // Calcular IMC
        val estaturaMetros = estaturaDouble / 100.0
        val imc = pesoDouble / (estaturaMetros * estaturaMetros)

        // Obtener categoría del IMC
        val rango = obtenerRangoIMC(imc)
            ?: return ValidationResult.Error(
                "No se pudo calcular el IMC"
            )

        // Crear datos del usuario
        val datos = DatosPersonales(
            nombre = nombre.trim(),
            sexo = sexo,
            peso = pesoDouble,
            estatura = estaturaDouble
        )

        // Devolver resultado
        return ValidationResult.Success(
            ResultadoIMC(
                datos = datos,
                imc = imc,
                rango = rango
            )
        )
    }
}
