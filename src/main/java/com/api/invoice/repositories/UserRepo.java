package com.api.invoice.repositories;
import com.api.invoice.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    public User findUserByUsername(String username);
    public List<User> findAllByTenantId(String tenant);
    public Integer countAllByTenantId(String tenant);
}
