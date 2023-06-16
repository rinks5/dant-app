package com.example.dankapp.dtos.response

data class ResUserEducationHistory(
    val _id: String,
    val publicId: Long,
    val userId: Long,
    val institutionId: Long,
    val degree: String,
    val name: String,
    val email: String
)
