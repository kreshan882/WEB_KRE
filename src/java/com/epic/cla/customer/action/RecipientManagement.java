/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.customer.action;

import com.epic.cla.customer.bean.RecipientManagementBean;
import com.epic.cla.customer.bean.RecipientManagementInputBean;
import com.epic.cla.customer.service.RecipientManagementService;
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
 * @author dimuthu_h
 */
public class RecipientManagement extends ActionSupport implements ModelDriven<RecipientManagementInputBean>, AccessControlService {

    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub;
    RecipientManagementInputBean inputBean = new RecipientManagementInputBean();
    RecipientManagementService reciservice = new RecipientManagementService();

    public SessionUserBean getSub() {
        return  (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }

    @Override
    public String execute() throws Exception {

        return SUCCESS;
    }

    @Override
    public RecipientManagementInputBean getModel() {
        try {
            inputBean.setCustomerList(reciservice.getCustomerList());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inputBean;
    }

    public String List() {
        try {
            List<RecipientManagementBean> dataList = null;
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }
                dataList = reciservice.loadData(inputBean, orderBy, from, rows);

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
            reciservice.findData(inputBean);
        } catch (Exception e) {
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
        }

        return "find";
    }
    public String Update() {
        try {
          
            if (doValidationUpdate(inputBean)) {

                if (reciservice.updateData(inputBean)) {
                    
                    addActionMessage(SystemMessage.RECIPIENT_UPDATED);
                    
                    DBProcesses.insertHistoryRecord(getSub().getUsername(),
                            Module.CUSTOMER_MANAGEMENT, PageVarList.CUSTOMER_RECIPIENT_MANAGEMENT, TaskVarList.UPDATE, SystemMessage.RECIPIENT_UPDATED, request.getRemoteAddr());

                    LogFileCreator.writeInfoToLog(SystemMessage.RECIPIENT_UPDATED);

                } else {
                    addActionError(SystemMessage.RECIPIENT_UPDATED_ERROR);
                }

            }
        } catch (Exception ex) {
            addActionError(SystemMessage.RECIPIENT_UPDATED_ERROR);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }
    
    public String Delete() {
        try {
           
            if(reciservice.deleteData( inputBean)){
                 DBProcesses.insertHistoryRecord(getSub().getUsername(),  Module.CUSTOMER_MANAGEMENT, PageVarList.CUSTOMER_RECIPIENT_MANAGEMENT, TaskVarList.DELETE,SystemMessage.RECIPIENT_DELETED,request.getRemoteAddr());
                 LogFileCreator.writeInfoToLog(SystemMessage.RECIPIENT_DELETED);
                 inputBean.setMessage(SystemMessage.RECIPIENT_DELETED);
                 inputBean.setSuccess(true);
           }else{
                 inputBean.setMessage(SystemMessage.RECIPIENT_DELETED_ERROR);
                 inputBean.setSuccess(false);
           }
                
        } catch (Exception ex) {
                inputBean.setMessage(SystemMessage.RECIPIENT_DELETED_ERROR);
                inputBean.setSuccess(false);
                ex.printStackTrace();
                LogFileCreator.writeErrorToLog(ex);
        }

        return "delete";
    }

   
 

    public String Add() {
        try {
            if (doValidation(inputBean)) {

                if (reciservice.addData(inputBean)) {
                    DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.CUSTOMER_MANAGEMENT, PageVarList.CUSTOMER_RECIPIENT_MANAGEMENT, TaskVarList.ADD, SystemMessage.RECIPIENT_ADD + " for " + inputBean.getName(), request.getRemoteAddr());
                    addActionMessage(SystemMessage.RECIPIENT_ADD);

                } else {
                    addActionError(SystemMessage.RECIPIENT_ADD_FAIL);
                }
            }

        } catch (Exception ex) {
            addActionError(SystemMessage.RECIPIENT_ADD_FAIL);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "add";
    }
  

    private boolean doValidation(RecipientManagementInputBean recipientBean) throws Exception {
        boolean ok = false;

        try {
            if (recipientBean.getCid() == null || recipientBean.getCid().equals("-1")) {
                addActionError(SystemMessage.RECIPIENT_CUSTOMER_ID_SELECT);
                return ok;
            } else if (recipientBean.getName() == null || recipientBean.getName().isEmpty()) {
                addActionError(SystemMessage.RECIPIENT_NAME_EMPTY);
                return ok;
            } else if (!Util.validateNAME(recipientBean.getName())) {
                addActionError(SystemMessage.RECIPIENT_NAME_INVALID);
                return ok;
            } else if (recipientBean.getNic() == null || recipientBean.getNic().isEmpty()) {
                addActionError(SystemMessage.RECIPIENT_NIC_EMPTY);
                return ok;
            } else if (!Util.validateNIC(recipientBean.getNic())) {
                addActionError(SystemMessage.RECIPIENT_NIC_INVALID);
                return ok;
            } else if (recipientBean.getMobile_no() == null || recipientBean.getMobile_no().isEmpty()) {
                addActionError(SystemMessage.RECIPIENT_MOBILE_EMPTY);
                return ok;
            } else if (!Util.validatePHONENO(recipientBean.getMobile_no())) {
                addActionError(SystemMessage.RECIPIENT_MOBILE_INVALID);
                return ok;
            }  else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;

    }

    private boolean doValidationUpdate(RecipientManagementInputBean recipientBean) throws Exception {
        boolean ok = false;

        try {

            if (recipientBean.getUpname()== null || recipientBean.getUpname().isEmpty()) {
                addActionError(SystemMessage.RECIPIENT_NAME_EMPTY);
                return ok;
            } else if (!Util.validateNAME(recipientBean.getUpname())) {
                addActionError(SystemMessage.RECIPIENT_NAME_INVALID);
                return ok;
            } else if (recipientBean.getUpnic()== null || recipientBean.getUpnic().isEmpty()) {
                addActionError(SystemMessage.RECIPIENT_NIC_EMPTY);
                return ok;
            } else if (!Util.validateNIC(recipientBean.getUpnic())) {
                addActionError(SystemMessage.RECIPIENT_NIC_INVALID);
                return ok;
            } else if (recipientBean.getUpmobile() == null || recipientBean.getUpmobile().isEmpty()) {
                addActionError(SystemMessage.RECIPIENT_MOBILE_EMPTY);
                return ok;
            } else if (!Util.validatePHONENO(recipientBean.getUpmobile())) {
                addActionError(SystemMessage.RECIPIENT_MOBILE_INVALID);
                return ok;
            } else if (recipientBean.getUpstatus().equals("-1")) {
                addActionError(SystemMessage.RECIPIENT_STATUS_SELECT);
                return ok;
            } else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;

    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.CUSTOMER_MANAGEMENT, request);
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
    public boolean checkAccess(String method, int userRole) {
        boolean status = false;
        applyUserPrivileges();
        String page = PageVarList.CUSTOMER_MANAGEMENT;
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
        }else if("RecipientList".equals(method)){
            task = TaskVarList.VIEW;
        }

        if ("execute".equals(method)) {
            status = true;
        } else {
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            status = new Common().checkMethodAccess(task, page, session);
        }
        return status;
    }
    public String RecipientList(){
        String txanctionid = inputBean.getCid();
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        session.setAttribute("cid", txanctionid);
        return "recipent";
    }
}
