package com.ilkaygunel.restservice

import com.ilkaygunel.pojo.MemberOperationPojo
import com.ilkaygunel.service.BulkOperationsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/bulk")
open class BulkOperationsWebServiceEndPoint @Autowired constructor(private val bulkOperationService: BulkOperationsService) {

    @RequestMapping("/add/user")
    open fun bulkAddingUser(@RequestParam("file") file: MultipartFile): ResponseEntity<MemberOperationPojo> {
        var memberOperationPojo: MemberOperationPojo = bulkOperationService.addBulkUserMember(file)
        return ResponseEntity(memberOperationPojo, HttpStatus.OK)
    }

    @RequestMapping("/add/admin")
    open fun bulkAddingAdmin(@RequestParam("file") file: MultipartFile): ResponseEntity<MemberOperationPojo> {
        var memberOperationPojo: MemberOperationPojo = bulkOperationService.addBulkAdminMember(file)
        return ResponseEntity(memberOperationPojo, HttpStatus.OK)
    }
}