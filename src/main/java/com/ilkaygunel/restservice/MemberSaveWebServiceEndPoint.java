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
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.service.MemberPostService;

@RestController
@RequestMapping("/memberPostWebServiceEndPoint")
public class MemberSaveWebServiceEndPoint {
	@Autowired
	private MemberPostService memberPostService;

	@RequestMapping(value = "/saveOneUserMember", method = RequestMethod.POST)
	public ResponseEntity<MemberOperationPojo> saveOneUserMember(@RequestBody Member member) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		memberOperationPojo.setMember(member);
		try {
			memberPostService.addOneUserMember(member);
			memberOperationPojo.setResult("Successfull");
		} catch (Exception ex) {
			memberOperationPojo.setResult("Failed! The problem is:" + ex);
		}
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveOneAdminMember",method=RequestMethod.POST)
	public ResponseEntity<MemberOperationPojo> saveOneAdminMember(@RequestBody Member member){
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		memberOperationPojo.setMember(member);
		try {
			memberPostService.addOneAdminMember(member);
			memberOperationPojo.setResult("Successfull");
		} catch (Exception ex) {
			memberOperationPojo.setResult("Failed! The problem is:" + ex);
		}
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveBulkUserMember", method = RequestMethod.POST)
	public ResponseEntity<MemberOperationPojo> saveBulkUserMember(@RequestBody List<Member> memberList) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		memberOperationPojo.setMemberList(memberList);
		try {
			memberPostService.addBulkUserMember(memberList);
			memberOperationPojo.setResult("Successfull!");
		} catch (Exception e) {
			memberOperationPojo.setResult("Fail! The problem is:" + e);
		}
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveBulkAdminMember", method = RequestMethod.POST)
	public ResponseEntity<MemberOperationPojo> saveBulkAdminMember(@RequestBody List<Member> memberList) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		memberOperationPojo.setMemberList(memberList);
		try {
			memberPostService.addBulkAdminMember(memberList);
			memberOperationPojo.setResult("Successfull!");
		} catch (Exception e) {
			memberOperationPojo.setResult("Fail! The problem is:" + e);
		}
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}
}
