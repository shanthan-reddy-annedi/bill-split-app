package com.example.billSplit.service;

import com.example.billSplit.entites.Payment;
import com.example.billSplit.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long paymentID) {
        return paymentRepository.findById(paymentID).orElse(null);
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long paymentID, Payment payment) {
        if (paymentRepository.existsById(paymentID)) {
            payment.setPaymentID(paymentID);
            return paymentRepository.save(payment);
        }
        return null;
    }

    public void deletePayment(Long paymentID) {
        paymentRepository.deleteById(paymentID);
    }
}
