package com.ecs.ecs_customer.repository;

import com.ecs.ecs_customer.entity.SearchHistory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SearchHistoryRepository extends MongoRepository<SearchHistory, ObjectId> {
    Optional<SearchHistory> findByCustomerId(Integer customerId);
    boolean existsByCustomerId(Integer customerId);
    void deleteByCustomerId(Integer customerId);
}
