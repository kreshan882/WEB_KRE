/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.callcenter.action;

import com.epic.cla.callcenter.bean.CallCenterBean;
import com.epic.cla.callcenter.bean.CallCenterInputBean;
import com.epic.cla.callcenter.service.CallCenterService;
import com.epic.cla.transactionmgt.bean.ViewTransactionDataBean;
import com.epic.cla.transactionmgt.bean.ViewTransactionInputBean;
import com.epic.init.TaskVarList;
import com.epic.login.bean.SessionUserBean;
import com.epic.login.bean.TaskBean;
import com.epic.util.AccessControlService;
import com.epic.util.Common;
import com.epic.util.LogFileCreator;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author dimuthu_h
 */
public class CallCenterManagement extends ActionSupport implements ModelDriven<CallCenterInputBean>, AccessControlService {
    
    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub = (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    CallCenterInputBean inputBean = new CallCenterInputBean();
    CallCenterService service = new CallCenterService();

    @Override
    public CallCenterInputBean getModel() {
        return inputBean;
                
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
    
    public String List() {

        try {

            List<CallCenterBean> dataList = null;

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;

            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }

            dataList = service.loadHistory(inputBean, orderBy, to, from);

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

        } catch (Exception e) {
            e.printStackTrace();
            LogFileCreator.writeErrorToLog(e);

        }

        return "list";
    }
     
    @Override
    public boolean checkAccess(String method,int userRole) {
       
        boolean status = false;
        applyUserPrivileges();
        String page = com.epic.init.PageVarList.TRANSACTION_VIEW_TXN;
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
        } else if ("BackupAndDownload".equals(method)) {
            task = TaskVarList.DOWNLOAD;
        } 
        
      
        
        if ("execute".equals(method)) {
            status = true;
        } else {
            

            HttpSession session = ServletActionContext.getRequest().getSession(false);
             
            status = new Common().checkMethodAccess(task, page, session);
         
        } 
       return status;
    }
    
    
    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(com.epic.init.PageVarList.TRANSACTION_VIEW_TXN, request);
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
    
    
}
