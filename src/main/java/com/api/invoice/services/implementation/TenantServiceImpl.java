package com.api.invoice.services.implementation;

import com.api.invoice.dto.request.RegisterTenantDTO;
import com.api.invoice.dto.response.TenantDTO;
import com.api.invoice.exceptions.ResourceNotFoundException;
import com.api.invoice.models.Tenant;
import com.api.invoice.models.User;
import com.api.invoice.repositories.TenantRepo;
import com.api.invoice.repositories.UserRepo;
import com.api.invoice.security.TokenUtils;
import com.api.invoice.services.TenantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TokenUtils tokenUtils;

    @Override
    public TenantDTO registerTenant(RegisterTenantDTO registerTenantDTO) {
        Tenant tenant = tenantRepo.save(mapTenant(registerTenantDTO,null));
        User adminUser = new User();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(adminUser,registerTenantDTO);
        adminUser.setPassword(new BCryptPasswordEncoder().encode(registerTenantDTO.getPassword()));
        adminUser.setTenantId(tenant.getId());
        adminUser.setEnabled(true);
        ArrayList<String> authorities = new ArrayList<>();
        authorities.add("ROLE_ADMIN");
        adminUser.setAuthorities(tokenUtils.getAuthorities(authorities));
        userRepo.save(adminUser);
        return mapTenantDTO(registerTenantDTO);
    }

    @Override
    public TenantDTO updateTenant(TenantDTO registerTenantDTO, String token) {
        Tenant tenant = tenantRepo.findById(tokenUtils.getTenantFromToken(token)).orElseThrow(() -> new ResourceNotFoundException("Tenant not found!"));
        tenantRepo.save(mapTenant(tenant,registerTenantDTO));
        return registerTenantDTO;
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
        tenantDTO.setSubscriptionId(tenant.getSubscriptionId());
        tenantDTO.setCustomerId(tenant.getCustomerId());
        return tenantDTO;
    }
    private Tenant mapTenant(Tenant tenant,TenantDTO tenantDTO){
        if(tenant == null){
            tenant = new Tenant();
        }
        tenant.setName(tenantDTO.getName());
        tenant.setCountry(tenantDTO.getCountry());
        tenant.setMaxEmployees(tenantDTO.getMaxEmployees());
        tenant.setSubscriptionId(tenantDTO.getSubscriptionId());
        tenant.setCustomerId(tenantDTO.getCustomerId());
        return tenant;
    }

    private Tenant mapTenant(RegisterTenantDTO registerTenantDTO, Tenant tenant){
        if(tenant == null){
            tenant = new Tenant();
        }
        tenant.setName(registerTenantDTO.getName());
        tenant.setCountry(registerTenantDTO.getCountry());
        tenant.setMaxEmployees(registerTenantDTO.getMaxEmployees());
        tenant.setCustomerId(registerTenantDTO.getCustomerId());
        tenant.setSubscriptionId(registerTenantDTO.getSubscriptionId());
        return tenant;
    }

    private TenantDTO mapTenantDTO(RegisterTenantDTO registerTenantDTO){
        TenantDTO tenantDTO = new TenantDTO();
        tenantDTO.setName(registerTenantDTO.getName());
        tenantDTO.setCountry(registerTenantDTO.getCountry());
        tenantDTO.setMaxEmployees(registerTenantDTO.getMaxEmployees());
        tenantDTO.setCustomerId(registerTenantDTO.getCustomerId());
        tenantDTO.setSubscriptionId(registerTenantDTO.getSubscriptionId());
        return tenantDTO;
    }
}
