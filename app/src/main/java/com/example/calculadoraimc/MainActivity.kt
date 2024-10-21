package com.example.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculadoraimc.data.PreferencesManager
import com.example.calculadoraimc.ui.screens.HomeView
import com.example.calculadoraimc.ui.screens.PacientesListView
import com.example.calculadoraimc.ui.screens.InstruccionesScreen
import com.example.calculadoraimc.viewModel.IMCViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesManager = PreferencesManager(context = this)

        setContent {
            val navController = rememberNavController()
            val viewModel: IMCViewModel = viewModel()

            // Verificar si es la primera vez
            lifecycleScope.launch {
                preferencesManager.isFirstTime.collect { isFirstTime ->
                    if (isFirstTime) {
                        navController.navigate("instrucciones") // Cambiar aquí a "instrucciones"
                    } else {
                        navController.navigate("pacientes_list")
                    }
                }
            }

            NavHost(navController = navController, startDestination = "pacientes_list") {
                composable("pacientes_list") { PacientesListView(navController, viewModel) }
                composable("calculadora_imc/{patientName}") { backStackEntry ->
                    val patientName = backStackEntry.arguments?.getString("patientName") ?: ""
                    HomeView(navController, patientName, viewModel)
                }
                composable("instrucciones") { InstruccionesScreen(navController, preferencesManager) }
            }
        }
    }
}
