package com.project.pettvaccine_api.controllers;

import com.project.pettvaccine_api.dtos.users.LoginRequestDTO;
import com.project.pettvaccine_api.dtos.users.LoginResponseDTO;
import com.project.pettvaccine_api.entity.UserEntity;
import com.project.pettvaccine_api.infra.security.JwtUtil;
import com.project.pettvaccine_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var user = (UserEntity) auth.getPrincipal();
        var token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token, user.getId(), user.getName()));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserEntity data) {
        if (this.userRepository.findByEmail(data.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado");
        }

        data.setPassword(passwordEncoder.encode(data.getPassword()));
        
        this.userRepository.save(data);
        return ResponseEntity.ok().build();
    }
}