package com.ilkaygunel.restservice;

import com.ilkaygunel.entities.JWTBlackList;
import com.ilkaygunel.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/logout")
public class LogoutWebServiceEndPoint {

    @Autowired
    private LogoutService logoutService;

    @PostMapping("/")
    public void logout(@Valid @RequestBody JWTBlackList jwtBlackList) {
        logoutService.logout(jwtBlackList);
    }
}
