package com.example.examentest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    //val context = LocalContext.current

    // Estados para capturar valores de los TextFields
    var herramientas by remember { mutableStateOf("") }
    var tecnicos by remember { mutableStateOf("") }
    var asignaciones by remember { mutableStateOf("") }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 40.dp)
    ) {
        EditNumberInput(
            value = herramientas,
            onValueChange = {herramientas = it},
            label = R.string.herramientas)
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

@Preview(showBackground = true)
@Composable
fun ExamenTestPreview() {
    ExamenTestTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
                ExamenTestApp()
        }
    }
}