package com.api.invoice.repositories;

import com.api.invoice.models.Invoice;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.Date;
import java.util.List;

@Repository
public interface InvoicesRepo extends MongoRepository<Invoice, String> {
    public Invoice getById(String id);
    public Invoice getByImage(Binary binary);
    public Integer countAllBy();
    public Integer countByCreatedDateBetween(Date date1,Date date2);
    public List<Invoice> getAllByCreatedDateBetween(Date date1,Date date2);
    public Invoice getInvoiceByNumber(String number);
    @Aggregation("{ '$group' : { '_id' : null, 'total' : { $sum: '$total' } } }")
    public Double sumTotalPrice();
}
