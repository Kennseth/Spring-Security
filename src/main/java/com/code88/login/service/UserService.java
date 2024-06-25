package com.code88.login.service;

import com.code88.login.dto.UserRegistrationDto;
import com.code88.login.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto userRegistrationDto);
}
