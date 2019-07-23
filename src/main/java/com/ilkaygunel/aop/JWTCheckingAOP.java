package com.ilkaygunel.aop;

import com.ilkaygunel.application.ResourceBundleMessageManager;
import com.ilkaygunel.entities.JWTBlackList;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;
import com.ilkaygunel.repository.JWTBlackListRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class JWTCheckingAOP {

    @Autowired
    private JWTBlackListRepository jwtBlackListRepository;

    @Autowired
    private ResourceBundleMessageManager resourceBundleMessageManager;

    @Around("(@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping)) && execution(public * *(..))")
    //@Around("execution(@org.springframework.web.bind.annotation.* * *(..))")
    public Object check(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String user = request.getUserPrincipal().getName();
        JWTBlackList jwtBlackList = jwtBlackListRepository.findByTokenAndUser(token, user);
        if (jwtBlackList != null) {
            throw new CustomException(ErrorCodes.ERROR_17.getErrorCode(), resourceBundleMessageManager
                    .getValueOfProperty(ErrorCodes.ERROR_17.getErrorCode(), "en"), HttpStatus.FORBIDDEN);

        }
        System.out.println("token:" + token);
        System.out.println("user:" + user);
        return proceedingJoinPoint.proceed();
    }
}
