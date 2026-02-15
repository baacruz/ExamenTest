package com.example.examentest

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.examentest.CRUD.HerramientasCRUD
import com.example.examentest.Configuracion.Transacciones.descripcion
import com.example.examentest.Configuracion.Transacciones.especificaciones
import com.example.examentest.Configuracion.Transacciones.nombre
import com.example.examentest.ui.theme.ExamenTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenTestTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ExamenTestApp()
                }
            }

        }
    }
}

@Composable
fun ExamenTestApp() {
    val context = LocalContext.current

    // Estados para capturar valores de los TextFields
    var fotoBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var especificaciones by remember { mutableStateOf("") }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 40.dp)
    ) {
        // Campo de foto con cámara
        FotoInput(context, fotoBitmap) { nuevaFoto ->
            fotoBitmap = nuevaFoto
        }

        EditNumberInput(
            value = nombre,
            onValueChange = { nombre = it },
            label = R.string.nombre
        )

        EditNumberInput(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = R.string.descripcion
        )

        EditNumberInput(
            value = especificaciones,
            onValueChange = { especificaciones = it },
            label = R.string.especificaciones
        )

        BotonAgregarHerramienta(
            context = context,
            nombre = nombre,
            descripcion = descripcion,
            especificaciones = especificaciones,
            foto = if (fotoBitmap != null) "FOTO_TOMADA" else ""
        )

    }
}


@Composable
fun EditNumberInput(
    value: String,
    onValueChange: (String) -> Unit,
    @androidx.annotation.StringRes label: Int
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        singleLine = true,
        modifier = Modifier
            .padding(bottom = 32.dp)
            .fillMaxWidth()
    )
}

@Composable
fun BotonAgregarHerramienta(
    context: Context,
    nombre: String,
    descripcion: String,
    especificaciones: String,
    foto: String
) {
    Button(
        onClick = {
            val crud = HerramientasCRUD(context)
            val resultado = crud.insertarHerramienta(nombre, descripcion, especificaciones, foto)
            if (resultado > 0) {
                Toast.makeText(context, "Herramienta registrada correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error al registrar herramienta", Toast.LENGTH_SHORT).show()
            }
        },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text("Agregar Herramienta")
    }
}

@Composable
fun FotoInput(
    context: Context,
    fotoBitmap: Bitmap?,
    onFotoTomada: (Bitmap) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let { onFotoTomada(it) }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Mostrar la foto si existe
        if (fotoBitmap != null) {
            Image(
                bitmap = fotoBitmap.asImageBitmap(),
                contentDescription = "Foto herramienta",
                modifier = Modifier
                    .size(150.dp)
                    .padding(8.dp)
            )
        }

        // Botón para abrir la cámara
        Button(onClick = { launcher.launch(null) }) {
            Text("Tomar Foto")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ExamenTestPreview() {
    ExamenTestTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
                ExamenTestApp()
        }
    }
}