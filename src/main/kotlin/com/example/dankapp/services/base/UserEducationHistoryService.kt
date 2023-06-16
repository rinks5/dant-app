package com.example.dankapp.services.base

import com.example.dankapp.dtos.request.ReqUserEducationHistoryDto
import com.example.dankapp.dtos.response.PaginationResponseDto
import com.example.dankapp.dtos.response.UserDto
import com.example.dankapp.models.UserEducationHistory

interface UserEducationHistoryService {
    fun addUserEducation(request: ReqUserEducationHistoryDto): UserEducationHistory
    fun getRecordById(id: String): UserEducationHistory
    fun getRecordsByUserId(id: Long): List<UserEducationHistory>
    fun updateRecord(id: String, request: ReqUserEducationHistoryDto): UserEducationHistory
    fun deleteRecord(id: String) : String
    fun getUniqueUser(institutionId: Long, pageNo: Int?, pageSize: Int?, sortDirection: String?, sortParam: String?): PaginationResponseDto
}