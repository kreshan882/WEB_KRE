/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.channel.action;

import com.epic.cla.channel.bean.ListenerBean;
import com.epic.cla.channel.bean.ListenerManagementInputBean;
import com.epic.cla.channel.service.ListenerManagementService;
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
public class ListenerManagement extends ActionSupport implements ModelDriven<ListenerManagementInputBean>, AccessControlService {

    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub;
    ListenerManagementInputBean inputBean = new ListenerManagementInputBean();
    ListenerManagementService listenerService = new ListenerManagementService();

    public SessionUserBean getSub() {
        return  (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }
    
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    public ListenerManagementInputBean getModel() {
        try {
            inputBean.setListenertypeList(listenerService.getListenerTypeList());
            inputBean.setContypeList(listenerService.getConTypeList());
        } catch (Exception ex) {
            LogFileCreator.writeErrorToLog(ex);
            ex.printStackTrace();
        }
        return inputBean;
    }

    public String List() {
        try {
            List<ListenerBean> dataList = null;
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }

            dataList = listenerService.loadData(inputBean, orderBy, from, rows);

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

    public String Add() {

        try {
            if (doValidation(inputBean)) {

                if (listenerService.addData(inputBean)) {
                    DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.CUSTOMER_MANAGEMENT, PageVarList.LISTENER_MANAGEMENT, TaskVarList.ADD, SystemMessage.LISTENER_ADD + " for " + inputBean.getName(), request.getRemoteAddr());
                    addActionMessage(SystemMessage.LISTENER_ADD);
                    LogFileCreator.writeInfoToLog(SystemMessage.LISTENER_ADD);

                } else {
                    addActionError(SystemMessage.LISTENER_ADD_FAIL);
                }
            }

        } catch (Exception ex) {
            addActionError(SystemMessage.LISTENER_ADD_FAIL);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "add";
    }

    public String Find() {

        try {
            listenerService.findData(inputBean);
        } catch (Exception e) {
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
            LogFileCreator.writeErrorToLog(e);
        }
        return "find";
    }

    public String Update() {
        try {

            if (doValidationUpdate(inputBean)) {

                if (listenerService.updateData(inputBean)) {

                    addActionMessage(SystemMessage.LISTENER_UPDATED);

                    DBProcesses.insertHistoryRecord(getSub().getUsername(),
                            Module.CHANNEL_MANAGEMENT, PageVarList.LISTENER_MANAGEMENT, TaskVarList.UPDATE, SystemMessage.LISTENER_UPDATED, request.getRemoteAddr());

                    LogFileCreator.writeInfoToLog(SystemMessage.LISTENER_UPDATED);

                } else {
                    addActionError(SystemMessage.LISTENER_UPDATED_ERROR);
                }

            }
        } catch (Exception ex) {
            addActionError(SystemMessage.LISTENER_UPDATED_ERROR);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }

    public String Delete() {
        try {

            if (listenerService.deleteData(inputBean)) {
                DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.CHANNEL_MANAGEMENT, PageVarList.LISTENER_MANAGEMENT, TaskVarList.DELETE, SystemMessage.LISTENER_DELETED, request.getRemoteAddr());
                LogFileCreator.writeInfoToLog(SystemMessage.LISTENER_DELETED);
                inputBean.setMessage(SystemMessage.LISTENER_DELETED);
                inputBean.setSuccess(true);
            } else {
                inputBean.setMessage(SystemMessage.LISTENER_DELETED_ERROR);
                inputBean.setSuccess(false);
            }

        } catch (Exception ex) {
            inputBean.setMessage(SystemMessage.LISTENER_DELETED_ERROR);
            inputBean.setSuccess(false);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "delete";
    }

    private boolean doValidation(ListenerManagementInputBean listenerBean) throws Exception {
        boolean ok = false;

        try {

            if (listenerBean.getName() == null || listenerBean.getName().isEmpty()) {
                addActionError(SystemMessage.LISTENER_NAME_EMPTY);
                return ok;
            } else if (!Util.validateNAME(listenerBean.getName())) {
                addActionError(SystemMessage.LISTENER_NAME_INVALID);
                return ok;
            } else if (listenerBean.getListenertype().equals("-1")) {
                addActionError(SystemMessage.LISTENER_TYPE_SELECT);
                return ok;
            } else if (listenerBean.getUids() == null || listenerBean.getUids().isEmpty()) {
                addActionError(SystemMessage.LISTENER_UIDS_EMPTY);
                return ok;
            } else if (!Util.validateSTRING(listenerBean.getUids())) {
                addActionError(SystemMessage.LISTENER_UIDS_INVALID);
                return ok;
            } else if (listenerBean.getPort() == null || listenerBean.getPort().isEmpty()) {
                addActionError(SystemMessage.LISTENER_PORT_EMPTY);
                return ok;
            } else if (!Util.validatePORT(listenerBean.getPort())) {
                addActionError(SystemMessage.LISTENER_PORT_INVALID);
                return ok;
            } else if (listenerBean.getBacklogsize() == null || listenerBean.getBacklogsize().isEmpty()) {
                addActionError(SystemMessage.LISTENER_BACKLOGSIZE_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(listenerBean.getBacklogsize())) {
                addActionError(SystemMessage.LISTENER_BACKLOGSIZE_INVALID);
                return ok;
            } else if (listenerBean.getContype().equals("-1")) {
                addActionError(SystemMessage.LISTENER_CONNECTION_TYPE_SELECT);
                return ok;
            } else if (listenerBean.getRtimeout() == null || listenerBean.getRtimeout().isEmpty()) {
                addActionError(SystemMessage.LISTENER_READTIMEOUT_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(listenerBean.getRtimeout())) {
                addActionError(SystemMessage.LISTENER_READTIMEOUT_INVALID);
                return ok;
            } else if (listenerBean.getHeadersize() == null || listenerBean.getHeadersize().isEmpty()) {
                addActionError(SystemMessage.LISTENER_HEADERSIZE_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(listenerBean.getHeadersize())) {
                addActionError(SystemMessage.LISTENER_HEADERSIZE_INVALID);
                return ok;
            } else if (listenerBean.getKalivestatus().equals("-1")) {
                addActionError(SystemMessage.LISTENER_KEEP_ALIVE_STATUS_SELECT);
                return ok;
            } else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;

    }

    private boolean doValidationUpdate(ListenerManagementInputBean channelBean) throws Exception {
        boolean ok = false;

        try {

            if (channelBean.getUpname() == null || channelBean.getUpname().isEmpty()) {
                addActionError(SystemMessage.LISTENER_NAME_EMPTY);
                return ok;
            } else if (!Util.validateNAME(channelBean.getUpname())) {
                addActionError(SystemMessage.LISTENER_NAME_INVALID);
                return ok;
            } else if (channelBean.getUplistenertype().equals("-1")) {
                addActionError(SystemMessage.LISTENER_TYPE_SELECT);
                return ok;
            } else if (channelBean.getUpuids() == null || channelBean.getUpuids().isEmpty()) {
                addActionError(SystemMessage.LISTENER_UIDS_EMPTY);
                return ok;
            } else if (!Util.validateSTRING(channelBean.getUpuids())) {
                addActionError(SystemMessage.LISTENER_UIDS_INVALID);
                return ok;
            } else if (channelBean.getUpport() == null || channelBean.getUpport().isEmpty()) {
                addActionError(SystemMessage.LISTENER_PORT_EMPTY);
                return ok;
            } else if (!Util.validatePORT(channelBean.getUpport())) {
                addActionError(SystemMessage.LISTENER_PORT_INVALID);
                return ok;
            } else if (channelBean.getUpbacklogsize() == null || channelBean.getUpbacklogsize().isEmpty()) {
                addActionError(SystemMessage.LISTENER_BACKLOGSIZE_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(channelBean.getUpbacklogsize())) {
                addActionError(SystemMessage.LISTENER_BACKLOGSIZE_INVALID);
                return ok;
            } else if (channelBean.getUpcontype().equals("-1")) {
                addActionError(SystemMessage.LISTENER_CONNECTION_TYPE_SELECT);
                return ok;
            } else if (channelBean.getUprtimeout() == null || channelBean.getUprtimeout().isEmpty()) {
                addActionError(SystemMessage.LISTENER_READTIMEOUT_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(channelBean.getUprtimeout())) {
                addActionError(SystemMessage.LISTENER_READTIMEOUT_INVALID);
                return ok;
            } else if (channelBean.getUpheadersize() == null || channelBean.getUpheadersize().isEmpty()) {
                addActionError(SystemMessage.LISTENER_HEADERSIZE_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(channelBean.getUpheadersize())) {
                addActionError(SystemMessage.LISTENER_HEADERSIZE_INVALID);
                return ok;
            } else if (channelBean.getUpstatus().equals("-1")) {
                addActionError(SystemMessage.LISTENER_STATUS_SELECT);
                return ok;
            } else if (channelBean.getUpkalivestatus().equals("-1")) {
                addActionError(SystemMessage.LISTENER_KEEP_ALIVE_STATUS_SELECT);
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
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.LISTENER_MANAGEMENT, request);
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
        String page = PageVarList.CHANNEL_MANAGEMENT;
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
