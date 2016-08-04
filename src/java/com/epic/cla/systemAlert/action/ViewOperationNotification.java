    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.systemAlert.action;

import com.epic.cla.systemAlert.bean.ViewOperationNotificationBean;
import com.epic.cla.systemAlert.bean.ViewOperationNotificationInputBean;
import com.epic.cla.systemAlert.service.ViewOperationNotificationService;
import com.epic.init.PageVarList;
import com.epic.init.TaskVarList;
import com.epic.login.bean.SessionUserBean;
import com.epic.login.bean.TaskBean;
import com.epic.util.AccessControlService;
import com.epic.util.Common;
import com.epic.util.LogFileCreator;
import com.epic.util.OperationListenerMonitor;
import com.epic.util.SystemMessage;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author dimuthu_h
 */
public class ViewOperationNotification extends ActionSupport implements ModelDriven<ViewOperationNotificationInputBean>, AccessControlService {

    private ViewOperationNotificationService service = new ViewOperationNotificationService();
    ViewOperationNotificationInputBean inputBean = new ViewOperationNotificationInputBean();
    HttpServletRequest request = ServletActionContext.getRequest();
    
    SessionUserBean sub=(SessionUserBean) ServletActionContext.getRequest().getSession(false).getAttribute("SessionObject");
    

    
    public String List() {
        try {
            List<ViewOperationNotificationBean> dataList = null;
            int rows = inputBean.getRows();
            int page = inputBean.getPage();
            int to = (rows * page);
            int from = to - rows;
            long records = 0;
            String orderBy = " ORDER BY TIME_STAMP DESC ";

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
        
        String retMsg = null;
        try {
            ByteArrayOutputStream outputStream = null;
            Object object = ExcelReportOperationNotification.generateExcelReport(inputBean);

            if (object instanceof XSSFWorkbook) {
                XSSFWorkbook workbook = (XSSFWorkbook) object;
                outputStream = new ByteArrayOutputStream();
                workbook.write(outputStream);
                inputBean.setExcelStream(new ByteArrayInputStream(outputStream.toByteArray()));
                retMsg = "downloadxl";
            } else if (object instanceof ByteArrayOutputStream) {
                outputStream = (ByteArrayOutputStream) object;
                inputBean.setZipStream(new ByteArrayInputStream(outputStream.toByteArray()));
                retMsg = "zip";
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogFileCreator.writeErrorToLog(e);
        }
        return retMsg;
    }

     @Override
    public ViewOperationNotificationInputBean getModel() {
        try {
            inputBean.setOperationList(service.getOperationList());
        } catch (Exception ex) {
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);
        }
        return inputBean;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
    
    

    private boolean applyUserPrivileges() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<TaskBean> tasklist = new Common().getUserTaskListByPage(PageVarList.VIEW_OPERATION_NOTIFICATION, request);
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
    
    
        public String Send() {
        try {
            //operation code/user id/client ip
            String in = inputBean.getOperation() + "|" + sub.getUsername() + "|" + request.getRemoteAddr();
            String ret = OperationListenerMonitor.ReqAndResponse(in.trim());
            System.out.println("::"+ret);
            if(ret.equals("Error")){
                inputBean.setSuccess(false);
                inputBean.setMessage("operation fail");
            }
            inputBean.setSuccess(true);
            inputBean.setMessage("operation successfull");
            
        } catch (Exception e) {
            inputBean.setSuccess(false);
            inputBean.setMessage("operation fail");
            e.printStackTrace();
            LogFileCreator.writeErrorToLog(e);
        }
        return "send";
    }

    @Override
    public boolean checkAccess(String method, int userRole) {
        boolean status = false;
        applyUserPrivileges();
        String page = PageVarList.VIEW_OPERATION_NOTIFICATION;
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
        }else if ("Send".equals(method)) {
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
