/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.transactionmgt.action;

import com.epic.cla.transactionmgt.bean.CustomerTransactionInputBean;
import com.epic.cla.transactionmgt.service.CustomerTransactionService;
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
 * @author chalaka_n
 */
public class CustomerTransction extends ActionSupport implements ModelDriven<CustomerTransactionInputBean>,AccessControlService{
    
    CustomerTransactionInputBean inputBean = new CustomerTransactionInputBean();
    CustomerTransactionService service = new CustomerTransactionService();
    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub;
    
    public SessionUserBean getSub() {
        return  (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }
    @Override
    public String execute(){
        return SUCCESS;
    }

    @Override
    public CustomerTransactionInputBean getModel() {
        return inputBean;
    }

    @Override
    public boolean checkAccess(String method, int userRole) {
        boolean status = false;
        applyUserPrivileges();
        String page = com.epic.init.PageVarList.CUSTOMER_TARANSACTION;
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
        } else if ("Cancel".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("BackupAndDownload".equals(method)) {
            task = TaskVarList.DOWNLOAD;
        } else if ("ListHistory".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("ListSmsHistory".equals(method)) {
            task = TaskVarList.VIEW;
        }

        if ("execute".equals(method)) {
            status = true;
        }else if ("getAccount".equals(method)) {
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
                } 
            }
        }

        return true;
    }
    
    public String Add() {
        try {
            if (doValidation(inputBean)) {

                if (service.addData(inputBean)) {
                    DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.TRANSECTION_MANAGEMENT, PageVarList.CUSTOMER_TARANSACTION, TaskVarList.ADD, SystemMessage.CUSTRA_TRANSACTION_SUCCESS + " for " + inputBean.getAccountNo(), request.getRemoteAddr());
                    addActionMessage(SystemMessage.CUSTRA_TRANSACTION_SUCCESS);
                    LogFileCreator.writeInfoToLog(SystemMessage.CUSTRA_TRANSACTION_SUCCESS);

                } else {
                    addActionError(SystemMessage.CUSTRA_TRANSACTION_FAIL);
                }
            }

        } catch (Exception ex) {
            addActionError(SystemMessage.CUSTRA_TRANSACTION_FAIL);
           // ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "add";
    }
      public String getAccount() {
          System.out.println("ssss");
        try {
            if (inputBean.getMobileNo() == null || inputBean.getMobileNo().isEmpty()) {
                inputBean.setSuccess(false);
                inputBean.setMessage(SystemMessage.CUSTRA_MOBILE_NUM_EMPTY);
                return "account";
            }
            
            if (service.addgetAccountNumber(inputBean)) {
                inputBean.setSuccess(true);
            } else {
                inputBean.setSuccess(false);
                inputBean.setMessage(SystemMessage.CUSTRA_TRANSACTION_NOACCOUNT);

            }
            

        } catch (Exception ex) {
            inputBean.setSuccess(false);
            inputBean.setMessage(SystemMessage.CUSTRA_TRANSACTION_NOACCOUNT);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "account";
    }
    
    private boolean doValidation(CustomerTransactionInputBean userBean) throws Exception {
        boolean ok = false;
        try {

            if (userBean.getAccountNo() == null || userBean.getAccountNo().isEmpty()) {
                addActionError(SystemMessage.CUSTRA_ACCOUNT_NUM_EMPTY);
                return ok;
            } else if (userBean.getMobileNo() == null || userBean.getMobileNo().isEmpty()) {
                addActionError(SystemMessage.CUSTRA_MOBILE_NUM_EMPTY);
                return ok;
                
            } else if (!Util.validatePHONENO(userBean.getMobileNo())) {
                addActionError(SystemMessage.CUSTRA_MOBILE_INVALID);
                return ok;
            } else if (userBean.getAmount() == null || userBean.getAmount().isEmpty()) {
                addActionError(SystemMessage.CUSTRA_AMOUNT_EMPTY);
                return ok;
            } else if (!Util.validateAMOUNT(userBean.getAmount())) {
                addActionError(SystemMessage.CUSTRA_AMOUNT_INVALID);
                return ok;
            } else if ((Double.parseDouble(userBean.getAmount())%100) != 0.0 ) {
                addActionError(SystemMessage.CUSTRA_AMOUNT_INVALID);
                return ok;
            }else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;

    }
}
