package com.example.billSplit.service;

import com.example.billSplit.dtos.CreateBill;
import com.example.billSplit.entites.*;
import com.example.billSplit.exception.ApplicationException;
import com.example.billSplit.repositories.BillRepository;
import com.example.billSplit.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Bill> getAllBillsOfGroup(Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return billRepository.findAllBillsByGroup(group);
    }

    public Bill getBillById(Long billID) {
        return billRepository.findById(billID).orElse(null);
    }

    public void createBill(CreateBill bill) {
        User payer = userService.getUserById(bill.getUserId());
        Group group = groupService.getGroupById(bill.getGroupId());
        if (payer == null || group == null) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST.value(), "Invalid Group or Payer", HttpStatus.BAD_REQUEST);
        }
        List<Long> groupUserIds = new ArrayList<>();
        List<Long> billUserIds = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for(User user: group.getUsers()){
            groupUserIds.add(user.getUserID());
        }

        for(Long id: bill.getAmountsToPay().keySet()){
            billUserIds.add(id);
            totalAmount = totalAmount.add(bill.getAmountsToPay().get(id));
        }

        if (totalAmount.equals(BigDecimal.ZERO)){
            totalAmount = bill.getTotalAmount();
        }

        boolean allPresent = new HashSet<>(groupUserIds).containsAll(billUserIds);

        if(!groupUserIds.contains(payer.getUserID()) || !allPresent || totalAmount.equals(bill.getTotalAmount())){
            throw new ApplicationException(HttpStatus.BAD_REQUEST.value(),
                    "Invalid UserID or Amount or User is not present in Group",
                    HttpStatus.BAD_REQUEST);
        }

        Bill bill1 = Bill.builder()
                .group(group)
                .payer(payer)
                .description(bill.getDescription())
                .totalAmount(bill.getTotalAmount())
                .build();

        Bill finalBill = billRepository.save(bill1);

        List<Transaction> transactions = new ArrayList<>();

        for(Long id: bill.getAmountsToPay().keySet()){
            User user = userService.getUserById(id);
            BigDecimal amount = bill.getAmountsToPay().get(id);
            Transaction transaction = Transaction.builder()
                    .group(group)
                    .payedToId(payer)
                    .bill(finalBill)
                    .payerId(user)
                    .totalAmount(amount)
                    .paidAmount(BigDecimal.ZERO)
                    .status(TransactionStatus.OPEN)
                    .build();

            transactions.add(transaction);
        }
        transactionRepository.saveAll(transactions);
    }

    public void deleteBill(Long billID) {
        billRepository.deleteById(billID);
    }
}
