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
import com.ilkaygunel.service.MemberSaveService;

@RestController
@RequestMapping("/memberPostWebServiceEndPoint")
public class MemberSaveWebServiceEndPoint {
	@Autowired
	private MemberSaveService memberSaveService;

	@RequestMapping(value = "/saveOneUserMember", method = RequestMethod.POST)
	public ResponseEntity<MemberOperationPojo> saveOneUserMember(@RequestBody Member member) {
		MemberOperationPojo memberOperationPojo = memberSaveService.addOneUserMember(member);
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveOneAdminMember", method = RequestMethod.POST)
	public ResponseEntity<MemberOperationPojo> saveOneAdminMember(@RequestBody Member member) {
		MemberOperationPojo memberOperationPojo = memberSaveService.addOneAdminMember(member);
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveBulkUserMember", method = RequestMethod.POST)
	public ResponseEntity<MemberOperationPojo> saveBulkUserMember(@RequestBody List<Member> memberList) {
		MemberOperationPojo memberOperationPojo = memberSaveService.addBulkUserMember(memberList);
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveBulkAdminMember", method = RequestMethod.POST)
	public ResponseEntity<MemberOperationPojo> saveBulkAdminMember(@RequestBody List<Member> memberList) {
		MemberOperationPojo memberOperationPojo = memberSaveService.addBulkAdminMember(memberList);
		return new ResponseEntity<MemberOperationPojo>(memberOperationPojo, HttpStatus.OK);
	}
}
