/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cla.user.service;

import com.epic.cla.user.bean.UsrProfileBean;
import com.epic.cla.user.bean.UsrProfileManagementInputBean;
import com.epic.db.DBConnection;
import com.epic.init.Status;
import com.epic.util.Util;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kreshan
 */
public class UsrProfileManagementService {
    
    
    
     public List<UsrProfileBean> loadData(UsrProfileManagementInputBean bean, String orderBy, int from, int rows) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        List<UsrProfileBean> dataList = null;
        long totalCount = 0;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sqlCount = "select count(*) AS TOTAL FROM CLA_USER_PROFILE where DESCRIPTION LIKE ?";   
            prepSt = con.prepareStatement(sqlCount);
            prepSt.setString(1, "%" + bean.getProfilename() + "%");
            res = prepSt.executeQuery();
            if (res.next()) {
                totalCount = res.getLong("TOTAL");
            }

            
            if (prepSt != null)   prepSt.close(); 
            if (res != null)  res.close();        
            
            
            getUsersListQuery = "select PROFILE_ID,DESCRIPTION,STATUS,CREATE_DATE from CLA_USER_PROFILE WHERE DESCRIPTION LIKE ? " + orderBy + " LIMIT " + from + "," + rows;

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1, "%" + bean.getProfilename() + "%");
            res = prepSt.executeQuery();
 
            dataList = new ArrayList<UsrProfileBean>();

            while (res.next()) {

                UsrProfileBean dataBean = new UsrProfileBean();
                dataBean.setProfileId(res.getString("PROFILE_ID"));
                dataBean.setProfileName(res.getString("DESCRIPTION"));
                dataBean.setStatus(res.getString("STATUS"));
                dataBean.setRegDate(res.getString("CREATE_DATE"));
                
                dataBean.setFullCount(totalCount);
                dataList.add(dataBean);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (prepSt != null)   prepSt.close(); 
            if (res != null)  res.close(); 
            if (con != null) con.close(); 

        }
        return dataList;
    }
    
      public void findData(UsrProfileManagementInputBean bean) throws Exception {
        
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "select PROFILE_ID,DESCRIPTION,STATUS "
                    + "from CLA_USER_PROFILE WHERE PROFILE_ID=? ";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1,bean.getUpprofileId());
            res = prepSt.executeQuery();
            while (res.next()) {
                bean.setUpprofileId(res.getString("PROFILE_ID"));
                bean.setUpprofilename(res.getString("DESCRIPTION"));
                bean.setUpestatus(res.getString("STATUS"));

            }
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

    }


    public boolean updateTaskData(UsrProfileManagementInputBean inputBean) throws Exception {

        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            sql = " delete  from CLA_USER_PROFILE_PRIVILAGE where PROFILE_ID=? AND SECTION_ID=?";
            prepSt = con.prepareStatement(sql);
            prepSt.setInt(1, Integer.parseInt(inputBean.getUpprofileId()));
            prepSt.setString(2, inputBean.getUppageId());
            int n= prepSt.executeUpdate();
            if(n>0){
                ok = true;
            }
            
            if (prepSt != null)     prepSt.close();
            
            for(int i=0;i<inputBean.getNewBox().size();i++){
                sql = " insert into CLA_USER_PROFILE_PRIVILAGE(PROFILE_ID,MODULE_ID,SECTION_ID,TASK_ID)"
                        + " values(?,?,?,?)";
                prepSt = con.prepareStatement(sql);
                prepSt.setInt(1, Integer.parseInt(inputBean.getUpprofileId()));
                prepSt.setString(2, inputBean.getUppageId().substring(0, 2));
                prepSt.setString(3, inputBean.getUppageId());
                prepSt.setString(4, inputBean.getNewBox().get(i));
                n= prepSt.executeUpdate();
            }
            if(n>0){
                ok = true;
            }
            
            

        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            if (prepSt != null)     prepSt.close();
            if (res != null)        res.close();
            if (con != null)        con.close();
        }
           return ok;
    }
    
    
        
    public boolean deleteData(UsrProfileManagementInputBean bean) throws Exception {
        PreparedStatement prepSt = null;
        Connection con = null;
        String deleteUser = null;
        boolean ok=false;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            deleteUser = "DELETE from CLA_USER_PROFILE_PRIVILAGE where PROFILE_ID=?";
            prepSt = con.prepareStatement(deleteUser);
            prepSt.setString(1, bean.getUpprofileId());            
            int n= prepSt.executeUpdate();
            if(n>0){
                ok = true;
            }
            if (prepSt != null)  prepSt.close();
            
            deleteUser = "DELETE from CLA_USER_PROFILE WHERE PROFILE_ID=?";
            prepSt = con.prepareStatement(deleteUser);
            prepSt.setString(1, bean.getUpprofileId());            
            n= prepSt.executeUpdate();
            if(n>0){
                ok = true;
            }
            
        } catch (Exception e) {
            con.rollback();
            ok=false;
            throw e;
        } finally {
            if (prepSt != null)  prepSt.close();
            if (con != null)   con.close();
        }
        return ok;
    }

    public Map<String,String> getModuleList() throws Exception {
        
        Map<String,String> modulelist=new HashMap<String,String>();
        
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "select MODULE_ID,DESCRIPTION from CLA_MT_MODULES";

            prepSt = con.prepareStatement(getUsersListQuery);
            res = prepSt.executeQuery();
            while (res.next()) {
                modulelist.put(res.getString("MODULE_ID"), res.getString("DESCRIPTION"));
            }
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
        return modulelist;
    }
    
    
     public Map<String,String> getPageList(String modulId) throws Exception {
        
        Map<String,String> modulelist=new HashMap<String,String>();
        
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "select SECTION_ID,SECTION_NAME from CLA_MT_SECTION WHERE MODULE_ID=?";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1, modulId);
            res = prepSt.executeQuery();
            while (res.next()) {
                modulelist.put(res.getString("SECTION_ID"), res.getString("SECTION_NAME"));
            }
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
        return modulelist;
    }
     public Map<String,String> getTaskList(String pageId) throws Exception {
        
        Map<String,String> modulelist=new HashMap<String,String>();
        
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "select st.TASK_ID AS TASK_ID,ta.DESCRIPTION TASK_NAME  "
                    + "from CLA_MT_SECTION_TASK st,CLA_MT_TASKS ta  WHERE st.TASK_ID=ta.TASK_ID AND  st.SECTION_ID=?";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1, pageId);
            res = prepSt.executeQuery();
            while (res.next()) {
                modulelist.put(res.getString("TASK_ID"), res.getString("TASK_NAME"));
            }
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
        return modulelist;
    }
     
     
    public boolean addData(UsrProfileManagementInputBean inputBean) throws Exception{
        Connection con = null;
        String sql;
        PreparedStatement preStat=null;
        boolean ok=false;
        
        
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sql="INSERT INTO CLA_USER_PROFILE (DESCRIPTION,STATUS,CREATE_DATE) "
                    + " VALUES(?,?,?)";
            
            preStat = con.prepareStatement(sql);
            preStat.setString(1, inputBean.getProfilename());
            preStat.setInt(2,Status.ACTIVE);
            preStat.setDate(3, (Date) Util.getLocalDate());

           int n= preStat.executeUpdate();
           if(n >= 0){
               ok=true;
           }
           
            
        } catch (Exception e) {
            throw  e;
        }finally{
            if(preStat != null)  preStat.close();
            if(con != null)   DBConnection.dbConnectionClose(con);   
        }
        return ok;
    }

    public void getTaskListsLoad(UsrProfileManagementInputBean inputBean) throws Exception {
        
//        Map<String,String> modulelist=new HashMap<String,String>();
        
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "select st.TASK_ID AS TASK_ID,ta.DESCRIPTION TASK_NAME  "
                    + "from CLA_MT_SECTION_TASK st,CLA_MT_TASKS ta  WHERE st.TASK_ID=ta.TASK_ID AND  st.SECTION_ID=?";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1, inputBean.getUppageId());
            res = prepSt.executeQuery();
            while (res.next()) {
                inputBean.getTaskList().put(res.getString("TASK_ID"), res.getString("TASK_NAME"));
            }
            
            if (prepSt != null)  prepSt.close();
            if (res != null)   res.close();
            if (con != null)   con.close();
            
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = " select pp.TASK_ID AS TASK_ID ,ts.DESCRIPTION AS TASK_NAME "
                    + " from CLA_USER_PROFILE_PRIVILAGE pp, CLA_MT_TASKS ts "
                    + " WHERE pp.TASK_ID=ts.TASK_ID AND  pp.PROFILE_ID=? AND pp.SECTION_ID=?";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setInt(1, Integer.parseInt(inputBean.getUpprofileId()));
            prepSt.setString(2, inputBean.getUppageId());
            res = prepSt.executeQuery();
            while (res.next()) { 
                inputBean.getSelectedtaskList().put(res.getString("TASK_ID"), res.getString("TASK_NAME"));
                inputBean.getTaskList().remove(res.getString("TASK_ID"));
            }
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
    }

    public boolean profilenameAlready(String profilename) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        boolean ok=false;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(true);
            String sql = "SELECT * FROM CLA_USER_PROFILE where DESCRIPTION=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, profilename);
            result = ps.executeQuery();

            if (result.next()) {
                ok = true;
            }
           
        }
        catch (Exception ex) {
            throw ex;
	}finally {
            if (ps != null) {
                ps.close();
            }
            if (result != null) {
                result.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return  ok;
    }

    public boolean updateData(UsrProfileManagementInputBean inputBean) throws Exception {

        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            sql = " update CLA_USER_PROFILE SET STATUS=? where PROFILE_ID=? ";
            prepSt = con.prepareStatement(sql);
            prepSt.setInt(1, Integer.parseInt(inputBean.getUpestatus()));
            prepSt.setInt(2, Integer.parseInt(inputBean.getUpeprofileId()));
            int n= prepSt.executeUpdate();
            if(n>0){
                ok = true;
            }
            
            

        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            if (prepSt != null)     prepSt.close();
            if (res != null)        res.close();
            if (con != null)        con.close();
        }
           return ok;
    }
}
