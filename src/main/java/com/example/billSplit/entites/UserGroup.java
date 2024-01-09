package com.example.billSplit.entites;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "UserGroup")
@Data
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userGroupID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "groupID", nullable = false)
    private Group group;

}

