package com.kyy.oauth2login.service;

public interface OAuthLoginService<A, B, C> {
    A getAuthorizationUrl();
    B getAccessToken(String code);
    C saveUser(String accessToken);
}
