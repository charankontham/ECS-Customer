package com.ecs.ecs_customer.repository;

import com.ecs.ecs_customer.entity.UserSearchDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserSearchRepository extends MongoRepository<UserSearchDoc, ObjectId> {
    void deleteByCustomerId(Integer customerId);
    UserSearchDoc findByCustomerIdAndSearchQuery(Integer customerId, String searchQuery);
}
