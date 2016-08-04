/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.channel.action;

import com.epic.cla.channel.bean.ListenerProfileManagementBean;
import com.epic.cla.channel.bean.ListenerProfileManagementInputBean;
import com.epic.cla.channel.service.ListenerProfileManagementService;
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
public class ListenerProfileManagement extends ActionSupport implements ModelDriven<ListenerProfileManagementInputBean>, AccessControlService {

    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub;
    ListenerProfileManagementInputBean inputBean = new ListenerProfileManagementInputBean();
    ListenerProfileManagementService listenerproservice = new ListenerProfileManagementService();

    public SessionUserBean getSub() {
        return (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }

    @Override
    public ListenerProfileManagementInputBean getModel() {
        return inputBean;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
    
    public String List() {
       
        try {
            List<ListenerProfileManagementBean> dataList = null;
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }

            dataList = listenerproservice.loadData(inputBean, orderBy, from, rows);

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
            listenerproservice.findData(inputBean);
        } catch (Exception e) {
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
            LogFileCreator.writeErrorToLog(e);
        }
        return "find";
    }

    public String Update() {
        try {

            if (doValidation(inputBean)) {

                if (listenerproservice.updateData(inputBean)) {

                    addActionMessage(SystemMessage.LISTENER_PRO_UPDATED);

                    DBProcesses.insertHistoryRecord(getSub().getUsername(),
                            Module.CHANNEL_MANAGEMENT, PageVarList.LISTENER_PROFILE_MANAGEMENT, TaskVarList.UPDATE, SystemMessage.LISTENER_PRO_UPDATED, request.getRemoteAddr());

                    LogFileCreator.writeInfoToLog(SystemMessage.LISTENER_PRO_UPDATED);

                } else {
                    addActionError(SystemMessage.LISTENER_PRO_UPDATED_ERROR);
                }

            }
        } catch (Exception ex) {
            addActionError(SystemMessage.LISTENER_UPDATED_ERROR);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }
    
    private boolean doValidation(ListenerProfileManagementInputBean listenerBean) throws Exception {
        boolean ok = false;

        try {

            if (listenerBean.getCollectionacc() == null || listenerBean.getCollectionacc().isEmpty()) {
                addActionError(SystemMessage.LISTENER_PRO_COLLECTION_ACCOUNT_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(listenerBean.getCollectionacc())) {
                addActionError(SystemMessage.LISTENER_PRO_COLLECTION_ACCOUNT_INVALID);
                return ok;
            }else if (listenerBean.getGlacc() == null || listenerBean.getGlacc().isEmpty()) {
                addActionError(SystemMessage.LISTENER_PRO_GL_ACCOUNT_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(listenerBean.getGlacc())) {
                addActionError(SystemMessage.LISTENER_PRO_GL_ACCOUNT_INVALID);
                return ok;
            }
//            else if (listenerBean.getCostCenter() == null || listenerBean.getCostCenter().isEmpty()) {
//                addActionError(SystemMessage.LISTENER_PRO_COST_CENTER_ACCOUNT_EMPTY);
//                return ok;
//            } else if (!Util.validateNUMBER(listenerBean.getCostCenter())) {
//                addActionError(SystemMessage.LISTENER_PRO_COST_CENTER_ACCOUNT_INVALID);
//                return ok;
//            }
            else if (listenerBean.getAmountHold().equals("-1")) {
                addActionError(SystemMessage.LISTENER_PRO_AMOUNT_HOLD_STATUS_SELECT);
                return ok;
            } else if (listenerBean.getSenderValidation().equals("-1")) {
                addActionError(SystemMessage.LISTENER_PRO_SENDER_VALIDATION_STATUS_SELECT);
                return ok;
            } else if (listenerBean.getTxnFee().equals("-1")) {
                addActionError(SystemMessage.LISTENER_PRO_TXN_FEE_STATUS_SELECT);
                return ok;
            }  else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;

    }
    

   private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.LISTENER_PROFILE_MANAGEMENT, request);
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
        String page = PageVarList.LISTENER_PROFILE_MANAGEMENT;
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
