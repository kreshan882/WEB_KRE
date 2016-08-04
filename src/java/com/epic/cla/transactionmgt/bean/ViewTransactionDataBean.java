/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.transactionmgt.bean;

/**
 *
 * @author tharaka
 */
public class ViewTransactionDataBean {

    private String id;
    private String txn_type;
    private String customerID;
    private String recepientMobile;
    private String amount;
    private String orderID;
    private String customerAccountNumber;
    private String channel;
    private String customerMobile;
    private String refNo;
    private String batchNumber;
    private String serviceCharge;
    private String responseCode;
    private String transactionDate;
    private String status;
    private String statusCode;
    private String collectionaccount;
    private String glaccount;
    private String costcenter;
    private String itmtid;
    private String itmlocation;
    private String datetime;
    private String timestamp;

    private long fullCount;

    //report data define hear
    private String TXN_TYPE;
    private String CUS_NAME;
    private String CUS_ACOUNT;
    private String REC_MOBILE;
    private String ORDER_ID;

    private String AMOUNT;
    private String SERVICE_FEE;
    private String CUS_MOBILE;
    private String RESPONCE_CODE;
    private String TXN_DATE;
    private String CHANNEL_TYPE;

    private String DATETIME;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTxn_type() {
        return txn_type;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItmtid() {
        return itmtid;
    }

    public void setItmtid(String itmtid) {
        this.itmtid = itmtid;
    }

    public String getItmlocation() {
        return itmlocation;
    }

    public void setItmlocation(String itmlocation) {
        this.itmlocation = itmlocation;
    }

    public void setTxn_type(String txn_type) {
        this.txn_type = txn_type;
    }

    public String getCHANNEL_TYPE() {
        return CHANNEL_TYPE;
    }

    public void setCHANNEL_TYPE(String CHANNEL_TYPE) {
        this.CHANNEL_TYPE = CHANNEL_TYPE;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getCollectionaccount() {
        return collectionaccount;
    }

    public void setCollectionaccount(String collectionaccount) {
        this.collectionaccount = collectionaccount;
    }

    public String getGlaccount() {
        return glaccount;
    }

    public void setGlaccount(String glaccount) {
        this.glaccount = glaccount;
    }

    public String getCostcenter() {
        return costcenter;
    }

    public void setCostcenter(String costcenter) {
        this.costcenter = costcenter;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getRecepientMobile() {
        return recepientMobile;
    }

    public void setRecepientMobile(String recepientMobile) {
        this.recepientMobile = recepientMobile;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    public void setCustomerAccountNumber(String customerAccountNumber) {
        this.customerAccountNumber = customerAccountNumber;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    public String getTXN_TYPE() {
        return TXN_TYPE;
    }

    public void setTXN_TYPE(String TXN_TYPE) {
        this.TXN_TYPE = TXN_TYPE;
    }

    public String getCUS_NAME() {
        return CUS_NAME;
    }

    public void setCUS_NAME(String CUS_NAME) {
        this.CUS_NAME = CUS_NAME;
    }

    public String getCUS_ACOUNT() {
        return CUS_ACOUNT;
    }

    public void setCUS_ACOUNT(String CUS_ACOUNT) {
        this.CUS_ACOUNT = CUS_ACOUNT;
    }

    public String getREC_MOBILE() {
        return REC_MOBILE;
    }

    public void setREC_MOBILE(String REC_MOBILE) {
        this.REC_MOBILE = REC_MOBILE;
    }

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public void setORDER_ID(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getSERVICE_FEE() {
        return SERVICE_FEE;
    }

    public void setSERVICE_FEE(String SERVICE_FEE) {
        this.SERVICE_FEE = SERVICE_FEE;
    }

    public String getCUS_MOBILE() {
        return CUS_MOBILE;
    }

    public void setCUS_MOBILE(String CUS_MOBILE) {
        this.CUS_MOBILE = CUS_MOBILE;
    }

    public String getRESPONCE_CODE() {
        return RESPONCE_CODE;
    }

    public void setRESPONCE_CODE(String RESPONCE_CODE) {
        this.RESPONCE_CODE = RESPONCE_CODE;
    }

    public String getTXN_DATE() {
        return TXN_DATE;
    }

    public void setTXN_DATE(String TXN_DATE) {
        this.TXN_DATE = TXN_DATE;
    }

    public String getDATETIME() {
        return DATETIME;
    }

    public void setDATETIME(String DATETIME) {
        this.DATETIME = DATETIME;
    }

}
