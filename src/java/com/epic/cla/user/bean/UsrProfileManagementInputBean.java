/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cla.user.bean;

import com.epic.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kreshan
 */
public class UsrProfileManagementInputBean {
    
    //Search Data
    private String profilename="";
    private boolean search;
    
    //Add data
    
    //Update Task Data
    private String upprofilename;
    private String upprofileId;        
    
    private String upmoduleId;
    private HashMap<String,String> moduleIdList = new HashMap<String,String>();
    private String uppageId;
    private HashMap<String,String> pageIdList = new HashMap<String,String>();
    
    private List<String> currentBox;
    private Map<String, String> taskList = new HashMap<String, String>();
    private List<String> newBox;
    private Map<String, String> selectedtaskList = new HashMap<String, String>();
    

    //Delete Data
    private String message;
    private boolean success;

    

    
    //Update Data
    private String upestatus;     
    private Map<Integer,String>  upstatusList=Util.getBasicStatus();
    private String upeprofilename;
    private String upeprofileId;  
    
    
        /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdate;
    private boolean vdelete;
    private boolean vdownload;
    private boolean vresetpass;
    //    private boolean vactdct;
    /*-------for access control-----------*/ 
    
    //Table data
    private List<UsrProfileBean> gridModel=new ArrayList<UsrProfileBean>();
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;

    public boolean isVadd() {
        return vadd;
    }

    public void setVadd(boolean vadd) {
        this.vadd = vadd;
    }

    public String getUpmoduleId() {
        return upmoduleId;
    }

    public void setUpmoduleId(String upmoduleId) {
        this.upmoduleId = upmoduleId;
    }

    public String getUpprofilename() {
        return upprofilename;
    }

    public void setUpprofilename(String upprofilename) {
        this.upprofilename = upprofilename;
    }

    public String getUpprofileId() {
        return upprofileId;
    }

    public void setUpprofileId(String upprofileId) {
        this.upprofileId = upprofileId;
    }

    public String getUppageId() {
        return uppageId;
    }

    public void setUppageId(String uppageId) {
        this.uppageId = uppageId;
    }

    public boolean isVupdate() {
        return vupdate;
    }

    public List<String> getNewBox() {
        return newBox;
    }

    public void setNewBox(List<String> newBox) {
        this.newBox = newBox;
    }

    public List<String> getCurrentBox() {
        return currentBox;
    }

    public void setCurrentBox(List<String> currentBox) {
        this.currentBox = currentBox;
    }

    public Map<String, String> getSelectedtaskList() {
        return selectedtaskList;
    }

    public void setSelectedtaskList(Map<String, String> selectedtaskList) {
        this.selectedtaskList = selectedtaskList;
    }



    public Map<String, String> getTaskList() {
        return taskList;
    }

    public void setTaskList(Map<String, String> taskList) {
        this.taskList = taskList;
    }




    public HashMap<String, String> getModuleIdList() {
        return moduleIdList;
    }

    public void setModuleIdList(HashMap<String, String> moduleIdList) {
        this.moduleIdList = moduleIdList;
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


    public HashMap<String, String> getPageIdList() {
        return pageIdList;
    }

    public void setPageIdList(HashMap<String, String> pageIdList) {
        this.pageIdList = pageIdList;
    }

    public String getUpestatus() {
        return upestatus;
    }

    public void setUpestatus(String upestatus) {
        this.upestatus = upestatus;
    }



    public Map<Integer, String> getUpstatusList() {
        return upstatusList;
    }

    public void setUpstatusList(Map<Integer, String> upstatusList) {
        this.upstatusList = upstatusList;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
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

    public List<UsrProfileBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<UsrProfileBean> gridModel) {
        this.gridModel = gridModel;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchOper() {
        return searchOper;
    }

    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }

    public String getUpeprofilename() {
        return upeprofilename;
    }

    public void setUpeprofilename(String upeprofilename) {
        this.upeprofilename = upeprofilename;
    }

    public String getUpeprofileId() {
        return upeprofileId;
    }

    public void setUpeprofileId(String upeprofileId) {
        this.upeprofileId = upeprofileId;
    }
    
    
    
    
}
