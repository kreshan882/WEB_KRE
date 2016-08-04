/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.login.bean;

/**
 *
 * @author kreshan
 */
public class PageBean {
    private String PAGE_ID;
    private String MODULE;
    private String PAGE_NAME;
    private String PAGE_URL;

    public String getPAGE_URL() {
        return PAGE_URL;
    }

    public void setPAGE_URL(String PAGE_URL) {
        this.PAGE_URL = PAGE_URL;
    }
    

    public String getMODULE() {
        return MODULE;
    }

    public void setMODULE(String MODULE) {
        this.MODULE = MODULE;
    }

    public String getPAGE_ID() {
        return PAGE_ID;
    }

    public void setPAGE_ID(String PAGE_ID) {
        this.PAGE_ID = PAGE_ID;
    }

    public String getPAGE_NAME() {
        return PAGE_NAME;
    }

    public void setPAGE_NAME(String PAGE_NAME) {
        this.PAGE_NAME = PAGE_NAME;
    }
    
}
