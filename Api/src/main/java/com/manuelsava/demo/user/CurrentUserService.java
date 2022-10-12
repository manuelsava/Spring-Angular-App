package com.manuelsava.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService implements UserDetailsService {

    //private final UserInMemoryRepository repository;
    private final UserEntityRepository repository;

    @Autowired
    public CurrentUserService(UserEntityRepository userEntityRepository) {
        this.repository = userEntityRepository;
    }

    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        CurrentUser currentUser = new CurrentUser();
        currentUser.setUsername(user.getUsername());
        currentUser.setPassword(user.getPassword());

        return currentUser;
    }
}
