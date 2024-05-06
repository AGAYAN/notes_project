package org.example.project_notes.Service;

import org.example.project_notes.DTO.UserDTO;
import org.example.project_notes.Entity.UserEntity;
import org.example.project_notes.Repository.UserRepository;
import org.example.project_notes.Token.JwtCore;
import org.example.project_notes.enumType.TokenType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtCore jwtCore;
    private final BCryptPasswordEncoder passwordEncoder;


    @Value("${jwt.secret}")
    private String secret;

    public AuthService(UserRepository userRepository, JwtCore jwtCore) {
        this.userRepository = userRepository;
        this.jwtCore = jwtCore;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    @Transactional
    public void registerUser(UserDTO userDTO) {

        Optional<UserEntity> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким адресом электронной почты уже существует");
        }
        UserEntity user = UserEntity.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();

        userRepository.save(user);
    }

//    public String registerUser(UserDTO userDTO) {
//        Optional<UserEntity> existingUser = userRepository.findByUsername(userDTO.getUsername());
//        if (existingUser.isPresent()) {
//            throw new IllegalArgumentException("User with this username already exists");
//        }
//
//        UserEntity user = UserEntity.builder()
//                .username(userDTO.getUsername())
//                .password(passwordEncoder.encode(userDTO.getPassword()))
//                .build();
//        userRepository.save(user);
//
//        return jwtCore.generateAccessToken(TokenType.ACCESS_TOKEN);
//    }
    public boolean loginUser(UserDTO userDTO) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(userDTO.getEmail());
        if (userOptional.isPresent() && passwordEncoder.matches(userDTO.getPassword(), userOptional.get().getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
