/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.login.bean;

/**
 *
 * @author Administrator
 */
public class ConfigurationBean {
    private String logBackUpPath;
    private int servicePort;

    public String getLogBackUpPath() {
        return logBackUpPath;
    }

    public void setLogBackUpPath(String logBackUpPath) {
        this.logBackUpPath = logBackUpPath;
    }

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }
    
    
}
