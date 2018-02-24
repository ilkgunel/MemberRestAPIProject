package com.ilkaygunel.service;

import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.repository.MemberRepository;

@Service
public class MemberPostService {

	private final static Logger LOGGER = Logger.getLogger(MemberPostService.class.getName());

	@Autowired
	private MemberRepository memberRepository;

	public void addOneMember(Member member) {
		Handler fileHandler = null;
		Formatter simpleFormatter = null;
		try {
			memberRepository.save(member);
			fileHandler = new FileHandler("./oneMemberSaving.log");
			simpleFormatter = new SimpleFormatter();
			fileHandler.setLevel(Level.ALL);
			LOGGER.addHandler(fileHandler);
			fileHandler.setFormatter(simpleFormatter);
			LOGGER.log(Level.INFO, "One member adding method is running!");
			LOGGER.log(Level.INFO, "Member saving is successfull. Member info is:" + member);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "An error occured while saving member. Error is:" + ex.getMessage());
		}

	}

	public void addBulkMember(List<Member> memberList) {
		LOGGER.log(Level.INFO, "Bulk member adding method is running!");
		try {
			memberRepository.save(memberList);
			LOGGER.log(Level.INFO, "Bulk member saving is successfull. Members info are:" + memberList);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "An error occured while saving member. Error is:" + ex.getMessage());
		}
	}
}
