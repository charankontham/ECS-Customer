package com.ecs.ecs_customer.service.interfaces;

import com.ecs.ecs_customer.entity.UserSearchDoc;

public interface IUserSearchService {
//    UserSearchDoc getUserSearchByUserId(Integer customerId);
    UserSearchDoc addOrUpdateUserSearch(UserSearchDoc userSearchDoc);
//    UserSearchDoc updateUserSearch(UserSearchDoc userSearchDoc);
    void deleteUserSearchByCustomerId(Integer customerId);
}
