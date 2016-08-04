/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.systemAlert.action;

import com.epic.cla.systemAlert.bean.EsMonitorBean;
import com.epic.cla.systemAlert.bean.LcMonitorBean;
import com.epic.cla.systemAlert.bean.MonitorInputBean;
import com.epic.cla.systemAlert.service.EsMonitorService;
import com.epic.cla.systemAlert.service.LcMonitorService;
import com.epic.init.PageVarList;
import com.epic.init.TaskVarList;
import com.epic.login.bean.SessionUserBean;
import com.epic.login.bean.TaskBean;
import com.epic.util.AccessControlService;
import com.epic.util.Common;
import com.epic.util.EswitchEchoTest;
import com.epic.util.LogFileCreator;
import com.epic.util.SystemMessage;
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
public class ViewCurrentStatus extends ActionSupport implements AccessControlService, ModelDriven<MonitorInputBean> {

    SessionUserBean sub;
    LcMonitorService lcService = new LcMonitorService();
    EsMonitorService esService = new EsMonitorService();
    MonitorInputBean lcInputBean = new MonitorInputBean();
    HttpServletRequest request = ServletActionContext.getRequest();

    public SessionUserBean getSub() {
        return (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String LcList() {
        try {

            List<LcMonitorBean> dataList = null;
            int rows = lcInputBean.getRows();
            int page = lcInputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            if (!lcInputBean.getSidx().isEmpty()) {
                orderBy = " order by " + lcInputBean.getSidx() + " " + lcInputBean.getSord();
            }

            dataList = lcService.loadData(lcInputBean, orderBy, from, rows);

            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                lcInputBean.setRecords(records);
                lcInputBean.setGridModel(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                lcInputBean.setTotal(total);
            } else {
                lcInputBean.setRecords(0L);
                lcInputBean.setTotal(0);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
        }
        return "list";

    }

    public String EsList() {
        try {
            EswitchEchoTest.checkEcho();
            List<EsMonitorBean> dataList = null;
            int rows = lcInputBean.getRows();
            int page = lcInputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = "";

            if (!lcInputBean.getSidx().isEmpty()) {
                orderBy = " order by " + lcInputBean.getSidx() + " " + lcInputBean.getSord();
            }

            dataList = esService.loadData(lcInputBean, orderBy, from, rows);

            if (!dataList.isEmpty()) {
                records = dataList.get(0).getFullCount();
                lcInputBean.setRecords(records);
                lcInputBean.setGridModel2(dataList);
                int total = (int) Math.ceil((double) records / (double) rows);
                lcInputBean.setTotal(total);
            } else {
                lcInputBean.setRecords(0L);
                lcInputBean.setTotal(0);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
            addActionError(SystemMessage.COMMON_ERROR_PROCESS);
        }
        return "list";

    }

//    public String CheckESStatus() throws Exception {
//        System.out.println("ES Status checking.................");
//        try {
////            new Thread(new EswitchEchoTest()).start();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return "esstatus";
//    }
    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.MONITOR_CURRENT_STATUS, request);
        lcInputBean.setVadd(true);
        lcInputBean.setVupdate(true);
        lcInputBean.setVdelete(true);
        lcInputBean.setVdownload(true);
        lcInputBean.setVresetpass(true);
        if (tasklist != null && tasklist.size() > 0) {
            for (TaskBean task : tasklist) {
                if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.ADD)) {
                    lcInputBean.setVadd(false);
                } else if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.UPDATE)) {
                    lcInputBean.setVupdate(false);
                } else if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.DELETE)) {
                    lcInputBean.setVdelete(false);
                } else if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.DOWNLOAD)) {
                    lcInputBean.setVdownload(false);
                } else if (task.getTASK_ID().toString().equalsIgnoreCase(TaskVarList.PWRESET)) {
                    lcInputBean.setVresetpass(false);
                }
            }
        }

        return true;

    }

    @Override
    public boolean checkAccess(String method, int userRole) {

        boolean status = false;
        applyUserPrivileges();
        String page = PageVarList.MONITOR_CURRENT_STATUS;
        String task = null;
//        System.out.println("invoke method "+ method);
        if ("View".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("LcList".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("Add".equals(method)) {
            task = TaskVarList.ADD;
        } else if ("Find".equals(method)) {
            task = TaskVarList.UPDATE;
        } else if ("Update".equals(method)) {
            task = TaskVarList.UPDATE;
        } else if ("Delete".equals(method)) {
            task = TaskVarList.DELETE;
        } else if ("CheckESStatus".equals(method)) {
            task = TaskVarList.VIEW;
        } else if ("ResetPw".equals(method)) {
            task = TaskVarList.PWRESET;
        } else if ("EsList".equals(method)) {
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

    @Override
    public MonitorInputBean getModel() {
        return lcInputBean;
    }
}
