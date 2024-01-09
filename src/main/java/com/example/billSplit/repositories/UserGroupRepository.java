package com.example.billSplit.repositories;

import com.example.billSplit.entites.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
