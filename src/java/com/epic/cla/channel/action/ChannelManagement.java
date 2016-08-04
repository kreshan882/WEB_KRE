/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.channel.action;

import com.epic.cla.channel.bean.ChannelBean;
import com.epic.cla.channel.bean.ChannelManagementInputBean;
import com.epic.cla.channel.service.ChannelManagementService;
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
public class ChannelManagement extends ActionSupport implements ModelDriven<ChannelManagementInputBean>, AccessControlService {

    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub;
    ChannelManagementInputBean inputBean = new ChannelManagementInputBean();
    ChannelManagementService channelservice = new ChannelManagementService();

    public SessionUserBean getSub() {
        return (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    public ChannelManagementInputBean getModel() {
        try {
            inputBean.setChanneltypeList(channelservice.getChannelTypeList());
            inputBean.setContypeList(channelservice.getConTypeList());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inputBean;
    }

    public String List() {
        try {
            List<ChannelBean> dataList = null;
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }

            dataList = channelservice.loadData(inputBean, orderBy, from, rows);

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

                if (channelservice.addData(inputBean)) {
                    DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.CUSTOMER_MANAGEMENT, PageVarList.CHANNEL_MANAGEMENT, TaskVarList.ADD, SystemMessage.CHANNEL_ADD + " for " + inputBean.getName(), request.getRemoteAddr());
                    addActionMessage(SystemMessage.CHANNEL_ADD);
                    LogFileCreator.writeInfoToLog(SystemMessage.CHANNEL_ADD);

                } else {
                    addActionError(SystemMessage.CHANNEL_ADD_FAIL);
                }
            }

        } catch (Exception ex) {
            addActionError(SystemMessage.CHANNEL_ADD_FAIL);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "add";
    }

    public String Find() {

        try {
            channelservice.findData(inputBean);
        } catch (Exception e) {
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
            LogFileCreator.writeErrorToLog(e);
        }

        return "find";
    }

    public String Update() {
        try {

            if (doValidationUpdate(inputBean)) {

                if (channelservice.updateData(inputBean)) {

                    addActionMessage(SystemMessage.CHANNEL_UPDATED);

                    DBProcesses.insertHistoryRecord(getSub().getUsername(),
                            Module.CHANNEL_MANAGEMENT, PageVarList.CHANNEL_MANAGEMENT, TaskVarList.UPDATE, SystemMessage.CHANNEL_UPDATED, request.getRemoteAddr());

                    LogFileCreator.writeInfoToLog(SystemMessage.CHANNEL_UPDATED);

                } else {
                    addActionError(SystemMessage.CHANNEL_UPDATED_ERROR);
                }

            }
        } catch (Exception ex) {
            addActionError(SystemMessage.CHANNEL_UPDATED_ERROR);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }

    public String Delete() {
        try {

            if (channelservice.deleteData(inputBean)) {
                DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.CHANNEL_MANAGEMENT, PageVarList.CHANNEL_MANAGEMENT, TaskVarList.DELETE, SystemMessage.CHANNEL_DELETED, request.getRemoteAddr());
                LogFileCreator.writeInfoToLog(SystemMessage.CHANNEL_DELETED);
                inputBean.setMessage(SystemMessage.CHANNEL_DELETED);
                inputBean.setSuccess(true);
            } else {
                inputBean.setMessage(SystemMessage.CHANNEL_DELETED_ERROR);
                inputBean.setSuccess(false);
            }

        } catch (Exception ex) {
            inputBean.setMessage(SystemMessage.CHANNEL_DELETED_ERROR);
            inputBean.setSuccess(false);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "delete";
    }

    private boolean doValidation(ChannelManagementInputBean channelBean) throws Exception {
        boolean ok = false;

        try {
            if (channelBean.getName() == null || channelBean.getName().isEmpty()) {
                addActionError(SystemMessage.CHANNEL_NAME_EMPTY);
                return ok;
            } else if (!Util.validateNAME(channelBean.getName())) {
                addActionError(SystemMessage.CHANNEL_NAME_INVALID);
                return ok;
            } else if (channelBean.getChanneltype().equals("-1")) {
                addActionError(SystemMessage.CHANNEL_TYPE_SELECT);
                return ok;
            } else if (channelBean.getIp() == null || channelBean.getIp().isEmpty()) {
                addActionError(SystemMessage.CHANNEL_IP_EMPTY);
                return ok;
            } else if (!Util.validateIP(channelBean.getIp())) {
                addActionError(SystemMessage.CHANNEL_IP_INVALID);
                return ok;
            } else if (channelBean.getPort() == null || channelBean.getPort().isEmpty()) {
                addActionError(SystemMessage.CHANNEL_PORT_EMPTY);
                return ok;
            } else if (!Util.validatePORT(channelBean.getPort())) {
                addActionError(SystemMessage.CHANNEL_PORT_INVALID);
                return ok;
            } else if (channelBean.getContimeout() == null || channelBean.getContimeout().isEmpty()) {
                addActionError(SystemMessage.CHANNEL_CONTIMEOUT_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(channelBean.getContimeout())) {
                addActionError(SystemMessage.CHANNEL_CONTIMEOUT_INVALID);
                return ok;
            } else if (channelBean.getContype().equals("-1")) {
                addActionError(SystemMessage.CHANNEL_CONNECTION_TYPE_SELECT);
                return ok;
            } else if (channelBean.getRtimeout() == null || channelBean.getRtimeout().isEmpty()) {
                addActionError(SystemMessage.CHANNEL_READTIMEOUT_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(channelBean.getRtimeout())) {
                addActionError(SystemMessage.CHANNEL_READTIMEOUT_INVALID);
                return ok;
            } else if (channelBean.getHeadersize() == null || channelBean.getHeadersize().isEmpty()) {
                addActionError(SystemMessage.CHANNEL_HEADERSIZE_EMPTY);
                return ok;
            } else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;

    }

    private boolean doValidationUpdate(ChannelManagementInputBean channelBean) throws Exception {
        boolean ok = false;

        try {

            if (channelBean.getUpname() == null || channelBean.getUpname().isEmpty()) {
                addActionError(SystemMessage.CHANNEL_NAME_EMPTY);
                return ok;
            } else if (!Util.validateNAME(channelBean.getUpname())) {
                addActionError(SystemMessage.CHANNEL_NAME_INVALID);
                return ok;
            } else if (channelBean.getUpchanneltype().equals("-1")) {
                addActionError(SystemMessage.CHANNEL_TYPE_SELECT);
                return ok;
            } else if (channelBean.getUpip() == null || channelBean.getUpip().isEmpty()) {
                addActionError(SystemMessage.CHANNEL_IP_EMPTY);
                return ok;
            } else if (!Util.validateIP(channelBean.getUpip())) {
                addActionError(SystemMessage.CHANNEL_IP_INVALID);
                return ok;
            } else if (channelBean.getUpport() == null || channelBean.getUpport().isEmpty()) {
                addActionError(SystemMessage.CHANNEL_PORT_EMPTY);
                return ok;
            } else if (!Util.validatePORT(channelBean.getUpport())) {
                addActionError(SystemMessage.CHANNEL_PORT_INVALID);
                return ok;
            } else if (channelBean.getUpcontimeout() == null || channelBean.getUpcontimeout().isEmpty()) {
                addActionError(SystemMessage.CHANNEL_CONTIMEOUT_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(channelBean.getUpcontimeout())) {
                addActionError(SystemMessage.CHANNEL_CONTIMEOUT_INVALID);
                return ok;
            } else if (channelBean.getUpcontype().equals("-1")) {
                addActionError(SystemMessage.CHANNEL_CONNECTION_TYPE_SELECT);
                return ok;
            } else if (channelBean.getUprtimeout() == null || channelBean.getUprtimeout().isEmpty()) {
                addActionError(SystemMessage.CHANNEL_READTIMEOUT_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(channelBean.getUprtimeout())) {
                addActionError(SystemMessage.CHANNEL_READTIMEOUT_INVALID);
                return ok;
            } else if (channelBean.getUpheadersize() == null || channelBean.getUpheadersize().isEmpty()) {
                addActionError(SystemMessage.CHANNEL_HEADERSIZE_EMPTY);
                return ok;
            } else if (channelBean.getUpstatus().equals("-1")) {
                addActionError(SystemMessage.CHANNEL_STATUS_SELECT);
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
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.CHANNEL_MANAGEMENT, request);
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
