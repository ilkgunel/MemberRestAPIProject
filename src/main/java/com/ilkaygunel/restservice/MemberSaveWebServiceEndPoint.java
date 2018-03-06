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
import com.ilkaygunel.service.MemberPostService;

@RestController
@RequestMapping("/memberPostWebServiceEndPoint")
public class MemberSaveWebServiceEndPoint {
	@Autowired
	private MemberPostService memberPostService;

	@RequestMapping(value = "/saveOneUserMember", method = RequestMethod.POST)
	public ResponseEntity<MemberRegisterOrUpdatePojo> saveOneUserMember(@RequestBody Member member) {
		MemberRegisterOrUpdatePojo memberRegisterOrUpdatePojo = new MemberRegisterOrUpdatePojo();
		memberRegisterOrUpdatePojo.setMember(member);
		try {
			memberPostService.addOneUserMember(member);
			memberRegisterOrUpdatePojo.setRegistirationResult("Successfull");
		} catch (Exception ex) {
			memberRegisterOrUpdatePojo.setRegistirationResult("Failed! The problem is:" + ex);
		}
		return new ResponseEntity<MemberRegisterOrUpdatePojo>(memberRegisterOrUpdatePojo, HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveOneAdminMember",method=RequestMethod.POST)
	public ResponseEntity<MemberRegisterOrUpdatePojo> saveOneAdminMember(@RequestBody Member member){
		MemberRegisterOrUpdatePojo memberRegisterOrUpdatePojo = new MemberRegisterOrUpdatePojo();
		memberRegisterOrUpdatePojo.setMember(member);
		try {
			memberPostService.addOneAdminMember(member);
			memberRegisterOrUpdatePojo.setRegistirationResult("Successfull");
		} catch (Exception ex) {
			memberRegisterOrUpdatePojo.setRegistirationResult("Failed! The problem is:" + ex);
		}
		return new ResponseEntity<MemberRegisterOrUpdatePojo>(memberRegisterOrUpdatePojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveBulkUserMember", method = RequestMethod.POST)
	public ResponseEntity<MemberRegisterOrUpdatePojo> saveBulkUserMember(@RequestBody List<Member> memberList) {
		MemberRegisterOrUpdatePojo memberRegisterOrUpdatePojo = new MemberRegisterOrUpdatePojo();
		memberRegisterOrUpdatePojo.setMemberList(memberList);
		try {
			memberPostService.addBulkUserMember(memberList);
			memberRegisterOrUpdatePojo.setRegistirationResult("Successfull!");
		} catch (Exception e) {
			memberRegisterOrUpdatePojo.setRegistirationResult("Fail! The problem is:" + e);
		}
		return new ResponseEntity<MemberRegisterOrUpdatePojo>(memberRegisterOrUpdatePojo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveBulkAdminMember", method = RequestMethod.POST)
	public ResponseEntity<MemberRegisterOrUpdatePojo> saveBulkAdminMember(@RequestBody List<Member> memberList) {
		MemberRegisterOrUpdatePojo memberRegisterOrUpdatePojo = new MemberRegisterOrUpdatePojo();
		memberRegisterOrUpdatePojo.setMemberList(memberList);
		try {
			memberPostService.addBulkAdminMember(memberList);
			memberRegisterOrUpdatePojo.setRegistirationResult("Successfull!");
		} catch (Exception e) {
			memberRegisterOrUpdatePojo.setRegistirationResult("Fail! The problem is:" + e);
		}
		return new ResponseEntity<MemberRegisterOrUpdatePojo>(memberRegisterOrUpdatePojo, HttpStatus.OK);
	}
}
