package com.istea.appdelclima.presentacion.clima

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.istea.appdelclima.repository.Repositorio
import com.istea.appdelclima.repository.modelos.Ciudad
import com.istea.appdelclima.repository.modelos.Clima2
import com.istea.appdelclima.router.Router
import kotlinx.coroutines.launch
import kotlin.math.log

class ClimaViewModel(
    val repositorio: Repositorio,
    val router: Router
) : ViewModel() {

    var uiState by mutableStateOf<ClimaEstado>(ClimaEstado.Vacio)

    fun ejecutar(intencion: ClimaIntencion) {
        when (intencion) {
            ClimaIntencion.BorrarTodo -> borrarTodo()
            ClimaIntencion.MostrarCaba -> mostrarCaba()
            ClimaIntencion.MostrarCordoba -> mostrarCordoba()
            ClimaIntencion.MostrarError -> mostrarError()
            is ClimaIntencion.MostrarCiudad -> mostrarCiudad(intencion.ciudad)
            is ClimaIntencion.MostrarPronostico -> mostrarPronostico(intencion.ciudad)

        }
    }

    private fun mostrarError() {
        uiState = ClimaEstado.Error("Este es un error de mentiras")
    }

    private fun borrarTodo() {
        uiState = ClimaEstado.Vacio
    }

    private fun mostrarCaba() {
        // Implementar si es necesario
    }

    private fun mostrarCordoba() {
        uiState = ClimaEstado.Cargando
        viewModelScope.launch {
            try {
                val cordoba = Ciudad(name = "buenos aires", state = "AR")
                val clima = repositorio.traerClima(cordoba)

                uiState = ClimaEstado.Exitoso(
                    ciudad = clima.name,
                    temperatura = clima.main.temp,
                    descripcion = clima.weather.firstOrNull()?.description ?: "No disponible",
                    st = clima.main.feels_like,

                )
            } catch (exception: Exception) {
                uiState = ClimaEstado.Error("Error al obtener el clima de buenos aires")
            }
        }
    }


    private fun mostrarCiudad(nombreCiudad: String) {
        uiState = ClimaEstado.Cargando
        viewModelScope.launch {
            try {
                val ciudad = Ciudad(name = nombreCiudad)
                val clima = repositorio.traerClima(ciudad)
                //val pronostico = repositorio.traerPronostico(ciudad)
                uiState = ClimaEstado.Exitoso(
                    ciudad = clima.name,
                    temperatura = clima.main.temp,
                    descripcion = clima.weather.firstOrNull()?.description ?: "No disponible",
                    st = clima.main.feels_like,
                    //pronostico = pronostico

                )
            } catch (exception: Exception) {
                uiState = ClimaEstado.Error("Error al obtener el clima de $nombreCiudad")
            }
        }
    }


    private fun mostrarPronostico(nombreCiudad: String) {
        uiState = ClimaEstado.Cargando
        viewModelScope.launch {
            try {
                val ciudad = Ciudad(name = nombreCiudad)
                val pronostico = repositorio.traerPronostico(ciudad)
                uiState = ClimaEstado.ExitosoPronostico(pronostico)
            } catch (exception: Exception) {
                uiState = ClimaEstado.Error("Error al obtener el pronóstico de $nombreCiudad")
            }
        }
    }


}

class ClimaViewModelFactory(
    private val repositorio: Repositorio,
    private val router: Router
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClimaViewModel::class.java)) {
            return ClimaViewModel(repositorio, router) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

