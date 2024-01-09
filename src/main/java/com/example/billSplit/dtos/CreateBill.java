package com.example.billSplit.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class CreateBill {
    private Long groupId;
    private Long userId;
    private String description;
    private BigDecimal totalAmount;
    private Map<Long, BigDecimal> amountsToPay;
}
