package com.example.studyplatform.properties.oauth;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;


import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration")
public class Oauth2ClientProperties implements InitializingBean {
    private final Map<String, Provider> provider = new HashMap<>();
    private final Map<String, Registration> registration = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        validate();
    }

    public Map<String, Provider> getProvider() {
        return this.provider;
    }

    public Map<String, Registration> getRegistration() {
        return this.registration;
    }

    public void validate() {
        getRegistration().values().forEach(this::validateRegistration);
    }

    private void validateRegistration(Registration registration) {
        if (!StringUtils.hasText(registration.getClientId())) {
            throw new IllegalStateException("Client id must not be empty.");
        }
    }
}
