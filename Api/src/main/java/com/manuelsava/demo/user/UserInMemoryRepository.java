package com.manuelsava.demo.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class UserInMemoryRepository {
    private static final HashMap<String, CurrentUser> REGISTERED_USER = new HashMap<>(2);

    @PostConstruct
    public void setUpUsers() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        REGISTERED_USER.put("user1", buildCurrentUser(
                "user1",
                encoder.encode("user1")
        ));
        REGISTERED_USER.put("user2", buildCurrentUser(
                "user2",
                encoder.encode("user2")
        ));
    }

    public CurrentUser findUserByUsername(final String username){
        return REGISTERED_USER.get(username);
    }

    private static CurrentUser buildCurrentUser(final String username, final  String password) {
        final CurrentUser currentUser = new CurrentUser();
        currentUser.setUsername(username);
        currentUser.setPassword(password);

        return currentUser;
    }
}
