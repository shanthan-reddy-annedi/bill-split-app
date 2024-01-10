package com.example.billSplit.repositories;

import com.example.billSplit.entites.Bill;
import com.example.billSplit.entites.Transaction;
import com.example.billSplit.entites.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM  Transaction t WHERE t.bill = :bill")
    List<Transaction> findTransactionsByBillId(Bill bill);

    @Query("SELECT t FROM Transaction t WHERE t.bill = :bill AND t.payerId = :user")
    Transaction getTransactionByUserAndBill(@Param("bill") Bill bill, @Param("user") User user);
}
