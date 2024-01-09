package com.example.billSplit.repositories;

import com.example.billSplit.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.userName=:username")
    Optional<User> findByUserName(String username);


    List<User> findAllByEmailIn(Set<String> emailAddresses);
}
