package com.example.dankapp.repositories

import com.example.dankapp.models.UserEducationHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserEducationHistoryRepository : MongoRepository<UserEducationHistory, String> {
    fun findByUserId(userId: Long) : List<UserEducationHistory>
    fun findDistinctByInstitutionIdAndUserId(instituteId: Long, pageAble: Pageable) : Page<UserEducationHistory>
}