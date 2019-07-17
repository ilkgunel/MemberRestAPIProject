package com.ilkaygunel.service;

import com.ilkaygunel.application.ResourceBundleMessageManager;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class PasswordChangeService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ResourceBundleMessageManager resourceBundleMessageManager;

    public MemberOperationPojo changeAdminOrUserPassword(String memberEmail, String oldPassword, String newPassword) throws CustomException {
        return updatePassword(memberEmail, oldPassword, newPassword);
    }

    public MemberOperationPojo changeUserPassword(String memberEmail, String oldPassword, String newPassword) throws CustomException {
        return updatePassword(memberEmail, oldPassword, newPassword);
    }

    private Member checkOldPassword(String memberEmail, String oldPassword) throws CustomException {
        Member member = memberRepository.findByEmail(memberEmail).orElseThrow(
                () -> new CustomException(ErrorCodes.ERROR_14.getErrorCode(), resourceBundleMessageManager
                        .getValueOfProperty(ErrorCodes.ERROR_14.getErrorCode(), "TR"))
        );
        boolean same = bCryptPasswordEncoder.matches(oldPassword, member.getPassword());
        if (!same) {
            throw new CustomException(ErrorCodes.ERROR_15.getErrorCode(), resourceBundleMessageManager
                    .getValueOfProperty(ErrorCodes.ERROR_15.getErrorCode(), member.getMemberLanguageCode()));
        }
        return member;
    }

    private MemberOperationPojo updatePassword(String memberEmail, String oldPassword, String newPassword) throws CustomException {
        Member member = checkOldPassword(memberEmail, oldPassword);
        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();

        member.setPassword(bCryptPasswordEncoder.encode(newPassword));
        memberRepository.save(member);

        memberOperationPojo.setResult(ObjectUtils.getDisplayString(memberOperationPojo.getResult()) + " "
                + resourceBundleMessageManager.getValueOfProperty(member.getRoleOfMember().getRole() + "_memberUpdatingSuccessful",
                member.getMemberLanguageCode()));
        return memberOperationPojo;
    }

}
