package com.project.pettvaccine_api.controllers;


import com.project.pettvaccine_api.dtos.users.UserRequestDTO;
import com.project.pettvaccine_api.dtos.users.UserResponseDTO;
import com.project.pettvaccine_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listUsers() {
        List<UserResponseDTO> list =userService.listUsers();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable UUID id) {
        UserResponseDTO user = userService.findByIdUser(id);
        return ResponseEntity.ok().body(user);
    }

//    @PostMapping
//    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO user) {
//        UserResponseDTO userRegister = userService.registerUser(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(userRegister);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody @Valid UserRequestDTO user, @PathVariable UUID id) {
        UserResponseDTO userEdit = userService.updateUser(id, user);
        return ResponseEntity.ok().body(userEdit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
