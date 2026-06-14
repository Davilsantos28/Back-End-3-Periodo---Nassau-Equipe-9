package com.ecopower.controller;

import com.ecopower.dto.LoginRequestDTO;
import com.ecopower.dto.LoginResponseDTO;
import com.ecopower.dto.RegisterRequestDTO;
import com.ecopower.model.Usuario;
import com.ecopower.repository.UsuarioRepository;
import com.ecopower.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto) {

        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado!");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setRole("USER");

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuarioSalvo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail()).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos!");
        }

        boolean senhaValida = passwordEncoder.matches(dto.getSenha(), usuario.getSenha());

        if (!senhaValida) {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos!");
        }

        String token = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}