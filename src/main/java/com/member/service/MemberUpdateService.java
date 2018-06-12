package com.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.member.constants.ConstantFields;
import com.member.entity.Member;
import com.member.exception.ErrorCodes;
import com.member.pojo.MemberOperationPojo;

@Service
public class MemberUpdateService extends BaseService {

    private Logger logger = Logger.getLogger(MemberUpdateService.class.getName());

    public MemberOperationPojo updateUserMember(List<Member> memberListForUpdate) {
        return updateBulkMember(memberListForUpdate, ConstantFields.ROLE_USER.getConstantField());
    }

    public MemberOperationPojo updateAdminMember(List<Member> memberListForUpdate) {
        return updateBulkMember(memberListForUpdate, ConstantFields.ROLE_ADMIN.getConstantField());
    }

    private MemberOperationPojo updateBulkMember(List<Member> memberListForUpdate, String role) {
        MemberOperationPojo pojoForMemberExistingChecking = memberUtil
                .checkMemberExistenceOnMemberList(memberUtil.getMemberIdListFromMemberList(memberListForUpdate), role);
        MemberOperationPojo pojoForPasswordContainsChecking = memberUtil
                .checkMemberListContainPassowrdForUpdate(memberListForUpdate);

        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();

        if (!ObjectUtils.isEmpty(pojoForMemberExistingChecking.getErrorCode())) {
            memberOperationPojo.setErrorCode(pojoForMemberExistingChecking.getErrorCode());
            memberOperationPojo.setResult(pojoForMemberExistingChecking.getResult());
        } else if (!ObjectUtils.isEmpty(pojoForPasswordContainsChecking.getErrorCode())) {
            memberOperationPojo.setErrorCode(pojoForPasswordContainsChecking.getErrorCode());
            memberOperationPojo.setResult(pojoForPasswordContainsChecking.getResult());
        } else {
            Member memberForUpdate = null;
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
                memberOperationPojo.setMemberList(memberUtil.removeFieldsFromReturningMember(updatedMemberList));
            } catch (Exception ex) {
                memberOperationPojo.setErrorCode(ErrorCodes.ERROR_10.getErrorCode());
                memberOperationPojo.setResult(ex.getMessage());
                logger.log(Level.SEVERE,
                        resourceBundleMessageManager.getValueOfProperty(role + "_memberUpdatingFailed",
                                memberForUpdate.getMemberLanguageCode())
                                + memberRepository.findOne(memberForUpdate.getId()) + ex.getMessage());
            }

        }
        return memberOperationPojo;
    }

    private void updateMember(Member memberForUpdate, String roleForCheck) {
        logger.log(Level.INFO, resourceBundleMessageManager.getValueOfProperty(roleForCheck + "_memberUpdatingMethod",
                memberForUpdate.getMemberLanguageCode()));
        logger.log(Level.INFO, resourceBundleMessageManager.getValueOfProperty("memberInformationBeforeUpdate",
                memberForUpdate.getMemberLanguageCode()));

        Member member = memberRepository.findOne(memberForUpdate.getId());

        memberForUpdate.setPassword(member.getPassword());
        memberForUpdate.setEnabled(member.isEnabled());
        memberRepository.save(memberForUpdate);

        logger.log(Level.INFO,
                resourceBundleMessageManager.getValueOfProperty(roleForCheck + "_memberUpdatingSuccessful",
                        memberForUpdate.getMemberLanguageCode()) + member);

    }
}
