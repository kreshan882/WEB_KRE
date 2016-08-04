/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.transactionmgt.bean;

/**
 *
 * @author nipun_t
 */
public class TransactionSmsHistoryBean {

    private String id;
    private String txnid;
    private String deliveryparty;
    private String mobileno;
    private String smsmsg;
    private String timestamp;

    private long fullCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getSmsmsg() {
        return smsmsg;
    }

    public void setSmsmsg(String smsmsg) {
        this.smsmsg = smsmsg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeliveryparty() {
        return deliveryparty;
    }

    public void setDeliveryparty(String deliveryparty) {
        this.deliveryparty = deliveryparty;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

}
