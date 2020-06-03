package com.api.invoice.services;

import com.api.invoice.models.Category;

import java.util.List;

public interface CategoryService {
    public void add(Category category);
    public Category add(String token,Category category);
    public void delete(String token, String id);
    public void deleteAll();
    public List<Category> getAll(String token);
    public List<Category> search(String token,String name);
    public Category getDefault();
    public Category getByName(String token, String name);
}
