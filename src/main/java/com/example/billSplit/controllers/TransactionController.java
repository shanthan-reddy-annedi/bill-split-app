package com.example.billSplit.controllers;

import com.example.billSplit.dtos.PayBill;
import com.example.billSplit.entites.Payment;
import com.example.billSplit.entites.Transaction;
import com.example.billSplit.entites.User;
import com.example.billSplit.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/bill/{billId}")
    public List<Transaction> getAllTransactionsOfABill(@PathVariable Long billId) {
        return transactionService.getAllTransactions(billId);
    }

    @GetMapping("/{transactionID}")
    public Transaction getTransactionById(@PathVariable Long transactionID) {
        return transactionService.getTransactionById(transactionID);
    }

    @PostMapping("/{billId}")
    public Payment createTransaction(@RequestBody PayBill payBill, @PathVariable Long billId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return transactionService.createTransaction(payBill, user, billId);
    }

    @PutMapping("/{transactionID}")
    public Transaction updateTransaction(@PathVariable Long transactionID,
    @RequestBody Transaction transaction) {
    return transactionService.updateTransaction(transactionID, transaction);
    }

    @DeleteMapping("/{transactionID}")
    public void deleteTransaction(@PathVariable Long transactionID) {
        transactionService.deleteTransaction(transactionID);
    }
}
