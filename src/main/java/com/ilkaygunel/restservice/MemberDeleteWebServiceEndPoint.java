package com.ilkaygunel.restservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.service.MemberDeleteService;
import com.ilkaygunel.wrapper.MemberIdWrapp;

@RestController
@RequestMapping("/memberDeleteWebServiceEndPoint")
public class MemberDeleteWebServiceEndPoint {

	@Autowired
	private MemberDeleteService memberDeleteService;

	@DeleteMapping("/deleteUserMember")
	public ResponseEntity<MemberOperationPojo> deleteUserMember(@RequestBody List<MemberIdWrapp> memberIdList) throws Exception {
		MemberOperationPojo memberOperationPojo = memberDeleteService.deleteUserMember(memberIdList);
		return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteAdminMember", method = RequestMethod.DELETE)
	public ResponseEntity<MemberOperationPojo> deleteAdminMember(@RequestBody List<MemberIdWrapp> memberIdList) throws Exception {
		MemberOperationPojo memberOperationPojo = memberDeleteService.deleteAdminMember(memberIdList);
		return new ResponseEntity<>(memberOperationPojo, HttpStatus.OK);
	}
}
