/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.configuration.action;

import com.epic.cla.configuration.bean.SystemParametersInputBean;
import com.epic.cla.configuration.service.SystemParametersService;
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
 * @author nipun_t
 */
public class SystemParameters extends ActionSupport implements ModelDriven<SystemParametersInputBean>, AccessControlService {

    private SystemParametersService service = new SystemParametersService();
    SystemParametersInputBean inputBean = new SystemParametersInputBean();
    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub;

    public SessionUserBean getSub() {
        return (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }

    public String Update() {
        try {
            if (doValidationUpdate(inputBean)) {

                if (service.updateData(inputBean)) {

                    addActionMessage(SystemMessage.SYSPARAMETER_UPDATED);

                    DBProcesses.insertHistoryRecord(getSub().getUsername(),
                            Module.CONF_MANAGEMENT, PageVarList.SYSTEM_PARAMETERS, TaskVarList.UPDATE, SystemMessage.SYSPARAMETER_UPDATED, request.getRemoteAddr());

                    LogFileCreator.writeInfoToLog(SystemMessage.SYSPARAMETER_UPDATED);

                } else {
                    addActionError(SystemMessage.SYSPARAMETER_UPDATED_ERROR);
                }

            }
        } catch (Exception ex) {
            addActionError(SystemMessage.SYSPARAMETER_UPDATED_ERROR);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }

    public String Find() {
        try {
            service.getProfileList(inputBean);
            service.getConfiguration(inputBean);
            inputBean.setLogLevelList(Util.getLogLevelList());
            inputBean.setLogBackupStatusList(Util.getBasicStatus());
        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "find";
    }

    @Override
    public SystemParametersInputBean getModel() {
        try {
            service.getProfileList(inputBean);
            service.getConfiguration(inputBean);
            inputBean.setLogLevelList(Util.getLogLevelList());
            inputBean.setLogBackupStatusList(Util.getBasicStatus());
        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.SYSTEM_PARAMETERS, request);
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
        String page = PageVarList.SYSTEM_PARAMETERS;
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

    private boolean doValidationUpdate(SystemParametersInputBean inputBean) throws Exception {

        boolean ok = false;

        try {

            if (inputBean.getMinpoolsize() == null || inputBean.getMinpoolsize().isEmpty()) {
                addActionError(SystemMessage.SYSPARAMETER_MIN_POOL_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getMinpoolsize())) {
                addActionError(SystemMessage.SYSPARAMETER_MIN_POOL_INVALID);
                return ok;
            } else if (inputBean.getMaxpoolsize() == null || inputBean.getMaxpoolsize().isEmpty()) {
                addActionError(SystemMessage.SYSPARAMETER_MAX_POOL_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getMaxpoolsize())) {
                addActionError(SystemMessage.SYSPARAMETER_MAX_POOL_INVALID);
                return ok;
            } else if (inputBean.getMaxqueuesize() == null || inputBean.getMaxqueuesize().isEmpty()) {
                addActionError(SystemMessage.SYSPARAMETER_MAX_POOL_QUESE_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getMaxqueuesize())) {
                addActionError(SystemMessage.SYSPARAMETER_MAX_POOL_QUESE_INVALID);
                return ok;
            } else if (inputBean.getOrdlength() == null || inputBean.getOrdlength().isEmpty()) {
                addActionError(SystemMessage.SYSPARAMETER_ORD_LENGTH_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getOrdlength())) {
                addActionError(SystemMessage.SYSPARAMETER_ORD_LENGTH_INVALID);
                return ok;
            } else if (Integer.parseInt(inputBean.getOrdlength()) < 4 || Integer.parseInt(inputBean.getOrdlength()) > 10) {
                addActionError(SystemMessage.SYSPARAMETER_ORD_LENGTH_LEN_INVALID);
                return ok;
            } else if (inputBean.getSeclength() == null || inputBean.getSeclength().isEmpty()) {
                addActionError(SystemMessage.SYSPARAMETER_SEC_LENGTH_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getSeclength())) {
                addActionError(SystemMessage.SYSPARAMETER_SEC_LENGTH_INVALID);
                return ok;
            } else if (Integer.parseInt(inputBean.getSeclength()) < 4 || Integer.parseInt(inputBean.getSeclength()) > 8) {
                addActionError(SystemMessage.SYSPARAMETER_SEC_LENGTH_LEN_INVALID);
                return ok;
            } else if (inputBean.getMaxpinretry()== null || inputBean.getMaxpinretry().isEmpty()) {
                addActionError(SystemMessage.SYSPARAMETER_MAX_RETRY_EMPTY);
                return ok;
            }  else if (inputBean.getServiceport() == null || inputBean.getServiceport().isEmpty()) {
                addActionError(SystemMessage.SYSPARAMETER_SERVICE_PORT_EMPTY);
                return ok;
            } else if (!Util.validatePORT(inputBean.getServiceport())) {
                addActionError(SystemMessage.SYSPARAMETER_SERVICE_PORT_INVALID);
                return ok;
            } else if (inputBean.getServiceportreadtimeout() == null || inputBean.getServiceportreadtimeout().isEmpty()) {
                addActionError(SystemMessage.SYSPARAMETER_SERVICE_READ_TIMEOUT_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getServiceportreadtimeout())) {
                addActionError(SystemMessage.SYSPARAMETER_SERVICE_READ_TIMEOUT_INVALID);
                return ok;
            } else if (inputBean.getLogbackupstatus().equals("-1")) {
                addActionError(SystemMessage.SYSPARAMETER_SELECT_STATUS);
                return ok;
            } else if (inputBean.getLogbackuppath() == null || inputBean.getLogbackuppath().isEmpty()) {
                addActionError(SystemMessage.SYSPARAMETER_LOG_BACKUP_PATH_EMPTY);
                return ok;
            } else if (inputBean.getNooflogfile() == null || inputBean.getNooflogfile().isEmpty()) {
                addActionError(SystemMessage.SYSPARAMETER_NOF_LOG_FILE_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getNooflogfile())) {
                addActionError(SystemMessage.SYSPARAMETER_NOF_LOG_FILE_INVALID);
                return ok;
            } else if (inputBean.getOperationport() == null || inputBean.getOperationport().isEmpty()) {
                addActionError(SystemMessage.SYSPARAMETER_OPERATION_PORT_EMPTY);
                return ok;
            } else if (!Util.validatePORT(inputBean.getOperationport())) {
                addActionError(SystemMessage.SYSPARAMETER_OPERATION_PORT_INVALID);
                return ok;
            } else if (inputBean.getLoglevel().equals("-1")) {
                addActionError(SystemMessage.SYSPARAMETER_SELECT_LOG_LEVEL);
                return ok;
            } else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;
    }

}
