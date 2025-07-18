package org.example.cryptosimulator.service;

import org.example.cryptosimulator.model.Authorization;
import org.example.cryptosimulator.repo.AuthorizationRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Collections;

public class CustomUserDetailsService implements UserDetailsService {

    private final AuthorizationRepository authRepository;

    public CustomUserDetailsService(AuthorizationRepository authRepositiry) {

        this.authRepository = authRepositiry;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Authorization auth = authRepository.findByLogin(login);
        if (auth == null) {
            throw new UsernameNotFoundException("User not found: " + login);
        }

        return User.builder().username(auth.getLogin()).password(auth.getPassword()).authorities(Collections.singletonList(new SimpleGrantedAuthority(auth.getRole()))).build();
    }

}
