package com.taskagile.domain.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by a username
     *
     * @param username the provided username used for finding user
     * @return an instance of <code>User</code> if found, null otherwise
     */
    User findByUsername(String username);

    /**
     * Find user by an email address
     *
     * @param emailAddress the provided email address used for finding user
     * @return an instance of <code>User</code> if found, null otherwise
     */
    User findByEmailAddress(String emailAddress);

}
