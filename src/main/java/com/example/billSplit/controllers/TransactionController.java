package com.example.billSplit.controllers;

import com.example.billSplit.entites.Transaction;
import com.example.billSplit.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/bill/{billId}")
    public List<Transaction> getAllTransactionsOfABill(@PathVariable Long billId) {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{transactionID}")
    public Transaction getTransactionById(@PathVariable Long transactionID) {
        return transactionService.getTransactionById(transactionID);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @PutMapping("/{transactionID}")
    public Transaction updateTransaction(@PathVariable Long transactionID, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(transactionID, transaction);
    }

    @DeleteMapping("/{transactionID}")
    public void deleteTransaction(@PathVariable Long transactionID) {
        transactionService.deleteTransaction(transactionID);
    }
}
