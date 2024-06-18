package com.istea.appdelclima.presentacion.ciudades

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.istea.appdelclima.repository.RepositorioApi
import com.istea.appdelclima.router.Enrutador
import com.istea.appdelclima.router.Router
import com.istea.appdelclima.router.Ruta

@Composable
fun CiudadesPage(navController: NavHostController) {
    val viewModel: CiudadesViewModel = viewModel(factory = CiudadesViewModelFactory(RepositorioApi(), object : Router {
        override fun navegar(ruta: Ruta) {
            when (ruta) {
                is Ruta.Ciudades -> navController.navigate(Ruta.Ciudades.id)
                is Ruta.Clima -> navController.navigate("${Ruta.Clima().id}/${ruta.ciudad}")
            }
        }
    }))
    val state = viewModel.uiState

    CiudadesView(
        state = state,
        onAction = { intencion -> viewModel.ejecutar(intencion) }
    )
}
