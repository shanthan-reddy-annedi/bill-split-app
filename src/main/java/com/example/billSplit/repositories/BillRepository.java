package com.example.billSplit.repositories;

import com.example.billSplit.entites.Bill;
import com.example.billSplit.entites.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT b FROM Bill b WHERE b.group=:group")
    List<Bill> findAllBillsByGroup(Group group);
}
