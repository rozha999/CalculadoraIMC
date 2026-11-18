# Calculadora IMC

## Descripción del proyecto

Calculadora IMC es una aplicación móvil para Android desarrollada con Kotlin y Jetpack Compose. Permite calcular el Índice de Masa Corporal (IMC) de una persona a partir de su peso y estatura, mostrando el resultado y su clasificación correspondiente.

## Funcionalidades principales

- Pantalla de bienvenida con botón para comenzar.
- Introducción del nombre del usuario.
- Selección del sexo: hombre o mujer.
- Introducción del peso en kilogramos.
- Introducción de la estatura en centímetros.
- Cálculo del Índice de Masa Corporal (IMC).
- Visualización del resultado numérico.
- Clasificación del IMC según los valores obtenidos.
- Visualización de una imagen según el sexo y la categoría del IMC.
- Mensajes descriptivos para cada categoría.
- Validación de los datos introducidos.

## Fórmula del IMC

La aplicación utiliza la siguiente fórmula:

**IMC = Peso (kg) / Estatura² (m)**

La estatura introducida en centímetros se convierte a metros antes de realizar el cálculo.

## Clasificación del IMC

Para clasificar los resultados se utiliza una lista inmutable `listOf()`.

| Valor del IMC | Clasificación |
|---|---|
| Menor de 18,5 | Bajo peso |
| Desde 18,5 hasta menos de 25 | Peso normal |
| Desde 25 hasta menos de 30 | Sobrepeso |
| 30 o más | Obesidad |

## Validación de datos

La aplicación comprueba que:

- El nombre no esté vacío.
- El peso sea un número válido entre 1 y 500 kg.
- La estatura sea un número válido entre 50 y 250 cm.

## Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material Design 3

## Recursos gráficos

La aplicación utiliza imágenes ilustrativas para las diferentes categorías del IMC, tanto para hombres como para mujeres.

Los archivos gráficos se encuentran en:

`app/src/main/res/drawable/`

Imágenes utilizadas:

- `bmi.jpg`
- `hombre_bajo_peso.png`
- `hombre_normal.png`
- `hombre_sobrepeso.png`
- `hombre_obesidad.png`
- `mujer_bajo_peso.png`
- `mujer_normal.png`
- `mujer_sobrepeso.png`
- `mujer_obesidad.png`

## Cómo ejecutar el proyecto

1. Abrir el proyecto en Android Studio.
2. Esperar a que finalice la sincronización de Gradle.
3. Ejecutar la aplicación en un emulador o dispositivo Android.
4. Pulsar el botón **Comenzar**.
5. Introducir los datos personales solicitados.
6. Pulsar **Calcular mi IMC** para obtener el resultado.

## Objetivo del proyecto

El objetivo es desarrollar una aplicación Android sencilla, funcional y visualmente atractiva que permita calcular el Índice de Masa Corporal, validar los datos introducidos y presentar los resultados de forma clara e intuitiva.