package com.istea.appdelclima.repository.modelos

import kotlinx.serialization.Serializable

data class Clima (
    val temperatura: Int,
    val humedad: Float,
    val ciudad: String,
    val st: Int,
    val viento: Int,
    val latitud: Long,
    val longitud: Long,
    val estado: String
)


@Serializable
data class Clima2(
    val coord: Coord,
    //val weather: List<Weather>,
    val weather: List<ClimaWeather>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val sys: Sys,
    val name: String,
    val cod: Int,
   // val dt_txt: Int
)



@Serializable
data class ClimaWeather(
    val description: String
)

@Serializable
data class Main(
    val temp: Double,
    val feels_like: Double
)



@Serializable
data class Coord(val lon: Double, val lat: Double)

@Serializable
data class Weather(val main: String, val description: String)

//@Serializable
//data class Main(val temp: Double, val feels_like: Double, val pressure: Int, val humidity: Int)

@Serializable
data class Wind(val speed: Double, val deg: Int)

@Serializable
data class Clouds(val all: Int)

@Serializable
data class Sys(val country: String, val sunrise: Long, val sunset: Long)


