package com.example.dankapp.controllers

import com.example.dankapp.dtos.request.ReqUserEducationHistoryDto
import com.example.dankapp.dtos.response.PaginationResponseDto
import com.example.dankapp.dtos.response.UserDto
import com.example.dankapp.models.UserEducationHistory
import com.example.dankapp.services.impl.UserEducationHistoryServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users/history")
class UserEducationHistoryController(
    @Autowired private val userEducationHistoryService: UserEducationHistoryServiceImpl
) {

    @PostMapping("/add")
    fun addUserEducation(@RequestBody request : ReqUserEducationHistoryDto): UserEducationHistory {
        return userEducationHistoryService.addUserEducation(request);
    }

    @GetMapping("/{id}")
    fun getRecord(@PathVariable id: String): UserEducationHistory {
        return userEducationHistoryService.getRecordById(id);
    }

    @GetMapping("/user/{id}")
    fun getUserRecords(@PathVariable id: Long): List<UserEducationHistory>{
        return userEducationHistoryService.getRecordsByUserId(id);
    }

    @PutMapping("/update/{id}")
    fun updateRecord(@PathVariable id:String, @RequestBody request : ReqUserEducationHistoryDto): UserEducationHistory{
        return userEducationHistoryService.updateRecord(id, request);
    }

    @DeleteMapping("/delete/{id}")
    fun deleteRecord(@PathVariable id:String) : String{
        return userEducationHistoryService.deleteRecord(id);
    }

    @GetMapping("/")
    fun getUniqueUsers(
        @RequestParam("institution_id", required = true) institutionId: Long,
        @RequestParam("page_size", required = false) pageSize: Int?,
        @RequestParam("page_no", required = false) pageNo: Int?,
        @RequestParam("sort_direction", required = false) sortDirection: String?,
        @RequestParam("sort_param", required = false) sortParam: String?
        ) : PaginationResponseDto {
            return userEducationHistoryService.getUniqueUser(institutionId, pageNo,pageSize, sortDirection, sortParam)
       }

}