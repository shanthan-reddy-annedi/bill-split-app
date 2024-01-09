package com.example.billSplit.service;

import com.example.billSplit.entites.Transaction;
import com.example.billSplit.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long transactionID) {
        return transactionRepository.findById(transactionID).orElse(null);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long transactionID, Transaction transaction) {
        if (transactionRepository.existsById(transactionID)) {
            transaction.setTransactionID(transactionID);
            return transactionRepository.save(transaction);
        }
        return null; // Handle not found scenario
    }

    public void deleteTransaction(Long transactionID) {
        transactionRepository.deleteById(transactionID);
    }
}
