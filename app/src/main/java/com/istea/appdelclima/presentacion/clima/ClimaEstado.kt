package com.istea.appdelclima.presentacion.clima

import com.istea.appdelclima.repository.modelos.Clima2

sealed class ClimaEstado {
    data class Exitoso (
        val ciudad: String = "",
        val temperatura: Double = 0.0,
        val descripcion: String= "",
        val st :Double = 0.0,
        //val pronostico: List<Clima2> = emptyList() //agrego el pronostico aca para q lo tome
    ) : ClimaEstado()
    data class Error(
        val mensaje :String = "",
    ) : ClimaEstado()

    data class ExitosoPronostico(val pronostico: List<Clima2>) : ClimaEstado()
    data object Vacio: ClimaEstado()
    data object Cargando: ClimaEstado()

}
