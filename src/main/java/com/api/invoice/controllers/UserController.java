package com.api.invoice.controllers;
import com.api.invoice.dto.request.AuthoritiesChangerDTO;
import com.api.invoice.dto.request.EnableUserDTO;
import com.api.invoice.dto.response.UserInfoAdminDTO;
import com.api.invoice.models.User;
import com.api.invoice.services.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping(path = "/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserInfoAdminDTO> getUsers(HttpServletRequest request){
        return userService.getUsersTenant(request.getHeader("Authorization").split(" ")[1]);
    }

    @PutMapping(path = "/enable")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserInfoAdminDTO> Delete(HttpServletRequest request, @RequestBody @Valid EnableUserDTO enableUserDTO){
        return new ResponseEntity<>(userService.disableUser(request.getHeader("Authorization").split(" ")[1],enableUserDTO.getUsername(),enableUserDTO.isEnable()),HttpStatus.OK);
    }

    @PutMapping(path = "/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserInfoAdminDTO> Update(HttpServletRequest request,@RequestBody AuthoritiesChangerDTO authoritiesChangerDTO){
        return new ResponseEntity<>(userService.updateUserAuthorities(request.getHeader("Authorization").split(" ")[1],authoritiesChangerDTO), HttpStatus.OK);
    }

}
