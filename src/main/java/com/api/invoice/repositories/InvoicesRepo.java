package com.api.invoice.repositories;

import com.api.invoice.models.Invoice;
import org.bson.types.Binary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicesRepo extends MongoRepository<Invoice, String> {
    public Invoice getById(String id);
    public Invoice getByImage(Binary binary);
}
