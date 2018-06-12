package com.member.restservice;

import com.member.service.ActivateMemberService;
import org.hibernate.annotations.GeneratorType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activateMemberWebServiceEndpoint")
public class ActivateMemberWebServiceEndpoint {

    private final ActivateMemberService activateMemberService;

    public ActivateMemberWebServiceEndpoint(ActivateMemberService activateMemberService) {
        this.activateMemberService = activateMemberService;
    }

    @GetMapping("/activateMember")
    public String activateMemberByEmailAddress(@RequestParam(value = "activationToken", defaultValue = "") String activationToken) {
        return activateMemberService.activateMember(activationToken);
    }
}