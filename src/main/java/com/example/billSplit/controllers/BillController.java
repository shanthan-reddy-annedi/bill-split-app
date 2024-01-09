package com.example.billSplit.controllers;

import com.example.billSplit.dtos.CreateBill;
import com.example.billSplit.entites.Bill;
import com.example.billSplit.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping("/group/{groupId}")
    public List<Bill> getAllBillsOfGroup(@PathVariable Long groupId) {
        return billService.getAllBillsOfGroup(groupId);
    }

    @GetMapping("/{billID}")
    public Bill getBillById(@PathVariable Long billID) {
        return billService.getBillById(billID);
    }

    @PostMapping
    public void createBill(@RequestBody CreateBill createBill) {
        billService.createBill(createBill);
    }

    @DeleteMapping("/{billID}")
    public void deleteBill(@PathVariable Long billID) {
        billService.deleteBill(billID);
    }
}

