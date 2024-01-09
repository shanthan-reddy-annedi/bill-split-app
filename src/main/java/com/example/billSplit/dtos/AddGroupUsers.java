package com.example.billSplit.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class AddGroupUsers {
    Set<String> userEmail;
}
