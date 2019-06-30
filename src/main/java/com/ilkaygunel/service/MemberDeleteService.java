package com.ilkaygunel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ilkaygunel.constants.ConstantFields;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.wrapper.MemberIdWrapp;

@Service
public class MemberDeleteService extends BaseService {

    public MemberOperationPojo deleteUserMember(List<MemberIdWrapp> memberIdList) throws Exception {
        return deleteBulkMember(memberIdList, ConstantFields.ROLE_USER.getConstantField());
    }

    public MemberOperationPojo deleteAdminMember(List<MemberIdWrapp> memberIdList) throws Exception {
        return deleteBulkMember(memberIdList, ConstantFields.ROLE_ADMIN.getConstantField());
    }

    private MemberOperationPojo deleteBulkMember(List<MemberIdWrapp> memberIdList, String roleForCheck) throws Exception {
        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
        //
        Logger LOGGER = loggingUtil.getLoggerForMemberDeleting(this.getClass());
        //
        MemberOperationPojo deleteMemberOpertaionPojo = memberUtil.checkMemberExistenceOnMemberList(memberIdList,
                roleForCheck);
        try {
            if (ObjectUtils.isEmpty(deleteMemberOpertaionPojo.getErrorCode())) {
                List<Member> deletedMemberList = new ArrayList<>();
                for (MemberIdWrapp memberIdWrapp : memberIdList) {
                    MemberOperationPojo temporaryMemberOperationPojo = deleteOneMember(memberIdWrapp.getId());
                    deletedMemberList.add(temporaryMemberOperationPojo.getMember());
                }
                memberOperationPojo.setResult(
                        resourceBundleMessageManager.getValueOfProperty(roleForCheck + "_bulkMemberDeletingSuccessfull",
                                deletedMemberList.get(0).getMemberLanguageCode()));
                memberOperationPojo.setMemberList(deletedMemberList);

            } else {
                memberOperationPojo.setErrorCode(deleteMemberOpertaionPojo.getErrorCode());
                memberOperationPojo.setResult(deleteMemberOpertaionPojo.getResult());
            }
        } catch (CustomException e) {
            LOGGER.log(Level.SEVERE, environment.getProperty(roleForCheck + "_memberDeletingFailed") + e.getErrorCode()
                    + " " + e.getErrorMessage());
            memberOperationPojo.setErrorCode(e.getErrorCode());
            memberOperationPojo.setResult(e.getErrorMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, environment.getProperty(roleForCheck + "_memberDeletingFailed") + e.getMessage());
            memberOperationPojo.setResult(e.getMessage());
        }

        return memberOperationPojo;
    }

    private MemberOperationPojo deleteOneMember(long memberId) throws CustomException, Exception {
        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
        Optional<Member> memberForDelete = memberRepository.findById(memberId);
        memberForDelete.ifPresent(member -> memberRepository.delete(member));
        memberForDelete.ifPresent(member -> memberOperationPojo.setMember(member));
        return memberOperationPojo;
    }

}
