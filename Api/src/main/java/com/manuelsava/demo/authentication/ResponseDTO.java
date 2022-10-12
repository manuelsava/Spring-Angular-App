package com.manuelsava.demo.authentication;

import lombok.Data;

@Data
public class ResponseDTO {
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
