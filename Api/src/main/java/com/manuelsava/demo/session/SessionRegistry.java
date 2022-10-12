package com.manuelsava.demo.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

@Component
public class SessionRegistry {
    private static final HashMap<String, String> SESSIONS = new HashMap<>();
    private final ValueOperations<String, String> redisSessionStorage;

    @Autowired
    public SessionRegistry(final RedisTemplate<String, String> redisTemplate) {
        redisSessionStorage = redisTemplate.opsForValue();
    }

    public String registerSession(final String username) {
        if(username == null) {
            throw new RuntimeException("Username needs to be provided");
        }

        //Uses in memory session as fallback
        String sessionId = generateSessionId();
        try {
            redisSessionStorage.set(sessionId, username);
        }catch (Exception e) {
            e.printStackTrace();
            SESSIONS.putIfAbsent(sessionId, username);
        }

        return sessionId;
    }

    public String getUsernameForSession(final String sessionId) {
        try {
            return redisSessionStorage.get(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            return SESSIONS.get(sessionId);
        }
    }

    private String generateSessionId() {
        //Encoding random UUID for session
        return new String(
                Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8))
        );
    }
}
