/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.channel.bean;

import com.epic.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dimuthu_h
 */
public class ListenerProfileManagementInputBean {

    //Update new listener 
    private String id;
    private String listenerName;
    private String amountHold;
    private String senderValidation;
    private String txnFee;
    private String collectionacc;
    private String glacc;
    private String costCenter;

    private Map<Integer, String> amountHoldMap = Util.getAliveStatus();
    private Map<Integer, String> senderValidationMap = Util.getAliveStatus();
    private Map<Integer, String> txnFeeMap = Util.getAliveStatus();

    //Delete Data
    private String message;
    private boolean success;

    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdate;
    private boolean vdelete;
    private boolean vdownload;
    private boolean vresetpass;
    //    private boolean vactdct;
    /*-------for access control-----------*/

    //Table data
    private List<ListenerProfileManagementBean> gridModel = new ArrayList<ListenerProfileManagementBean>();
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;

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

    public Map<Integer, String> getAmountHoldMap() {
        return amountHoldMap;
    }

    public void setAmountHoldMap(Map<Integer, String> amountHoldMap) {
        this.amountHoldMap = amountHoldMap;
    }

    public Map<Integer, String> getSenderValidationMap() {
        return senderValidationMap;
    }

    public void setSenderValidationMap(Map<Integer, String> senderValidationMap) {
        this.senderValidationMap = senderValidationMap;
    }

    public Map<Integer, String> getTxnFeeMap() {
        return txnFeeMap;
    }

    public void setTxnFeeMap(Map<Integer, String> txnFeeMap) {
        this.txnFeeMap = txnFeeMap;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isVadd() {
        return vadd;
    }

    public void setVadd(boolean vadd) {
        this.vadd = vadd;
    }

    public boolean isVupdate() {
        return vupdate;
    }

    public void setVupdate(boolean vupdate) {
        this.vupdate = vupdate;
    }

    public boolean isVdelete() {
        return vdelete;
    }

    public void setVdelete(boolean vdelete) {
        this.vdelete = vdelete;
    }

    public boolean isVdownload() {
        return vdownload;
    }

    public void setVdownload(boolean vdownload) {
        this.vdownload = vdownload;
    }

    public boolean isVresetpass() {
        return vresetpass;
    }

    public void setVresetpass(boolean vresetpass) {
        this.vresetpass = vresetpass;
    }

    public List<ListenerProfileManagementBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<ListenerProfileManagementBean> gridModel) {
        this.gridModel = gridModel;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchOper() {
        return searchOper;
    }

    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }

}
