package com.generation.dominion.controller;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.dominion.dto.AuthResponseDto;
import com.generation.dominion.dto.CredentialsDto;
import com.generation.dominion.model.Player;
import com.generation.dominion.model.Role;
import com.generation.dominion.model.UserEntity;
import com.generation.dominion.repository.PlayerRepository;
import com.generation.dominion.repository.RoleRepository;
import com.generation.dominion.repository.UserRepository;
import com.generation.dominion.security.JWTGenerator;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTGenerator jwtGenerator;


    @PostMapping("login")
    public AuthResponseDto login(@RequestBody CredentialsDto loginDto)
    {
        Authentication user = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()));

        Player player = playerRepository.findByNick(loginDto.getUsername()).get();

        SecurityContextHolder.getContext().setAuthentication(user);

        String token = jwtGenerator.generateToken(user);

        String role = user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()).get(0);

        return new AuthResponseDto(token, role, player);
    }


    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody CredentialsDto registerDto) 
    {
        if (userRepository.existsByUsername(registerDto.getUsername())) 
        {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        Player player = new Player();
        player.setNick(registerDto.getUsername());

        playerRepository.save(player);
        userRepository.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
