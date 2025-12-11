package com.ecs.ecs_customer.service;

import com.ecs.ecs_customer.entity.UserSearchDoc;
import com.ecs.ecs_customer.repository.UserSearchRepository;
import com.ecs.ecs_customer.service.interfaces.IUserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserSearchServiceImpl implements IUserSearchService {
    private final UserSearchRepository userSearchRepository;

    @Override
    public UserSearchDoc addOrUpdateUserSearch(UserSearchDoc userSearchDoc) {
        userSearchDoc.setTimestamp(LocalDateTime.now());
        userSearchDoc.setExpireAt(new Date());
        userSearchDoc.setSearchQuery(userSearchDoc.getSearchQuery().toLowerCase());
        UserSearchDoc existingDoc = userSearchRepository.findByCustomerIdAndSearchQuery(
                userSearchDoc.getCustomerId(), userSearchDoc.getSearchQuery());
        if (existingDoc != null) {
            userSearchDoc.setId(existingDoc.getId());
            Map<String, Object> userMetaData = userSearchDoc.getMetadata();
            if (userMetaData != null) {
                userMetaData.put("search_frequency", (int) existingDoc.getMetadata().get("search_frequency") + 1);
                userSearchDoc.setMetadata(userMetaData);
            } else {
                userSearchDoc.setMetadata(
                        Map.of("search_frequency", (int) existingDoc.getMetadata().get("search_frequency") + 1));
            }
        } else {
            if (userSearchDoc.getMetadata() == null) {
                userSearchDoc.setMetadata(Map.of("search_frequency", 1));
            } else {
                Map<String, Object> metadata = userSearchDoc.getMetadata();
                metadata.put("search_frequency", 1);
                userSearchDoc.setMetadata(metadata);
            }
        }
        return userSearchRepository.save(userSearchDoc);
    }

    @Override
    public void deleteUserSearchByCustomerId(Integer customerId) {
        userSearchRepository.deleteByCustomerId(customerId);
    }
}
