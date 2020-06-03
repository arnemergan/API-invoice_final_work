package com.api.invoice.repositories;

import com.api.invoice.models.Category;
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
    Page<Invoice> findAllByTenantIdAndUsername(String tenant, String username, Pageable pageable);
    Page<Invoice> findAllByTenantIdAndCategory(String tenant, Category category, Pageable pageable);
    public Invoice getByIdAndTenantId(String id,String tenant);
    public Invoice getInvoiceByFilenameAndTenantId(String filename, String tenant);
    public Integer countAllByTenantId(String tenant);
    public Integer countAllByTenantIdAndUsername(String tenant, String username);
    public Integer countAllByTenantIdAndCategory(String tenant, Category category);
    public Integer countByCreatedDateBetweenAndTenantId(Date date1,Date date2,String tenant);
    public Integer countByCreatedDateBetweenAndTenantIdAndUsername(Date date1,Date date2,String tenant,String username);
    public Integer countByCreatedDateBetweenAndTenantIdAndCategory(Date date1,Date date2,String tenant, Category category);
    public List<Invoice> getAllByTenantIdAndCategory(String tenant,Category category);
    public List<Invoice> getAllByTenantIdAndUsername(String tenant,String username);
    public List<Invoice> getAllByCreatedDateBetweenAndTenantId(Date date1,Date date2,String tenant);
    public List<Invoice> getAllByCreatedDateBetweenAndTenantIdAndUsername(Date date1,Date date2,String tenant,String username);
    public List<Invoice> getAllByCreatedDateBetweenAndTenantIdAndCategory(Date date1,Date date2,String tenant,Category category);
    public Invoice getInvoiceByNumberAndTenantId(String number,String tenant);
    @Aggregation("{ '$group' : { '_id' : null, 'total' : { $sum: '$total' } } }")
    public Double sumTotalPriceAndTenantId(String tenant);
}
