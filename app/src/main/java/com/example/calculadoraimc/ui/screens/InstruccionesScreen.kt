package com.example.calculadoraimc.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calculadoraimc.data.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun InstruccionesScreen(navController: NavController, preferencesManager: PreferencesManager) {
    var currentTab by remember { mutableStateOf(0) }

    // Crear las pestañas
    TabRow(selectedTabIndex = currentTab) {
        for (tabIndex in 0..2) {
            Tab(
                selected = currentTab == tabIndex,
                onClick = { currentTab = tabIndex },
                text = { Text("Pestaña ${tabIndex + 1}") }
            )
        }
    }

    when (currentTab) {
        0 -> TabContent("Introducción 1: Esta aplicación te permitirá poder registrar a pacientes y calcular su IMC, con un registro de pacientes.")
        1 -> TabContent("Introducción 2: Primero debes presionar el boton plus en la parte inferior derecha, luego ingresas el nombre del paciente y presionas el botón medir IMC. ")
        2 -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Introducción 3: Te dirigirá a una calculadora de IMC en donde podrás saber tu estado de salud y registrar tus datos en la lista de pacientes. ")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    // Guardar el estado y navegar
                    CoroutineScope(Dispatchers.Main).launch {
                        preferencesManager.saveFirstTimeState(false) // Guardar el estado
                        navController.navigate("pacientes_list") // Navegar a la lista
                    }
                }) {
                    Text("Ingresar a la aplicación")
                }
            }
        }
    }
}

@Composable
fun TabContent(text: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun InstruccionesScreenPreview() {
    // Proporcionamos un navController simulado para la vista previa
    val navController = rememberNavController()
    InstruccionesScreen(navController, PreferencesManager(context = LocalContext.current)) // Usar LocalContext
}
