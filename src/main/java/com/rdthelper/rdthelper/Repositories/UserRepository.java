package com.rdthelper.rdthelper.Repositories;

import com.rdthelper.rdthelper.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);

    @Modifying(flushAutomatically = true)
    @Query("UPDATE User u SET u.username = :username, u.password = :password, u.rdtToken = :rdtToken WHERE u.username = :username")
    void update(@Param(value = "username") String username, @Param(value = "password") String password, @Param(value = "rdtToken") String rdtToken);
}
