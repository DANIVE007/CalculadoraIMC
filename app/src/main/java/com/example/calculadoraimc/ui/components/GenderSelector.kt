package com.example.calculadoraimc.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check

@Composable
fun GenderSelector(gender: String?, onGenderChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val options = listOf("Hombre", "Mujer")
        options.forEach { option ->
            Button(
                onClick = { onGenderChange(option) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (option == gender) MaterialTheme.colorScheme.primary else Color.Transparent,
                    contentColor = if (option == gender) Color.White else MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Si el género está seleccionado, se mostrará  el ícono de check en color blanco
                    if (option == gender) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Check icon",
                            tint = Color.White, // El ícono será de color blanco
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                    Text(option)
                }
            }
        }
    }
}
