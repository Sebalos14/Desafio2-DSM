package com.example.desafio2

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: Int, // Índice de la respuesta correcta (0, 1, 2)
    val category: String,
    val difficulty: String // "Fácil" o "Difícil"
)