package com.example.dankapp.dtos.response

data class PaginationResponseDto(
    val users: List<UserDto>,
    val totalPages: Long,
    val nextPage: Int,
    val prevPage: Int,
    val currentPage: Int
)
