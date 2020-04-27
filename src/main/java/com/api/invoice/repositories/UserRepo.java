package com.api.invoice.repositories;
import com.api.invoice.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, Long> {
    public User findUserByUsername(String username);
}
