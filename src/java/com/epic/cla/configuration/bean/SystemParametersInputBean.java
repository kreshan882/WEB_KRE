/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.configuration.bean;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nipun_t
 */
public class SystemParametersInputBean {

    //Search Data
    private String username = "";

    //Add data
//    private String username;
    private String version;
    private String loglevel;
    private String minpoolsize;
    private String maxpoolsize;
    private String maxqueuesize;
    private String nooflogfile;
    private String userPro;
    private Map<String, String> logLevelList = new HashMap<String, String>();
    private Map<Integer, String> logBackupStatusList = new HashMap<Integer, String>();
    private HashMap<String, String> userProList = new HashMap<String, String>();
    private String serviceport;
    private String operationport;
    private String serviceportreadtimeout;
    private String logbackupstatus;
    private String logbackuppath;
    private String ordlength;
    private String seclength;
    private String maxpinretry;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, String> getLogLevelList() {
        return logLevelList;
    }

    public void setLogLevelList(Map<String, String> logLevelList) {
        this.logLevelList = logLevelList;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserPro() {
        return userPro;
    }

    public String getOrdlength() {
        return ordlength;
    }

    public void setOrdlength(String ordlength) {
        this.ordlength = ordlength;
    }

    public String getMaxpinretry() {
        return maxpinretry;
    }

    public void setMaxpinretry(String maxpinretry) {
        this.maxpinretry = maxpinretry;
    }

    public String getSeclength() {
        return seclength;
    }

    public void setSeclength(String seclength) {
        this.seclength = seclength;
    }

    public void setUserPro(String userPro) {
        this.userPro = userPro;
    }

    public HashMap<String, String> getUserProList() {
        return userProList;
    }

    public void setUserProList(HashMap<String, String> userProList) {
        this.userProList = userProList;
    }

    public String getLoglevel() {
        return loglevel;
    }

    public void setLoglevel(String loglevel) {
        this.loglevel = loglevel;
    }

    public String getMinpoolsize() {
        return minpoolsize;
    }

    public void setMinpoolsize(String minpoolsize) {
        this.minpoolsize = minpoolsize;
    }

    public String getMaxpoolsize() {
        return maxpoolsize;
    }

    public void setMaxpoolsize(String maxpoolsize) {
        this.maxpoolsize = maxpoolsize;
    }

    public String getMaxqueuesize() {
        return maxqueuesize;
    }

    public void setMaxqueuesize(String maxqueuesize) {
        this.maxqueuesize = maxqueuesize;
    }

    public String getNooflogfile() {
        return nooflogfile;
    }

    public void setNooflogfile(String nooflogfile) {
        this.nooflogfile = nooflogfile;
    }

    public String getServiceport() {
        return serviceport;
    }

    public void setServiceport(String serviceport) {
        this.serviceport = serviceport;
    }

    public String getOperationport() {
        return operationport;
    }

    public void setOperationport(String operationport) {
        this.operationport = operationport;
    }

    public String getServiceportreadtimeout() {
        return serviceportreadtimeout;
    }

    public void setServiceportreadtimeout(String serviceportreadtimeout) {
        this.serviceportreadtimeout = serviceportreadtimeout;
    }

    public void setLogLevelList(HashMap<String, String> logLevelList) {
        this.logLevelList = logLevelList;
    }

    public Map<Integer, String> getLogBackupStatusList() {
        return logBackupStatusList;
    }

    public void setLogBackupStatusList(Map<Integer, String> logBackupStatusList) {
        this.logBackupStatusList = logBackupStatusList;
    }

    public String getLogbackupstatus() {
        return logbackupstatus;
    }

    public void setLogbackupstatus(String logbackupstatus) {
        this.logbackupstatus = logbackupstatus;
    }

    public String getLogbackuppath() {
        return logbackuppath;
    }

    public void setLogbackuppath(String logbackuppath) {
        this.logbackuppath = logbackuppath;
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

}
