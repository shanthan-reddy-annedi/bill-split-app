package com.example.billSplit.exception;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApiErrorResponse {
    private final String message;
    private final Integer statusCode;
    private final String statusName;
    private final LocalDateTime timestamp;
}