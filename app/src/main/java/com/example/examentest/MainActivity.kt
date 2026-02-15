package com.example.examentest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examentest.CRUD.HerramientasCRUD
import com.example.examentest.CRUD.TecnicosCRUD
import com.example.examentest.Configuracion.Transacciones
import com.example.examentest.ui.theme.ExamenTestTheme
import java.io.File
import java.io.FileOutputStream

enum class Screen {
    HERRAMIENTAS,
    TECNICOS
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenTestTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    var currentScreen by remember { mutableStateOf(Screen.HERRAMIENTAS) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { currentScreen = Screen.HERRAMIENTAS }) {
                Text("Herramientas")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { currentScreen = Screen.TECNICOS }) {
                Text("Técnicos")
            }
        }

        when (currentScreen) {
            Screen.HERRAMIENTAS -> RegistroHerramientasScreen()
            Screen.TECNICOS -> RegistroTecnicosScreen()
        }
    }
}

@Composable
fun RegistroHerramientasScreen() {
    val context = LocalContext.current

    var fotoBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var nombreHerramienta by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var especificaciones by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 40.dp).fillMaxHeight()
    ) {
        FotoInput(fotoBitmap) { nuevoBitmap ->
            fotoBitmap = nuevoBitmap
        }

        Spacer(modifier = Modifier.height(16.dp))

        EditNumberInput(
            value = nombreHerramienta,
            onValueChange = { nombreHerramienta = it },
            label = Transacciones.nombre_herramienta
        )

        EditNumberInput(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = Transacciones.descripcion
        )

        EditNumberInput(
            value = especificaciones,
            onValueChange = { especificaciones = it },
            label = Transacciones.especificaciones
        )

        BotonAgregarHerramienta(
            context = context,
            nombre = nombreHerramienta,
            descripcion = descripcion,
            especificaciones = especificaciones,
            fotoBitmap = fotoBitmap
        )
    }
}

@Composable
fun RegistroTecnicosScreen() {
    val context = LocalContext.current
    var nombreTecnico by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 40.dp).fillMaxHeight()
    ) {
        Text("Registrar Técnicos", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))

        EditNumberInput(
            value = nombreTecnico,
            onValueChange = { nombreTecnico = it },
            label = "Nombre"
        )

        EditNumberInput(
            value = telefono,
            onValueChange = { telefono = it },
            label = "Teléfono"
        )

        EditNumberInput(
            value = especialidad,
            onValueChange = { especialidad = it },
            label = "Especialidad"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = {
                try {
                    if (nombreTecnico.isBlank() || telefono.isBlank() || especialidad.isBlank()) {
                        Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
                    } else {
                        val crud = TecnicosCRUD(context)
                        val resultado = crud.insertarTecnico(nombreTecnico, telefono, especialidad)
                        if (resultado > 0) {
                            Toast.makeText(context, "Técnico registrado con éxito. ID: $resultado", Toast.LENGTH_LONG).show()
                            nombreTecnico = ""
                            telefono = ""
                            especialidad = ""
                        } else {
                            Toast.makeText(context, "Error: No se pudo registrar el técnico.", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (ex: Exception) {
                    Toast.makeText(context, "Error al registrar: ${ex.message}", Toast.LENGTH_LONG).show()
                }
            }) {
                Text("Agregar")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                Toast.makeText(context, "Función no implementada", Toast.LENGTH_SHORT).show()
            }) {
                Text("Ver Listado")
            }
        }
    }
}

@Composable
fun EditNumberInput(value: String, onValueChange: (String) -> Unit, label: String) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
    )
}

@Composable
fun BotonAgregarHerramienta(
    context: Context,
    nombre: String,
    descripcion: String,
    especificaciones: String,
    fotoBitmap: Bitmap?
) {
    Button(
        onClick = {
            try {
                if (nombre.isBlank() || descripcion.isBlank() || especificaciones.isBlank()) {
                    Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
                    return@Button
                }

                var fotoPath = ""
                fotoBitmap?.let {
                    fotoPath = saveBitmapToInternalStorage(context, it)
                }

                val crud = HerramientasCRUD(context)
                val resultado = crud.insertarHerramienta(nombre, descripcion, especificaciones, fotoPath)

                if (resultado > 0) {
                    Toast.makeText(context, "Herramienta registrada con éxito. ID: $resultado", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Error: No se pudo registrar la herramienta.", Toast.LENGTH_LONG).show()
                }
            } catch (ex: Exception) {
                Toast.makeText(context, "Error al registrar: ${ex.message}", Toast.LENGTH_LONG).show()
            }
        },
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text("Agregar Herramienta")
    }
}

@Composable
fun FotoInput(fotoBitmap: Bitmap?, onFotoTomada: (Bitmap) -> Unit) {
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let(onFotoTomada)
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
            onFotoTomada(bitmap)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (fotoBitmap != null) {
            Image(
                bitmap = fotoBitmap.asImageBitmap(),
                contentDescription = "Foto herramienta",
                modifier = Modifier.size(150.dp).padding(8.dp)
            )
        }

        Row {
            Button(onClick = { cameraLauncher.launch(null) }) {
                Text("Tomar Foto")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("Elegir de Galería")
            }
        }
    }
}

private fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap): String {
    val imagesDir = File(context.filesDir, "images")
    if (!imagesDir.exists()) {
        imagesDir.mkdirs()
    }

    val imageFile = File(imagesDir, "img_${System.currentTimeMillis()}.jpg")
    try {
        FileOutputStream(imageFile).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error al guardar imagen: ${e.message}", Toast.LENGTH_SHORT).show()
    }
    return imageFile.absolutePath
}

@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
    ExamenTestTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainApp()
        }
    }
}
