package com.api.invoice.repositories;

import com.api.invoice.models.Tenant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TenantRepo extends MongoRepository<Tenant, String> {
    public Tenant getTenantById(String id);
}
