package com.api.invoice.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Set;

@Document
public class Category extends BaseSaasEntity{
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    private boolean deletable;
    @Transient
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Invoice> invoices;
    public Category(String name, boolean deletable) {
        this.name = name;
        this.deletable = deletable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }
}
