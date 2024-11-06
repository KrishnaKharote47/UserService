package com.user.service;

import com.user.models.Token;
import com.user.models.User;
import com.user.repository.TokenRepo;
import com.user.repository.UserRepo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepo userRepo;
    private TokenRepo tokenRepo;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepo userRepo, TokenRepo tokenRepo) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
    }

    public User singUp(String email, String name, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        return userRepo.save(user);
    }

    public Token login(String email, String password) {

        // 1. check if user exist
        Optional<User> user = userRepo.findByEmail(email);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found for eamil " + email);
        }

        //2. validate password

        if (!bCryptPasswordEncoder.matches(password, user.get().getHashedPassword())) {
            throw new RuntimeException("Password does not match ");
        }


        Token token = generateToken(user);
        tokenRepo.save(token);
        return token;
    }

    private Token generateToken(Optional<User> user) {

        Token token = new Token();
        token.setUser(user.get());
        token.setValue(RandomStringUtils.randomAlphanumeric(10));

        //expiry date
        LocalDate currentDate = LocalDate.now();
        // Add 30 days
        LocalDate thirtyDay = currentDate.plusDays(30);
        // Format the date (optional)
        Date expiryDate = Date.from(thirtyDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
        token.setExpiryAt(expiryDate);

        return token;
    }


    public User validatToken(String tokenValue) {
        Optional<Token> token = tokenRepo.getTokenByValueAndExpiryAtGreaterThan(tokenValue, new Date());

        if (token.isEmpty()) {
            throw new RuntimeException("Invlaid Token");
        }
        return token.get().getUser();
    }
}
