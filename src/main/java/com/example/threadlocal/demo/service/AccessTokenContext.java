package com.example.threadlocal.demo.service;

public class AccessTokenContext {

    private AccessTokenContext(){}

    private static final ThreadLocal<String> ACCESS_TOKEN_CONTEXT = ThreadLocal.withInitial(() -> "");

    public static String getCurrentAccessToken() {
        return ACCESS_TOKEN_CONTEXT.get();
    }

    public static void setCurrentAccessToken(String accessToken) {
        ACCESS_TOKEN_CONTEXT.set(accessToken);
    }

    public static void removeAccessTokenContext(){
        ACCESS_TOKEN_CONTEXT.remove();
    }

}
