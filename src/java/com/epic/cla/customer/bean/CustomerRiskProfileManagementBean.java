/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.customer.bean;

/**
 *
 * @author nipun_t
 */
public class CustomerRiskProfileManagementBean {

    private String profileId;
    private String usertypeID;
    private String id;

    private String name;
    private String maxamountperday;
    private String transfermode;
    private String msgdeliverymodeord;
    private String chargesmode;
    private String msgvalidityperiod;
    private String msgformatcode;
    private String nooftxnallowedperdayrecipient;
    private String minamountpertxn;
    private String maxamountpertxn;
    private String feecalculationmethod;
    private String feevalue;
    private String globalamount;
    private String defaultStatus;

    private String msgdeliverymodesec;
    private String status;

    private long fullCount;

    public String getProfileId() {
        return profileId;
    }

    public String getMaxamountpertxn() {
        return maxamountpertxn;
    }

    public void setMaxamountpertxn(String maxamountpertxn) {
        this.maxamountpertxn = maxamountpertxn;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getUsertypeID() {
        return usertypeID;
    }

    public void setUsertypeID(String usertypeID) {
        this.usertypeID = usertypeID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(String defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaxamountperday() {
        return maxamountperday;
    }

    public void setMaxamountperday(String maxamountperday) {
        this.maxamountperday = maxamountperday;
    }

    public String getTransfermode() {
        return transfermode;
    }

    public void setTransfermode(String transfermode) {
        this.transfermode = transfermode;
    }

    public String getMsgdeliverymodeord() {
        return msgdeliverymodeord;
    }

    public void setMsgdeliverymodeord(String msgdeliverymodeord) {
        this.msgdeliverymodeord = msgdeliverymodeord;
    }

    public String getChargesmode() {
        return chargesmode;
    }

    public void setChargesmode(String chargesmode) {
        this.chargesmode = chargesmode;
    }

    public String getMsgvalidityperiod() {
        return msgvalidityperiod;
    }

    public void setMsgvalidityperiod(String msgvalidityperiod) {
        this.msgvalidityperiod = msgvalidityperiod;
    }

    public String getMsgformatcode() {
        return msgformatcode;
    }

    public void setMsgformatcode(String msgformatcode) {
        this.msgformatcode = msgformatcode;
    }

    public String getNooftxnallowedperdayrecipient() {
        return nooftxnallowedperdayrecipient;
    }

    public void setNooftxnallowedperdayrecipient(String nooftxnallowedperdayrecipient) {
        this.nooftxnallowedperdayrecipient = nooftxnallowedperdayrecipient;
    }

    public String getMinamountpertxn() {
        return minamountpertxn;
    }

    public void setMinamountpertxn(String minamountpertxn) {
        this.minamountpertxn = minamountpertxn;
    }

    public String getFeecalculationmethod() {
        return feecalculationmethod;
    }

    public void setFeecalculationmethod(String feecalculationmethod) {
        this.feecalculationmethod = feecalculationmethod;
    }

    public String getFeevalue() {
        return feevalue;
    }

    public void setFeevalue(String feevalue) {
        this.feevalue = feevalue;
    }

    public String getGlobalamount() {
        return globalamount;
    }

    public void setGlobalamount(String globalamount) {
        this.globalamount = globalamount;
    }

    public String getMsgdeliverymodesec() {
        return msgdeliverymodesec;
    }

    public void setMsgdeliverymodesec(String msgdeliverymodesec) {
        this.msgdeliverymodesec = msgdeliverymodesec;
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

}
