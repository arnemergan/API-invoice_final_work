package com.api.invoice.controllers;

import com.api.invoice.models.Jwt;
import com.api.invoice.models.User;
import com.api.invoice.models.UserLogin;
import com.api.invoice.security.JwtToken;
import com.api.invoice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("authenticate")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserLogin userLogin) throws Exception {
        authenticate(userLogin.getUsername(), userLogin.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(userLogin.getUsername());
        final String token = jwtToken.generateToken(userDetails);
        return ResponseEntity.ok(new Jwt(token));

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

    }
}
