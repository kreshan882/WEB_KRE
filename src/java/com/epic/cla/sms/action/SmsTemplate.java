/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.sms.action;

import com.epic.cla.sms.bean.SmsBean;
import com.epic.cla.sms.bean.SmsTemplateInputBean;
import com.epic.cla.sms.service.SmsTemplateService;
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
public class SmsTemplate extends ActionSupport implements ModelDriven<SmsTemplateInputBean>, AccessControlService {

    SmsTemplateService service = new SmsTemplateService();
    SmsTemplateInputBean inputBean = new SmsTemplateInputBean();
    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub;
    HttpSession session = ServletActionContext.getRequest().getSession(false);
    String profileID = (String) session.getAttribute("tempid");

    public SessionUserBean getSub() {
        return (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }

    public String execute() {
        return SUCCESS;
    }

    @Override
    public SmsTemplateInputBean getModel() {
        try {
//            HttpSession session = ServletActionContext.getRequest().getSession(false);
//            String profileID = (String) session.getAttribute("tempid");
//            System.out.println("hereee=" + profileID);
//
//            inputBean.setSmstprofileList(service.getProfileList(profileID));
//            inputBean.setSmstprofileId(profileID);
//            inputBean.setUpsmstprofileList(service.getProfileList());
            inputBean.setSmstemplatecList(service.getAllSMSTemplateList());

        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }

    public String ListTemplates() {
        String profileID = inputBean.getProId();
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        session.setAttribute("tempid", profileID);
        return "templates";
    }

    public String List() {

        HttpSession session = ServletActionContext.getRequest().getSession(false);
        String profileID = (String) session.getAttribute("tempid");

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

            dataList = service.loadData(inputBean, orderBy, from, rows, profileID);

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
            service.findData(inputBean, profileID);
        } catch (Exception e) {
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
            LogFileCreator.writeErrorToLog(e);
        }

        return "find";
    }

    public String Update() {

        try {
            if (doValidationUpdate(inputBean)) {

                if (service.updateData(inputBean, profileID)) {

                    addActionMessage(SystemMessage.SMS_TEMP_UPDATED);
                    DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.SMS_MANAGEMENT, PageVarList.SMS_TEMPLATE, TaskVarList.UPDATE, SystemMessage.SMS_TEMP_UPDATED, request.getRemoteAddr());
                    LogFileCreator.writeInfoToLog(SystemMessage.SMS_TEMP_UPDATED);

                } else {
                    addActionError(SystemMessage.SMS_TEMP_UPDATED_ERROR);
                }

            }
        } catch (Exception ex) {
            addActionError(SystemMessage.SMS_TEMP_UPDATED_ERROR);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }

    public String Delete() {
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        String profileID = (String) session.getAttribute("tempid");
        //System.out.println("hereee=" + profileID);
        try {
            if (service.deleteData(inputBean, profileID)) {
                DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.SMS_MANAGEMENT, PageVarList.SMS_TEMPLATE, TaskVarList.DELETE, SystemMessage.SMS_TEMP_DELETED, request.getRemoteAddr());
                LogFileCreator.writeInfoToLog(SystemMessage.SMS_TEMP_DELETED);
                inputBean.setMessage(SystemMessage.SMS_TEMP_DELETED);
                inputBean.setSuccess(true);
            } else {
                inputBean.setMessage(SystemMessage.SMS_TEMP_DELETED_ERROR);
                inputBean.setSuccess(false);
            }

        } catch (Exception ex) {
            inputBean.setMessage(SystemMessage.SMS_TEMP_DELETED_ERROR);
            inputBean.setSuccess(false);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "delete";
    }

    public String Add() {

        try {
            if (doValidation(inputBean)) {

                if (service.addData(inputBean, profileID)) {
                    DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.SMS_MANAGEMENT, PageVarList.SMS_TEMPLATE, TaskVarList.ADD, SystemMessage.SMS_TEMP_ADD, request.getRemoteAddr());
                    addActionMessage(SystemMessage.SMS_TEMP_ADD);
                    LogFileCreator.writeInfoToLog(SystemMessage.SMS_TEMP_ADD);

                } else {
                    addActionError(SystemMessage.SMS_TEMP_ADD_FAIL);
                }
            }

        } catch (Exception ex) {
            addActionError(SystemMessage.SMS_TEMP_ADD_FAIL);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "add";
    }

//    public String  Load(){
////        System.out.println("inside load data function ");
//        try {
//            inputBean.getSmstemplatecList().putAll(service.getSMSTemplateList(inputBean));
//        } catch (Exception ex) {
//            LogFileCreator.writeErrorToLog(ex);
//            ex.printStackTrace();
//        }
//        return "loaddata";
//    }
    private boolean doValidation(SmsTemplateInputBean inputBean) throws Exception {
        boolean ok = false;

        try {

//            if (inputBean.getSmstprofileId().equals("-1")) {
//                addActionError(SystemMessage.SMS_PROF_NAME_SELECT);
//                return ok;
//            }
            //            else if (inputBean.getSenderType().equals("-1")) {
            //                addActionError(SystemMessage.SMS_DELIVERY_PARTY_SELECT);
            //                return ok;
            //            }
            if (inputBean.getSmstemplatecId().equals("-1")) {
                addActionError(SystemMessage.SMS_TEMPLATE_CATEGORY_SELECT);
                return ok;
            } else if (inputBean.getMsg() == null || inputBean.getMsg().isEmpty()) {
                addActionError(SystemMessage.SMS_MESSAGE_EMPTY);
                return ok;
            } else if (service.checkPNameANDTCategory(profileID, inputBean.getSmstemplatecId())) {
                addActionError(SystemMessage.SMS_TEMPLATE_DUBLICATE);
            } //            else if (!Util.validateSTRING(inputBean.getMsg())) {
            //                addActionError(SystemMessage.SMS_MESSAGE_INVALID);
            //                return ok;
            //            }
            else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;

    }

    private boolean doValidationUpdate(SmsTemplateInputBean userBean) throws Exception {
        boolean ok = false;

        try {
//            if (inputBean.getUpsmstprofileId().equals("-1")) {
//                addActionError(SystemMessage.SMS_PROF_NAME_SELECT);
//                return ok;
//            } else if (inputBean.getUpsenderType().equals("-1")) {
//                addActionError(SystemMessage.SMS_DELIVERY_PARTY_SELECT);
//                return ok;
//            }else if (inputBean.getUpsmstemplatecId().equals("-1")) {
//                addActionError(SystemMessage.SMS_TEMPLATE_CATEGORY_SELECT);
//                return ok;
            if (inputBean.getUpmsg() == null || inputBean.getUpmsg().isEmpty()) {
                addActionError(SystemMessage.SMS_MESSAGE_EMPTY);
                return ok;
            } //             else if (!Util.validateSTRING(inputBean.getUpmsg())) {
            //                addActionError(SystemMessage.SMS_MESSAGE_INVALID);
            //                return ok;
            //            }
            else if (inputBean.getUpstatus1().equals("-1")) {
                addActionError(SystemMessage.SMS_TEMPLATE_STATUS_SELECT);
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
    public boolean checkAccess(String method, int userRole) {
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
        } else if ("ListTemplates".equals(method)) {
            task = TaskVarList.VIEW;
        }

        if ("execute".equals(method)) {
            status = true;
        } else if ("Load".equals(method)) {
            status = true;
        } else {
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            status = new Common().checkMethodAccess(task, page, session);
        }
        return status;
    }
}
