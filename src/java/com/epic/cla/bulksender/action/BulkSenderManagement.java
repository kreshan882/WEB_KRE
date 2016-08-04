/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.bulksender.action;

import com.epic.cla.bulksender.bean.BulkSenderBean;
import com.epic.cla.bulksender.bean.BulkSenderInputBean;
import com.epic.cla.bulksender.service.BulkSenderService;
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
import static com.opensymphony.xwork2.Action.SUCCESS;
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
public class BulkSenderManagement extends ActionSupport implements ModelDriven<BulkSenderInputBean>, AccessControlService {

    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub;
    BulkSenderInputBean inputBean = new BulkSenderInputBean();
    BulkSenderService service = new BulkSenderService();

    public SessionUserBean getSub() {
        return (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }

    @Override
    public BulkSenderInputBean getModel() {
        try {
            inputBean.setCus_id(Integer.toString(getSub().getCusId()));
            inputBean.setBatch_no(String.format("%06d", (service.getMaxBatchID(Integer.toString(getSub().getCusId())) + 1)));

        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String List() {

        try {
            List<BulkSenderBean> dataList = null;

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;

            long records = 0;
            String orderBy = "";
            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }

            dataList = service.loadData(inputBean, orderBy, from, to);

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

    public String Add() {

        try {
            if (doValidation(inputBean) && getSub().getUserType().equals("02")) {
                inputBean.setCus_id(Integer.toString(getSub().getCusId()));
                if (service.addData(inputBean)) {
                    DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.BULK_MESSAGE_MANAGEMENT, PageVarList.BULK_MESSAGE_SENDER, TaskVarList.ADD, SystemMessage.BULK_MSG_ADD, request.getRemoteAddr());
                    addActionMessage(SystemMessage.BULK_MSG_ADD);

                } else {
                    addActionError(SystemMessage.BULK_MSG_ADD_FAIL);
                }
            }

        } catch (Exception ex) {
            addActionError(SystemMessage.BULK_MSG_ADD_FAIL);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "add";
    }

    public String Update() {
        try {
            if (service.updateData(inputBean)) {

                addActionMessage(SystemMessage.BULK_MSG_SEND_SUCCESSFULY);
                DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.BULK_MESSAGE_MANAGEMENT, PageVarList.BULK_MESSAGE_SENDER, TaskVarList.UPDATE, SystemMessage.BULK_MSG_SEND_SUCCESSFULY, request.getRemoteAddr());
                LogFileCreator.writeInfoToLog(SystemMessage.BULK_MSG_SEND_SUCCESSFULY);

            } else {
                addActionError(SystemMessage.BULK_MSG_SEND_FAIL);
            }

        } catch (Exception ex) {
            addActionError(SystemMessage.BULK_MSG_SEND_FAIL);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }

    public String Delete() {
        try {

            if (service.deleteData(inputBean)) {
                DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.BULK_MESSAGE_MANAGEMENT, PageVarList.BULK_MESSAGE_SENDER, TaskVarList.DELETE, SystemMessage.BULK_MSG_DELETED, request.getRemoteAddr());
                LogFileCreator.writeInfoToLog(SystemMessage.BULK_MSG_DELETED);
                inputBean.setMessage(SystemMessage.BULK_MSG_DELETED);
                inputBean.setSuccess(true);
            } else {
                inputBean.setMessage(SystemMessage.BULK_MSG_DELETED_FAIL);
                inputBean.setSuccess(false);
            }

        } catch (Exception ex) {
            inputBean.setMessage(SystemMessage.BULK_MSG_DELETED_FAIL);
            inputBean.setSuccess(false);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "delete";
    }

    private boolean doValidation(BulkSenderInputBean inputBean) throws Exception {
        boolean ok = false;
        try {

            if (inputBean.getReci_mobile() == null || inputBean.getReci_mobile().isEmpty()) {
                addActionError(SystemMessage.BULK_MSG_RECIPIENT_MOBILE_EMPTY);
                return ok;
            } else if (!Util.validateMOBILE(inputBean.getReci_mobile())) {
                addActionError(SystemMessage.BULK_MSG_RECIPIENT_MOBILE_INVALID);
            } else if (service.checkDuplicateMobile(inputBean)) {
                addActionError(SystemMessage.BULK_MSG_RECIPIENT_MOBILE_DUPLICATE);
                return ok;
            } else if (inputBean.getAmount() == null || inputBean.getAmount().isEmpty()) {
                addActionError(SystemMessage.BULK_MSG_AMOUNT_EMPTY);
                return ok;
            } else if (!Util.validateAMOUNT(inputBean.getAmount())) {
                addActionError(SystemMessage.BULK_MSG_AMOUNT_INVALID);
                return ok;
            } else if ((Double.parseDouble(inputBean.getAmount())%100) != 0.0 ) {
                addActionError(SystemMessage.BULK_MSG_AMOUNT_INVALID);
                return ok;
            }else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;

    }

    @Override
    public boolean checkAccess(String method, int userRole) {

        boolean status = false;
        applyUserPrivileges();
        String page = com.epic.init.PageVarList.BULK_MESSAGE_SENDER;
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
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(com.epic.init.PageVarList.BULK_MESSAGE_SENDER, request);
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
