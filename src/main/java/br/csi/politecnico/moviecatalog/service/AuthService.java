package br.csi.politecnico.moviecatalog.service;

import br.csi.politecnico.moviecatalog.dto.LoginFormDTO;
import br.csi.politecnico.moviecatalog.dto.UserDTO;
import br.csi.politecnico.moviecatalog.exception.EntityExistsException;
import br.csi.politecnico.moviecatalog.model.User;
import br.csi.politecnico.moviecatalog.repository.UserRepository;
import br.csi.politecnico.moviecatalog.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public String register(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EntityExistsException("Email já cadastrado.");
        }

        User user = User.builder()
                .uuid(UUID.randomUUID())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        userRepository.save(user);
        return jwtUtil.generateToken(user);
    }

    public String login(LoginFormDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        UserDetails userDetails = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return jwtUtil.generateToken(userDetails);
    }
}
