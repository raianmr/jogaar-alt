package com.jogaar.security;

import com.jogaar.daos.UserDao;
import com.jogaar.dtos.UserCreateDto;
import com.jogaar.dtos.UserLoginResponseDto;
import com.jogaar.dtos.UserLoginDto;
import com.jogaar.dtos.UserReadDto;
import com.jogaar.dtos.mappers.UserMapper;
import com.jogaar.entities.User;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto login(UserLoginDto userLoginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getEmail(),
                        userLoginDto.getPassword()
                )
        );

        User user = userDao
                .findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return UserLoginResponseDto
                .builder()
                .token(jwtService.generateToken(user))
                .user(userMapper.toReadDto(user))
                .build();
    }

    public UserReadDto register(UserCreateDto createDto) throws DataIntegrityViolationException {
        createDto.setPassword(passwordEncoder.encode(createDto.getPassword()));

        User newU = userDao.save(userMapper.fromCreateDto(createDto));

        return userMapper.toReadDto(newU);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userDao.save(user);
    }
}
