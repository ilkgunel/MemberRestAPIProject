package com.ilkaygunel.restservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.pojo.MemberRegisterPojo;
import com.ilkaygunel.service.MemberService;

@RestController
public class MemberSaveWebServiceEndPoint {
	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/saveOneMember", method = RequestMethod.POST)
	public ResponseEntity<MemberRegisterPojo> saveOneMember(@RequestBody Member member) {
		MemberRegisterPojo memberRegisterPojo = new MemberRegisterPojo();
		memberRegisterPojo.setMember(member);
		try {
			memberService.addMember(member);
			memberRegisterPojo.setRegistirationResult("Successfull");
		} catch (Exception ex) {
			memberRegisterPojo.setRegistirationResult("Failed! The problem is:" + ex);
		}
		return new ResponseEntity<MemberRegisterPojo>(memberRegisterPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveBulkMember", method = RequestMethod.POST)
	public ResponseEntity<MemberRegisterPojo> saveBulkMember(@RequestBody List<Member> memberList) {
		MemberRegisterPojo memberRegisterPojo = new MemberRegisterPojo();
		memberRegisterPojo.setMemberList(memberList);
		try {
			memberService.addBulkMember(memberList);
			memberRegisterPojo.setRegistirationResult("Successfull!");
		} catch (Exception e) {
			memberRegisterPojo.setRegistirationResult("Fail! The problem is:" + e);
		}
		return new ResponseEntity<MemberRegisterPojo>(memberRegisterPojo, HttpStatus.OK);
	}
}
