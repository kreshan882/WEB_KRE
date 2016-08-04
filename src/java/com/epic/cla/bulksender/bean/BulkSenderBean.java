/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.bulksender.bean;

/**
 *
 * @author dimuthu_h
 */
public class BulkSenderBean {
    private String recipient_mobile;
    private String amount;
    private String status;
    private String customerId;
    private String batchNo;
    
    private long fullCount;

    public String getRecipient_mobile() {
        return recipient_mobile;
    }

    public void setRecipient_mobile(String recipient_mobile) {
        this.recipient_mobile = recipient_mobile;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
    
}
