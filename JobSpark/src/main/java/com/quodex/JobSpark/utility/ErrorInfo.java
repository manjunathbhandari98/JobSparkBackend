package com.quodex.JobSpark.utility;

import java.time.LocalDateTime;

/**
 * This class represents error information, including an error message,
 * an error code, and a timestamp indicating when the error occurred.
 */


public class ErrorInfo {
    private String errorMessage; // Stores the error message
    private Integer errorCode; // Stores the error code
    private LocalDateTime timeStamp; // Stores the timestamp of when the error occurred

    // Default constructor
    public ErrorInfo() {}

    /**
     * Parameterized constructor to initialize the error information.
     *
     * errorMessage - Description of the error
     * errorCode - Numeric code representing the error type
     * timeStamp - Time when the error occurred
     */
    public ErrorInfo(String errorMessage, Integer errorCode, LocalDateTime timeStamp) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.timeStamp = timeStamp;
    }

    // Getter method for retrieving the error message
    public String getErrorMessage() {
        return errorMessage;
    }

    // Setter method for updating the error message
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // Getter method for retrieving the error code
    public Integer getErrorCode() {
        return errorCode;
    }

    // Setter method for updating the error code
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    // Getter method for retrieving the timestamp
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    // Setter method for updating the timestamp
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
