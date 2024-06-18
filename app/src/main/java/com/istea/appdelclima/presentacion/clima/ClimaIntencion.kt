package com.istea.appdelclima.presentacion.clima

sealed class ClimaIntencion {
    object BorrarTodo: ClimaIntencion()
    object MostrarCordoba: ClimaIntencion()
    object MostrarCaba: ClimaIntencion()
    object MostrarError: ClimaIntencion()
    data class MostrarCiudad(val ciudad: String) : ClimaIntencion()
    data class MostrarPronostico(val ciudad: String) : ClimaIntencion()
}



