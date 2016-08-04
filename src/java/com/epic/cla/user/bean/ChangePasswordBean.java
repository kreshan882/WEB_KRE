/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.user.bean;

/**
 *
 * @author kreshan
 */
public class ChangePasswordBean {
    
    
    private String passwordOld;
    private String passwordNew1;
    private String passwordNew2;
    
    private String passwordDb;


    
    private String username;
    private int instuteid;

       
    public String getPasswordDb() {
        return passwordDb;
    }

    public void setPasswordDb(String passwordDb) {
        this.passwordDb = passwordDb;
    }
    public int getInstuteid() {
        return instuteid;
    }

    public void setInstuteid(int instuteid) {
        this.instuteid = instuteid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPasswordNew1() {
        return passwordNew1;
    }

    public void setPasswordNew1(String passwordNew1) {
        this.passwordNew1 = passwordNew1;
    }

    public String getPasswordNew2() {
        return passwordNew2;
    }

    public void setPasswordNew2(String passwordNew2) {
        this.passwordNew2 = passwordNew2;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }
}
