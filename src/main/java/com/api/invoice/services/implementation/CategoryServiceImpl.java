package com.api.invoice.services.implementation;

import com.api.invoice.exceptions.ApiRequestException;
import com.api.invoice.exceptions.ResourceNotFoundException;
import com.api.invoice.models.Category;
import com.api.invoice.repositories.CategoryRepo;
import com.api.invoice.security.TokenUtils;
import com.api.invoice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    TokenUtils tokenUtils;

    @Override
    public void add(Category category) {
        if(categoryRepo.getCategoryByName(category.getName()) == null){
            categoryRepo.save(category);
        }
    }

    @Override
    public Category add(String token, Category category) {
        String tenantid = tokenUtils.getTenantFromToken(token);
        if(categoryRepo.getCategoryByNameAndTenantIdOrDeletableAndName(category.getName(),tenantid,false,category.getName()) != null){
            throw new ApiRequestException("Category is already at your service!");
        }
        category.setTenantId(tenantid);
        category.setDeletable(true);
        return categoryRepo.save(category);
    }

    @Override
    public void delete(String token, String id) {
        Category category = categoryRepo.getCategoryByIdAndTenantId(id,tokenUtils.getTenantFromToken(token));
        if(category == null){
            throw new ResourceNotFoundException("Category not found");
        }
        if(category.isDeletable()){
            categoryRepo.delete(category);
        }
        throw new ResourceNotFoundException("Category is not deletable");
    }

    @Override
    public void deleteAll() {
        categoryRepo.deleteAll();
    }

    @Override
    public List<Category> getAll(String token) {
        return categoryRepo.getCategoriesByTenantIdOrDeletable(tokenUtils.getTenantFromToken(token),false);
    }

    @Override
    public List<Category> search(String token,String name) {
        return categoryRepo.getCategoriesByTenantIdAndName(tokenUtils.getTenantFromToken(token),name);
    }

    @Override
    public Category getDefault() {
        return categoryRepo.getCategoryByName("default");
    }

    @Override
    public Category getByName(String token, String name) {
        Category category = categoryRepo.getCategoryByName(name);
        if(category == null){
            throw new ApiRequestException("Request not valid");
        }
        if(category.getTenantId().equals(tokenUtils.getTenantFromToken(token))){
            throw new ApiRequestException("Request not valid");
        }
        return category;
    }
}
