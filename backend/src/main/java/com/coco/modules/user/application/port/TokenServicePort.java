package com.coco.modules.user.application.port;

public interface TokenServicePort {

    String generateAccessToken(String subject);

    long getAccessTokenExpirationSeconds();
}
