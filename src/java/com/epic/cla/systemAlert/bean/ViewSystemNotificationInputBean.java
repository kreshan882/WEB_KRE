/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.systemAlert.bean;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nipun_t
 */
public class ViewSystemNotificationInputBean {

    //Search Data
    private String username = "";
    private String id = "";
    private boolean search;
    private String fromdate = "";
    private String todate = "";
    private String risklevelform = "";
    private Map<String, String> riskLevelList = new HashMap<String, String>();
    private String readstatusform = "";
    private Map<String, String> readStatusList = new HashMap<String, String>();
    private String alertidform = "";
    private HashMap<String, String> userProList = new HashMap<String, String>();
    
    private String criticalAlertCountSysAlert;
    private String warningAlertCountSysAlert;
    //Delete Data
    private String message;
    private boolean success;
    //**************Export XSL***************
    private ByteArrayInputStream excelStream;
    private ByteArrayInputStream zipStream;


    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdate;
    private boolean vdelete;
    private boolean vdownload;
    private boolean vresetpass;
    //    private boolean vactdct;
    /*-------for access control-----------*/

    //Table data
    private List<ViewSystemNotificationBean> gridModel = new ArrayList<ViewSystemNotificationBean>();
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

 
    public void setRiskLevelList(HashMap<String, String> riskLevelList) {
        this.riskLevelList = riskLevelList;
    }

  
    public void setReadStatusList(HashMap<String, String> readStatusList) {
        this.readStatusList = readStatusList;
    }

    public String getId() {
        return id;
    }

    public String getFromdate() {
        return fromdate;
    }

    public String getCriticalAlertCountSysAlert() {
        return criticalAlertCountSysAlert;
    }

    public void setCriticalAlertCountSysAlert(String criticalAlertCountSysAlert) {
        this.criticalAlertCountSysAlert = criticalAlertCountSysAlert;
    }

    public String getWarningAlertCountSysAlert() {
        return warningAlertCountSysAlert;
    }

    public void setWarningAlertCountSysAlert(String warningAlertCountSysAlert) {
        this.warningAlertCountSysAlert = warningAlertCountSysAlert;
    }

    public Map<String, String> getRiskLevelList() {
        return riskLevelList;
    }

    public void setRiskLevelList(Map<String, String> riskLevelList) {
        this.riskLevelList = riskLevelList;
    }

    public Map<String, String> getReadStatusList() {
        return readStatusList;
    }

    public void setReadStatusList(Map<String, String> readStatusList) {
        this.readStatusList = readStatusList;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public String getRisklevelform() {
        return risklevelform;
    }

    public void setRisklevelform(String risklevelform) {
        this.risklevelform = risklevelform;
    }

    public String getReadstatusform() {
        return readstatusform;
    }

    public void setReadstatusform(String readstatusform) {
        this.readstatusform = readstatusform;
    }

    public String getAlertidform() {
        return alertidform;
    }

    public void setAlertidform(String alertidform) {
        this.alertidform = alertidform;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public HashMap<String, String> getUserProList() {
        return userProList;
    }

    public void setUserProList(HashMap<String, String> userProList) {
        this.userProList = userProList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
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

    public ByteArrayInputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(ByteArrayInputStream excelStream) {
        this.excelStream = excelStream;
    }

    public ByteArrayInputStream getZipStream() {
        return zipStream;
    }

    public void setZipStream(ByteArrayInputStream zipStream) {
        this.zipStream = zipStream;
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

    public List<ViewSystemNotificationBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<ViewSystemNotificationBean> gridModel) {
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
