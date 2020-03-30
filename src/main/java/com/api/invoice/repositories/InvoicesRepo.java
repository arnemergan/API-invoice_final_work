package com.api.invoice.repositories;

import com.api.invoice.models.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoicesRepo extends MongoRepository<Invoice, String> {
    public Invoice getById(String id);
}
