/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.customer.bean;

import com.epic.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author nipun_t
 */
public class CostomerRiskProfileManagementInputBean {

    //Search Data
    private String username;
    private String rpname = "";
    private String id = "";
    private boolean search;

    //Add data
    private String name;
    private String maxamountperday;
    private String transfermode;
    private HashMap<String, String> transferModeList = new HashMap<String, String>();
    private String msgdeliverymodeord;
    private HashMap<String, String> messegeDeliveryList = new HashMap<String, String>();
    private String chargesmode;
    private String msgvalidityperiod;
    private TreeMap<String, String> msgvalidityperiodList = new TreeMap<String, String>();
    private HashMap<String, String> userProList = new HashMap<String, String>();
    private String msgformatcode;
    private HashMap<String, String> msgFormatList = new HashMap<String, String>();

    private String nooftxnallowedperdayrecipient;
    private String minamountpertxn;
    private String maxamountpertxn;
    private String feecalculationmethod;
    private HashMap<String, String> feeCalMethodList = new HashMap<String, String>();
    private String feevalue;
    private String msgdeliverymodesec;
    private String globalamount;

    //Delete Data
    private String message;
    private boolean success;

    //Update Data
    private String upid;
    private String upname;
    private String upmaxamountperday;
    private String uptransfermode;
    private String upmsgdeliverymodeord;
    private String upmsgvalidityperiod;
    private String upmsgformatcode;
    private String upchargesmode;
    private String upnooftxnallowedperdayrecipient;
    private String upminamountpertxn;
    private String upmaxamountpertxn;
    private String upfeecalculationmethod;
    private String upfeevalue;
    private String upmsgdeliverymodesec;
    private String upstatus;
    private String upglobalamount;
    private Map<Integer, String> upstatusList = Util.getBasicStatus();

    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdate;
    private boolean vdelete;
    private boolean vdownload;
    private boolean vresetpass;
    //    private boolean vactdct;
    /*-------for access control-----------*/

    //Table data
    private List<CustomerRiskProfileManagementBean> gridModel = new ArrayList<CustomerRiskProfileManagementBean>();
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;

    public boolean isVadd() {
        return vadd;
    }

    public String getRpname() {
        return rpname;
    }

    public void setRpname(String rpname) {
        this.rpname = rpname;
    }

    public String getUpstatus() {
        return upstatus;
    }

    public void setUpstatus(String upstatus) {
        this.upstatus = upstatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGlobalamount() {
        return globalamount;
    }

    public void setGlobalamount(String globalamount) {
        this.globalamount = globalamount;
    }

    public String getUpglobalamount() {
        return upglobalamount;
    }

    public void setUpglobalamount(String upglobalamount) {
        this.upglobalamount = upglobalamount;
    }

    public String getMaxamountperday() {
        return maxamountperday;
    }

    public String getUpid() {
        return upid;
    }

    public String getUpchargesmode() {
        return upchargesmode;
    }

    public void setUpchargesmode(String upchargesmode) {
        this.upchargesmode = upchargesmode;
    }

    public void setUpid(String upid) {
        this.upid = upid;
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

    public HashMap<String, String> getTransferModeList() {
        return transferModeList;
    }

    public void setTransferModeList(HashMap<String, String> transferModeList) {
        this.transferModeList = transferModeList;
    }

    public String getMsgdeliverymodeord() {
        return msgdeliverymodeord;
    }

    public TreeMap<String, String> getMsgvalidityperiodList() {
        return msgvalidityperiodList;
    }

    public void setMsgvalidityperiodList(TreeMap<String, String> msgvalidityperiodList) {
        this.msgvalidityperiodList = msgvalidityperiodList;
    }

    public void setMsgdeliverymodeord(String msgdeliverymodeord) {
        this.msgdeliverymodeord = msgdeliverymodeord;
    }

    public HashMap<String, String> getMessegeDeliveryList() {
        return messegeDeliveryList;
    }

    public void setMessegeDeliveryList(HashMap<String, String> messegeDeliveryList) {
        this.messegeDeliveryList = messegeDeliveryList;
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

    public HashMap<String, String> getMsgFormatList() {
        return msgFormatList;
    }

    public void setMsgFormatList(HashMap<String, String> msgFormatList) {
        this.msgFormatList = msgFormatList;
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

    public String getMaxamountpertxn() {
        return maxamountpertxn;
    }

    public void setMaxamountpertxn(String maxamountpertxn) {
        this.maxamountpertxn = maxamountpertxn;
    }

    public String getFeecalculationmethod() {
        return feecalculationmethod;
    }

    public void setFeecalculationmethod(String feecalculationmethod) {
        this.feecalculationmethod = feecalculationmethod;
    }

    public HashMap<String, String> getFeeCalMethodList() {
        return feeCalMethodList;
    }

    public void setFeeCalMethodList(HashMap<String, String> feeCalMethodList) {
        this.feeCalMethodList = feeCalMethodList;
    }

    public String getFeevalue() {
        return feevalue;
    }

    public void setFeevalue(String feevalue) {
        this.feevalue = feevalue;
    }

    public String getMsgdeliverymodesec() {
        return msgdeliverymodesec;
    }

    public void setMsgdeliverymodesec(String msgdeliverymodesec) {
        this.msgdeliverymodesec = msgdeliverymodesec;
    }

    public void setVadd(boolean vadd) {
        this.vadd = vadd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getUserProList() {
        return userProList;
    }

    public void setUserProList(HashMap<String, String> userProList) {
        this.userProList = userProList;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<Integer, String> getUpstatusList() {
        return upstatusList;
    }

    public void setUpstatusList(Map<Integer, String> upstatusList) {
        this.upstatusList = upstatusList;
    }

    public String getUpname() {
        return upname;
    }

    public void setUpname(String upname) {
        this.upname = upname;
    }

    public String getUpmaxamountperday() {
        return upmaxamountperday;
    }

    public void setUpmaxamountperday(String upmaxamountperday) {
        this.upmaxamountperday = upmaxamountperday;
    }

    public String getUptransfermode() {
        return uptransfermode;
    }

    public void setUptransfermode(String uptransfermode) {
        this.uptransfermode = uptransfermode;
    }

    public String getUpmsgdeliverymodeord() {
        return upmsgdeliverymodeord;
    }

    public void setUpmsgdeliverymodeord(String upmsgdeliverymodeord) {
        this.upmsgdeliverymodeord = upmsgdeliverymodeord;
    }

    public String getUpmsgvalidityperiod() {
        return upmsgvalidityperiod;
    }

    public void setUpmsgvalidityperiod(String upmsgvalidityperiod) {
        this.upmsgvalidityperiod = upmsgvalidityperiod;
    }

    public String getUpmsgformatcode() {
        return upmsgformatcode;
    }

    public void setUpmsgformatcode(String upmsgformatcode) {
        this.upmsgformatcode = upmsgformatcode;
    }

    public String getUpnooftxnallowedperdayrecipient() {
        return upnooftxnallowedperdayrecipient;
    }

    public void setUpnooftxnallowedperdayrecipient(String upnooftxnallowedperdayrecipient) {
        this.upnooftxnallowedperdayrecipient = upnooftxnallowedperdayrecipient;
    }

    public String getUpminamountpertxn() {
        return upminamountpertxn;
    }

    public void setUpminamountpertxn(String upminamountpertxn) {
        this.upminamountpertxn = upminamountpertxn;
    }

    public String getUpmaxamountpertxn() {
        return upmaxamountpertxn;
    }

    public void setUpmaxamountpertxn(String upmaxamountpertxn) {
        this.upmaxamountpertxn = upmaxamountpertxn;
    }

    public String getUpfeecalculationmethod() {
        return upfeecalculationmethod;
    }

    public void setUpfeecalculationmethod(String upfeecalculationmethod) {
        this.upfeecalculationmethod = upfeecalculationmethod;
    }

    public String getUpfeevalue() {
        return upfeevalue;
    }

    public void setUpfeevalue(String upfeevalue) {
        this.upfeevalue = upfeevalue;
    }

    public String getUpmsgdeliverymodesec() {
        return upmsgdeliverymodesec;
    }

    public void setUpmsgdeliverymodesec(String upmsgdeliverymodesec) {
        this.upmsgdeliverymodesec = upmsgdeliverymodesec;
    }

    public List<CustomerRiskProfileManagementBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<CustomerRiskProfileManagementBean> gridModel) {
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

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }
}
