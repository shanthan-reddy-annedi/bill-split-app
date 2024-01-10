package com.example.billSplit.dtos;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayBill {
    private BigDecimal amount;
}
