package com.ilkaygunel.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ilkaygunel.logging.LoggingUtil;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.repository.MemberRepository;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.*;

@Service
public class MemberDeleteService {
	@Autowired
	private MemberRepository memberRepository;

	public MemberOperationPojo deleteOneUserMember(long memberId) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Logger LOGGER = new LoggingUtil().getLoggerForMemberDeleting(this.getClass());
		try {
			LOGGER.log(Level.INFO, "One user member deleting method is running!");
			if (memberRepository.findOne(memberId) == null) {
				throw new CustomException("ERROR-01", "There is no user member in the system with this member id!");
			}
			memberRepository.delete(memberId);
			Member updatedMember = memberRepository.findOne(memberId);
			memberOperationPojo.setResult("One user member deleting is successfull. Member info is:" + updatedMember);
			LOGGER.log(Level.INFO, "One user member deleting is successfull. Member info is:" + updatedMember);
		} catch (CustomException e) {
			LOGGER.log(Level.SEVERE, "An error occured while deleting user member. Error is:" + e.getMessage());
			memberOperationPojo.setErrorCode(e.getErrorCode());
			memberOperationPojo.setResult(e.getErrorMessage());
		}
		return memberOperationPojo;
	}

	public void deleteOneAdminMember(long memberId) {
		Logger LOGGER = new LoggingUtil().getLoggerForMemberDeleting(this.getClass());
		try {
			LOGGER.log(Level.INFO, "One admin member deleting method is running!");
			if (memberRepository.findOne(memberId) == null) {
				throw new Exception("There is no admin member in the system with this member id!");
			}
			memberRepository.delete(memberId);
			LOGGER.log(Level.INFO, "One admin member deleting is successfull. Member info is:");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "An error occured while deleting admin member. Error is:" + e.getMessage());
		}
	}
}
