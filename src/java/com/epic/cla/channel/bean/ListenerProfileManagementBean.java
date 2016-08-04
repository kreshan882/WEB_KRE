/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.channel.bean;

/**
 *
 * @author dimuthu_h
 */
public class ListenerProfileManagementBean {

    private String id;
    private String listenerName;
    private String amountHold;
    private String senderValidation;
    private String txnFee;
    private String collectionacc;
    private String glacc;
    private String costCenter;

    private long fullCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListenerName() {
        return listenerName;
    }

    public void setListenerName(String listenerName) {
        this.listenerName = listenerName;
    }

    public String getAmountHold() {
        return amountHold;
    }

    public void setAmountHold(String amountHold) {
        this.amountHold = amountHold;
    }

    public String getSenderValidation() {
        return senderValidation;
    }

    public void setSenderValidation(String senderValidation) {
        this.senderValidation = senderValidation;
    }

    public String getTxnFee() {
        return txnFee;
    }

    public void setTxnFee(String txnFee) {
        this.txnFee = txnFee;
    }

    public String getCollectionacc() {
        return collectionacc;
    }

    public void setCollectionacc(String collectionacc) {
        this.collectionacc = collectionacc;
    }

    public String getGlacc() {
        return glacc;
    }

    public void setGlacc(String glacc) {
        this.glacc = glacc;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

}
