package com.ecs.ecs_customer.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_search_docs")
public class UserSearchDoc {
    @Id
    private String id;
    @Field("search_query")
    private String searchQuery;
    @Field("customer_id")
    private Integer customerId;
    @Field("timestamp")
    private LocalDateTime timestamp;
    @Indexed(name = "expire_after_30_days", expireAfterSeconds = 2592000)
    private Date expireAt;
    @Field("metadata")
    private Map<String, Object> metadata;
}
