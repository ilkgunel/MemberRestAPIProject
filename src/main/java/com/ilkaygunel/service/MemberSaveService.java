package com.ilkaygunel.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ilkaygunel.exception.ErrorCodes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ilkaygunel.constants.ConstantFields;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.entities.MemberRoles;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.pojo.MemberOperationPojo;
import org.springframework.util.ObjectUtils;

@Service
public class MemberSaveService extends BaseService {

    public MemberOperationPojo addOneUserMember(Member member) {
        Logger LOGGER = loggingUtil.getLoggerForMemberSaving(this.getClass());
        MemberOperationPojo memberOperationPojo = memberUtil.checkEmailAddress(member, LOGGER);
        if (ObjectUtils.isEmpty(memberOperationPojo.getErrorCode())) {
            memberOperationPojo = addOneMember(member, ConstantFields.ROLE_USER.getConstantField(), LOGGER);
        }
        return memberOperationPojo;
    }

    public MemberOperationPojo addOneAdminMember(Member member) {
        Logger LOGGER = loggingUtil.getLoggerForMemberSaving(this.getClass());
        MemberOperationPojo memberOperationPojo = memberUtil.checkEmailAddress(member, LOGGER);
        if (ObjectUtils.isEmpty(memberOperationPojo.getErrorCode())) {
            memberOperationPojo = addOneMember(member, ConstantFields.ROLE_ADMIN.getConstantField(), LOGGER);
        }
        return memberOperationPojo;
    }

    public MemberOperationPojo addBulkUserMember(List<Member> memberList) {
        Logger LOGGER = loggingUtil.getLoggerForMemberSaving(this.getClass());
        MemberOperationPojo memberOperationPojo;

        try {
            memberUtil.checkEmailAddressOnMemberList(memberList, LOGGER);
            memberOperationPojo = addBulkMember(memberList, ConstantFields.ROLE_USER.getConstantField(), LOGGER);
        } catch (CustomException customException) {
            LOGGER.log(Level.SEVERE,
                    environment.getProperty(ConstantFields.ROLE_USER.getConstantField() + "_memberAddingFaled") + customException.getErrorCode() + " "
                            + customException.getErrorMessage());
            memberOperationPojo = new MemberOperationPojo();
            memberOperationPojo.setErrorCode(customException.getErrorCode());
            memberOperationPojo.setResult(customException.getErrorMessage());
        }
        return memberOperationPojo;
    }

    public MemberOperationPojo addBulkAdminMember(List<Member> memberList) {
        Logger LOGGER = loggingUtil.getLoggerForMemberSaving(this.getClass());
        MemberOperationPojo memberOperationPojo;
        try {
            memberUtil.checkEmailAddressOnMemberList(memberList, LOGGER);
            memberOperationPojo = addBulkMember(memberList, ConstantFields.ROLE_ADMIN.getConstantField(), LOGGER);
        } catch (CustomException customException) {
            LOGGER.log(Level.SEVERE,
                    environment.getProperty(ConstantFields.ROLE_ADMIN.getConstantField() + "_memberAddingFaled") + customException.getErrorCode() + " "
                            + customException.getErrorMessage());
            memberOperationPojo = new MemberOperationPojo();
            memberOperationPojo.setErrorCode(customException.getErrorCode());
            memberOperationPojo.setResult(customException.getErrorMessage());
        }
        return memberOperationPojo;
    }

    public MemberOperationPojo addOneMember(Member member, String role, Logger LOGGER) {
        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
        try {
            LOGGER.log(Level.INFO, applicationConfig.getValueOfProperty(role + "_memberAddingMethod", "tr"));
            member.setPassword(getHashedPassword(member.getPassword()));
            member.setEnabled(false);
            addMemberRolesObject(role, member);
            addActivationToken(member);
            memberRepository.save(member);
            mailUtil.sendActivationMail(member.getEmail(), member.getActivationToken());
            memberOperationPojo.setResult(applicationConfig.getValueOfProperty(role + "_memberAddingSuccessfull","tr"));

            List<Member> memberList = new ArrayList<>();
            memberList.add(member);

            memberOperationPojo.setMemberList(memberList);
            LOGGER.log(Level.INFO, applicationConfig.getValueOfProperty(role + "_memberAddingSuccessfull","tr") + member);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, applicationConfig.getValueOfProperty(role + "_memberAddingFaled","tr") + e.getMessage());
            memberOperationPojo.setErrorCode(ErrorCodes.ERROR_10.getErrorCode());
            memberOperationPojo.setResult(e.getMessage());
        }
        return memberOperationPojo;
    }

    public MemberOperationPojo addBulkMember(List<Member> memberList, String role, Logger LOGGER) {
        LOGGER.log(Level.INFO, environment.getProperty(role + "_bulkMemberAddingMethod"));
        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
        List<Member> savedMemberList = new ArrayList<>();
        try {
            for (Member member : memberList) {
                MemberOperationPojo addOneMemberOperationPojo = addOneMember(member, role, LOGGER);
                if (!ObjectUtils.isEmpty(addOneMemberOperationPojo.getErrorCode())) {
                    throw new CustomException(addOneMemberOperationPojo.getErrorCode(), environment.getProperty(addOneMemberOperationPojo.getErrorCode()));
                }
                savedMemberList.add(member);
            }
            memberOperationPojo.setResult(environment.getProperty(role + "_bulkMemberAddingSuccessfull"));
            memberOperationPojo.setMemberList(savedMemberList);
            LOGGER.log(Level.INFO, environment.getProperty(role + "_bulkMemberAddingSuccessfull") + memberList);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, environment.getProperty(role + "_bulkMemberAddingFaled") + e.getMessage());
            memberOperationPojo.setResult(e.getMessage());
        }
        return memberOperationPojo;
    }

    private String getHashedPassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }

    private void addMemberRolesObject(String role, Member member) {
        MemberRoles rolesOfMember = new MemberRoles();
        rolesOfMember.setRole(role);
        rolesOfMember.setEmail(member.getEmail());
        member.setRoleOfMember(rolesOfMember);
    }

    private void addActivationToken(Member member) {
        String activationToken = UUID.randomUUID().toString();
        member.setActivationToken(activationToken);

        LocalDateTime activationTokenExpDate = LocalDateTime.now().plusDays(1);
        // LocalDateTime activationTokenExpDate = LocalDateTime.now();//Use for expire
        // date test!
        member.setActivationTokenExpDate(activationTokenExpDate);
    }
}
