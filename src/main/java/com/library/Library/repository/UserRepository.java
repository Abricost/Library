package com.library.Library.repository;

import com.library.Library.model.Rating;
import com.library.Library.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User> {
    User findUserByUsernameIgnoreCase(String username);
}
