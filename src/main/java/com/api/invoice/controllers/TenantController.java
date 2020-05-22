package com.api.invoice.controllers;

import com.api.invoice.dto.request.RegisterDTO;
import com.api.invoice.dto.request.RegisterTenantDTO;
import com.api.invoice.dto.response.TenantDTO;
import com.api.invoice.dto.response.UserDTO;
import com.api.invoice.services.implementation.TenantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("tenant")
public class TenantController {
    @Autowired
    private TenantServiceImpl tenantService;
    @PostMapping("/register")
    public ResponseEntity<TenantDTO> register(@RequestBody @Valid RegisterTenantDTO registerTenantDTO) {
        return new ResponseEntity<>(tenantService.registerTenant(registerTenantDTO), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<TenantDTO> info(HttpServletRequest request) {
        return new ResponseEntity<>(tenantService.getTenantInfo(request.getHeader("Authorization").split(" ")[1]), HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TenantDTO> update(HttpServletRequest request,@RequestBody @Valid TenantDTO registerTenantDTO) {
        return new ResponseEntity<>(tenantService.updateTenant(registerTenantDTO,request.getHeader("Authorization").split(" ")[1]), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity delete(HttpServletRequest request) {
        tenantService.deleteTenant(request.getHeader("Authorization").split(" ")[1]);
        return new ResponseEntity(HttpStatus.OK);
    }
}
