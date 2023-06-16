package com.example.dankapp.services.impl

import com.example.dankapp.dtos.request.ReqUserEducationHistoryDto
import com.example.dankapp.dtos.response.CountDto
import com.example.dankapp.dtos.response.PaginationResponseDto
import com.example.dankapp.dtos.response.UserDto
import com.example.dankapp.models.UserEducationHistory
import com.example.dankapp.repositories.UserEducationHistoryRepository
import com.example.dankapp.services.base.UserEducationHistoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service


@Service
class UserEducationHistoryServiceImpl(
    @Autowired private val userEducationHistoryRepository: UserEducationHistoryRepository,
    @Autowired private val mongoTemplate: MongoTemplate
) : UserEducationHistoryService {
    override fun addUserEducation(request: ReqUserEducationHistoryDto): UserEducationHistory {
        val userEducationHistoryRecord = UserEducationHistory(
            publicId = request.publicId?:0,
            userId = request.userId?:0,
            degree = request.degree?:"",
            email = request.email?:"",
            name = request.name?:"",
            institutionId = request.institutionId?:0
        )
        return userEducationHistoryRepository.save(userEducationHistoryRecord);
    }

    override fun getRecordById(id: String): UserEducationHistory {
        return userEducationHistoryRepository.findById(id).get();
    }

    override fun getRecordsByUserId(id: Long): List<UserEducationHistory> {
        return userEducationHistoryRepository.findByUserId(id);
    }

    override fun updateRecord(id: String, request: ReqUserEducationHistoryDto): UserEducationHistory {
        val record = getRecordById(id);
        record.degree = request.degree?:record.degree
        record.name = request.name?:record.name
        record.email = request.email?:record.email

        return userEducationHistoryRepository.save(record)
    }

    override fun deleteRecord(id: String) : String {
        userEducationHistoryRepository.deleteById(id)
        return "Record with $id has been deleted"
    }

    override fun getUniqueUser(
        institutionId: Long,
        pageNo: Int?,
        pageSize: Int?,
        sortDirection: String?,
        sortParam: String?
    ): PaginationResponseDto {
        val dir = sortDirection?:"asc"
        val pN = pageNo?:0
        val pS = pageSize?:10

        val sort = if(dir == "asc")
            Sort.by(sortParam?:"name")
            else
            Sort.by(sortParam?:"name").descending()

        val aggregation: Aggregation = newAggregation(
            match(Criteria.where("institutionId").`is`(institutionId)),
            group("userId").first("name").`as`("name").first("email").`as`("email"),
            sort(sort),
            skip((pN) * (pS).toLong()),
            limit(pS.toLong())
        )

        val countAggregation: Aggregation = newAggregation(
            match(Criteria.where("institutionId").`is`(institutionId)),
            group("userId").first("name").`as`("name").first("email").`as`("email"),
            group().count().`as`("count")
        )

        val commonUsersList = getCommonUsers()

        val result = mongoTemplate.aggregate(aggregation, "user_education_history", UserDto::class.java)
        val count = mongoTemplate.aggregate(countAggregation, "user_education_history", CountDto::class.java)
        val totalElements = count.toList()[0].count
        val totalPages = totalElements / pS

        val finalList  = ArrayList<UserDto>()
        val topUsersList = ArrayList<UserDto>()
        val othersList= ArrayList<UserDto>()
        result.toList().map {
            userDto ->
            if(userDto._id in commonUsersList)
                topUsersList.add(userDto)
            else
                othersList.add(userDto)
        }

        finalList.addAll(topUsersList)
        finalList.addAll(othersList)

        return PaginationResponseDto(
           users = finalList,
           totalPages = if(totalPages == 0L) 1 else totalElements / (pS),
           prevPage = if(pN == 0) 0 else (pN-1),
            nextPage = if(pN*pS + pS >= totalElements) pN else (pN+1),
            currentPage = pN
        )
    }

    fun getCommonUsers() : List<Long>{
        return listOf(3,4,5,6,7,8,9,10)
    }
}