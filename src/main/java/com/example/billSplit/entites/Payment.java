package com.example.billSplit.entites;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Payment")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;

    @ManyToOne
    @JoinColumn(name = "transactionID", nullable = false)
    private Transaction transaction;

    private BigDecimal amount;

    private Date datePaid;

    private Date createdOn;

    @PrePersist
    protected void onCreate() {
        this.createdOn = new Date();
    }

}