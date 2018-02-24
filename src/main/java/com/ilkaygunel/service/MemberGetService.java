package com.ilkaygunel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.repository.MemberRepository;

@Service
public class MemberGetService {
	@Autowired
	private MemberRepository memberRepository;

	public List<Member> getAllMemberList() {
		return (List<Member>) memberRepository.findAll();
	}

	public Member getMemberViaId(long id) {
		return memberRepository.findOne(id);
	}

	public Member getMemberViaFirstName(String firstName) {
		return memberRepository.findByFirstName(firstName);
	}
}
