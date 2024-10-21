package com.example.calculadoraimc.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class IMCViewModel : ViewModel() {
    var gender = mutableStateOf<String?>(null)
    var age = mutableStateOf("")
    var weight = mutableStateOf("")
    var height = mutableStateOf("")
    var result = mutableStateOf("")

    private val patients = mutableListOf<Patient>() // Lista de pacientes

    fun setGender(newGender: String) {
        gender.value = newGender
    }

    fun setAge(newAge: String) {
        age.value = newAge
    }

    fun setWeight(newWeight: String) {
        weight.value = newWeight
    }

    fun setHeight(newHeight: String) {
        height.value = newHeight
    }

    fun calculateIMC() {
        val weightValue = weight.value.toDoubleOrNull() ?: return
        val heightValue = height.value.toDoubleOrNull() ?: return
        val imc = weightValue / ((heightValue / 100) * (heightValue / 100))
        result.value = "%.1f".format(imc) // Formato de un decimal
    }

    fun addOrUpdatePatient(name: String, imc: String, age: String, gender: String, healthStatus: String, isImcCalculated: Boolean) {
        val existingPatient = patients.find { it.name == name } // Buscar paciente existente
        if (existingPatient != null) {
            // Actualizar el paciente existente
            patients[patients.indexOf(existingPatient)] = Patient(name, imc, age, gender, healthStatus, isImcCalculated)
        } else {
            // Agregar nuevo paciente
            patients.add(Patient(name, imc, age, gender, healthStatus, isImcCalculated))
        }
    }

    fun getPatients(): List<Patient> {
        return patients // Retornar la lista de pacientes
    }

    fun resetFields() {
        gender.value = null
        age.value = ""
        weight.value = ""
        height.value = ""
        result.value = ""
    }
}

data class Patient(
    val name: String,
    val imc: String,
    val age: String,
    val gender: String,
    val healthStatus: String,
    val isImcCalculated: Boolean
)
