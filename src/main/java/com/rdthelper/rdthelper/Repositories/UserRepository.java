package com.rdthelper.rdthelper.Repositories;

import com.rdthelper.rdthelper.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
