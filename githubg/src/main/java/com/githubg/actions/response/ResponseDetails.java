package com.githubg.actions.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseDetails {
    private Date timestamp;
    private int responseCode;
    private String message;
    private String responseMsg;
}
