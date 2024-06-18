package com.istea.appdelclima.presentacion.clima

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.istea.appdelclima.ui.theme.AppDelClimaTheme

import androidx.compose.runtime.*
import com.istea.appdelclima.repository.modelos.Clima2


@Composable
fun ClimaView(
    modifier: Modifier = Modifier,
    state: ClimaEstado,
    onAction: (ClimaIntencion) -> Unit
) {

    var ciudad by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (state) {
            is ClimaEstado.Error -> ErrorView(mensaje = state.mensaje)
            is ClimaEstado.Exitoso -> ClimaInfoView(
                ciudad = state.ciudad,
                temperatura = state.temperatura,
                descripcion = state.descripcion,
                st = state.st
            )

            //Spacer(modifier = Modifier.height(20.dp))
                  //  Text(text = "Pronóstico para los próximos días:", style = MaterialTheme.typography.bodyMedium)
                //PronosticoView(pronostico = state.pronostico)

            is ClimaEstado.ExitosoPronostico -> {
                Text(
                    text = "Pronóstico para los próximos días:",
                    style = MaterialTheme.typography.bodyMedium
                )
                PronosticoView(pronostico = state.pronostico)



        }

            ClimaEstado.Vacio -> EmptyView()
            ClimaEstado.Cargando -> Text(text = "Cargando...")
        }


        Spacer(modifier = Modifier.height(20.dp))

       // Button(onClick = { onAction(ClimaIntencion.BorrarTodo) }) {
       //     Text(text = "Borrar todo")
       // }
       // Button(onClick = { onAction(ClimaIntencion.MostrarCaba) }) {
        //    Text(text = "Mostrar Caba")
       // }

        Button(onClick = { onAction(ClimaIntencion.MostrarPronostico(ciudad)) }) {
            Text(text = "Mostrar Pronóstico prox dias")
        }
        Button(onClick = { onAction(ClimaIntencion.MostrarCordoba) }) {
            Text(text = "Geolocalizacion BS AS")
        }
        //Button(onClick = { onAction(ClimaIntencion.MostrarError) }) {
        //    Text(text = "Mostrar Error")
       // }

    }
}


@Composable
fun EmptyView() {
    Text(text = "No hay nada que mostrar")
}

@Composable
fun ErrorView(mensaje: String) {
    Text(text = mensaje)
}

@Composable
fun ClimaInfoView(ciudad: String, temperatura: Double, descripcion: String, st: Double) {
    Column {
        Text(text = ciudad, style = MaterialTheme.typography.titleMedium)
        Text(text = "${temperatura}°", style = MaterialTheme.typography.titleLarge)
        Text(text = descripcion, style = MaterialTheme.typography.bodyMedium)
        Text(text = "Sensación térmica: ${st}°", style = MaterialTheme.typography.bodyMedium)

    }
}

@Composable
fun PronosticoView(pronostico: List<Clima2>) {
    LazyColumn {
        items(pronostico) { clima ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    // Nombre de la ciudad
                    Text(text = clima.name, style = MaterialTheme.typography.bodyMedium)

                    // Información del clima
                    clima.weather.firstOrNull()?.let { weather ->
                        Text(
                            text = "Descripción: ${weather.description}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    // Temperatura y otros detalles climáticos
                    Text(
                        text = "Temperatura: ${clima.main.temp}°",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Sensación térmica: ${clima.main.feels_like}°",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ClimaPreviewVacio() {
    AppDelClimaTheme {
        ClimaView(state = ClimaEstado.Vacio, onAction = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ClimaPreviewError() {
    AppDelClimaTheme {
        ClimaView(state = ClimaEstado.Error("Se rompió todo"), onAction = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ClimaPreviewExitoso() {
    AppDelClimaTheme {
        ClimaView(
            state = ClimaEstado.Exitoso(
                ciudad = "Cordoba",
                temperatura = 25.0,
                descripcion = "Despejado",
                st = 27.0
            ),
            onAction = {}
        )
    }
}


