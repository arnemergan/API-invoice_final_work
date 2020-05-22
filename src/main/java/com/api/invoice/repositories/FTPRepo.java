package com.api.invoice.repositories;

import com.api.invoice.models.FTP;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FTPRepo extends MongoRepository<FTP, String> {
    public FTP getByIdAndTenantId(String id, String tenant);
    public List<FTP> getAllByTenantId(String tenant);
}
