package com.githubg.actions.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}
