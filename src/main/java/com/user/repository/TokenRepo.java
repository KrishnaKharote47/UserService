package com.user.repository;

import com.user.models.Token;
import com.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    public Optional<Token> getTokenByValueAndExpiryAtGreaterThan(String tokenValue, Date date);
}
