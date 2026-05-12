package com.project.pettvaccine_api.services;

import com.project.pettvaccine_api.dtos.users.UserRequestDTO;
import com.project.pettvaccine_api.dtos.users.UserResponseDTO;
import com.project.pettvaccine_api.entity.UserEntity;
import com.project.pettvaccine_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CloudinaryService cloudinaryService;

    public List<UserResponseDTO> listUsers() {
        List<UserEntity> usuarios = userRepository.findAll();

        return usuarios.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getPhotoUrl(),
                        user.getContact(),
                        user.getEmail()
                ))
                .toList();
    }

    public UserResponseDTO findByIdUser(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Id não localizado"));

        return new UserResponseDTO(user.getId(), user.getName(), user.getContact(),user.getPhotoUrl(), user.getEmail());
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    }

//    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
//        if(userRepository.findByEmail(userRequestDTO.email()).isPresent()) {
//            throw new RuntimeException("Email já cadastrado");
//        }
//
//        UserEntity userEntity = new UserEntity();
//        userEntity.setName(userRequestDTO.name());
//        userEntity.setEmail(userRequestDTO.email());
//        userEntity.setPassword(userRequestDTO.password());
//
//        UserEntity save = userRepository.save(userEntity);
//
//        return new UserResponseDTO(save.getId(), save.getName(), save.getEmail());
//
//    }



    public UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO) {
        if(userRepository.findById(id).isPresent()) {
            UserEntity userEntity = userRepository.findById(id).get();
            if(userRequestDTO.name() != null ) {
                userEntity.setName(userRequestDTO.name());
            }
            userEntity.setEmail(userEntity.getEmail());

            if(userRequestDTO.password() != null ) {
                userEntity.setPassword(passwordEncoder.encode(userRequestDTO.password()));
            }
            if(userRequestDTO.contact() != null ) {
                userEntity.setContact(userRequestDTO.contact());
            }

            if(userEntity.getName() != null  && !userEntity.getName().equals(userRequestDTO.name())) {
                cloudinaryService.deleteImage(userEntity.getPhotoUrl());
            }



            userEntity.setPhotoUrl(userRequestDTO.photoUrl());


            UserEntity save = userRepository.save(userEntity);
            return new UserResponseDTO(save.getId(), save.getName(),save.getPhotoUrl(),save.getContact(), save.getEmail());
        }
        throw new RuntimeException("Id não localizado");
    }

    public void deleteUser(UUID id){
        UserEntity userExist = userRepository.findById(id).orElse(null);
        if(userExist != null && !userExist.getPhotoUrl().isEmpty()) {
            cloudinaryService.deleteImage(userExist.getPhotoUrl());
        }
        userRepository.deleteById(id);
    }

}
