package learnsmartly.ls_api.controller;

import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import learnsmartly.ls_api.entity.LsUser;
import learnsmartly.ls_api.entity.UserRole;
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

        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        LsUser user = new LsUser();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setPhoneNumber(req.getPhoneNumber());
        user.setAddress(req.getAddress());

        // default role
        UserRole role = req.getRole() == null ? UserRole.STUDENT : req.getRole();
        user.setRole(role);

        LsUser saved = userRepository.save(user);

        String token = jwtService.generateToken(saved);
        MeResponseDTO me = new MeResponseDTO(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getRole());

        return ResponseEntity.ok(new AuthResponseDTO(token, me));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO req) {

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
        if (auth == null || auth.getPrincipal() == null) {
            return ResponseEntity.status(401).build();
        }

        // JwtAuthFilter sets principal = userId
        Long userId = Long.valueOf(auth.getPrincipal().toString());

        Optional<LsUser> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        LsUser u = userOpt.get();
        return ResponseEntity.ok(new MeResponseDTO(u.getId(), u.getUsername(), u.getEmail(), u.getRole()));
    }
}
