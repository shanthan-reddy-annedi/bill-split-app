package com.example.billSplit.entites;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Groups")
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupID;

    private String groupName;

    @ManyToMany
    @JoinTable(
            name = "UserGroup",
            joinColumns = @JoinColumn(name = "groupID"),
            inverseJoinColumns = @JoinColumn(name = "userID"))
    private Set<User> users = new HashSet<>();
}
