package learnsmartly.ls_api.controller;

import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import learnsmartly.ls_api.entity.LsUser;
import learnsmartly.ls_api.repository.LsUserRepository;
import learnsmartly.ls_api.security.JwtService;
import learnsmartly.ls_api.dto.request.LoginRequestDTO;
import learnsmartly.ls_api.dto.request.RegisterRequestDTO;
import learnsmartly.ls_api.dto.response.AuthResponseDTO;
import learnsmartly.ls_api.dto.response.MeResponseDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final LsUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(LsUserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO req) {
        // adjust field names if your DTOs differ
        if (req.getEmail() == null || req.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<LsUser> existing = userRepository.findByEmail(req.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        LsUser user = new LsUser();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        LsUser saved = userRepository.save(user);

        String token = jwtService.generateToken(saved);

        // build MeResponseDTO with role (matching available constructor) and return AuthResponseDTO(token, me)
        MeResponseDTO me = new MeResponseDTO(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getRole());
        AuthResponseDTO resp = new AuthResponseDTO(token, me);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO req) {
        if (req.getEmail() == null || req.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<LsUser> userOpt = userRepository.findByEmail(req.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        LsUser user = userOpt.get();
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).build();
        }

        String token = jwtService.generateToken(user);

        MeResponseDTO me = new MeResponseDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(new AuthResponseDTO(token, me));
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponseDTO> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            return ResponseEntity.status(401).build();
        }

        Optional<LsUser> userOpt = userRepository.findByEmail(auth.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        LsUser u = userOpt.get();
        MeResponseDTO dto = new MeResponseDTO(u.getId(), u.getUsername(), u.getEmail(), u.getRole());
        return ResponseEntity.ok(dto);
    }
}