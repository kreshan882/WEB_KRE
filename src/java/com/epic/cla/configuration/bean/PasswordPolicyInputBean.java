/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.configuration.bean;

import java.util.HashMap;

/**
 *
 * @author nipun_t
 */
public class PasswordPolicyInputBean {

    private String id;
    private String minlength;
    private String maxlength;
    private String allowspecialcharacters;
    private String minimumspecialcharacters;
    private String maximumspecialcharacters;
    private String minimumuppercasecharacters;
    private String minimumnumericalcharacters;
    private HashMap<String, String> userProList = new HashMap<String, String>();
    private String description;

    //Update Data
    private String upid;
    private String upminlength;
    private String upmaxlength;
    private String upallowspecialcharacters;
    private String upminimumspecialcharacters;
    private String upmaximumspecialcharacters;
    private String upminimumuppercasecharacters;
    private String upminimumnumericalcharacters;
    private HashMap<String, String> upuserProList = new HashMap<String, String>();
    private String updescription;

    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdate;
    private boolean vdelete;
    private boolean vdownload;
    private boolean vresetpass;
    //    private boolean vactdct;
    /*-------for access control-----------*/

    public String getMinlength() {
        return minlength;
    }

    public void setMinlength(String minlength) {
        this.minlength = minlength;
    }

    public HashMap<String, String> getUserProList() {
        return userProList;
    }

    public void setUserProList(HashMap<String, String> userProList) {
        this.userProList = userProList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpid() {
        return upid;
    }

    public String getMinimumspecialcharacters() {
        return minimumspecialcharacters;
    }

    public void setMinimumspecialcharacters(String minimumspecialcharacters) {
        this.minimumspecialcharacters = minimumspecialcharacters;
    }

    public String getMaximumspecialcharacters() {
        return maximumspecialcharacters;
    }

    public void setMaximumspecialcharacters(String maximumspecialcharacters) {
        this.maximumspecialcharacters = maximumspecialcharacters;
    }

    public String getUpminimumspecialcharacters() {
        return upminimumspecialcharacters;
    }

    public void setUpminimumspecialcharacters(String upminimumspecialcharacters) {
        this.upminimumspecialcharacters = upminimumspecialcharacters;
    }

    public String getUpmaximumspecialcharacters() {
        return upmaximumspecialcharacters;
    }

    public void setUpmaximumspecialcharacters(String upmaximumspecialcharacters) {
        this.upmaximumspecialcharacters = upmaximumspecialcharacters;
    }

    public void setUpid(String upid) {
        this.upid = upid;
    }

    public String getUpminlength() {
        return upminlength;
    }

    public void setUpminlength(String upminlength) {
        this.upminlength = upminlength;
    }

    public String getUpmaxlength() {
        return upmaxlength;
    }

    public void setUpmaxlength(String upmaxlength) {
        this.upmaxlength = upmaxlength;
    }

    public String getUpallowspecialcharacters() {
        return upallowspecialcharacters;
    }

    public void setUpallowspecialcharacters(String upallowspecialcharacters) {
        this.upallowspecialcharacters = upallowspecialcharacters;
    }
    public String getUpminimumuppercasecharacters() {
        return upminimumuppercasecharacters;
    }

    public void setUpminimumuppercasecharacters(String upminimumuppercasecharacters) {
        this.upminimumuppercasecharacters = upminimumuppercasecharacters;
    }

    public String getUpminimumnumericalcharacters() {
        return upminimumnumericalcharacters;
    }

    public void setUpminimumnumericalcharacters(String upminimumnumericalcharacters) {
        this.upminimumnumericalcharacters = upminimumnumericalcharacters;
    }

    public HashMap<String, String> getUpuserProList() {
        return upuserProList;
    }

    public void setUpuserProList(HashMap<String, String> upuserProList) {
        this.upuserProList = upuserProList;
    }

    public String getUpdescription() {
        return updescription;
    }

    public void setUpdescription(String updescription) {
        this.updescription = updescription;
    }

    public String getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    public String getAllowspecialcharacters() {
        return allowspecialcharacters;
    }

    public void setAllowspecialcharacters(String allowspecialcharacters) {
        this.allowspecialcharacters = allowspecialcharacters;
    }
 
    public String getMinimumuppercasecharacters() {
        return minimumuppercasecharacters;
    }

    public void setMinimumuppercasecharacters(String minimumuppercasecharacters) {
        this.minimumuppercasecharacters = minimumuppercasecharacters;
    }

    public String getMinimumnumericalcharacters() {
        return minimumnumericalcharacters;
    }

    public void setMinimumnumericalcharacters(String minimumnumericalcharacters) {
        this.minimumnumericalcharacters = minimumnumericalcharacters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
