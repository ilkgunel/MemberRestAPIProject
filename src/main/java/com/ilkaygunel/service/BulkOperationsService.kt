package com.ilkaygunel.service

import com.ilkaygunel.constants.ConstantFields
import com.ilkaygunel.entities.Member
import com.ilkaygunel.pojo.MemberOperationPojo
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.*

@Service
open class BulkOperationsService : BaseService() {

    open fun addBulkUserMember(file: MultipartFile): MemberOperationPojo {
        val members = processFile(file)
        val memberOperationPojo: MemberOperationPojo
        memberOperationPojo = memberSaveService.addBulkMember(members, ConstantFields.ROLE_USER.constantField)
        return memberOperationPojo
    }

    open fun addBulkAdminMember(file: MultipartFile): MemberOperationPojo {
        val members = processFile(file)
        val memberOperationPojo: MemberOperationPojo
        memberOperationPojo = memberSaveService.addBulkMember(members, ConstantFields.ROLE_ADMIN.constantField)
        return memberOperationPojo
    }

    open fun processFile(file: MultipartFile): ArrayList<Member> {
        var fileReader: BufferedReader? = null
        var inputStream: InputStream?
        val members = ArrayList<Member>()
        try {
            var line: String?
            inputStream = file.inputStream

            fileReader = BufferedReader(InputStreamReader(inputStream))
            line = fileReader.readLine()
            while (line != null) {
                val memberInfos = line.split(",")
                if (memberInfos.isNotEmpty()) {
                    val member = Member()
                    member.firstName = memberInfos[0]
                    member.lastName = memberInfos[1]
                    member.email = memberInfos[2]
                    member.password = memberInfos[3]
                    member.memberLanguageCode = memberInfos[4]

                    members.add(member)
                }
                line = fileReader.readLine()
            }
        } catch (ioException: IOException) {
            println("Error on reading CSV File!$ioException")
        } finally {
            fileReader!!.close()
        }
        return members
    }
}