/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.user.action;

import com.epic.init.Module;
import com.epic.util.LogFileCreator;
import com.epic.login.bean.SessionUserBean;
import com.epic.cla.user.bean.ChangePasswordBean;
import com.epic.cla.user.service.ChangePasswordService;
import com.epic.db.DBProcesses;
import com.epic.init.PageVarList;
import com.epic.init.TaskVarList;
import com.epic.login.action.UserLogin;
import com.epic.util.PasswordValidator;
import com.epic.util.SystemMessage;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author kreshan
 */
public class ChangePassword extends ActionSupport implements ModelDriven<ChangePasswordBean>{
    ChangePasswordBean changePasswordBean= new ChangePasswordBean();
    ChangePasswordService cps= new ChangePasswordService();
    HttpServletRequest request = ServletActionContext.getRequest();
    PasswordValidator validator = new PasswordValidator();
    
    
    public String execute() {
        return SUCCESS;
    }
       
     public String changePwFunction(){ 
         String msg=null;
             
            HttpSession session = ServletActionContext.getRequest().getSession(false);
            SessionUserBean sub = (SessionUserBean)session.getAttribute("SessionObject");
            changePasswordBean.setUsername(sub.getUsername());
            try{
            
             cps.getOldPasswordFromDb(changePasswordBean);
           if(!(changePasswordBean.getPasswordOld() == null || changePasswordBean.getPasswordOld().isEmpty())){
            
               if(cps.checkPasswordMatchWithUserPass(changePasswordBean)){
                if(!changePasswordBean.getPasswordOld().equals(changePasswordBean.getPasswordNew1())){
                if(cps.checkNewPasswordsMatch(changePasswordBean)){
                   
                   
                        msg = validator.validatePassword(changePasswordBean.getPasswordNew1());
                    
                    
                    if(msg.equals("Successful")){

                            cps.updatePassword(changePasswordBean);
                            DBProcesses.insertHistoryRecord(sub.getUsername(),
                            Module.USER_MANAGEMENT,PageVarList.USER_MANAGEMENT,TaskVarList.PWRESET,SystemMessage.USR_PW_CHG,request.getRemoteAddr());
                            addActionMessage(SystemMessage.USR_PW_UPDATE);
                            LogFileCreator.writeInfoToLog(SystemMessage.USR_PW_UPDATE);

                            return "pwchangedloginAgain";
                    }else{
                        addActionError(msg);
                    }
                    
                 }else{
                     addActionError(SystemMessage.USR_PW_NOT_MAT);
                 }
                }else{
                    addActionError(SystemMessage.USR_PW_CNT_EQUAL_NWEPW);
                }
             }else{
                 addActionError(SystemMessage.USR_PW_WORNG);
             }
            }else{
                 addActionError(SystemMessage.USR_PW_WORNG_OLD);
             }
            }catch(Exception ex){
                ex.printStackTrace();
                LogFileCreator.writeErrorToLog(ex);
            }
            
        return "message";
    }

    @Override
    public ChangePasswordBean getModel() {
        return changePasswordBean;
    }
    
 
}
