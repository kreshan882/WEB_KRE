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
public class EsMonitorBean {
    private String trafic;
    private String checkout;
    private String dbcon;
    private String status;
    private String queueSize;
    private long fullCount;

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
    
    public String getTrafic() {
        return trafic;
    }

    public void setTrafic(String trafic) {
        this.trafic = trafic;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getDbcon() {
        return dbcon;
    }

    public void setDbcon(String dbcon) {
        this.dbcon = dbcon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(String queueSize) {
        this.queueSize = queueSize;
    }
    
    
}
