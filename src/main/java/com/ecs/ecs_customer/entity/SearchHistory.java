package com.ecs.ecs_customer.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_search_history")
public class SearchHistory {
    @Id
    private String id;
    @Field("customer_id")
    private Integer customerId;
    @Field("search_history")
    private List<Map<String, Object>> searchHistory;
}
