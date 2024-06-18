package com.istea.appdelclima.presentacion.clima

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.istea.appdelclima.presentacion.ciudades.CiudadesViewModel
import com.istea.appdelclima.presentacion.ciudades.CiudadesViewModelFactory
import com.istea.appdelclima.repository.RepositorioApi
import com.istea.appdelclima.repository.RepositorioMock
import com.istea.appdelclima.router.Enrutador
import com.istea.appdelclima.router.Router
import com.istea.appdelclima.router.Ruta

@Composable
fun ClimaPage(
    navHostController: NavHostController, ciudad: String?
){
    val viewModel: ClimaViewModel = viewModel(factory = ClimaViewModelFactory(RepositorioApi(), object : Router {
        override fun navegar(ruta: Ruta) {
            when (ruta) {
                is Ruta.Ciudades -> navHostController.navigate(Ruta.Ciudades.id)
                is Ruta.Clima -> navHostController.navigate("${Ruta.Clima().id}/${ruta.ciudad}")
            }
        }
    }))

    // Ejecutar la acción para mostrar el clima de la ciudad seleccionada al cargar la página
    LaunchedEffect(ciudad) {
        ciudad?.let {
            viewModel.ejecutar(ClimaIntencion.MostrarCiudad(it))
        }
    }

    val state = viewModel.uiState

    ClimaView(
        state = state,
        onAction = { intencion -> viewModel.ejecutar(intencion) }
    )
}
