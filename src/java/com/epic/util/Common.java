/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.util;

import com.epic.login.bean.TaskBean;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kreshan
 */
public class Common {
    
      //checks the accees to the method name passed
    public boolean checkMethodAccess( String task, String page, HttpSession sessionk){
        boolean access = false;
        try{
            HttpSession session = sessionk;
            List<String> profilePageidList = (List<String>) session.getAttribute("profilePageidList");
             HashMap<String, List<TaskBean>> pageTaskList =(HashMap<String, List<TaskBean>>) session.getAttribute("pageTaskList");
               
                if(profilePageidList.contains(page)){
                    if(this.checkTaskAvaliable(pageTaskList.get(page), task)){
                        access=true;
                    }else{
                        access=false;
                    }
                    
                }else{
                    access=false;
                }
        }catch(Exception e){
            e.printStackTrace();
            LogFileCreator.writeErrorToLog(e);
        }      
        return access;
    }

    private static boolean checkTaskAvaliable(List<TaskBean> taskList, String task) {
        
       for(int i=0;i<taskList.size();i++){
           if(taskList.get(i).getTASK_ID().equals(task)){
               return true;
           }
       }
       return false;
    }

    
     //returns allowed task list of current user
    public List<TaskBean> getUserTaskListByPage(String page, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        HashMap<String, List<TaskBean>> pageMap = (HashMap<String, List<TaskBean>>) session.getAttribute("pageTaskList");
        
        Iterator itr = pageMap.keySet().iterator();
        
        while(itr.hasNext()){
            itr.next();
        }
        
        
        List<TaskBean> taskList = pageMap.get(page);
        return taskList;
    }
}
