/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.login.bean;

/**
 *
 * @author kreshan
 */
public class UserProfileModuleBean {
    private int PROFILE_ID;
    private String MODULE_ID;
    private String PAGE_ID;

    public String getMODULE_ID() {
        return MODULE_ID;
    }

    public void setMODULE_ID(String MODULE_ID) {
        this.MODULE_ID = MODULE_ID;
    }

    public String getPAGE_ID() {
        return PAGE_ID;
    }

    public void setPAGE_ID(String PAGE_ID) {
        this.PAGE_ID = PAGE_ID;
    }

    public int getPROFILE_ID() {
        return PROFILE_ID;
    }

    public void setPROFILE_ID(int PROFILE_ID) {
        this.PROFILE_ID = PROFILE_ID;
    }
}
