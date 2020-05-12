package com.api.invoice.controllers;

import com.api.invoice.dto.request.LoginDTO;
import com.api.invoice.dto.request.PasswordChangerDTO;
import com.api.invoice.dto.request.RegisterDTO;
import com.api.invoice.dto.request.UserInfoChangerDTO;
import com.api.invoice.dto.response.UserDTO;
import com.api.invoice.dto.response.UserInfoDTO;
import com.api.invoice.dto.response.UserTokenDTO;
import com.api.invoice.services.implementation.UserDetailServiceImpl;
import com.api.invoice.services.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("authenticate")
public class AuthController {

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid LoginDTO authenticationRequest) {
        return new ResponseEntity<>(userDetailsService.login(authenticationRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterDTO registerDTO,HttpServletRequest request) {
        return new ResponseEntity<>(userService.registerUser(registerDTO,request.getHeader("Authorization").split(" ")[1]), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserTokenDTO> refreshAuthenticationToken(HttpServletRequest request) {
        return new ResponseEntity<>(userDetailsService.refreshAuthenticationToken(request), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity changePassword(@RequestBody @Valid PasswordChangerDTO passwordChanger) {
        userDetailsService.changePassword(passwordChanger.getOldPassword(), passwordChanger.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/info")
    public ResponseEntity info(HttpServletRequest request) {
        return new ResponseEntity<>(userService.getUserInfo(request.getHeader("Authorization").split(" ")[1]), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<UserInfoDTO> refreshAuthenticationToken(@RequestBody @Valid UserInfoChangerDTO userInfoChangerDTO, HttpServletRequest request) {
        return new ResponseEntity<>(userService.updateUserInfo(request.getHeader("Authorization").split(" ")[1],userInfoChangerDTO), HttpStatus.OK);
    }
}
