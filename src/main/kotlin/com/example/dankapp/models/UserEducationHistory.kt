package com.example.dankapp.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("user_education_history")
data class UserEducationHistory(
    @Id
    val _id: String? = null,
    val publicId: Long,
    val userId: Long,
    val institutionId: Long,
    var degree: String,
    var name: String,
    var email: String
)
