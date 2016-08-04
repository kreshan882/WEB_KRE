/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.login.service;

import com.epic.db.DBConnection;
import com.epic.login.bean.ConfigurationBean;
import com.epic.login.bean.ModuleBean;
import com.epic.login.bean.PageBean;
import com.epic.login.bean.TaskBean;
import com.epic.login.bean.UserLoginBean;
import com.epic.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kreshan
 */
public class LoginService {

    public boolean getDbUserPassword(UserLoginBean ulb) throws Exception {
        PreparedStatement perSt = null;
        ResultSet res = null;
        Connection con = null;
        boolean isUser = false;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            String sql = "SELECT USERNAME,NAME,PROFILE_ID,STATUS,USER_TYPE,CUSTOMER_ID,PASSWORD FROM CLA_USER WHERE USERNAME=? ";
            perSt = con.prepareStatement(sql);
            perSt.setString(1, ulb.getUserName());
            //perSt.setString(2, Util.generateHash(ulb.getPassword()));
            res = perSt.executeQuery();
            if (res.next()) {
                ulb.setUserName(res.getString("USERNAME"));
                ulb.setProfileId(res.getInt("PROFILE_ID"));
                ulb.setStatus(res.getInt("STATUS"));
                ulb.setName(res.getString("NAME"));
                ulb.setUsertype(res.getString("USER_TYPE"));
                ulb.setCusId(res.getInt("CUSTOMER_ID"));
                ulb.setDbPassword(res.getString("PASSWORD"));
                isUser = true;
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (perSt != null) {
                perSt.close();
            }
            if (res != null) {
                res.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return isUser;

    }

//    public boolean varifilogin(UserLoginBean userloginBean) throws Exception{
//
//        
//       
//        if (Util.generateHash(userloginBean.getPassword()).equals(userloginBean.getDBpassword())) {
//
//            return true;
//        } else {
//            return false;
//        }
//
//    }
    //get REC section page according to the user role
    public Map<ModuleBean, List<PageBean>> getModulePageByUser(int profileId) throws Exception {
        PreparedStatement perSt = null;
        ResultSet res = null;
        Connection con = null;
        Map<ModuleBean, List<PageBean>> modulePageList = null;

        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            perSt = con.prepareStatement("SELECT ISC.SECTION_ID,ISC.SECTION_NAME,ISC.SECTION_URL,ISP.MODULE_ID,ISP.DESCRIPTION,IP.PROFILE_ID "
                    + " FROM CLA_MT_MODULES ISP,CLA_MT_SECTION ISC,CLA_USER_PROFILE_PRIVILAGE IP "
                    + " WHERE ISP.MODULE_ID=IP.MODULE_ID AND ISC.SECTION_ID=IP.SECTION_ID AND IP.PROFILE_ID=? "
                    + " GROUP BY ISC.SECTION_ID,ISC.SECTION_NAME,ISC.SECTION_URL,ISP.MODULE_ID,ISP.DESCRIPTION,IP.PROFILE_ID ORDER BY ISP.MODULE_ID,ISC.SECTION_ID ");

            perSt.setInt(1, profileId);

            res = perSt.executeQuery();
            modulePageList = new HashMap<ModuleBean, List<PageBean>>();
            String currentModule = "";
            List<PageBean> pageList = null;
            ModuleBean module = null;
            while (res.next()) {
                if (!res.getString("MODULE_ID").equals(currentModule)) {
                    currentModule = res.getString("MODULE_ID");
                    if (pageList != null && module != null) {
                        modulePageList.put(module, pageList);
                        pageList = null;
                        module = null;
                    }
                    module = new ModuleBean();
                    module.setMODULE_ID(res.getString("MODULE_ID"));
                    module.setMODULE_NAME(res.getString("DESCRIPTION"));

                    pageList = new ArrayList<PageBean>();
                    PageBean page = new PageBean();
                    page.setPAGE_ID(res.getString("SECTION_ID"));
                    page.setPAGE_NAME(res.getString("SECTION_NAME"));
                    page.setPAGE_URL(res.getString("SECTION_URL"));

                    pageList.add(page);

                } else {

                    PageBean page = new PageBean();
                    page.setPAGE_ID(res.getString("SECTION_ID"));
                    page.setPAGE_NAME(res.getString("SECTION_NAME"));
                    page.setPAGE_URL(res.getString("SECTION_URL"));

                    pageList.add(page);
                }
            }
            if (pageList != null && module != null) {
                modulePageList.put(module, pageList);
                pageList = null;
                module = null;
            }

        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            if (perSt != null) {
                perSt.close();
            }
            if (res != null) {
                res.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return modulePageList;

    }

    public List<String> getUserprofilePageidList(int dBuserProfile) throws SQLException, Exception {

        List<String> pageidlist = new ArrayList<String>();
        PreparedStatement perSt = null;
        ResultSet res = null;
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sql = "select SECTION_ID from CLA_USER_PROFILE_PRIVILAGE where PROFILE_ID=? GROUP BY SECTION_ID";
            perSt = con.prepareStatement(sql);
            perSt.setInt(1, dBuserProfile);
            res = perSt.executeQuery();

            while (res.next()) {
                pageidlist.add(res.getString("SECTION_ID"));
            }

        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            if (perSt != null) {
                perSt.close();
            }
            if (res != null) {
                res.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return pageidlist;

    }

    // please change below method
    public HashMap<String, List<TaskBean>> getAllPageTask(int profileID) throws Exception {
        HashMap<String, List<TaskBean>> pageTaskMap = new HashMap<String, List<TaskBean>>();
        PreparedStatement perSt = null;
        ResultSet res = null;
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sql = "SELECT SECTION_ID,TASK_ID FROM CLA_USER_PROFILE_PRIVILAGE where PROFILE_ID=? ORDER BY SECTION_ID";
            perSt = con.prepareStatement(sql);
            perSt.setInt(1, profileID);
            res = perSt.executeQuery();

            String currentPage = "";
            List<TaskBean> taskBeanList = new ArrayList<TaskBean>();
            TaskBean taskBean;
            while (res.next()) {

                if (res.getString("SECTION_ID").equals(currentPage)) {
                    taskBean = new TaskBean();
                    taskBean.setTASK_ID(res.getString("TASK_ID"));
                    taskBean.setTASK_NAME(res.getString("TASK_ID"));
                    taskBeanList.add(taskBean);
                } else {

                    if (currentPage != "") {
                        pageTaskMap.put(currentPage, taskBeanList);
                        taskBeanList = null;
                        taskBeanList = new ArrayList<TaskBean>();
                    }

                    currentPage = res.getString("SECTION_ID");
                    taskBean = new TaskBean();
                    taskBean.setTASK_ID(res.getString("TASK_ID"));
                    taskBean.setTASK_NAME(res.getString("TASK_ID"));
                    taskBeanList.add(taskBean);
                }

            }

            if (currentPage != "" && taskBeanList != null) {
                pageTaskMap.put(currentPage, taskBeanList);
                taskBeanList = null;
                taskBeanList = new ArrayList<TaskBean>();
                currentPage = "";
            }
        } catch (Exception ex) {
            con.rollback();
            throw ex;
        } finally {
            if (perSt != null) {
                perSt.close();
            }
            if (res != null) {
                res.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return pageTaskMap;
    }

    public boolean checkHaveCustomers(int cusId) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String query = null;
        boolean ok = false;

        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            query = "SELECT * FROM CLA_CUSTOMER Where CUSTOMER_ID=? AND STATUS=1";

            prepSt = con.prepareStatement(query);
            prepSt.setInt(1, cusId);
            res = prepSt.executeQuery();
            if (res.next()) {
                ok = true;
            }
//            while (res.next()) {
//                res.getString("BIN");
//
//            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (prepSt != null) {
                prepSt.close();
            }
            if (res != null) {
                res.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return ok;

    }

}
