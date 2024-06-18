package com.istea.appdelclima.repository

import com.istea.appdelclima.repository.modelos.Ciudad
import com.istea.appdelclima.repository.modelos.Clima
import com.istea.appdelclima.repository.modelos.Clima2
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.request
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.net.URLEncoder

class RepositorioApi : Repositorio {

    private val apiKey = "8e0af9a65e0f0b0663509216ea77440d"

    private val cliente = HttpClient(){
        install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun buscarCiudad(ciudad: String): Array<Ciudad> {
        println("JOJO: Buscar ciudad")

        try {

            val respuesta = cliente.get("https://api.openweathermap.org/geo/1.0/direct") {
                parameter("q", ciudad)
                parameter("limit", 5)
                parameter("appid", apiKey)
            }

            if (respuesta.status == HttpStatusCode.OK) {
                println("JOJO: Status OK")
                return respuesta.body() ?: emptyArray()
            } else {
                println("JOJO: Status no ok: ${respuesta.status}")
                throw Exception("Error al buscar ciudades: ${respuesta.status}")
            }
        } catch (e: Exception) {
            println("JOJO: Excepción al buscar ciudad: ${e.message}")
            throw e
        }
    }
    override suspend fun traerClima(ciudad: Ciudad): Clima2 {
        val respuesta = cliente.get("https://api.openweathermap.org/data/2.5/weather"){
            // parameter("lat",ciudad.lat)
            // parameter("lon",ciudad.lon)
            parameter("units","metric")
            parameter("q", "${ciudad.name}")  // ,${ciudad.state} Utiliza el nombre y el estado para buscar la ciudad
            parameter("appid",apiKey)
        }
        if (respuesta.status == HttpStatusCode.OK) {
            return respuesta.body() ?: throw Exception("Respuesta nula al obtener el clima")
        } else {
            throw Exception("Error al obtener el clima: ${respuesta.status}")
        }
    }

    override suspend fun traerPronostico(ciudad: Ciudad): List<Clima2> {

        val respuesta = cliente.get("https://api.openweathermap.org/data/2.5/forecast?") {
            parameter("q", "${ciudad.name}")
            parameter("appid", apiKey)
        }
        if (respuesta.status == HttpStatusCode.OK) {
            return respuesta.body() ?: throw Exception("Respuesta nula al obtener el clima")
        } else {
            throw Exception("Error al obtener el pronóstico: ${respuesta.status}")
        }

    }

}


