package com.api.invoice.repositories;
import com.api.invoice.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepo extends MongoRepository<User, String> {
    public User findUserByUsername(String username);
    public List<User> findAllByTenantId(String tenant);
    public Integer countAllByTenantId(String tenant);
}
