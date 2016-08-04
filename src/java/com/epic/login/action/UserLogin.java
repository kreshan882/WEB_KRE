/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.login.action;

import com.epic.init.Module;
import com.epic.init.Status;
import com.epic.util.LogFileCreator;
import com.epic.login.bean.HomeValues;
import com.epic.login.bean.ModuleBean;
import com.epic.login.bean.PageBean;
import com.epic.login.bean.SessionUserBean;
import com.epic.login.bean.TaskBean;
import com.epic.login.bean.UserLoginBean;
import com.epic.login.service.LoginService;
import com.epic.db.DBProcesses;
import com.epic.init.InitConfigValue;
import com.epic.init.UserType;
import com.epic.util.SessionVarlist;
import com.epic.util.SystemMessage;
import com.epic.util.Util;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author kreshan
 */
public class UserLogin extends ActionSupport implements Action, ModelDriven<UserLoginBean> {

    UserLoginBean inputBean = new UserLoginBean();
    LoginService service = new LoginService();
    HomeValues homeValues = new HomeValues();
    SessionUserBean sub = new SessionUserBean();

    HttpServletRequest request = ServletActionContext.getRequest();
    List<String> profilePageidList = new ArrayList<String>();

    public String execute() {
        return SUCCESS;
    }

    public String loginCheck() {

        try {
            if (service.getDbUserPassword(inputBean)) {
                if (inputBean.getStatus() == Status.ACTIVE) {
                    if (inputBean.getUsertype().equals(UserType.MERCHANT)) {//Merchant User=02 ,Bank User=01
                        
                        if(Util.generateHash(inputBean.getPassword()).equals(inputBean.getDbPassword())){
                            
                            if (service.checkHaveCustomers(inputBean.getCusId())) {
                                sub.setUsername(inputBean.getUserName());
                                sub.setUserProfileId(inputBean.getProfileId());
                                sub.setLogFilePath(InitConfigValue.LOGPATH);
                                sub.setName(inputBean.getName());
                                sub.setUserType(inputBean.getUsertype());
                                sub.setCusId(inputBean.getCusId());

                                HttpSession sessionPrevious = ServletActionContext.getRequest().getSession(false);
                                if (sessionPrevious != null) {
                                    sessionPrevious.invalidate();
                                }

                                HttpSession session = ServletActionContext.getRequest().getSession(true);
                                sub.setCurrentSessionId(session.getId());
                                session.setAttribute("SessionObject", sub);

                                //set user and sessionid to hashmap              
                                ServletContext sc = ServletActionContext.getServletContext();
                                HashMap<String, String> userMap = (HashMap<String, String>) sc.getAttribute(SessionVarlist.USERMAP);
                                if (userMap == null) {
                                    userMap = new HashMap<String, String>();
                                }
                                userMap.put(sub.getUsername(), session.getId());
                                sc.setAttribute(SessionVarlist.USERMAP, userMap);

                                DBProcesses.insertHistoryRecord(sub.getUsername(), Module.LOGIN_MANAGEMENT, null, null, SystemMessage.LOGIN_MSG + " User:" + inputBean.getUserName(), request.getRemoteAddr());

                                LogFileCreator.writeInfoToLog(SystemMessage.LOGIN_MSG + " User:" + inputBean.getUserName());

                                profilePageidList = service.getUserprofilePageidList(inputBean.getProfileId());
                                session.setAttribute("profilePageidList", profilePageidList);

                                HashMap<String, List<TaskBean>> pageTaskList = service.getAllPageTask(inputBean.getProfileId());
                                session.setAttribute("pageTaskList", pageTaskList);

                                Map<ModuleBean, List<PageBean>> modulePageList = service.getModulePageByUser(inputBean.getProfileId());
                                session.setAttribute("modulePageList", modulePageList);

                                return "success";
                            } else {
                                addActionError("You haven't register as a customer");
                                return "login";
                            }
                        }else{
                            addActionError(SystemMessage.LOGIN_INVALID_PW); //merchant paeeword wrong
                            return "login";
                        }
                    } else if(inputBean.getUsertype().equals(UserType.BANK)){
                        
                                sub.setUsername(inputBean.getUserName());
                                sub.setUserProfileId(inputBean.getProfileId());
                                sub.setLogFilePath(InitConfigValue.LOGPATH);
                                sub.setName(inputBean.getName());
                                sub.setUserType(inputBean.getUsertype());
                                sub.setCusId(inputBean.getCusId());

                                HttpSession sessionPrevious = ServletActionContext.getRequest().getSession(false);
                                if (sessionPrevious != null) {
                                    sessionPrevious.invalidate();
                                }

                                HttpSession session = ServletActionContext.getRequest().getSession(true);
                                sub.setCurrentSessionId(session.getId());
                                session.setAttribute("SessionObject", sub);

                                //set user and sessionid to hashmap              
                                ServletContext sc = ServletActionContext.getServletContext();
                                HashMap<String, String> userMap = (HashMap<String, String>) sc.getAttribute(SessionVarlist.USERMAP);
                                if (userMap == null) {
                                    userMap = new HashMap<String, String>();
                                }
                                userMap.put(sub.getUsername(), session.getId());
                                sc.setAttribute(SessionVarlist.USERMAP, userMap);

                                DBProcesses.insertHistoryRecord(sub.getUsername(), Module.LOGIN_MANAGEMENT, null, null, SystemMessage.LOGIN_MSG + " User:" + inputBean.getUserName(), request.getRemoteAddr());

                                LogFileCreator.writeInfoToLog(SystemMessage.LOGIN_MSG + " User:" + inputBean.getUserName());

                                profilePageidList = service.getUserprofilePageidList(inputBean.getProfileId());
                                session.setAttribute("profilePageidList", profilePageidList);

                                HashMap<String, List<TaskBean>> pageTaskList = service.getAllPageTask(inputBean.getProfileId());
                                session.setAttribute("pageTaskList", pageTaskList);

                                Map<ModuleBean, List<PageBean>> modulePageList = service.getModulePageByUser(inputBean.getProfileId());
                                session.setAttribute("modulePageList", modulePageList);
                                    if(InitConfigValue.AD_STATUS==Status.ACTIVE){
                                        if(Util.userADValidation(inputBean.getUserName(), inputBean.getPassword())){
                                            return "success";
                                        }else{
                                            addActionError(SystemMessage.LOGIN_INVALID_BANKUSER); 
                                            return "login";
                                        }    
                                    }else{
                                        if(Util.generateHash(inputBean.getPassword()).equals(inputBean.getDbPassword())){
                                            return "success";
                                        }else{
                                            addActionError(SystemMessage.LOGIN_INVALID_PW);
                                            return "login";
                                        }
                                    }
                                
                            }

                } else {
                    addActionError("User inactive");
                    return "login";
                }

            } else {
                addActionError(SystemMessage.LOGIN_INVALID);//unvalid user only
                return "login";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            addActionError("Error contact administrator");
            LogFileCreator.writeErrorToLog(ex);
        }
        return null;

    }

    public String homeFunction() {
        return SUCCESS;

    }

    @Override
    public UserLoginBean getModel() {
        return inputBean;
    }

    public  String Logout() {
        try {
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            if (session != null) {

                if (inputBean.getMessage() != null && !inputBean.getMessage().isEmpty()) {
                    String message = inputBean.getMessage();
                    if (message.equals("error1")) {
                        addActionError("Access denied. Please login again.");
                    } else if (message.equals("error2")) {
                        addActionError("You have been logged another mechine.");
                    } else if (message.equals("error3")) {
                        addActionMessage("Your password changed successfully. Please login with the new password.");
                    }
                }

                SessionUserBean su = (SessionUserBean) session.getAttribute("SessionObject");
                if (su != null) {
                    DBProcesses.insertHistoryRecord(su.getUsername(), Module.LOGIN_MANAGEMENT, null, null, SystemMessage.LOGOUT_MSG, request.getRemoteAddr());
                } else {
                    addActionError("Session timeout.");
                }

                session.removeAttribute("SessionObject");
                session.removeAttribute("SessionHomeValues");
                session.removeAttribute("profilePageidList");
                session.removeAttribute("modulePageList");
                session.invalidate();
                session = null;
            } else {
                addActionError("Session timeout");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return LOGIN;
    }

}
