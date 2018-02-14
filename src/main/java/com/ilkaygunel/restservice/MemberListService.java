package com.ilkaygunel.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.service.MemberService;

@RestController
public class MemberListService {
	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/getOneMember", method = RequestMethod.GET)
	public String getOneMember() {

		Member member = new Member();
		member.setFirstName("ilkay");
		member.setLastName("günel");

		memberService.addMember(member);
		return "deneme";
	}
}
