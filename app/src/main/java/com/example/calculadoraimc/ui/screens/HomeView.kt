package com.example.calculadoraimc.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calculadoraimc.viewModel.IMCViewModel
import com.example.calculadoraimc.ui.components.GenderSelector

@Composable
fun HomeView(navController: NavController, patientName: String, viewModel: IMCViewModel = viewModel()) {
    // Variables y estados
    val gender by viewModel.gender
    val age by viewModel.age
    val weight by viewModel.weight
    val height by viewModel.height
    val result by viewModel.result
    var showAgeError by remember { mutableStateOf(false) }
    var showWeightError by remember { mutableStateOf(false) }
    var showGeneralError by remember { mutableStateOf(false) }
    var healthStatus by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Contenido de la vista
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Calculadora de IMC", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelector(gender = gender, onGenderChange = { viewModel.setGender(it) })

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Edad
        OutlinedTextField(
            value = age,
            onValueChange = { viewModel.setAge(it) },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.onFocusChanged { focusState ->
                if (!focusState.isFocused && age.isNotEmpty() && !age.all { it.isDigit() }) {
                    showAgeError = true
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Peso
        OutlinedTextField(
            value = weight,
            onValueChange = { viewModel.setWeight(it) },
            label = { Text("Peso (Kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.onFocusChanged { focusState ->
                if (!focusState.isFocused && weight.isNotEmpty() && !weight.all { it.isDigit() }) {
                    showWeightError = true
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Altura
        OutlinedTextField(
            value = height,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    viewModel.setHeight(newValue)
                } else {
                    Toast.makeText(context, "Caracteres erróneos, solo números", Toast.LENGTH_SHORT).show()
                }
            },
            label = { Text("Altura (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Calcular
        Button(onClick = {
            if (age.isBlank() || weight.isBlank() || height.isBlank() || gender == null) {
                showGeneralError = true
            } else {
                viewModel.calculateIMC()
                // Determinar el estado de salud
                healthStatus = getHealthStatus(result.toDouble())
            }
        }) {
            Text("Calcular")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar resultado
        Text(
            result,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 40.sp
        )

        // Mostrar estado de salud
        if (result.isNotEmpty()) {
            Text(
                text = "Estado de Salud: $healthStatus",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 20.sp
            )
        }

        // Botón Guardar
        if (result.isNotEmpty()) {
            Button(onClick = {
                viewModel.addOrUpdatePatient(
                    patientName,
                    result,
                    age,
                    gender ?: "No definido",
                    healthStatus,
                    true
                )
                viewModel.resetFields()
                navController.navigate("pacientes_list")
            }) {
                Text("Guardar")
            }
        }

        // Modales de error
        // ... (aquí van los AlertDialog para mostrar los errores)
    }
}

// Función para obtener el estado de salud basado en el IMC
fun getHealthStatus(imc: Double): String {
    return when {
        imc < 18.5 -> "Bajo peso"
        imc < 25 -> "Peso Normal"
        imc < 30 -> "Sobrepeso"
        imc < 35 -> "Obesidad I"
        imc < 40 -> "Obesidad II"
        else -> "Obesidad III"
    }
}

@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    // Proporcionamos un navController simulado para la vista previa
    val navController = rememberNavController()
    HomeView(navController = navController, patientName = "Paciente de prueba")
}
