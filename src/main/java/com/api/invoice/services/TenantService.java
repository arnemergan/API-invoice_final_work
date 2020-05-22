package com.api.invoice.services;

import com.api.invoice.dto.request.RegisterTenantDTO;
import com.api.invoice.dto.response.TenantDTO;

public interface TenantService {
    public TenantDTO registerTenant(RegisterTenantDTO registerTenantDTO);
    public TenantDTO updateTenant(TenantDTO registerTenantDTO,String id);
    public void deleteTenant(String id);
    public TenantDTO getTenantInfo(String id);
}
