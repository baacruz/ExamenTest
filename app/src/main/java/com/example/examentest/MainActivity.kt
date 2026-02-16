package com.example.examentest

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examentest.CRUD.AsignacionesCRUD
import com.example.examentest.CRUD.HerramientasCRUD
import com.example.examentest.CRUD.TecnicosCRUD
import com.example.examentest.Configuracion.Transacciones
import com.example.examentest.ui.theme.ExamenTestTheme
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar

enum class Screen {
    REGISTRO_HERRAMIENTAS,
    LISTA_HERRAMIENTAS,
    REGISTRO_TECNICOS,
    LISTA_TECNICOS,
    ASIGNACION
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    var currentScreen by remember { mutableStateOf(Screen.REGISTRO_HERRAMIENTAS) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { currentScreen = Screen.REGISTRO_HERRAMIENTAS }) {
                Text("Herramientas")
            }
            Button(onClick = { currentScreen = Screen.REGISTRO_TECNICOS }) {
                Text("Técnicos")
            }
            Button(onClick = { currentScreen = Screen.ASIGNACION }) {
                Text("Asignar")
            }
        }

        when (currentScreen) {
            Screen.REGISTRO_HERRAMIENTAS -> RegistroHerramientasScreen { currentScreen = Screen.LISTA_HERRAMIENTAS }
            Screen.LISTA_HERRAMIENTAS -> HerramientasListScreen { currentScreen = Screen.REGISTRO_HERRAMIENTAS }
            Screen.REGISTRO_TECNICOS -> RegistroTecnicosScreen { currentScreen = Screen.LISTA_TECNICOS }
            Screen.LISTA_TECNICOS -> TecnicosListScreen { currentScreen = Screen.REGISTRO_TECNICOS }
            Screen.ASIGNACION -> AsignacionScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsignacionScreen() {
    val context = LocalContext.current
    val herramientasDisponibles = remember { mutableStateListOf<Pair<Int, String>>() }
    val tecnicos = remember { mutableStateListOf<Pair<Int, String>>() }

    var herramientaSeleccionada by remember { mutableStateOf<Pair<Int, String>?>(null) }
    var tecnicoSeleccionado by remember { mutableStateOf<Pair<Int, String>?>(null) }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }

    var expandedHerramienta by remember { mutableStateOf(false) }
    var expandedTecnico by remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    // Cargar herramientas y técnicos
    LaunchedEffect(Unit) {
        val herramCrud = HerramientasCRUD(context)
        herramCrud.obtenerHerramientasDisponibles()?.use { cursor ->
            if (cursor.moveToFirst()) {
                herramientasDisponibles.clear()
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(Transacciones.herramienta_id))
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow(Transacciones.nombre_herramienta))
                    herramientasDisponibles.add(id to nombre)
                } while (cursor.moveToNext())
            }
        }

        val tecCrud = TecnicosCRUD(context)
        tecCrud.obtenerTodosLosTecnicos()?.use { cursor ->
            if (cursor.moveToFirst()) {
                tecnicos.clear()
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(Transacciones.tecnico_id))
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow(Transacciones.nombre_tecnico))
                    tecnicos.add(id to nombre)
                } while (cursor.moveToNext())
            }
        }
    }

    Column(
        modifier = Modifier.padding(horizontal = 40.dp).fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Asignar Herramienta", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))

        // Selector de Herramienta
        ExposedDropdownMenuBox(expanded = expandedHerramienta, onExpandedChange = { expandedHerramienta = !expandedHerramienta }) {
            TextField(
                value = herramientaSeleccionada?.second ?: "",
                onValueChange = {},
                label = { Text("Herramienta") },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedHerramienta)
                },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expandedHerramienta, onDismissRequest = { expandedHerramienta = false }) {
                herramientasDisponibles.forEach { herramienta ->
                    DropdownMenuItem(text = { Text(herramienta.second) }, onClick = {
                        herramientaSeleccionada = herramienta
                        expandedHerramienta = false
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selector de Técnico
        ExposedDropdownMenuBox(expanded = expandedTecnico, onExpandedChange = { expandedTecnico = !expandedTecnico }) {
            TextField(
                value = tecnicoSeleccionado?.second ?: "",
                onValueChange = {},
                label = { Text("Técnico") },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTecnico)
                },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expandedTecnico, onDismissRequest = { expandedTecnico = false }) {
                tecnicos.forEach { tecnico ->
                    DropdownMenuItem(text = { Text(tecnico.second) }, onClick = {
                        tecnicoSeleccionado = tecnico
                        expandedTecnico = false
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selectores de Fecha
        DatePicker(label = "Fecha de Inicio") { fechaInicio = it }
        Spacer(modifier = Modifier.height(16.dp))
        DatePicker(label = "Fecha de Fin") { fechaFin = it }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { 
            if(herramientaSeleccionada == null || tecnicoSeleccionado == null || fechaInicio.isBlank() || fechaFin.isBlank()){
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
            } else {
                showDialog.value = true
            }
        }) {
            Text("Asignar")
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Confirmar Asignación") },
            text = { Text("¿Confirmar asignación de ${herramientaSeleccionada?.second} a ${tecnicoSeleccionado?.second} del $fechaInicio al $fechaFin?") },
            confirmButton = {
                Button(
                    onClick = {
                        try {
                            val asignacionesCrud = AsignacionesCRUD(context)
                            val herramientasCrud = HerramientasCRUD(context)
                            val resultado = asignacionesCrud.insertarAsignacion(
                                herramientaSeleccionada!!.first,
                                tecnicoSeleccionado!!.first,
                                fechaInicio,
                                fechaFin
                            )
                            if (resultado > 0) {
                                herramientasCrud.actualizarEstadoHerramienta(herramientaSeleccionada!!.first, "ASIGNADA")
                                Toast.makeText(context, "Asignación creada con éxito", Toast.LENGTH_SHORT).show()
                                // Limpiar campos
                                herramientaSeleccionada = null
                                tecnicoSeleccionado = null
                                fechaInicio = ""
                                fechaFin = ""
                            } else {
                                Toast.makeText(context, "Error al crear la asignación", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                        showDialog.value = false
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun DatePicker(label: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var date by remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            date = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            onDateSelected(date)
        },
        year, month, day
    )

    Box(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = date,
            onValueChange = { },
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .height(56.dp)
                .padding(0.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Button(onClick = { datePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {}
        }
    }
}



// ... (El resto de tus Composable functions)

@Composable
fun RegistroHerramientasScreen(onNavigateToList: () -> Unit) {
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
        Text("Registrar Herramienta", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

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

        Row {
            Button(
                onClick = {
                    try {
                        if (nombreHerramienta.isBlank() || descripcion.isBlank() || especificaciones.isBlank()) {
                            Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
                        } else {
                            var fotoPath = ""
                            fotoBitmap?.let { fotoPath = saveBitmapToInternalStorage(context, it) }

                            val crud = HerramientasCRUD(context)
                            val resultado = crud.insertarHerramienta(nombreHerramienta, descripcion, especificaciones, fotoPath)

                            if (resultado > 0) {
                                Toast.makeText(context, "Herramienta registrada con éxito. ID: $resultado", Toast.LENGTH_LONG).show()
                                nombreHerramienta = ""
                                descripcion = ""
                                especificaciones = ""
                                fotoBitmap = null
                            } else {
                                Toast.makeText(context, "Error: No se pudo registrar la herramienta.", Toast.LENGTH_LONG).show()
                            }
                        }
                    } catch (ex: Exception) {
                        Toast.makeText(context, "Error al registrar: ${ex.message}", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text("Agregar")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onNavigateToList) {
                Text("Ver Listado")
            }
        }
    }
}

@Composable
fun RegistroTecnicosScreen(onNavigateToList: () -> Unit) {
    val context = LocalContext.current
    var nombreTecnico by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var especialidad by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 40.dp).fillMaxHeight()
    ) {
        Text("Registrar Técnico", fontSize = 24.sp, fontWeight = FontWeight.Bold)
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
            Button(onClick = onNavigateToList) {
                Text("Ver Listado")
            }
        }
    }
}

@Composable
fun HerramientasListScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val crud = HerramientasCRUD(context)
    val cursor = crud.obtenerTodasLasHerramientas()
    val herramientas = remember { mutableStateListOf<Pair<Int, String>>() }

    if (cursor != null) {
        if (cursor.moveToFirst()) {
            herramientas.clear()
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(Transacciones.herramienta_id))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(Transacciones.nombre_herramienta))
                herramientas.add(Pair(id, nombre))
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = onNavigateBack, modifier = Modifier.padding(bottom = 16.dp)) {
            Text("Volver")
        }
        LazyColumn {
            items(herramientas) { herramienta ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), elevation = CardDefaults.cardElevation(4.dp)) {
                    Text(text = "ID: ${herramienta.first}, Nombre: ${herramienta.second}", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
fun TecnicosListScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val crud = TecnicosCRUD(context)
    val cursor = crud.obtenerTodosLosTecnicos()
    val tecnicos = remember { mutableStateListOf<Pair<Int, String>>() }

    if (cursor != null) {
        if (cursor.moveToFirst()) {
            tecnicos.clear()
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(Transacciones.tecnico_id))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(Transacciones.nombre_tecnico))
                tecnicos.add(Pair(id, nombre))
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = onNavigateBack, modifier = Modifier.padding(bottom = 16.dp)) {
            Text("Volver")
        }
        LazyColumn {
            items(tecnicos) { tecnico ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), elevation = CardDefaults.cardElevation(4.dp)) {
                    Text(text = "ID: ${tecnico.first}, Nombre: ${tecnico.second}", modifier = Modifier.padding(16.dp))
                }
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
