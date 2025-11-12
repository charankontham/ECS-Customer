package com.ecs.ecs_customer.service.interfaces;

import com.ecs.ecs_customer.entity.SearchHistory;

public interface ISearchHistoryService {
    SearchHistory getSearchHistoryByCustomerId(Integer customerId);
    SearchHistory addOrUpdateSearchHistory(Integer customerId, String searchQuery);
    void deleteSearchHistoryByCustomerId(Integer customerId, String searchQuery);
}
