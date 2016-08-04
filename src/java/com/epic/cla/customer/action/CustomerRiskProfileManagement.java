/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.customer.action;

import com.epic.cla.customer.bean.CostomerRiskProfileManagementInputBean;
import com.epic.cla.customer.bean.CustomerRiskProfileManagementBean;
import com.epic.cla.customer.service.CustomerRiskProfileManagementService;
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
public class CustomerRiskProfileManagement extends ActionSupport implements ModelDriven<CostomerRiskProfileManagementInputBean>, AccessControlService {

    private CustomerRiskProfileManagementService service = new CustomerRiskProfileManagementService();
    CostomerRiskProfileManagementInputBean inputBean = new CostomerRiskProfileManagementInputBean();
    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub;

    public SessionUserBean getSub() {
        return (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }

    public String List() {
        try {
            List<CustomerRiskProfileManagementBean> dataList = null;
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }

//            if (inputBean.getRpname().isEmpty()) {
                dataList = service.loadData(inputBean, orderBy, from, rows);
//            } else {
//                dataList = service.loadDataSearch(inputBean, orderBy, from, rows);
//            }

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
            service.findData(inputBean);
        } catch (Exception e) {
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
        }

        return "find";
    }

    public String Add() {
        try {
            if (doValidation(inputBean)) {

                if (service.addData(inputBean)) {
                    DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.CUSTOMER_MANAGEMENT, PageVarList.USER_MANAGEMENT, TaskVarList.ADD, SystemMessage.CUSTRISKPROMGMNT_ADD + " for " + inputBean.getUsername(), request.getRemoteAddr());
                    addActionMessage(SystemMessage.CUSTRISKPROMGMNT_ADD);

                } else {
                    addActionError(SystemMessage.CUSTRISKPROMGMNT_ADD_FAIL);
                }
            }

        } catch (Exception ex) {
            addActionError(SystemMessage.CUSTRISKPROMGMNT_ADD_FAIL);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "add";
    }

    public String Update() {
        try {
            if (doValidationUpdate(inputBean)) {

                if (service.updateData(inputBean)) {

                    addActionMessage(SystemMessage.CUSTRISKPROMGMNT_UPDATED);

                    DBProcesses.insertHistoryRecord(getSub().getUsername(),
                            Module.CUSTOMER_MANAGEMENT, PageVarList.CUSTOMER_RISK_PROFILE_MANAGEMENT, TaskVarList.UPDATE, SystemMessage.CUSTRISKPROMGMNT_UPDATED, request.getRemoteAddr());

                    LogFileCreator.writeInfoToLog(SystemMessage.CUSTRISKPROMGMNT_UPDATED);

                } else {
                    addActionError(SystemMessage.CUSTRISKPROMGMNT_UPDATED_ERROR);
                }

            }
        } catch (Exception ex) {
            addActionError(SystemMessage.CUSTRISKPROMGMNT_UPDATED_ERROR);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }

    public String Delete() {
        try {
            if (service.deleteData(inputBean)) {
                DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.CUSTOMER_MANAGEMENT, PageVarList.CUSTOMER_RISK_PROFILE_MANAGEMENT, TaskVarList.DELETE, SystemMessage.CUSTRISKPROMGMNT_DELETED, request.getRemoteAddr());
                LogFileCreator.writeInfoToLog(SystemMessage.CUSTRISKPROMGMNT_DELETED);
                inputBean.setMessage(SystemMessage.CUSTRISKPROMGMNT_DELETED);
                inputBean.setSuccess(true);
            } else {
                inputBean.setMessage(SystemMessage.CUSTRISKPROMGMNT_DELETED_ERROR);
                inputBean.setSuccess(false);
            }

        } catch (Exception ex) {
            inputBean.setMessage(SystemMessage.CUSTRISKPROMGMNT_DELETED_ERROR);
            inputBean.setSuccess(false);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "delete";
    }

    @Override
    public CostomerRiskProfileManagementInputBean getModel() {
        try {
            service.getProfileList(inputBean);
            inputBean.getTransferModeList().putAll(Util.getTransferModeList());
            inputBean.getMessegeDeliveryList().putAll(Util.getMessegeDeliveryPartyList());
            inputBean.getMsgFormatList().putAll(Util.getMessegeFormatCodeList());
            inputBean.getFeeCalMethodList().putAll(Util.getFeeMethodList());
            inputBean.getMsgvalidityperiodList().putAll(Util.getValidityPeriod());
        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.CUSTOMER_RISK_PROFILE_MANAGEMENT, request);
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
        String page = PageVarList.CUSTOMER_RISK_PROFILE_MANAGEMENT;
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

    private boolean doValidation(CostomerRiskProfileManagementInputBean inputBean) throws Exception {
        boolean ok = false;

        try {

            if (inputBean.getName() == null || inputBean.getName().isEmpty()) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_NAME_EMPTY);
                return ok;
            } else if (inputBean.getMinamountpertxn() == null || inputBean.getMinamountpertxn().isEmpty()) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MINAMNTPERTXN_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getMinamountpertxn())) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MINAMNTPERTXN_INVALID);
                return ok;
            } else if (inputBean.getMaxamountpertxn() == null || inputBean.getMaxamountpertxn().isEmpty()) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MAXAMNTPERTXN_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getMaxamountpertxn())) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MAXAMNTPERTXN_INVALID);
                return ok;
            } else if (inputBean.getMaxamountperday() == null || inputBean.getMaxamountperday().isEmpty()) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MAXAMOUNTPERDAY_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getMaxamountperday())) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MAX_AMOUNT_PER_DAY_INVALID);
                return ok;
            } else if (inputBean.getNooftxnallowedperdayrecipient() == null || inputBean.getNooftxnallowedperdayrecipient().isEmpty()) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_NOOFTXNALLOWEDPERRECIPIENT_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getNooftxnallowedperdayrecipient())) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_NOOFTXNALLOWEDPERRECIPIENT_INVALID);
                return ok;
            } else if (inputBean.getFeecalculationmethod().equals("-1")) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_FEECALCMETHOD_SELECT);
                return ok;
            } else if (inputBean.getFeevalue() == null || inputBean.getFeevalue().isEmpty()) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_FEEVALUE_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getFeevalue())) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_FEEVALUE_INVALID);
                return ok;
            } 
//            else if (inputBean.getGlobalamount() == null || inputBean.getGlobalamount().isEmpty()) {
//                addActionError(SystemMessage.CUSTRISKPROMGMNT_GLOBAL_AMOUNT_EMPTY);
//                return ok;
//            } else if (!Util.validateAMOUNT(inputBean.getGlobalamount())) {
//                addActionError(SystemMessage.CUSTRISKPROMGMNT_GLOBAL_AMOUNT_INVALID);
//                return ok;
//            }
            else if (inputBean.getTransfermode().equals("-1")) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_TRANSFERMODE_SELECT);
                return ok;
            } else if (inputBean.getMsgdeliverymodeord().equals("-1")) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MSGDELMODEORDER_SELECT);
                return ok;
            } else if (inputBean.getMsgvalidityperiod().equals("-1")) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MSGVALIDITYPERIOD_SELECT);
                return ok;
            } else if (inputBean.getMsgdeliverymodesec().equals("-1")) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MSGDELMODESEC_SELECT);
                return ok;
            } else if (inputBean.getChargesmode().equals("-1")) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_CHARGESMODE_SELECT);
                return ok;
            } else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;
    }

    private boolean doValidationUpdate(CostomerRiskProfileManagementInputBean inputBean) throws Exception {
        boolean ok = false;

        try {

            if (inputBean.getUpname() == null || inputBean.getUpname().isEmpty()) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_NAME_EMPTY);
                return ok;
            } else if (inputBean.getUpstatus().equals("-1")) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_STATUS_SELECT);
                return ok;
            } else if (inputBean.getUpminamountpertxn() == null || inputBean.getUpminamountpertxn().isEmpty()) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MINAMNTPERTXN_EMPTY);
                return ok;
            } else if (!Util.validateAMOUNT(inputBean.getUpminamountpertxn())) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MINAMNTPERTXN_INVALID);
                return ok;
            } else if (inputBean.getUpmaxamountpertxn() == null || inputBean.getUpmaxamountpertxn().isEmpty()) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MAXAMNTPERTXN_EMPTY);
                return ok;
            } else if (!Util.validateAMOUNT(inputBean.getUpmaxamountpertxn())) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MAXAMNTPERTXN_INVALID);
                return ok;
            } else if (inputBean.getUpmaxamountperday() == null || inputBean.getUpmaxamountperday().isEmpty()) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MAXAMOUNTPERDAY_EMPTY);
                return ok;
            } else if (!Util.validateAMOUNT(inputBean.getUpmaxamountperday())) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MAX_AMOUNT_PER_DAY_INVALID);
                return ok;
            } else if (inputBean.getUpnooftxnallowedperdayrecipient() == null || inputBean.getUpnooftxnallowedperdayrecipient().isEmpty()) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_NOOFTXNALLOWEDPERRECIPIENT_EMPTY);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getUpnooftxnallowedperdayrecipient())) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_NOOFTXNALLOWEDPERRECIPIENT_INVALID);
                return ok;
            } else if (inputBean.getUpfeecalculationmethod().equals("-1")) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_FEECALCMETHOD_SELECT);
                return ok;
            } else if (inputBean.getUpfeevalue() == null || inputBean.getUpfeevalue().isEmpty()) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_FEEVALUE_EMPTY);
                return ok;
            } else if (!Util.validateAMOUNT(inputBean.getUpfeevalue())) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_FEEVALUE_INVALID);
                return ok;
            } 
//            else if (inputBean.getUpglobalamount() == null || inputBean.getUpglobalamount().isEmpty()) {
//                addActionError(SystemMessage.CUSTRISKPROMGMNT_GLOBAL_AMOUNT_EMPTY);
//                return ok;
//            } else if (!Util.validateAMOUNT(inputBean.getUpglobalamount())) {
//                addActionError(SystemMessage.CUSTRISKPROMGMNT_GLOBAL_AMOUNT_INVALID);
//                return ok;
//            }
            else if (inputBean.getUptransfermode().equals("-1")) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_TRANSFERMODE_SELECT);
                return ok;
            } else if (inputBean.getUpmsgdeliverymodeord().equals("-1")) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MSGDELMODEORDER_SELECT);
                return ok;
            } else if (!Util.validateNUMBER(inputBean.getUpmsgdeliverymodeord())) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MSGVALIDITYPERIOD_INVALID);
                return ok;
            } else if (inputBean.getUpmsgvalidityperiod().equals("-1")) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MSGVALIDITYPERIOD_SELECT);
                return ok;
            } else if (inputBean.getUpmsgdeliverymodesec().equals("-1")) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_MSGDELMODESEC_SELECT);
                return ok;
            } else if (inputBean.getUpchargesmode().equals("-1")) {
                addActionError(SystemMessage.CUSTRISKPROMGMNT_CHARGESMODE_SELECT);
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
