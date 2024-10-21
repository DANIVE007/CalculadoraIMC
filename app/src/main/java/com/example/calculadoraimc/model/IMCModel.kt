package com.example.calculadoraimc.model

data class IMCModel(
    val gender: String? = null,
    val age: String = "",
    val weight: String = "",
    val height: String = "",
    val result: String = ""
)
