/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cla.sms.action;

import com.epic.cla.sms.bean.SmsBean;
import com.epic.cla.sms.bean.SmsProfileInputBean;
import com.epic.cla.sms.service.SmsProfileService;
import com.epic.db.DBProcesses;
import com.epic.init.Module;
import com.epic.init.PageVarList;
import com.epic.init.TaskVarList;
import com.epic.login.bean.SessionUserBean;
import com.epic.login.bean.TaskBean;
import com.epic.util.AccessControlService;
import com.epic.util.Common;
import com.epic.util.LogFileCreator;
import com.epic.util.SystemMessage;
import com.epic.util.Util;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author kreshan
 */
public class SmsProfile extends ActionSupport implements ModelDriven<SmsProfileInputBean>, AccessControlService{
    SmsProfileService  service = new SmsProfileService();
    SmsProfileInputBean inputBean = new SmsProfileInputBean();
    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub;

    public SessionUserBean getSub() {
        return  (SessionUserBean)ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }
    
    
    public String execute() {
        return SUCCESS;
    }

    @Override
    public SmsProfileInputBean getModel() {
        return inputBean;
    }

    public String List() {
            
        try {
                List<SmsBean> dataList = null;  
                int rows = inputBean.getRows();
                int page = inputBean.getPage();
                int to = (rows * page);
                int from = to - rows;
                long records = 0;
                String orderBy = "";    
                
                if (!inputBean.getSidx().isEmpty()) {
                    orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
                }

                dataList = service.loadData( inputBean,orderBy, from, rows);

                if (!dataList.isEmpty()) {
                    records = dataList.get(0).getFullCount();
                    inputBean.setRecords(records);
                    inputBean.setGridModel(dataList);
                    int total = (int) Math.ceil((double) records / (double) rows);
                    inputBean.setTotal(total);
                } else {
                    inputBean.setRecords(0L);
                    inputBean.setTotal(0);
                }

        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
        }
        return "list";
    }
    
    
    
        
    public String Find() {
        try {
            service.findData(inputBean);
        } catch (Exception ex) {    
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
             LogFileCreator.writeErrorToLog(ex);
        }

        return "find";
    }
        
        
    public String Update() {
        try {
            if (doValidationUpdate(inputBean)) {

                if (service.updateData(inputBean)) {
                    
                    addActionMessage(SystemMessage.SMS_PROF_UPDATED);
                    
                    DBProcesses.insertHistoryRecord(getSub().getUsername(),
                            Module.SMS_MANAGEMENT, PageVarList.SMS_TEMPLATE_PROFILE, TaskVarList.UPDATE, SystemMessage.SMS_PROF_UPDATED, request.getRemoteAddr());

                    LogFileCreator.writeInfoToLog(SystemMessage.SMS_PROF_UPDATED);

                } else {
                    addActionError(SystemMessage.SMS_PROF_UPDATED_ERROR);
                }

            }
        } catch (Exception ex) {
            addActionError(SystemMessage.SMS_PROF_UPDATED_ERROR);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }
    
    public String Delete() {
        try {
            if(service.deleteData( inputBean)){
                 DBProcesses.insertHistoryRecord(getSub().getUsername(),  Module.SMS_MANAGEMENT, PageVarList.SMS_TEMPLATE_PROFILE, TaskVarList.DELETE,SystemMessage.SMS_PROF_DELETED,request.getRemoteAddr());
                 LogFileCreator.writeInfoToLog(SystemMessage.SMS_PROF_DELETED);
                 inputBean.setMessage(SystemMessage.SMS_PROF_DELETED);
                 inputBean.setSuccess(true);
           }else{
                 inputBean.setMessage(SystemMessage.SMS_PROF_DELETED_ERROR);
                 inputBean.setSuccess(false);
           }
                
        } catch (Exception ex) {
                inputBean.setMessage(SystemMessage.SMS_PROF_DELETED_ERROR);
                inputBean.setSuccess(false);
                ex.printStackTrace();
                LogFileCreator.writeErrorToLog(ex);
        }

        return "delete";
    }

   
 
    public String Add() {
        try {
            if (doValidation(inputBean)) {
                
                if (service.addData( inputBean)) {
                    DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.SMS_MANAGEMENT,PageVarList.SMS_TEMPLATE_PROFILE, TaskVarList.ADD, SystemMessage.SMS_PROF_ADD + " for " + inputBean.getProName(),request.getRemoteAddr());
                    addActionMessage(SystemMessage.SMS_PROF_ADD);
                     LogFileCreator.writeInfoToLog(SystemMessage.SMS_PROF_ADD);
                    
                } else {
                    addActionError(SystemMessage.SMS_PROF_ADD_FAIL);
                }
            }
            
        } catch (Exception ex) {
                addActionError(SystemMessage.SMS_PROF_ADD_FAIL);
                ex.printStackTrace();
                LogFileCreator.writeErrorToLog(ex);
        }
        
        return "add";
    }

    
    private boolean  doValidation (SmsProfileInputBean userBean) throws Exception{
        boolean ok = false;
        
        try {
            
            if (userBean.getProName() == null || userBean.getProName().isEmpty()) {
                addActionError(SystemMessage.SMS_PROF_NAME_EMPTY);
                return ok;
            } else if (!Util.validateNAME(userBean.getProName())) {
                addActionError(SystemMessage.SMS_PROF_NAME_INVALID);
                return ok;
            }else if (service.checkUserName(userBean.getProName())) {
                addActionError(SystemMessage.SMS_PROF_NAME_ALREADY);
                return ok;
            } else {
                ok = true;
            }
            
        } catch (Exception e) {
           throw e; 
        }
        return ok;
    
    }
    private boolean  doValidationUpdate (SmsProfileInputBean userBean) throws Exception{
        boolean ok = false;
        
        try {
            
            if (userBean.getUpproName() == null || userBean.getUpproName().isEmpty()) {
                addActionError(SystemMessage.SMS_PROF_NAME_EMPTY);
                return ok;
            } else if (!Util.validateNAME(userBean.getUpproName())) {
                addActionError(SystemMessage.SMS_PROF_NAME_INVALID);
                return ok;
//            }else if (service.checkUserName(userBean.getUpproName())) {
//                addActionError(SystemMessage.SMS_NAME_ALREADY);
//                return ok;
            }else if (userBean.getUpstatus().equals("-1")) {
                addActionError(SystemMessage.USR_STATUS_SELECT);
                return ok;
            }else {
                ok = true;
            }
            
        } catch (Exception e) {
           throw e; 
        }
        return ok;
    
    }
     private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.SMS_TEMPLATE_PROFILE, request);
        inputBean.setVadd(true);
        inputBean.setVupdate(true);
        inputBean.setVdelete(true);
        inputBean.setVdownload(true);
        inputBean.setVresetpass(true);
        if (tasklist != null && tasklist.size() > 0) {
            for (TaskBean task : tasklist) {
                if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.ADD)) {
                    inputBean.setVadd(false);
                } else if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.UPDATE)) {
                    inputBean.setVupdate(false);
                } else if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.DELETE)) {
                    inputBean.setVdelete(false);
                } else if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.DOWNLOAD)) {
                    inputBean.setVdownload(false);
                } else if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.PWRESET)) {
                    inputBean.setVresetpass(false);
                } 
            }
        }

        return true;
    }
        
    @Override
    public boolean checkAccess(String method,int userRole) {
        boolean status = false;
        applyUserPrivileges();
        String page = PageVarList.SMS_TEMPLATE_PROFILE;
        String task = null;
        if ("View".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("List".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("Add".equals(method)) {
            task = TaskVarList.ADD;
        } else if ("Find".equals(method)) {
            task = TaskVarList.UPDATE;
        } else if ("Update".equals(method)) {
            task = TaskVarList.UPDATE;
        } else if ("Delete".equals(method)) {
            task = TaskVarList.DELETE;
        } else if ("Download".equals(method)) {
            task = TaskVarList.DOWNLOAD;
        } else if ("ResetPw".equals(method)) {
            task = TaskVarList.PWRESET;
        } 
        
        if ("execute".equals(method)) {
            status = true;
        } else {
              HttpSession session = ServletActionContext.getRequest().getSession(false);
              status = new Common().checkMethodAccess(task, page, session);
        } 
       return status;
    }
}
