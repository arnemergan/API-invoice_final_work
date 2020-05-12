package com.api.invoice.controllers;
import com.api.invoice.dto.request.AuthoritiesChangerDTO;
import com.api.invoice.dto.response.UserInfoAdminDTO;
import com.api.invoice.models.User;
import com.api.invoice.services.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping(path = "/")
    @CrossOrigin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserInfoAdminDTO> getUsers(HttpServletRequest request){
        return userService.getUsersTenant(request.getHeader("Authorization").split(" ")[1]);
    }

    @DeleteMapping(path = "/")
    @CrossOrigin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserInfoAdminDTO> Delete(HttpServletRequest request,@RequestParam String username){
        return new ResponseEntity<>(userService.disableUser(request.getHeader("Authorization").split(" ")[1],username),HttpStatus.OK);
    }

    @PutMapping(path = "/")
    @CrossOrigin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserInfoAdminDTO> Update(HttpServletRequest request,@RequestBody AuthoritiesChangerDTO authoritiesChangerDTO){
        return new ResponseEntity<>(userService.updateUserAuthorities(request.getHeader("Authorization").split(" ")[1],authoritiesChangerDTO), HttpStatus.OK);
    }

}
