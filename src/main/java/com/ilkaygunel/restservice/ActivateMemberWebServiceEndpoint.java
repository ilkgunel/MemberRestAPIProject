package com.ilkaygunel.restservice;

import com.ilkaygunel.service.ActivateMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activateMemberWebServiceEndpoint")
public class ActivateMemberWebServiceEndpoint {

    @Autowired
    private ActivateMemberService activateMemberService;

    @RequestMapping(value = "/activateMember",method = RequestMethod.GET)
    public String activateMemberByEmailAddress(@RequestParam(value = "activationToken", defaultValue = "") String activationToken){
        return activateMemberService.activateMember(activationToken);
    }
}
