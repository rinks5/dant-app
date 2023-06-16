package com.example.dankapp.dtos.request

data class ReqUserEducationHistoryDto(
    val publicId: Long?,
    val userId: Long?,
    val institutionId: Long?,
    val degree: String?,
    val name: String?,
    val email: String?
)
