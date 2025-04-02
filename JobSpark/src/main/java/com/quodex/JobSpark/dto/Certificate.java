package com.quodex.JobSpark.dto;

import java.time.LocalDateTime;

public class Certificate {
    private String name;
    private String issuer;
    private LocalDateTime issueDate;
    private String certificateId;

    public Certificate(String name, String issuer, LocalDateTime issueDate, String certificateId){
        this.name = name;
        this.issuer = issuer;
        this.issueDate = issueDate;
        this.certificateId = certificateId;
    }

    public Certificate(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }
}
