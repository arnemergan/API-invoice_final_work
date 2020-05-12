package com.api.invoice.repositories;

import com.api.invoice.models.Invoice;
import org.bson.types.Binary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface InvoicesRepo extends MongoRepository<Invoice, String> {
    public Page<Invoice> findAllByTenantId(String tenant, Pageable pageable);
    public Invoice getByIdAndTenantId(String id,String tenant);
    public Invoice getByImage(Binary binary);
    public Integer countAllByTenantId(String tenant);
    public Integer countByCreatedDateBetweenAndTenantId(Date date1,Date date2,String tenant);
    public List<Invoice> getAllByCreatedDateBetweenAndTenantId(Date date1,Date date2,String tenant);
    public Invoice getInvoiceByNumberAndTenantId(String number,String tenant);
    @Aggregation("{ '$group' : { '_id' : null, 'total' : { $sum: '$total' } } }")
    public Double sumTotalPriceAndTenantId(String tenant);
}
