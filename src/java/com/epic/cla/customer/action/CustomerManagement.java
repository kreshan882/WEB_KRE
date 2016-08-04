/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.customer.action;

import com.epic.cla.customer.bean.CostomerManagementInputBean;
import com.epic.cla.customer.bean.CustomerManagementBean;
import com.epic.cla.customer.service.CustomerManagementService;
import com.epic.db.DBProcesses;
import com.epic.init.InitConfigValue;
import com.epic.init.Module;
import com.epic.init.PageVarList;
import com.epic.init.TaskVarList;
import com.epic.login.bean.SessionUserBean;
import com.epic.login.bean.TaskBean;
import com.epic.util.AccessControlService;
import com.epic.util.Common;
import com.epic.util.CommunicationChannelHandler;
import com.epic.util.LogFileCreator;
import com.epic.util.SystemMessage;
import com.epic.util.Util;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author nipun_t
 */
public class CustomerManagement extends ActionSupport implements ModelDriven<CostomerManagementInputBean>, AccessControlService {

    private CustomerManagementService service = new CustomerManagementService();
    CostomerManagementInputBean inputBean = new CostomerManagementInputBean();
    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub;

    public SessionUserBean getSub() {
        return (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }

    public String execute() {
        return SUCCESS;
    }

    public String List() {
        try {
            //System.out.println("service port =" + InitConfigValue.SERVICEPORT);
            List<CustomerManagementBean> dataList = null;
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }

            dataList = service.loadData(inputBean, orderBy, from, rows);
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

    public String CustomerIQR() {
        try {
            String req = "";
            String response = "";
            req = "1|" + inputBean.getNic();
            Map addrMap = new HashMap<String, String>();
            Map accMap = new HashMap<String, String>();
            //00|CIF|NAME|ADDRESSES|PHONE|DOB|BRANCH|STATUS|COUNT|ACC NO.
            response = CommunicationChannelHandler.ReqAndResponse(req);
//            response = "000|100000002|Kreshan Rajendran|Address -1^Address -2^Address -3|+94772070386|19880120|Colombo 7|1|3|12345678^4454353453^6546446544^";//CommunicationChannelHandler.ReqAndResponse(req);

            String responseArray[] = response.split("\\|");

            if ("00".equals(responseArray[0])) {
                inputBean.setSuccess(true);

                String address[] = responseArray[3].split("\\^");
                if (!"0".equals(responseArray[8])) {
                    String accno[] = responseArray[9].split("\\^");
                    for (int i = 0; i < accno.length; i++) {
                        accMap.put(accno[i], accno[i]);
                    }
                } else {
                    inputBean.setSuccess(false);
                    inputBean.setMessage("No available account");
                }

                for (int i = 0; i < address.length; i++) {
                    addrMap.put(address[i], address[i]);
                }

                inputBean.setCif(responseArray[1]);
                inputBean.setName(responseArray[2]);
                inputBean.setAddressList((HashMap<String, String>) addrMap);
                inputBean.setMobile(responseArray[4]);
                inputBean.setDateofbirth(Util.changeDateFormat(responseArray[5]));
                inputBean.setBranch(responseArray[6]);
//              inputBean.setStatus(responseArray[7]) 
//              inputBean.setCount(responseArray[8])
                inputBean.setAccountnumberList((HashMap<String, String>) accMap);

            } else if (response.equals("C2")) {
                inputBean.setSuccess(false);
                inputBean.setMessage(SystemMessage.CUST_NOT_REGISTERED_NIC);
            }
//            else {
//                inputBean.setSuccess(false);
//                inputBean.setMessage("Error");
//                System.out.println("sw return error code......................");
//            }

        } catch (Exception ex) {
            inputBean.setSuccess(false);
            inputBean.setMessage("Error");
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "cusiqry";
    }

    public String AccountINQR() {
        try {
            String req = "";
            String response = "";
            req = "2|" + inputBean.getAccountnumber();
            response = CommunicationChannelHandler.ReqAndResponse(req);

            String responseArray[] = response.split("\\|");
            String tooltipdata[] = responseArray[1].split("\\^");
            if ("00".equals(responseArray[0])) {
                inputBean.setSuccess(true);
                inputBean.setTacctype(tooltipdata[1]);
                inputBean.setTamount(tooltipdata[2] + " " + tooltipdata[4]);
                inputBean.setTstatus(tooltipdata[3]);

            }
//            else {
//                inputBean.setSuccess(false);
//                inputBean.setMessage("Connection problem");
//                System.out.println("sw return error code......................");
//            }

        } catch (Exception ex) {

            inputBean.setSuccess(false);
            inputBean.setMessage("Connection problem");
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "acciqry";
    }

    public String Load() {
        try {
            service.getMerchantList(inputBean);
            inputBean.getRiskProfileList().putAll(service.getRiskProfileIDList(inputBean));
        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "merchantLoad";
    }

    public String Add() {
        try {
            if (doValidation(inputBean)) {

                if (service.addData(inputBean)) {
                    DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.CUSTOMER_MANAGEMENT, PageVarList.CUSTOMER_MANAGEMENT, TaskVarList.ADD, SystemMessage.CUST_ADD + " for " + inputBean.getUsername(), request.getRemoteAddr());
                    addActionMessage(SystemMessage.CUST_ADD);
                    LogFileCreator.writeInfoToLog(SystemMessage.CUST_ADD);
                    String req = "";
                    String response = "";
                    req = "7|" + inputBean.getMobile() + "|" + inputBean.getSmsprofileid();
                    response = CommunicationChannelHandler.ReqAndResponse(req);

                } else {
                    addActionError(SystemMessage.CUST_ADD_FAIL);
                }
            }

        } catch (Exception ex) {
            addActionError(SystemMessage.CUST_ADD_FAIL);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "add";
    }

    public String Find() {

        try {
            service.getMerchantList(inputBean);
            service.findData(inputBean);
            inputBean.getRiskProfileList().clear();
            inputBean.getRiskProfileList().putAll(service.getRiskProfileIDList(inputBean));
        } catch (Exception e) {
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
        }

        return "find";
    }

    public String Update() {

        try {
            if (doValidationUpdate(inputBean)) {

                if (service.updateData(inputBean)) {

                    addActionMessage(SystemMessage.CUST_UPDATED);

                    DBProcesses.insertHistoryRecord(getSub().getUsername(),
                            Module.CUSTOMER_MANAGEMENT, PageVarList.CUSTOMER_MANAGEMENT, TaskVarList.UPDATE, SystemMessage.CUST_UPDATED, request.getRemoteAddr());

                    LogFileCreator.writeInfoToLog(SystemMessage.CUST_UPDATED);

                } else {
                    addActionError(SystemMessage.CUST_UPDATED_ERROR);
                }

            }
        } catch (Exception ex) {
            addActionError(SystemMessage.CUST_UPDATED_ERROR);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return "update";
    }

    public String Delete() {
        try {
            if (service.deleteData(inputBean)) {
                DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.CUSTOMER_MANAGEMENT, PageVarList.CUSTOMER_MANAGEMENT, TaskVarList.DELETE, SystemMessage.CUST_DELETED, request.getRemoteAddr());
                LogFileCreator.writeInfoToLog(SystemMessage.CUST_DELETED);
                inputBean.setMessage(SystemMessage.CUST_DELETED);
                inputBean.setSuccess(true);
            } else {
                inputBean.setMessage(SystemMessage.CUST_DELETED_ERROR);
                inputBean.setSuccess(false);
            }

        } catch (Exception ex) {
            inputBean.setMessage(SystemMessage.CUST_DELETED_ERROR);
            inputBean.setSuccess(false);
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }

        return "delete";
    }

    private boolean doValidationUpdate(CostomerManagementInputBean userBean) throws Exception {
        boolean ok = false;

        try {

            if (userBean.getUpname() == null || userBean.getUpname().isEmpty()) {
                addActionError(SystemMessage.CUST_NAME_EMPTY);
                return ok;
            } else if (userBean.getUpnic() == null || userBean.getUpnic().isEmpty()) {
                addActionError(SystemMessage.CUST_NIC_EMPTY);
                return ok;
            } else if (!Util.validateNIC(userBean.getUpnic())) {
                addActionError(SystemMessage.CUST_NIC_INVALID);
                return ok;
            } //            else if (service.checkDublicateNIC(userBean.getUpnic())) {
            //                addActionError(SystemMessage.CUST_NIC_DUPLICATE);
            //                return ok;
            //            }
            else if (userBean.getUpcif() == null || userBean.getUpcif().isEmpty()) {
                addActionError(SystemMessage.CUST_CIF_EMPTY);
                return ok;
            } else if (!Util.validatePHONENO(userBean.getUpmobile())) {
                addActionError(SystemMessage.CUST_MOBILE_INVALID);
                return ok;
            } else if (service.checkDublicateMobileUpdate(userBean.getUpmobile(), userBean.getUpcustomerid())) {
                addActionError(SystemMessage.CUST_MOBILE_DUPLICATE);
                return ok;
            } //            else if (!Util.validateNUMBER(userBean.getCif())) {
            //                addActionError(SystemMessage.CUST_CIF_INVALID);
            //                return ok;
            //            } 
            //            else if (userBean.getUpmobile() == null || userBean.getUpmobile().isEmpty()) {
            //                addActionError(SystemMessage.CUST_MOBILE_EMPTY);
            //                return ok;
            //            } else if (!Util.validatePHONENO(userBean.getUpmobile())) {
            //                addActionError(SystemMessage.CUST_MOBILE_INVALID);
            //                return ok;
            //            } 
            //            else if (userBean.getUpaccountnumber() == null || userBean.getUpaccountnumber().isEmpty()) {
            //                addActionError(SystemMessage.CUST_ACC_NO_EMPTY);
            //                return ok;
            //            } //            else if (userBean.getUpusertype().equals("-1")) {
            //                addActionError(SystemMessage.CUST_USERTYPE_SELECT);
            //                return ok;
            //            } 
            else if (userBean.getUpriskprofileid().equals("-1")) {
                addActionError(SystemMessage.CUST_RISK_PROF_SELECT);
                return ok;
            } else if (userBean.getUpsmsprofileid().equals("-1")) {
                addActionError(SystemMessage.CUST_SMS_PROF_SELECT);
                return ok;
            } else if (userBean.getUpaddress() == null || userBean.getUpaddress().isEmpty()) {
                addActionError(SystemMessage.CUST_ADDRESS_EMPTY);
                return ok;
            } else if (!Util.validateDESCRIPTION(userBean.getUpaddress())) {
                addActionError(SystemMessage.CUST_ADDRESS_INVALID);
                return ok;
            } else if (userBean.getUpvalidaterecipient().equals("-1")) {
                addActionError(SystemMessage.CUST_VALIDATE_REC_EMPTY);
                return ok;
            } //            else if (userBean.getUpbranch() == null || userBean.getUpbranch().isEmpty()) {
            //                addActionError(SystemMessage.CUST_BRANCH_EMPTY);
            //                return ok;
            //            }
            else if (userBean.getUpstatus().equals("-1")) {
                addActionError(SystemMessage.CUST_STATUS_SELECT);
                return ok;
            } else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;

    }

    private boolean doValidation(CostomerManagementInputBean userBean) throws Exception {
        boolean ok = false;
        try {

            if (userBean.getNic() == null || userBean.getNic().isEmpty()) {
                addActionError(SystemMessage.CUST_NIC_EMPTY);
                return ok;
            } else if (!Util.validateNIC(userBean.getNic())) {
                addActionError(SystemMessage.CUST_NIC_INVALID);
                return ok;
            } else if (userBean.getName() == null || userBean.getName().isEmpty()) {
                addActionError(SystemMessage.CUST_NAME_EMPTY);
                return ok;
            } else if (userBean.getCif() == null || userBean.getCif().isEmpty()) {
                addActionError(SystemMessage.CUST_CIF_EMPTY);
                return ok;
            } else if (!Util.validateNAME(userBean.getName())) {
                addActionError(SystemMessage.CUST_NAME_INVALID);
                return ok;
            } else if (!Util.validateNUMBER(userBean.getCif())) {
                addActionError(SystemMessage.CUST_CIF_INVALID);
                return ok;
            } else if (userBean.getMobile() == null || userBean.getMobile().isEmpty()) {
                addActionError(SystemMessage.CUST_MOBILE_EMPTY);
                return ok;
            } else if (!Util.validatePHONENO(userBean.getMobile())) {
                addActionError(SystemMessage.CUST_MOBILE_INVALID);
                return ok;
            } else if (service.checkDublicateMobile(userBean.getMobile())) {
                addActionError(SystemMessage.CUST_MOBILE_DUPLICATE);
                return ok;
            } else if (userBean.getAccountnumber().equals("-1")) {
                addActionError(SystemMessage.CUST_ACC_NO_SELECT);
                return ok;
            } else if (userBean.getRiskprofileid().equals("-1")) {
                addActionError(SystemMessage.CUST_RISK_PROF_SELECT);
                return ok;
            } else if (userBean.getSmsprofileid().equals("-1")) {
                addActionError(SystemMessage.CUST_SMS_PROF_SELECT);
                return ok;
            } //            else if (userBean.getAddress() == null || userBean.getAddress().isEmpty()) {
            //                addActionError(SystemMessage.CUST_ADDRESS_EMPTY);
            //                return ok;
            //            } 
            //            else if (!Util.validateDESCRIPTION(userBean.getAddress())) {
            //                addActionError(SystemMessage.CUST_ADDRESS_INVALID);
            //                return ok;
            //            } 
            else if (userBean.getValidaterecipient().equals("-1")) {
                addActionError(SystemMessage.CUST_VALIDATE_REC_EMPTY);
                return ok;
            } else if (userBean.getBranch() == null || userBean.getBranch().isEmpty()) {
                addActionError(SystemMessage.CUST_BRANCH_EMPTY);
                return ok;
            } else if (userBean.getUsertype().equals("-1")) {
                addActionError(SystemMessage.CUST_USERTYPE_SELECT);
                return ok;
            } else if (userBean.getUsertype().equals("02") && (userBean.getMerchantId().equals("-1") || userBean.getMerchantId() == null)) {
                addActionError(SystemMessage.CUST_USER_MERCHANT_EMPTY);
                return ok;
            } else {
                ok = true;
            }

        } catch (Exception e) {
            throw e;
        }
        return ok;

    }

    @Override
    public CostomerManagementInputBean getModel() {
        try {
            service.getProfileList(inputBean);
            inputBean.getUsertypeList().putAll(Util.getCustomerUserTypeList());
            inputBean.getValidaterecipientList().putAll(Util.getValidateStatusList());
            inputBean.getSmsProfileList().putAll(service.getSmsProfileList());
        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.CUSTOMER_MANAGEMENT, request);
        inputBean.setVadd(true);
        inputBean.setVupdate(true);
        inputBean.setVdelete(true);
        inputBean.setVdownload(true);
        inputBean.setVresetpass(true);
        inputBean.setVrecipientadd(true);
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
                } else if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.RECIPIENTADD)) {
                    inputBean.setVrecipientadd(false);
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
        } else if ("CustomerIQR".equals(method)) {
            task = TaskVarList.UPDATE;
        } else if ("AccountINQR".equals(method)) {
            task = TaskVarList.UPDATE;
        } else if ("Load".equals(method)) {
            task = TaskVarList.UPDATE;
        } else if ("RecipientList".equals(method)) {
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

}
