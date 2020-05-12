package com.api.invoice.services.implementation;

import com.api.invoice.dto.request.RegisterTenantDTO;
import com.api.invoice.dto.response.TenantDTO;
import com.api.invoice.exceptions.ResourceNotFoundException;
import com.api.invoice.models.Invoice;
import com.api.invoice.models.Tenant;
import com.api.invoice.models.User;
import com.api.invoice.repositories.TenantRepo;
import com.api.invoice.repositories.UserRepo;
import com.api.invoice.security.TokenUtils;
import com.api.invoice.services.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TenantServiceImpl implements TenantService {
    @Autowired
    private TenantRepo tenantRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public TenantDTO registerTenant(RegisterTenantDTO registerTenantDTO) {
        Tenant tenant = tenantRepo.save(mapTenant(registerTenantDTO,null));
        User adminUser = new User();
        adminUser.setTenantId(tenant.getId());
        adminUser.setEnabled(true);
        adminUser.setUsername(registerTenantDTO.getUsername());
        adminUser.setFirstName(registerTenantDTO.getFirstName());
        adminUser.setLastName(registerTenantDTO.getLastName());
        adminUser.setEmail(registerTenantDTO.getEmail());
        adminUser.setPassword(new BCryptPasswordEncoder().encode(registerTenantDTO.getPassword()));
        ArrayList<String> authorities = new ArrayList<>();
        authorities.add("ROLE_ADMIN");
        adminUser.setAuthorities(tokenUtils.getAuthorities(authorities));
        userRepo.save(adminUser);
        mongoTemplate.createCollection("invoices" + tenant.getId());
        return mapTenantDTO(registerTenantDTO);
    }

    @Override
    public TenantDTO updateTenant(RegisterTenantDTO registerTenantDTO, String token) {
        Tenant tenant = tenantRepo.findById(tokenUtils.getTenantFromToken(token)).orElseThrow(() -> new ResourceNotFoundException("Tenant not found!"));
        tenantRepo.save(mapTenant(registerTenantDTO,tenant));
        return mapTenantDTO(registerTenantDTO);
    }

    @Override
    public void deleteTenant(String token) {
        Tenant tenant = tenantRepo.findById(tokenUtils.getTenantFromToken(token)).orElseThrow(() -> new ResourceNotFoundException("Tenant not found!"));
        tenantRepo.delete(tenant);
    }

    @Override
    public TenantDTO getTenantInfo(String token) {
        Tenant tenant = tenantRepo.findById(tokenUtils.getTenantFromToken(token)).orElseThrow(() -> new ResourceNotFoundException("Tenant not found!"));
        return mapTenant(tenant);
    }

    private TenantDTO mapTenant(Tenant tenant){
        TenantDTO tenantDTO = new TenantDTO();
        tenantDTO.setName(tenant.getName());
        tenantDTO.setCountry(tenant.getCountry());
        tenantDTO.setMaxEmployees(tenant.getMaxEmployees());
        return tenantDTO;
    }

    private Tenant mapTenant(RegisterTenantDTO registerTenantDTO, Tenant tenant){
        if(tenant == null){
            tenant = new Tenant();
        }
        tenant.setName(registerTenantDTO.getName());
        tenant.setCountry(registerTenantDTO.getCountry());
        tenant.setMaxEmployees(registerTenantDTO.getMaxEmployees());
        return tenant;
    }

    private TenantDTO mapTenantDTO(RegisterTenantDTO registerTenantDTO){
        TenantDTO tenantDTO = new TenantDTO();
        tenantDTO.setName(registerTenantDTO.getName());
        tenantDTO.setCountry(registerTenantDTO.getCountry());
        tenantDTO.setMaxEmployees(registerTenantDTO.getMaxEmployees());
        return tenantDTO;
    }
}
