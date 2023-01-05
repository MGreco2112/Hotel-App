package com.hotel.controllers;

import com.hotel.Security.JwtUtils;
import com.hotel.Security.Services.UserDetailsImpl;
import com.hotel.models.auth.ERole;
import com.hotel.models.auth.Role;
import com.hotel.models.auth.User;
import com.hotel.payloads.request.LoginRequest;
import com.hotel.payloads.request.NewUserRequest;
import com.hotel.payloads.response.JwtResponse;
import com.hotel.repositories.RoleRepository;
import com.hotel.repositories.UserRepository;
import com.hotel.payloads.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${Spring.datasource.driver-class-name}")
    private String myDriver;

    @Value("${Spring.datasource.url}")
    private String myUrl;

    @Value("${Spring.datasource.username}")
    private String username;

    @Value("${Spring.datasource.password}")
    private String password;


    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(new JwtResponse(userDetails.getId(), jwt, userDetails.getUsername(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody NewUserRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


        User user = new User(signupRequest.getUsername(), encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        int roleCheck = roleRepository.isRoleEmpty();

        if (roleCheck < ERole.values().length) {
            int id = 1;
            for (ERole role : ERole.values()) {
                if (roleRepository.findByName(role).isEmpty()) {
                    try {
                        Connection conn = DriverManager.getConnection(myUrl, username, password);
                        Class.forName(myDriver);
                        String query = "Insert into role (id, name) values (?,?)";
                        PreparedStatement statement = conn.prepareStatement(query);

                        statement.setString(1, Integer.toString(id));
                        statement.setString(2, role.toString());

                        statement.executeUpdate();

                    } catch (Exception e) {
                        Logger logger = LoggerFactory.getLogger(AuthController.class);
                        System.out.println(e.getMessage());

                    }
                }
                id++;
            }
        }

        Role defaultUserRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
        roles.add(defaultUserRole);
        if (strRoles != null) {

            strRoles.forEach(role -> {
                switch(role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(userRole);
                }
            });

        }

        user.setRoles(roles);

        userRepository.save(user);

        return new ResponseEntity<>(new MessageResponse("User Registered Successfully"), HttpStatus.CREATED);
    }
}