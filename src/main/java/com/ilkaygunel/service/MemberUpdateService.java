package com.ilkaygunel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ilkaygunel.constants.ConstantFields;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.wrapper.MemberIdWrapp;

@Service
public class MemberUpdateService extends BaseService {

	public MemberOperationPojo updateOneUserMember(List<Member> memberListForUpdate) {
		return updateBulkMember(memberListForUpdate, ConstantFields.ROLE_USER.getConstantField());
	}

	public MemberOperationPojo updateOneAdminMember(List<Member> memberListForUpdate) {
		return updateBulkMember(memberListForUpdate, ConstantFields.ROLE_ADMIN.getConstantField());
	}

	public MemberOperationPojo updateBulkUserMember(List<Member> memberListForUpdate) {
		return updateBulkMember(memberListForUpdate, ConstantFields.ROLE_USER.getConstantField());
	}

	public MemberOperationPojo updateBulkAdminMember(List<Member> memberListForUpdate) {
		return updateBulkMember(memberListForUpdate, ConstantFields.ROLE_ADMIN.getConstantField());
	}

	private void updateMember(Member memberForUpdate, String roleForCheck) {
		Logger LOGGER = loggingUtil.getLoggerForMemberUpdating(this.getClass());
		try {
			LOGGER.log(Level.INFO, resourceBundleMessageManager.getValueOfProperty(
					roleForCheck + "_memberUpdatingMethod", memberForUpdate.getMemberLanguageCode()));
			LOGGER.log(Level.INFO, resourceBundleMessageManager.getValueOfProperty("memberInformationBeforeUpdate",
					memberForUpdate.getMemberLanguageCode()));
			memberRepository.save(memberForUpdate);
			LOGGER.log(Level.INFO,
					resourceBundleMessageManager.getValueOfProperty(roleForCheck + "_memberUpdatingSuccessful",
							memberForUpdate.getMemberLanguageCode())
							+ memberRepository.findOne(memberForUpdate.getId()));
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE,
					resourceBundleMessageManager.getValueOfProperty(roleForCheck + "_memberUpdatingFailed",
							memberForUpdate.getMemberLanguageCode()) + memberRepository.findOne(memberForUpdate.getId())
							+ ex.getMessage());
		}
	}

	private MemberOperationPojo updateBulkMember(List<Member> memberListForUpdate, String role) {
		// Create memberIdList and check member existence
		List<MemberIdWrapp> memberIdList = new ArrayList<>();
		for (Member member : memberListForUpdate) {
			MemberIdWrapp memberIdWrapp = new MemberIdWrapp();
			memberIdWrapp.setId(member.getId());
			memberIdList.add(memberIdWrapp);
		}
		MemberOperationPojo pojoForMemberExistingChecking = memberUtil.checkMemberExistenceOnMemberList(memberIdList,
				role);
		// Get logger
		Logger LOGGER = loggingUtil.getLoggerForMemberUpdating(this.getClass());
		//
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Member memberForUpdate = null;

		if (ObjectUtils.isEmpty(pojoForMemberExistingChecking.getErrorCode())) {
			try {
				List<Member> updatedMemberList = new ArrayList<>();
				for (Member member : memberListForUpdate) {
					memberForUpdate = member;
					updateMember(memberForUpdate, role);
					updatedMemberList.add(memberForUpdate);
				}
				memberOperationPojo.setResult(ObjectUtils.getDisplayString(memberOperationPojo.getResult()) + " "
						+ resourceBundleMessageManager.getValueOfProperty(role + "_memberUpdatingSuccessful",
								updatedMemberList.get(0).getMemberLanguageCode()));
				memberOperationPojo.setMemberList(updatedMemberList);
			} catch (Exception ex) {
				LOGGER.log(Level.SEVERE,
						resourceBundleMessageManager.getValueOfProperty(role + "_memberUpdatingFailed",
								memberForUpdate.getMemberLanguageCode())
								+ memberRepository.findOne(memberForUpdate.getId()) + ex.getMessage());
			}

		} else {
			memberOperationPojo.setErrorCode(pojoForMemberExistingChecking.getErrorCode());
			memberOperationPojo.setResult(pojoForMemberExistingChecking.getResult());
		}
		return memberOperationPojo;
	}
}
