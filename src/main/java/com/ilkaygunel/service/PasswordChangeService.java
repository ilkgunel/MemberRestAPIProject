package com.ilkaygunel.service;

import com.ilkaygunel.application.ResourceBundleMessageManager;
import com.ilkaygunel.entities.JWTBlackList;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.repository.JWTBlackListRepository;
import com.ilkaygunel.repository.MemberRepository;
import com.ilkaygunel.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class PasswordChangeService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ResourceBundleMessageManager resourceBundleMessageManager;

    @Autowired
    private JWTBlackListRepository jwtBlackListRepository;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private PasswordResetService passwordResetService;

    public MemberOperationPojo changePassword(String memberEmail, String oldPassword, String newPassword) throws CustomException {
        return updatePassword(memberEmail, oldPassword, newPassword);
    }

    public MemberOperationPojo sendPasswordResetMail(String email) throws CustomException, MessagingException {
        Member member = memberRepository.findByEmail(email).orElse(null);
        if (member == null) {
            throw new CustomException(ErrorCodes.ERROR_14.getErrorCode(), resourceBundleMessageManager
                    .getValueOfProperty(ErrorCodes.ERROR_14.getErrorCode(), "tr"));
        } else {
            String resetPasswordToken = UUID.randomUUID().toString();
            mailUtil.sendPasswordResetMail(email, resetPasswordToken, member.getMemberLanguageCode());

            passwordResetService.savePasswordResetToken(resetPasswordToken, member.getEmail());

            MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
            memberOperationPojo.setResult(ObjectUtils.getDisplayString(memberOperationPojo.getResult()) + " "
                    + resourceBundleMessageManager.getValueOfProperty(member.getRoleOfMember().getRole() + "_memberUpdatingSuccessful",
                    member.getMemberLanguageCode()));
            return memberOperationPojo;
        }
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

    private void checkUser(String memberEmail, String languageCode) throws CustomException {
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!memberEmail.equals(loggedInUserEmail)) {
            throw new CustomException(ErrorCodes.ERROR_16.getErrorCode(), resourceBundleMessageManager
                    .getValueOfProperty(ErrorCodes.ERROR_16.getErrorCode(), languageCode));
        }
    }

    private void addTokenToBlackList() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();

        JWTBlackList jwtBlackList = new JWTBlackList();
        jwtBlackList.setToken(request.getHeader("Authorization").replace("Bearer ", ""));
        jwtBlackList.setUser(request.getUserPrincipal().getName());

        jwtBlackListRepository.save(jwtBlackList);

    }

    private MemberOperationPojo updatePassword(String memberEmail, String oldPassword, String newPassword) throws CustomException {
        Member member = checkOldPassword(memberEmail, oldPassword);
        checkUser(memberEmail, member.getMemberLanguageCode());
        MemberOperationPojo memberOperationPojo = new MemberOperationPojo();

        member.setPassword(bCryptPasswordEncoder.encode(newPassword));
        memberRepository.save(member);

        addTokenToBlackList();

        memberOperationPojo.setResult(ObjectUtils.getDisplayString(memberOperationPojo.getResult()) + " "
                + resourceBundleMessageManager.getValueOfProperty("updatePassword.successfull", member.getMemberLanguageCode()));
        return memberOperationPojo;
    }

}
