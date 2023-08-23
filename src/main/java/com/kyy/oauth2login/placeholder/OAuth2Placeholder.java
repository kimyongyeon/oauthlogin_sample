package com.kyy.oauth2login.placeholder;

import com.kyy.oauth2login.dto.KakaoToken;
import com.kyy.oauth2login.dto.NaverProfile;
import com.kyy.oauth2login.dto.NaverToken;
import com.kyy.oauth2login.dto.UserProfile;
import com.kyy.oauth2login.service.KakaoOAuthLoginServiceServiceImpl;
import com.kyy.oauth2login.service.NaverOAuthLoginServiceServiceImpl;
import com.kyy.oauth2login.service.OAuthLoginService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2Placeholder {

    private final OAuthLoginService<String, NaverToken, UserProfile> naverOAuthLoginService;
    private final OAuthLoginService<String, KakaoToken, UserProfile> kakaoOAuthLoginService;

    public boolean kakaoLogin(String code) {
        //access 토큰 받기
        KakaoToken oauthToken = kakaoOAuthLoginService.getAccessToken(code);

        //사용자 정보받기 및 회원가입
        UserProfile saveUser = kakaoOAuthLoginService.saveUser(oauthToken.getAccess_token());

        //jwt토큰 저장
//        JwtToken jwtTokenDTO = jwtService.joinJwtToken(saveUser.getUserid());
        return true;
    }

    public boolean naverLogin(String code) {
        //access 토큰 받기
        NaverToken oauthToken = naverOAuthLoginService.getAccessToken(code);

        //사용자 정보받기 및 회원가입
        UserProfile saveUser = naverOAuthLoginService.saveUser(oauthToken.getAccess_token());

        return true;

    }

    
}
