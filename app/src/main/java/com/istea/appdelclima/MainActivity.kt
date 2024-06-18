package com.istea.appdelclima

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.istea.appdelclima.router.Router
import com.istea.appdelclima.router.Ruta
import com.istea.appdelclima.ui.theme.AppDelClimaTheme

class MainActivity : ComponentActivity(), Router {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppDelClimaTheme {
                navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainPage(navController = navController)
                }
            }
        }
    }

    override fun navegar(ruta: Ruta) {
        when (ruta) {
            is Ruta.Ciudades -> navController.navigate(Ruta.Ciudades.id)
            is Ruta.Clima -> navController.navigate("${Ruta.Clima().id}/${ruta.ciudad}")
        }
    }
}
