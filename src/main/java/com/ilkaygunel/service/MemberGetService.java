package com.ilkaygunel.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.repository.MemberRepository;

@Service
public class MemberGetService extends BaseService {
	@Autowired
	private MemberRepository memberRepository;

	public List<Member> getAllMemberList() {
		Logger LOGGER = loggingUtil.getLoggerForMemberGetting(this.getClass());
		try {
			LOGGER.log(Level.INFO, "getAllMemberList() method is running.");
			return (List<Member>) memberRepository.findAll();
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "An error occured while getting all members. Error is:" + ex.getMessage());
		}
		return null;
	}

	public Member getMemberViaId(long id) {
		Logger LOGGER = loggingUtil.getLoggerForMemberGetting(this.getClass());
		try {
			LOGGER.log(Level.INFO, "getMemberViaId(...) method is running.");
			return memberRepository.findById(id).orElse(null);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "An error occured while getting member via id. Error is:" + ex.getMessage());
		}
		return null;
	}

	public Member getMemberViaFirstName(String firstName) {
		Logger LOGGER = loggingUtil.getLoggerForMemberGetting(this.getClass());
		try {
			LOGGER.log(Level.INFO, "getMemberViaFirstName(...) method is running.");
			return memberRepository.findByFirstName(firstName);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE,
					"An error occured while getting member via first name. Error is:" + ex.getMessage());
		}
		return null;
	}
}
