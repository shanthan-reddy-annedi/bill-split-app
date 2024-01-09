package com.example.billSplit.entites;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Bill")
@Data
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billID;

    @ManyToOne
    @JoinColumn(name = "groupID", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "payerID", nullable = false)
    private User payer;

    private String description;

    private BigDecimal totalAmount;

    private Date dateIssued;

//    private boolean isSplit;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @PrePersist
    protected void onCreate() {
        this.dateIssued = new Date();
    }
}

