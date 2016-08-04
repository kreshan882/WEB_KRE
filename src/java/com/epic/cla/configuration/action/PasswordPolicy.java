/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.configuration.action;

import com.epic.cla.configuration.bean.PasswordPolicyInputBean;
import com.epic.cla.configuration.service.PasswordPolicyService;
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
public class PasswordPolicy extends ActionSupport implements ModelDriven<PasswordPolicyInputBean>, AccessControlService {

    private PasswordPolicyService service = new PasswordPolicyService();
    PasswordPolicyInputBean inputBean = new PasswordPolicyInputBean();
    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub;

    public SessionUserBean getSub() {
        return  (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }

    public String Update() {

        try {
            if (doValidationUpdate(inputBean)) {

                if (service.updateData(inputBean)) {

                    addActionMessage(SystemMessage.PASSPOLICY_UPDATED);

                    DBProcesses.insertHistoryRecord(getSub().getUsername(),
                            Module.CONF_MANAGEMENT, PageVarList.PASSWORD_POLICY, TaskVarList.UPDATE, SystemMessage.PASSPOLICY_UPDATED, request.getRemoteAddr());

                    LogFileCreator.writeInfoToLog(SystemMessage.PASSPOLICY_UPDATED);

                } else {
                    addActionError(SystemMessage.PASSPOLICY_UPDATED_ERROR);
                }

            }
        } catch (Exception ex) {
            addActionError(SystemMessage.PASSPOLICY_UPDATED_ERROR);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }

    public String Find() {

        try {
            service.findData(inputBean);
        } catch (Exception e) {
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
        }

        return "find";
    }

    @Override
    public PasswordPolicyInputBean getModel() {
        try {
            inputBean = service.getPasswordPolicyDetails(inputBean);
        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.PASSWORD_POLICY, request);
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
        String page = PageVarList.USER_MANAGEMENT;
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

    private boolean doValidationUpdate(PasswordPolicyInputBean inputBean) throws Exception {

        boolean ok = false;        
        try {

            if (inputBean.getUpminlength()== null || inputBean.getUpminlength().isEmpty()) {
                addActionError(SystemMessage.PASSPOLICY_MINLENGTH_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getUpminlength())) {
                addActionError(SystemMessage.PASSPOLICY_MINLENGTH_INVALID);
                return ok;
            } else if (inputBean.getUpmaxlength()== null || inputBean.getUpmaxlength().isEmpty()) {
                addActionError(SystemMessage.PASSPOLICY_MAXLENGTH_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getUpmaxlength())) {
                addActionError(SystemMessage.PASSPOLICY_MAXLENGTH_INVALID);
                return ok;
            }else if (inputBean.getUpallowspecialcharacters()== null || inputBean.getUpallowspecialcharacters().isEmpty()) {
                addActionError(SystemMessage.PASSPOLICY_ALLOWSPECIALCHARS_EMPTY);
                return ok;
            } else if (!Util.validateSPECIALCHAR(inputBean.getUpallowspecialcharacters())) {
                addActionError(SystemMessage.PASSPOLICY_ALLOWSPECIALCHARS_INVALID);
                return ok;
            }else if (inputBean.getUpminimumspecialcharacters()== null || inputBean.getUpminimumspecialcharacters().isEmpty()) {
                addActionError(SystemMessage.PASSPOLICY_MINSPECIALCHARS_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getUpminimumspecialcharacters())) {
                addActionError(SystemMessage.PASSPOLICY_MINSPECIALCHARS_INVALID);
                return ok;
            }else if (inputBean.getUpmaximumspecialcharacters()== null || inputBean.getUpmaximumspecialcharacters().isEmpty()) {
                addActionError(SystemMessage.PASSPOLICY_MAXSPECIALCHARS_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getUpmaximumspecialcharacters())) {
                addActionError(SystemMessage.PASSPOLICY_MAXSPECIALCHARS_INVALID);
                return ok;
            }else if (!(Integer.parseInt(inputBean.getUpmaximumspecialcharacters()) >= Integer.parseInt(inputBean.getUpminimumspecialcharacters()))) {
                addActionError(SystemMessage.PASSPOLICY_MAXSPECIALCHARS_INVALID2);
                return ok;
            }else if (inputBean.getUpminimumuppercasecharacters()== null || inputBean.getUpminimumuppercasecharacters().isEmpty()) {
                addActionError(SystemMessage.PASSPOLICY_MINUPPERCHARS_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getUpminimumuppercasecharacters())) {
                addActionError(SystemMessage.PASSPOLICY_MINUPPERCHARS_INVALID);
                return ok;
            }else if (inputBean.getUpminimumnumericalcharacters()== null || inputBean.getUpminimumnumericalcharacters().isEmpty()) {
                addActionError(SystemMessage.PASSPOLICY_MINNUMERICALCHARS_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getUpminimumnumericalcharacters())) {
                addActionError(SystemMessage.PASSPOLICY_MINNUMERICALCHARS_INVALID);
                return ok;
            }else if (inputBean.getUpdescription()== null || inputBean.getUpdescription().isEmpty()) {
                addActionError(SystemMessage.PASSPOLICY_DESCRIPTION_EMPTY);
                return ok;
            } else if (!Util.validateDESCRIPTION(inputBean.getUpdescription())) {
                addActionError(SystemMessage.PASSPOLICY_DESCRIPTION_INVALID);
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
