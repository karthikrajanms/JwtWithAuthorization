package com.example.controller;


import com.example.bean.JwtRequest;
import com.example.bean.JwtResponse;
import com.example.bean.LoginResponse;
import com.example.entity.UserAccount;
import com.example.service.UserAccountService;
import com.example.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserAccountService userAccountService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserAccountService userAccountService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userAccountService = userAccountService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping()
    public String homePage() {
        return "After Authenticate jwt token";
    }
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody UserAccount userAccount) {
//        // Additional validation logic
////
////        userAccountService.registerUser(userAccount);
////        return ResponseEntity.ok("User registered successfully");
//        return null;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody JwtRequest authenticationRequest) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            authenticationRequest.getUsername(),
//                            authenticationRequest.getPassword()
//                    )
//            );
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//
//        UserDetails userDetails = userAccountService.loadUserByUsername(authenticationRequest.getUsername());
//        String token = jwtUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(token);
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userAccountService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtil.generateToken(userDetails);

        UserAccount userAccount = userAccountService.findByUserEmail(jwtRequest.getUsername());
        userAccount.setAccessToken(token);
        userAccountService.saveUserAccount(userAccount);
        LoginResponse loginResponse = new LoginResponse();
        mapToResponse(token, userAccount, loginResponse);
        return ResponseEntity.ok(loginResponse);
    }

    private void mapToResponse(String token, UserAccount userAccount, LoginResponse loginResponse) {
        loginResponse.setUserId(userAccount.getId());
        loginResponse.setCompanyName(userAccount.getCompanyName());
        loginResponse.setUserEmail(userAccount.getUserEmail());
        loginResponse.setDisplayName(String.format("%s %s", userAccount.getFirstName(), userAccount.getLastName()));
        loginResponse.setAccessToken(token);
    }
//    @PostMapping("/authenticate")
//    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{
//
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            jwtRequest.getUsername(),
//                            jwtRequest.getPassword()
//                    )
//            );
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//
//        final UserDetails userDetails
//                = userAccountService.loadUserByUsername(jwtRequest.getUsername());
//
//        final String token =
//                jwtUtil.generateToken(userDetails);
//
//        return  new JwtResponse(token);
//    }
}

