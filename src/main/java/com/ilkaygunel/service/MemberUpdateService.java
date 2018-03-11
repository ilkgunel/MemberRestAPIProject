package com.ilkaygunel.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.repository.MemberRepository;
import com.ilkaygunel.util.LoggingUtil;

@Service
public class MemberUpdateService {
	@Autowired
	private MemberRepository memberRepository;

	public void updateOneMember(Member memberForUpdate) {
		Logger LOGGER = new LoggingUtil().getLoggerForMemberUpdating(this.getClass());
		try {
			LOGGER.log(Level.INFO, "One Member Update Method Is Running!");
			LOGGER.log(Level.INFO,
					"Member information before updating:" + memberRepository.findOne(memberForUpdate.getId()));
			memberRepository.save(memberForUpdate);
			LOGGER.log(Level.INFO,
					"Member information after updating:" + memberRepository.findOne(memberForUpdate.getId()));
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "An error occured while updating member. Error is:" + ex.getMessage());
		}
	}

	public void updateBulkMember(List<Member> memberListForUpdate) {
		Logger LOGGER = new LoggingUtil().getLoggerForMemberUpdating(this.getClass());
		List<Long> idList = memberListForUpdate.stream().map(Member::getId).collect(Collectors.toList());
		try {
			LOGGER.log(Level.INFO, "Bulk Member Update Method Is Running!");
			LOGGER.log(Level.INFO, "Member informations before updating:" + memberRepository.findAll(idList));
			memberRepository.save(memberListForUpdate);
			LOGGER.log(Level.INFO, "Member information after updating:" + memberRepository.findAll(idList));
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "An error occured while updating bulk member. Error is:" + e.getMessage());
		}
	}
}
