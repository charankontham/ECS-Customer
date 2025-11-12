package com.ecs.ecs_customer.controller;


import com.ecs.ecs_customer.entity.SearchHistory;
import com.ecs.ecs_customer.entity.UserSearchDoc;
import com.ecs.ecs_customer.service.interfaces.ISearchHistoryService;
import com.ecs.ecs_customer.service.interfaces.IUserSearchService;
import com.ecs.ecs_customer.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/api/searchHistory")
@RequiredArgsConstructor
public class SearchController {
    private final IUserSearchService userSearchService;
    private final ISearchHistoryService searchHistoryService;

    @GetMapping("/getSearchHistoryById/{customerId}")
    public ResponseEntity<SearchHistory> getSearchHistoryByCustomerId(@PathVariable("customerId") Integer customerId) {
        SearchHistory searchHistory = searchHistoryService.getSearchHistoryByCustomerId(customerId);
        if(searchHistory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(searchHistory);
    }

    @PutMapping("/addUserSearch")
    public ResponseEntity<?> addUserSearch(@RequestBody UserSearchDoc userSearchDoc) {
        if (userSearchDoc != null &&
                userSearchDoc.getCustomerId() != null &&
                userSearchDoc.getSearchQuery() != null &&
                !userSearchDoc.getSearchQuery().trim().isEmpty()) {
            userSearchDoc = userSearchService.addOrUpdateUserSearch(userSearchDoc);
            if (!userSearchDoc.getId().isEmpty()) {
                return ResponseEntity.ok(searchHistoryService.addOrUpdateSearchHistory(userSearchDoc.getCustomerId(), userSearchDoc.getSearchQuery()));
            } else {
                return ResponseEntity.internalServerError().body("Error saving data in Database!");
            }
        } else {
            return ResponseEntity.badRequest().body("Request cannot be processed, validation failed!");
        }
    }

    @DeleteMapping("/deleteUserSearch/{customerId}/{searchQuery}")
    public ResponseEntity<?> deleteSearchHistory(@PathVariable("customerId") Integer customerId,@PathVariable("searchQuery") String searchQuery) {
        searchHistoryService.deleteSearchHistoryByCustomerId(customerId, searchQuery);
        return ResponseEntity.noContent().build();
    }
}
