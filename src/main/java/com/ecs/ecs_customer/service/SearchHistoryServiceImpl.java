package com.ecs.ecs_customer.service;

import com.ecs.ecs_customer.entity.SearchHistory;
import com.ecs.ecs_customer.exception.ResourceNotFoundException;
import com.ecs.ecs_customer.repository.SearchHistoryRepository;
import com.ecs.ecs_customer.service.interfaces.ISearchHistoryService;
import com.ecs.ecs_customer.util.ErrorStatusCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SearchHistoryServiceImpl implements ISearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;

    @Override
    public SearchHistory getSearchHistoryByCustomerId(Integer customerId) {
        return searchHistoryRepository.findByCustomerId(customerId).orElse( null);
    }

    @Override
    public SearchHistory addOrUpdateSearchHistory(Integer customerId, String searchQuery) {
        Map<String, Object> newEntry = Map.of(
                "query", searchQuery,
                "timestamp", LocalDateTime.now(ZoneId.of("UTC"))
        );
        if (searchHistoryRepository.existsByCustomerId(customerId)) {
            SearchHistory searchHistory = searchHistoryRepository.findByCustomerId(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Search history not found!"));
            searchHistory.getSearchHistory().removeIf(entry ->
                    searchQuery.equalsIgnoreCase((String) entry.get("query"))
            );
            searchHistory.getSearchHistory().add(0, newEntry);
            searchHistory.setSearchHistory(searchHistory.getSearchHistory().size() > 10 ?
                    searchHistory.getSearchHistory().subList(0, 10) :
                    searchHistory.getSearchHistory());
            return searchHistoryRepository.save(searchHistory);
        } else {
            SearchHistory userSearchHistory = new SearchHistory();
            userSearchHistory.setCustomerId(customerId);
            userSearchHistory.setSearchHistory(List.of(newEntry));
            return searchHistoryRepository.save(userSearchHistory);
        }
    }

    @Override
    public void deleteSearchHistoryByCustomerId(Integer customerId, String searchQuery) {
        if(searchQuery == null ) {
            searchHistoryRepository.deleteByCustomerId(customerId);
        }else{
            SearchHistory searchHistory = searchHistoryRepository.findByCustomerId(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Search history not found!")
            );
            searchHistory.getSearchHistory().removeIf((entry) -> entry.get("query").equals(searchQuery));
            searchHistory.setSearchHistory(searchHistory.getSearchHistory());
        }
    }
}
