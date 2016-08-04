/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.sms.bean;

import com.epic.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kreshan
 */
public class SmsTemplateInputBean {

    //Search Data

    private String profileId = "";
    private boolean search;
    private String proId;
    //Add data
    private String smstprofileId;
    private String smstemplatecId;
    private String msg;
    private Map<String, String> smstprofileList = new HashMap<String, String>();
    private Map<String, String> smstemplatecList = new HashMap<String, String>();
    private String senderType;
    private Map<String, String> senderTypeList = Util.getMessegeDeliveryPartyList();

    //Delete Data
    private String message;
    private boolean success;

    //Update Data
    private String upsmstprofileId;
    private String upsmsproname;
    private String upsmstemplatecId;
    private String upsmstcname;
    private String upmsg;
    private String upstatus1;
    private Map<Integer, String> upstatusList1 = Util.getBasicStatus();

    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdate;
    private boolean vdelete;
    private boolean vdownload;
    private boolean vresetpass;
    //    private boolean vactdct;
    /*-------for access control-----------*/

    //Table data
    private List<SmsBean> gridModel = new ArrayList<SmsBean>();
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public String getSmstprofileId() {
        return smstprofileId;
    }

    public void setSmstprofileId(String smstprofileId) {
        this.smstprofileId = smstprofileId;
    }

    public String getSmstemplatecId() {
        return smstemplatecId;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public void setSmstemplatecId(String smstemplatecId) {
        this.smstemplatecId = smstemplatecId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, String> getSmstprofileList() {
        return smstprofileList;
    }

    public void setSmstprofileList(Map<String, String> smstprofileList) {
        this.smstprofileList = smstprofileList;
    }

    public Map<String, String> getSmstemplatecList() {
        return smstemplatecList;
    }

    public void setSmstemplatecList(Map<String, String> smstemplatecList) {
        this.smstemplatecList = smstemplatecList;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public Map<String, String> getSenderTypeList() {
        return senderTypeList;
    }

    public void setSenderTypeList(Map<String, String> senderTypeList) {
        this.senderTypeList = senderTypeList;
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

    public String getUpsmstprofileId() {
        return upsmstprofileId;
    }

    public void setUpsmstprofileId(String upsmstprofileId) {
        this.upsmstprofileId = upsmstprofileId;
    }

    public String getUpsmsproname() {
        return upsmsproname;
    }

    public void setUpsmsproname(String upsmsproname) {
        this.upsmsproname = upsmsproname;
    }

    public String getUpsmstemplatecId() {
        return upsmstemplatecId;
    }

    public void setUpsmstemplatecId(String upsmstemplatecId) {
        this.upsmstemplatecId = upsmstemplatecId;
    }

    public String getUpsmstcname() {
        return upsmstcname;
    }

    public void setUpsmstcname(String upsmstcname) {
        this.upsmstcname = upsmstcname;
    }

    public String getUpmsg() {
        return upmsg;
    }

    public void setUpmsg(String upmsg) {
        this.upmsg = upmsg;
    }

    public boolean isVadd() {
        return vadd;
    }

    public String getUpstatus1() {
        return upstatus1;
    }

    public void setUpstatus1(String upstatus1) {
        this.upstatus1 = upstatus1;
    }

    public Map<Integer, String> getUpstatusList1() {
        return upstatusList1;
    }

    public void setUpstatusList1(Map<Integer, String> upstatusList1) {
        this.upstatusList1 = upstatusList1;
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

    public List<SmsBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<SmsBean> gridModel) {
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
