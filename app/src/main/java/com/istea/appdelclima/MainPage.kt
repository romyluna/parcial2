package com.istea.appdelclima

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.istea.appdelclima.presentacion.ciudades.CiudadesPage
import com.istea.appdelclima.presentacion.clima.ClimaPage
import com.istea.appdelclima.router.Enrutador
import com.istea.appdelclima.router.Ruta

@Composable
fun MainPage(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Ruta.Ciudades.id
    ) {
        composable(
            route = Ruta.Ciudades.id
        ) {
            CiudadesPage(navController)
        }
        composable(
            route = "${Ruta.Clima().id}/{ciudad}"
        ) { backStackEntry ->
            val ciudad = backStackEntry.arguments?.getString("ciudad")
            ClimaPage(navController, ciudad)
        }
    }
}