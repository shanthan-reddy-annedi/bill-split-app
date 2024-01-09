package com.example.billSplit.entites;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Transaction")
@Data
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionID;

    @ManyToOne
    @JoinColumn(name = "billID", nullable = false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "payedToId", nullable = false)
    private User payedToId;

    @ManyToOne
    @JoinColumn(name = "payeeID", nullable = false)
    private User payerId;

    private BigDecimal totalAmount;

    private BigDecimal paidAmount;

    private Date datePaid;

    private Date createdOn;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne
    @JoinColumn(name = "groupID", nullable = false)
    private Group group;

    @PrePersist
    protected void onCreate() {
        this.createdOn = new Date();
    }
}

