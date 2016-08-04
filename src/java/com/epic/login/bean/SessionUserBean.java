/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.login.bean;

/**
 *
 * @author kreshan
 */
public class SessionUserBean {

   
    private String username;
    private int UserProfileId;
    private String userType;
    private String name;
    private int cusId;
    
    private String logFilePath;
    private String currentSessionId;

    public String getUsername() {
        return username;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserProfileId() {
        return UserProfileId;
    }

    public void setUserProfileId(int UserProfileId) {
        this.UserProfileId = UserProfileId;
    }



    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public String getCurrentSessionId() {
        return currentSessionId;
    }

    public void setCurrentSessionId(String currentSessionId) {
        this.currentSessionId = currentSessionId;
    }

    
}
