package org.gamsiz.chatapp.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetail {

    private final Date timestamp;
    private final String message;
    private final String details;

}
