/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.login.bean;

/**
 *
 * @author kreshan
 */
public class HomeValues {
        //Home Page
    private String appTypeName ;
    private String appIdName;        
    private String  versionNo;
    private String  efiModuleNo;
    private String  description;
    private String  institute;

    public String getAppIdName() {
        return appIdName;
    }

    public void setAppIdName(String appIdName) {
        this.appIdName = appIdName;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getAppTypeName() {
        return appTypeName;
    }

    public void setAppTypeName(String appTypeName) {
        this.appTypeName = appTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEfiModuleNo() {
        return efiModuleNo;
    }

    public void setEfiModuleNo(String efiModuleNo) {
        this.efiModuleNo = efiModuleNo;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }
    
    
}
