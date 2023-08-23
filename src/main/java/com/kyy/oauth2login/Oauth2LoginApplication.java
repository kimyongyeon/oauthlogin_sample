package com.kyy.oauth2login;

import com.kyy.oauth2login.placeholder.OAuth2Placeholder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class Oauth2LoginApplication implements ApplicationRunner {

//    private final OAuth2Placeholder OAuth2Placeholder;


    public static void main(String[] args) {
        SpringApplication.run(Oauth2LoginApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        boolean isKakaoLogin = OAuth2Placeholder.kakaoLogin("code");
//        boolean isNaverLogin = OAuth2Placeholder.naverLogin("code");
    }
}
