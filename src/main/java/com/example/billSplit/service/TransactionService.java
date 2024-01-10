package com.example.billSplit.service;

import com.example.billSplit.dtos.PayBill;
import com.example.billSplit.entites.Bill;
import com.example.billSplit.entites.Payment;
import com.example.billSplit.entites.Transaction;
import com.example.billSplit.entites.TransactionStatus;
import com.example.billSplit.entites.User;
import com.example.billSplit.exception.ApplicationException;
import com.example.billSplit.repositories.BillRepository;
import com.example.billSplit.repositories.PaymentRepository;
import com.example.billSplit.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Transaction> getAllTransactions(Long billId) {
        Bill bill = this.billRepository.findById(billId).orElseThrow(
                () -> new ApplicationException(HttpStatus.FORBIDDEN.value(), "Bill Not Found", HttpStatus.FORBIDDEN));
        return transactionRepository.findTransactionsByBillId(bill);
    }

    public Transaction getTransactionById(Long transactionID) {
        return transactionRepository.findById(transactionID).orElse(null);
    }

    public Payment createTransaction(PayBill payBill, User user, Long billId) {
        Bill bill = this.billRepository.findById(billId).orElseThrow(
                () -> new ApplicationException(HttpStatus.FORBIDDEN.value(), "Bill Not Found", HttpStatus.FORBIDDEN));
        Transaction transaction = this.transactionRepository.getTransactionByUserAndBill(bill, user);
        if (transaction.getStatus().equals(TransactionStatus.COMPLETED)) {
            return null;
        }
        if (transaction.getTotalAmount().compareTo(payBill.getAmount()) < 0) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST.value(),
                    "Given amount is greater than Amount to be paid", HttpStatus.BAD_REQUEST);
        }
        transaction.setPaidAmount(payBill.getAmount());
        if (transaction.getTotalAmount().equals(payBill.getAmount())) {
            transaction.setStatus(TransactionStatus.COMPLETED);
        } else {
            transaction.setStatus(TransactionStatus.PARTIAL);
        }
        transaction.setDatePaid(new Date());

        transaction = this.transactionRepository.save(transaction);

        Payment payment = Payment.builder()
                        .amount(payBill.getAmount())
                        .transaction(transaction).build();
        payment=this.paymentRepository.save(payment);
        return payment;
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
