package com.example.calculadoraimc.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.calculadoraimc.viewModel.IMCViewModel

@Composable
fun PacientesListView(navController: NavController, viewModel: IMCViewModel = viewModel()) {
    val pacientes = viewModel.getPatients() // Obtener la lista de pacientes

    var showAddPatientDialog by remember { mutableStateOf(false) }
    var newPatientName by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddPatientDialog = true }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Lista de Pacientes",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pacientes) { paciente ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Nombre: ${paciente.name}", style = MaterialTheme.typography.titleMedium)
                            Text(text = "Edad: ${paciente.age}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "IMC: ${paciente.imc}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Estado de Salud: ${paciente.healthStatus}", style = MaterialTheme.typography.bodyMedium)

                            // Botón "Calcular IMC" que redirige a la calculadora
                            if (!paciente.isImcCalculated) {
                                Button(
                                    onClick = {
                                        navController.navigate("calculadora_imc/${paciente.name}")
                                    },
                                    modifier = Modifier.align(Alignment.End) // Alinear el botón a la derecha
                                ) {
                                    Text("Calcular IMC")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal para agregar un nuevo paciente
    if (showAddPatientDialog) {
        AlertDialog(
            onDismissRequest = { showAddPatientDialog = false },
            title = { Text("Agregar Paciente") },
            text = {
                OutlinedTextField(
                    value = newPatientName,
                    onValueChange = { newPatientName = it },
                    label = { Text("Nombre del paciente") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (newPatientName.isNotEmpty()) {
                        viewModel.addOrUpdatePatient(newPatientName, "0.0", "0", "No definido", "No definido", false)
                        newPatientName = ""
                        showAddPatientDialog = false
                    }
                }) {
                    Text("Agregar")
                }
            },
            dismissButton = {
                Button(onClick = { showAddPatientDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
