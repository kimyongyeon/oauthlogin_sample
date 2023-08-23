package com.kyy.oauth2login.service;

public class AbstOAuthLoginServiceServiceImpl<A, B, C> implements OAuthLoginService<A, B, C> {

    @Override
    public A getAuthorizationUrl() {
        return null;
    }

    @Override
    public B getAccessToken(String code) {
        return null;
    }

    @Override
    public C saveUser(String accessToken) {
        return null;
    }
}
