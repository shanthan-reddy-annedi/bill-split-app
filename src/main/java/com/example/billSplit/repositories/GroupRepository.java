package com.example.billSplit.repositories;

import com.example.billSplit.entites.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT ug.group FROM UserGroup ug WHERE ug.user.userID = :userID")
    List<Group> findGroupsByUserId(@Param("userID") Long userID);
}
