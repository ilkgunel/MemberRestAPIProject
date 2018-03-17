package com.ilkaygunel.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ilkaygunel.constants.ConstantFields;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.repository.MemberRepository;
import com.ilkaygunel.util.LoggingUtil;
import com.ilkaygunel.wrapper.MemberIdWrapp;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:errorMeanings.properties")
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messageTexts.properties")
@Service
public class MemberDeleteService {
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private Environment environment;

	public MemberOperationPojo deleteOneUserMember(long memberId) {
		MemberOperationPojo memberOperationPojo = deleteOneMember(memberId, ConstantFields.ROLE_USER);
		return memberOperationPojo;
	}

	public MemberOperationPojo deleteOneAdminMember(long memberId) {
		MemberOperationPojo memberOperationPojo = deleteOneMember(memberId, ConstantFields.ROLE_ADMIN);
		return memberOperationPojo;
	}

	public MemberOperationPojo deleteBulkUserMember(List<MemberIdWrapp> memberIdList) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		for (MemberIdWrapp memberIdWrapp : memberIdList) {
			MemberOperationPojo temporaryMemberOperationPojo = deleteOneMember(memberIdWrapp.getId(),
					ConstantFields.ROLE_USER);
			memberOperationPojo.setResult(ObjectUtils.getDisplayString(memberOperationPojo.getResult()) + " "
					+ environment.getProperty(ConstantFields.ROLE_USER + "_bulkMemberDeletingSuccessfull")
					+ temporaryMemberOperationPojo.getMember());
		}

		return memberOperationPojo;
	}

	public MemberOperationPojo deleteBulkAdminMember(List<MemberIdWrapp> memberIdList) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		for (MemberIdWrapp memberIdWrapp : memberIdList) {
			MemberOperationPojo temporaryMemberOperationPojo = deleteOneMember(memberIdWrapp.getId(),
					ConstantFields.ROLE_ADMIN);
			memberOperationPojo.setResult(ObjectUtils.getDisplayString(memberOperationPojo.getResult()) + " "
					+ environment.getProperty(ConstantFields.ROLE_ADMIN + "_bulkMemberDeletingSuccessfull")
					+ temporaryMemberOperationPojo.getMember());
		}
		return memberOperationPojo;
	}

	private Member checkMember(Long memberId, String roleForCheck) throws CustomException, Exception {
		Member memberForDelete = memberRepository.findOne(memberId);
		if (memberForDelete == null) {
			if (ConstantFields.ROLE_USER.equals(roleForCheck)) {
				throw new CustomException(ErrorCodes.ERROR_01, environment.getProperty(ErrorCodes.ERROR_01));
			} else {
				throw new CustomException(ErrorCodes.ERROR_02, environment.getProperty(ErrorCodes.ERROR_02));
			}
		} else if (ConstantFields.ROLE_USER.equals(roleForCheck)
				&& !ConstantFields.ROLE_USER.equals(memberForDelete.getRole())) {
			throw new CustomException(ErrorCodes.ERROR_03, environment.getProperty(ErrorCodes.ERROR_03));
		} else if (ConstantFields.ROLE_ADMIN.equals(roleForCheck)
				&& !ConstantFields.ROLE_ADMIN.equals(memberForDelete.getRole())) {
			throw new CustomException(ErrorCodes.ERROR_04, environment.getProperty(ErrorCodes.ERROR_04));
		}
		memberRepository.delete(memberId);
		return memberForDelete;
	}

	public MemberOperationPojo deleteOneMember(long memberId, String roleOfMember) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Logger LOGGER = new LoggingUtil().getLoggerForMemberDeleting(this.getClass());
		try {
			LOGGER.log(Level.INFO, environment.getProperty(roleOfMember + "_memberDeletingMethod"));
			Member memberForDelete = checkMember(memberId, ConstantFields.ROLE_USER);
			memberOperationPojo.setMember(memberForDelete);
			memberOperationPojo
					.setResult(environment.getProperty(roleOfMember + "_memberDeletingSuccessfull") + memberForDelete);
			LOGGER.log(Level.INFO,
					environment.getProperty(roleOfMember + "_memberDeletingSuccessfull") + memberForDelete);
		} catch (CustomException e) {
			LOGGER.log(Level.SEVERE, environment.getProperty(roleOfMember + "_memberDeletingFailed") + e.getErrorCode()
					+ " " + e.getErrorMessage());
			memberOperationPojo.setErrorCode(e.getErrorCode());
			memberOperationPojo.setResult(e.getErrorMessage());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, environment.getProperty(roleOfMember + "_memberDeletingFailed") + e.getMessage());
			memberOperationPojo.setResult(e.getMessage());
		}
		return memberOperationPojo;
	}
}
