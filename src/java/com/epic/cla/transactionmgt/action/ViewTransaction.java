/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.transactionmgt.action;

import com.epic.cla.transactionmgt.bean.ViewTransactionDataBean;
import com.epic.cla.transactionmgt.bean.ViewTransactionInputBean;
import com.epic.cla.transactionmgt.service.ViewTransactionService;
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
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author tharaka
 */
public class ViewTransaction extends ActionSupport implements ModelDriven<ViewTransactionInputBean>, AccessControlService {

    private ViewTransactionInputBean inputBean = new ViewTransactionInputBean();
    private ViewTransactionService service = new ViewTransactionService();
    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub;

    public SessionUserBean getSub() {
        return (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }

    public String execute() {

        return Action.SUCCESS;
    }

    public String List() {

        try {

            List<ViewTransactionDataBean> dataList = null;

            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;

            long records = 0;
            String orderBy = " ORDER BY TIMESTAMP DESC ";

            if (!inputBean.getSidx().isEmpty()) {
                orderBy = " order by " + inputBean.getSidx() + " " + inputBean.getSord();
            }

            if (this.getSub().getUserType().equals("02")) {
                dataList = service.loadPrivateHistory(inputBean, orderBy, to, from, this.getSub());
            } else {
                dataList = service.loadHistory(inputBean, orderBy, to, from);
            }

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
            addActionError(SystemMessage.TRANSACTION_CHANNELTYPE_EMPTY);
            e.printStackTrace();
            LogFileCreator.writeErrorToLog(e);

        }

        return "list";
    }

    public String Download() throws Exception {
        //System.out.println("here 1");

        try{ 
            if (inputBean.getChanneltype().equals("-1")) {
                //System.out.println("here 2");
                addActionError(SystemMessage.TRANSACTION_CHANNELTYPE_EMPTY);
                return "success";
            } else {
                //System.out.println("here 3");
                inputBean.getParameterMap().put("Txn_From", inputBean.getFromdate());
                inputBean.getParameterMap().put("Txn_To", inputBean.getTodate());
                inputBean.getParameterMap().put("Channel_type", service.getChannelTypeList().get(Integer.parseInt(inputBean.getChanneltype())));

                inputBean.setReportdatalist(service.downloadData(inputBean));
                DBProcesses.insertHistoryRecord(this.getSub().getUsername(),
                        Module.TRANSECTION_MANAGEMENT, PageVarList.TRANSACTION_VIEW_TXN, TaskVarList.VIEW, SystemMessage.TRANSACTION_VIEWTRANS_DOWNLOAD_PDF, request.getRemoteAddr());
            }
        }catch(Exception e){
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
            e.printStackTrace();
            LogFileCreator.writeErrorToLog(e);
        }
            return "txnreport";
        

    }

    public String Cancel() {
        try {
            service.CancelTransaction(inputBean);

            DBProcesses.insertHistoryRecord(this.getSub().getUsername(),
                    Module.TRANSECTION_MANAGEMENT, PageVarList.TRANSACTION_VIEW_TXN, TaskVarList.VIEW, SystemMessage.TRANSACTION_VIEWTRANS_CANCLED, request.getRemoteAddr());

            LogFileCreator.writeInfoToLog(SystemMessage.USR_PROFILE_UPDATED);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "canceled";
    }

    public String ListHistory() {
        String txanctionid = inputBean.getId();
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        session.setAttribute("id", txanctionid);
        return "viewpopup";
    }

    public String ListSmsHistory() {
        String txanctionid = inputBean.getId();
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        session.setAttribute("id", txanctionid);
        return "viewsmspopup";
    }

    @Override
    public ViewTransactionInputBean getModel() {
        try {
            inputBean.setChanneltypeList(service.getChannelTypeList());
            inputBean.setStatusList(service.statusTypeList());
        } catch (Exception ex) {
            Logger.getLogger(ViewTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inputBean;
    }

    @Override
    public boolean checkAccess(String method, int userRole) {

        boolean status = false;
        applyUserPrivileges();
        String page = com.epic.init.PageVarList.TRANSACTION_VIEW_TXN;
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
        } else if ("Cancel".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("BackupAndDownload".equals(method)) {
            task = TaskVarList.DOWNLOAD;
        } else if ("ListHistory".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("ListSmsHistory".equals(method)) {
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

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(com.epic.init.PageVarList.TRANSACTION_VIEW_TXN, request);
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
