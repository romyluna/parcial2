package com.istea.appdelclima.presentacion.ciudades
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import com.istea.appdelclima.presentacion.clima.ClimaIntencion
import com.istea.appdelclima.repository.modelos.Ciudad


@Composable
fun CiudadesView (
    modifier: Modifier = Modifier,
    state : CiudadesEstado,
    onAction: (CiudadesIntencion)->Unit
) {
    var value by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        TextField(
            value = value,
            label = { Text(text = "Buscar clima por ciudad") },
            onValueChange = {
                value = it
                onAction(CiudadesIntencion.Buscar(value))
            },
        )
        when (state) {
            CiudadesEstado.Cargando -> Text(text = "Cargando")
            is CiudadesEstado.Error -> Text(text = state.mensaje)
            is CiudadesEstado.Resultado -> ListaDeCiudades(state.ciudades) { onAction(CiudadesIntencion.Seleccionar(it)) }
            CiudadesEstado.Vacio -> Text(text = "No hay resultados")
        }
        //Button(onClick = { onAction(CiudadesIntencion.Seleccionar(0)) }) {
            //Text(text = "geolocalizacion Buenos aires")
        //}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaDeCiudades(ciudades: Array<Ciudad>, onSelect: (Int)->Unit) {
    LazyColumn {
        itemsIndexed(ciudades) { index, ciudad ->
            Card(onClick = { onSelect(index) }) {
                Text(text = ciudad.name)
            }
        }
    }
}