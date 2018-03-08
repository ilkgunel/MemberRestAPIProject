package com.ilkaygunel.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;
import com.ilkaygunel.logging.LoggingUtil;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.repository.MemberRepository;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:errorMeanings.properties")
@Service
public class MemberDeleteService {
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private Environment environment;

	public MemberOperationPojo deleteOneUserMember(long memberId) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Logger LOGGER = new LoggingUtil().getLoggerForMemberDeleting(this.getClass());
		try {
			LOGGER.log(Level.INFO, "One user member deleting method is running!");
			Member memberForDelete = memberRepository.findOne(memberId);
			if (memberForDelete == null) {
				throw new CustomException(ErrorCodes.ERROR_01, environment.getProperty(ErrorCodes.ERROR_01));
			}
			memberRepository.delete(memberId);
			memberOperationPojo.setResult("One user member deleting is successfull. Member info is:" + memberForDelete);
			LOGGER.log(Level.INFO, "One user member deleting is successfull. Member info is:" + memberForDelete);
		} catch (CustomException e) {
			LOGGER.log(Level.SEVERE, "An error occured while deleting user member. Error is:" + e.getErrorCode() + " "
					+ e.getErrorMessage());
			memberOperationPojo.setErrorCode(e.getErrorCode());
			memberOperationPojo.setResult(e.getErrorMessage());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "An error occured while deleting user member. Error is:" + e.getMessage());
			memberOperationPojo.setResult(e.getMessage());
		}
		return memberOperationPojo;
	}

	public MemberOperationPojo deleteOneAdminMember(long memberId) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Logger LOGGER = new LoggingUtil().getLoggerForMemberDeleting(this.getClass());
		try {
			LOGGER.log(Level.INFO, "One admin member deleting method is running!");
			Member memberForDelete = memberRepository.findOne(memberId);
			if (memberForDelete == null) {
				throw new CustomException(ErrorCodes.ERROR_02, environment.getProperty(ErrorCodes.ERROR_02));
			}
			memberRepository.delete(memberId);
			memberOperationPojo
					.setResult("One admin member deleting is successfull. Member info is:" + memberForDelete);
			LOGGER.log(Level.INFO, "One admin member deleting is successfull. Member info is:" + memberForDelete);
		} catch (CustomException e) {
			LOGGER.log(Level.SEVERE, "An error occured while deleting admin member. Error is:" + e.getMessage());
			memberOperationPojo.setErrorCode(e.getErrorCode());
			memberOperationPojo.setResult(e.getErrorMessage());
		}
		return memberOperationPojo;
	}
}
