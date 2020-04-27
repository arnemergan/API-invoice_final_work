package com.api.invoice.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.UUID;

@Document
public class Tenant {
    @Id
    private String id;
    private String name;
    private String database_name;
    private boolean active;

    public Tenant(String name, String database_name, boolean active) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.database_name = database_name;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatabase_name() {
        return database_name;
    }

    public void setDatabase_name(String database_name) {
        this.database_name = database_name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
