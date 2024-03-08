package com.ennov.tickets.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;

}
