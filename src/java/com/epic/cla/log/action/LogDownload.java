/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.log.action;

import com.epic.cla.log.bean.LogDownloadInputBean;
import com.epic.cla.log.bean.LogFileDataBean;
import com.epic.cla.log.service.LogDownloadService;
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
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author tharaka
 */
public class LogDownload extends ActionSupport implements ModelDriven<LogDownloadInputBean>, AccessControlService {

    LogDownloadService service = new LogDownloadService();
    LogDownloadInputBean inputBean = new LogDownloadInputBean();
    HttpServletRequest request = ServletActionContext.getRequest();
    SessionUserBean sub ;

    public SessionUserBean getSub() {
        return (SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    }

    public String execute() {
        return SUCCESS;
    }

    @Override
    public LogDownloadInputBean getModel() {

        return inputBean;
    }

    public String List() {
        try {
            List<LogFileDataBean> dataList = null;
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

    public String Download() {

        try {

            File fileToDownload = new File(inputBean.getFilePath());
            inputBean.setContentLength(fileToDownload.length());
            inputBean.setInputStream(new FileInputStream(fileToDownload));
            inputBean.setFileName(fileToDownload.getName());

            DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.LOG_MANAGEMENT, PageVarList.LOG_DOWNLOAD, TaskVarList.DOWNLOAD, SystemMessage.LOG_DOWNLOAD_SUCCESS, request.getRemoteAddr());
             LogFileCreator.writeInfoToLog(SystemMessage.LOG_DOWNLOAD_SUCCESS);
        } catch (Exception e) {
            addActionError(SystemMessage.LOG_DOWNLOAD_ERROR);
            e.printStackTrace();
            LogFileCreator.writeErrorToLog(e);
        }

        return "download";
    }

    public String BackupAndDownload() {

        try {
            File fileToDownload = service.generateTodayZip();
            inputBean.setContentLength(fileToDownload.length());
            inputBean.setInputStream(new FileInputStream(fileToDownload));
            inputBean.setFileName(fileToDownload.getName());

            fileToDownload.delete();

            DBProcesses.insertHistoryRecord(getSub().getUsername(), Module.LOG_MANAGEMENT, PageVarList.LOG_DOWNLOAD, TaskVarList.DOWNLOAD, SystemMessage.LOG_DOWNLOAD_SUCCESS, request.getRemoteAddr());
            LogFileCreator.writeInfoToLog(SystemMessage.LOG_DOWNLOAD_SUCCESS);

        } catch (FileNotFoundException fex) {
            LogFileCreator.writeErrorToLog(fex);
            addActionError(SystemMessage.LOG_DOWNLOAD_ERROR);
            fex.printStackTrace();
            return SUCCESS;

        } catch (Exception e) {
            addActionError(SystemMessage.LOG_DOWNLOAD_ERROR);
            e.printStackTrace();
            LogFileCreator.writeErrorToLog(e);
            return SUCCESS;
        }
        return "backupanddownload";
    }

    @Override
    public boolean checkAccess(String method, int userRole) {
        boolean status = false;
        applyUserPrivileges();
        String page = PageVarList.LOG_DOWNLOAD;
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
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.LOG_DOWNLOAD, request);
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
