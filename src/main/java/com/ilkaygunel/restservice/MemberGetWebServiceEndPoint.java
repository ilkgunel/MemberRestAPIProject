package com.ilkaygunel.restservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.service.MemberGetService;

@RestController
@RequestMapping("/memberGetWebServiceEndPoint")
public class MemberGetWebServiceEndPoint {
	@Autowired
	private MemberGetService memberGetService;

	@RequestMapping(value = "/getAllMembers", method = RequestMethod.GET)
	public List<Member> getAllMemberList() {
		return memberGetService.getAllMemberList();
	}

	@RequestMapping(value = "/getMemberViaId/{memberid}", method = RequestMethod.GET)
	public Member getMemberViaId(@PathVariable("memberid") long memberId) {
		return memberGetService.getMemberViaId(memberId);
	}

	@RequestMapping(value = "/getMemberViaFirstName")
	public Member getMemberViaFirstName(@RequestParam(value = "firstName", defaultValue = "") String memberName) {
		return memberGetService.getMemberViaFirstName(memberName);
	}
}
