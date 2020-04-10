package com.api.invoice.repositories;

import com.api.invoice.models.Invoice;
import org.bson.types.Binary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface InvoicesRepo extends MongoRepository<Invoice, String> {
    public Invoice getById(String id);
    public Invoice getByImage(Binary binary);
    public Integer countByCreatedDateBetween(Date date1,Date date2);
}
