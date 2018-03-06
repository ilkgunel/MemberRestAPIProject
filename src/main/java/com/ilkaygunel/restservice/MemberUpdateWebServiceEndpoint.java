package com.ilkaygunel.restservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.pojo.MemberRegisterOrUpdatePojo;
import com.ilkaygunel.service.MemberUpdateService;

@RestController
@RequestMapping("/memberUpdateWebServiceEndpoint")
public class MemberUpdateWebServiceEndpoint {

	@Autowired
	private MemberUpdateService memberUpdateServices;

	@RequestMapping(value = "/updateOneMember", method = RequestMethod.PUT)
	private ResponseEntity<MemberRegisterOrUpdatePojo> updateOneMember(@RequestBody Member member) {
		MemberRegisterOrUpdatePojo memberRegisterOrUpdatePojo = new MemberRegisterOrUpdatePojo();
		memberRegisterOrUpdatePojo.setMember(member);
		try {
			memberUpdateServices.updateOneMember(member);
			memberRegisterOrUpdatePojo.setRegistirationResult("Successfull");
		} catch (Exception ex) {
			memberRegisterOrUpdatePojo.setRegistirationResult("Failed! The problem is:" + ex);
		}
		return new ResponseEntity<MemberRegisterOrUpdatePojo>(memberRegisterOrUpdatePojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateBulkMember", method = RequestMethod.PUT)
	private ResponseEntity<MemberRegisterOrUpdatePojo> updateBulkMember(@RequestBody List<Member> memberListForUpdate) {
		MemberRegisterOrUpdatePojo memberRegisterOrUpdatePojo = new MemberRegisterOrUpdatePojo();
		memberRegisterOrUpdatePojo.setMemberList(memberListForUpdate);
		try {
			memberUpdateServices.updateBulkMember(memberListForUpdate);
			memberRegisterOrUpdatePojo.setRegistirationResult("Successfull");
		} catch (Exception e) {
			memberRegisterOrUpdatePojo.setRegistirationResult("Failed! The problem is:" + e);
		}
		return new ResponseEntity<MemberRegisterOrUpdatePojo>(memberRegisterOrUpdatePojo, HttpStatus.OK);
	}

}
