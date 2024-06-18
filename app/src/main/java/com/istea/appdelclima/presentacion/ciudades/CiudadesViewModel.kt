package com.istea.appdelclima.presentacion.ciudades

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.istea.appdelclima.repository.Repositorio
import com.istea.appdelclima.repository.modelos.Ciudad
import com.istea.appdelclima.router.Router
import com.istea.appdelclima.router.Ruta
import kotlinx.coroutines.launch

class CiudadesViewModel(
    val repositorio: Repositorio,
    val router: Router
) : ViewModel(){

    var uiState by mutableStateOf<CiudadesEstado>(CiudadesEstado.Vacio)
    private var ciudadesList: List<Ciudad> = emptyList()

    fun ejecutar(intencion: CiudadesIntencion) {
        when (intencion) {
            is CiudadesIntencion.Buscar -> buscar(nombre = intencion.nombre)
            is CiudadesIntencion.Seleccionar -> seleccionar(indice = intencion.indice)
        }
    }

    private fun buscar(nombre: String) {
        uiState = CiudadesEstado.Cargando
        viewModelScope.launch {
            try {
                ciudadesList = repositorio.buscarCiudad(nombre).toList()
                uiState = if (ciudadesList.isNotEmpty()) {
                    CiudadesEstado.Resultado(ciudadesList.toTypedArray())
                } else {
                    CiudadesEstado.Vacio
                }
            } catch (exception: Exception) {
                uiState = CiudadesEstado.Error("Error al buscar la ciudad")
            }
        }
    }

    private fun seleccionar(indice: Int) {
        if (indice >= 0 && indice < ciudadesList.size) {
            val ciudadSeleccionada = ciudadesList[indice]
            router.navegar(Ruta.Clima(ciudadSeleccionada.name))
        }
    }
}


class CiudadesViewModelFactory(
    private val repositorio: Repositorio,
    private val router: Router
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CiudadesViewModel::class.java)) {
            return CiudadesViewModel(repositorio,router) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}