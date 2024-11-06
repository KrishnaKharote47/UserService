package com.user.repository;

import com.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User ,Long> {

    Optional<User> findByEmail(String email);
}
