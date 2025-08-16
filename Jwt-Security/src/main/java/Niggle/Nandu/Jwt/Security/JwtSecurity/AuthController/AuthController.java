package Niggle.Nandu.Jwt.Security.JwtSecurity.AuthController;

import Niggle.Nandu.Jwt.Security.JwtSecurity.model.Role;
import Niggle.Nandu.Jwt.Security.JwtSecurity.model.RoleEntity;
import Niggle.Nandu.Jwt.Security.JwtSecurity.model.UserEntity;
import Niggle.Nandu.Jwt.Security.JwtSecurity.payload.JwtResponse;
import Niggle.Nandu.Jwt.Security.JwtSecurity.payload.LoginRequest;
import Niggle.Nandu.Jwt.Security.JwtSecurity.payload.SignupRequest;
import Niggle.Nandu.Jwt.Security.JwtSecurity.repository.RoleRepository;
import Niggle.Nandu.Jwt.Security.JwtSecurity.repository.UserRepository;
import Niggle.Nandu.Jwt.Security.JwtSecurity.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails);

        String jwt = jwtUtils.generateTokenFromUsername(userDetails);
        System.out.println(jwt);

        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(userDetails.getUsername());
        if (userEntityOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: User not found!");
        }

        UserEntity user = userEntityOptional.get();

        List<String> roles = user.getRoles().stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, roles, user.getUserId(), user.getUsername(), user.getEmail()));

    }

    @PostMapping("signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if(userRepository.findByUsername(signupRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        UserEntity user = new UserEntity();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());

        Optional<RoleEntity> userRole = roleRepository.findByRole(Role.ROLE_USER);
        RoleEntity role = roleRepository.findByRole(Role.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Default role not found!"));
        user.setRoles(Collections.singleton(role));


        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("User logged out successfully!");
    }
}