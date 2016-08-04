/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.systemAlert.bean;

/**
 *
 * @author chalaka_n
 */
public class LcMonitorBean {
    private String lcId;
    private String type;
    private String connectSta;
    private String lanSta;
    private long fullCount;

    public String getConnectSta() {
        return connectSta;
    }

    public void setConnectSta(String connectSta) {
        this.connectSta = connectSta;
    }

    public String getLanSta() {
        return lanSta;
    }

    public void setLanSta(String lanSta) {
        this.lanSta = lanSta;
    }

    
    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
    
    public String getLcId() {
        return lcId;
    }

    public void setLcId(String lcId) {
        this.lcId = lcId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    
    
    
}
