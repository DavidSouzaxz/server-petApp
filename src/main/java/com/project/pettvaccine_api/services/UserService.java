package com.project.pettvaccine_api.services;

import com.project.pettvaccine_api.dtos.users.UserRequestDTO;
import com.project.pettvaccine_api.dtos.users.UserResponseDTO;
import com.project.pettvaccine_api.entity.UserEntity;
import com.project.pettvaccine_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponseDTO> listUsers() {
        List<UserEntity> usuarios = userRepository.findAll();

        return usuarios.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .toList();
    }

    public UserResponseDTO findByIdUser(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Id não localizado"));

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        if(userRepository.findByEmail(userRequestDTO.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setName(userRequestDTO.name());
        userEntity.setEmail(userRequestDTO.email());
        userEntity.setPassword(userRequestDTO.password());

        UserEntity save = userRepository.save(userEntity);

        return new UserResponseDTO(save.getId(), save.getName(), save.getEmail());

    }

    public UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO) {
        if(userRepository.findById(id).isPresent()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(userRequestDTO.name());
            userEntity.setEmail(userRequestDTO.email());
            userEntity.setPassword(userRequestDTO.password());
            UserEntity save = userRepository.save(userEntity);
            return new UserResponseDTO(save.getId(), save.getName(), save.getEmail());
        }
        throw new RuntimeException("Id não localizado");
    }

    public void deleteUser(UUID id){
        userRepository.deleteById(id);
    }

}
