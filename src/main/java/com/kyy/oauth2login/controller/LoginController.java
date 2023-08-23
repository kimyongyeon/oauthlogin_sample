package com.kyy.oauth2login.controller;

import com.kyy.oauth2login.placeholder.OAuth2Placeholder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final OAuth2Placeholder oAuth2Placeholder;

    @GetMapping("/naver/login")
    public String naverLogin(@RequestParam String code) {
        if (oAuth2Placeholder.naverLogin(code)) {
            return "naver login success";
        }
        return "naver login fail";
    }

    @GetMapping("/kakao/login")
    public String kakaLogin(@RequestParam String code) {
        if (oAuth2Placeholder.kakaoLogin(code)) {
            return "kakao login success";
        }
        return "kakao login fail";
    }

    @GetMapping("/google/login")
    public String googleLogin(@RequestParam String code) {
        return "kakao login";
    }
}
