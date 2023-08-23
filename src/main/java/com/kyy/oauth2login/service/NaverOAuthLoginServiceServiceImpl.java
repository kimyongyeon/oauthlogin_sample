package com.kyy.oauth2login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyy.oauth2login.dto.NaverProfile;
import com.kyy.oauth2login.dto.NaverToken;
import com.kyy.oauth2login.dto.UserProfile;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service("naverOAuthLoginService")
public class NaverOAuthLoginServiceServiceImpl extends AbstOAuthLoginServiceServiceImpl<String, NaverToken, UserProfile> {

    @Value("${oauth2.naver.client-id}")
    private final String clientId = "clientId";
    @Value("${oauth2.naver.client-secret}")
    private final String clientSecret = "clientSecret";
    @Value("${oauth2.naver.redirect-uri}")
    private final String redirectUri = "redirectUri";
    private final String accessTokenUri = "https://nid.naver.com/oauth2.0/token";
    private final String UserInfoUri = "https://openapi.naver.com/v1/nid/me";

    public NaverToken getAccessToken(String code) {

        //요청 param (body)
        MultiValueMap<String , String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id",clientId );
        params.add("redirect_uri",redirectUri);
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
        NaverToken naverToken =null;

        try {
            naverToken = objectMapper.readValue(response, NaverToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return naverToken;
    }

    public NaverProfile findProfile(String token) {

        //Http 요청
        WebClient wc = WebClient.create(UserInfoUri);
        String response = wc.get()
                .uri(UserInfoUri)
                .header("Authorization", "Bearer " + token)
                .header("Content-type", "application/xml;charset=utf-8")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        NaverProfile naverProfile = null;

        try {
            naverProfile = objectMapper.readValue(response, NaverProfile.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return naverProfile;
    }

    @Transactional
    public UserProfile saveUser(String access_token) {
        // 회원가입 로직 추가
        return new UserProfile();
    }
}
