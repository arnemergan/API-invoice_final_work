package com.api.invoice.repositories;

import com.api.invoice.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends MongoRepository<Category, String> {
    Category getCategoryByIdAndTenantId(String id,String tenantid);
    List<Category> getCategoriesByTenantIdAndName(String tenantid, String name);
    List<Category> getCategoriesByTenantIdOrDeletable(String tenantid, Boolean deletable);
    Category getCategoryByName(String name);
    Category getCategoryByNameAndTenantIdOrDeletableAndName(String name, String tenantid, boolean deletable, String secondname);
}
