package com.kyy.oauth2login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyy.oauth2login.dto.KakaoProfile;
import com.kyy.oauth2login.dto.KakaoToken;
import com.kyy.oauth2login.dto.UserProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service("kakaoOAuthLoginService")
public class KakaoOAuthLoginServiceServiceImpl extends AbstOAuthLoginServiceServiceImpl<String, KakaoToken, UserProfile> {
    @Value("${oauth2.kakao.client-id}")
    private final String clientId = "clientId";
    @Value("${oauth2.kakao.client-secret}")
    private final String clientSecret = "clientSecret";
    @Value("${oauth2.kakao.redirect-uri}")
    private final String redirectUri = "redirectUri";

    private final String accessTokenUri = "https://kauth.kakao.com/oauth/token";
    private final String UserInfoUri = "https://kapi.kakao.com/v2/user/me";

    public KakaoToken getAccessToken(String code) {
        //요청 param (body)
        MultiValueMap<String , String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id",clientId );
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret", clientSecret);


        //request
        WebClient wc = WebClient.create(accessTokenUri);
        String response = wc.post()
                .uri(accessTokenUri)
                .body(BodyInserters.fromFormData(params))
                .header("Content-type","application/x-www-form-urlencoded;charset=utf-8" ) //요청 헤더
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //json형태로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoToken kakaoToken = null;

        try {
            kakaoToken = objectMapper.readValue(response, KakaoToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoToken;
    }

    /**
     * 사용자 정보 가져오기
     */
    public KakaoProfile findProfile(String token) {

        //Http 요청
        WebClient wc = WebClient.create(UserInfoUri);
        String response = wc.post()
                .uri(UserInfoUri)
                .header("Authorization", "Bearer " + token)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper.readValue(response, KakaoProfile.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return kakaoProfile;
    }

    public UserProfile saveUser(String access_token) {
        // 회원가입 로직 추가
        return new UserProfile();
    }

}
