package com.api.invoice.controllers;

import com.api.invoice.dto.request.RegisterTenantDTO;
import com.api.invoice.dto.response.TenantDTO;
import com.api.invoice.exceptions.ApiRequestException;
import com.api.invoice.security.TokenUtils;
import com.api.invoice.services.implementation.StripeService;
import com.api.invoice.services.implementation.TenantServiceImpl;
import com.api.invoice.services.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${stripe.keys.public}")
    private String API_PUBLIC_KEY;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private TenantServiceImpl tenantService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<TenantDTO> register(@RequestBody @Valid RegisterTenantDTO registerTenantDTO) {
        if (registerTenantDTO.getStripeToken() == null || registerTenantDTO.getPlan().isEmpty()) {
            throw new ApiRequestException("Stripe payment token is missing. Please, try again later.");
        }
        String customerId = stripeService.createCustomer(registerTenantDTO.getEmail(),registerTenantDTO.getStripeToken());
        if (customerId == null) {
            throw new ApiRequestException("An error occurred while trying to create a customer.");
        }
        String subscriptionId = stripeService.createSubscription(customerId, registerTenantDTO.getPlan());
        if (subscriptionId == null) {
            throw new ApiRequestException("An error occurred while trying to create a subscription.");
        }
        registerTenantDTO.setCustomerId(customerId);
        registerTenantDTO.setSubscriptionId(subscriptionId);
        return new ResponseEntity<>(tenantService.registerTenant(registerTenantDTO), HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity cancel(HttpServletRequest request, @PathVariable String id){
        boolean status = stripeService.cancelSubscription(id);
        String token = request.getHeader("Authorization").split(" ")[1];
        if(!status){
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        userService.disableUsersTenant(token);
        tenantService.deleteTenant(token);
        return new ResponseEntity(HttpStatus.OK);
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
}
