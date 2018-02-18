package com.ilkaygunel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	public void addMember(Member member) {
		try {
			memberRepository.save(member);
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

	public void addBulkMember(List<Member> memberList) {
		try {
			memberRepository.save(memberList);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
