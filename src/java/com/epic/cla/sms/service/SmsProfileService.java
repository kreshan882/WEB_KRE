/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cla.sms.service;

import com.epic.cla.sms.bean.SmsBean;
import com.epic.cla.sms.bean.SmsProfileInputBean;
import com.epic.db.DBConnection;
import com.epic.init.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kreshan
 */
public class SmsProfileService {
    
    public List<SmsBean> loadData(SmsProfileInputBean bean, String orderBy, int from, int rows) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        List<SmsBean> dataList = null;
        long totalCount = 0;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sqlCount = "select count(*) AS TOTAL FROM CLA_SMS_TEMPLATE_PROFILE where DESCRIPTION LIKE ?";   
            prepSt = con.prepareStatement(sqlCount);
            prepSt.setString(1, "%" + bean.getProName() + "%");
            res = prepSt.executeQuery();
            if (res.next()) {
                totalCount = res.getLong("TOTAL");
            }

            
            if (prepSt != null)   prepSt.close(); 
            if (res != null)  res.close();        
            
            
            getUsersListQuery = "SELECT CODE,DESCRIPTION,STATUS,DEFAULT_STATUS FROM CLA_SMS_TEMPLATE_PROFILE WHERE DESCRIPTION LIKE ? " + orderBy + " LIMIT " + from + "," + rows;

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1, "%" + bean.getProName() + "%");
            res = prepSt.executeQuery();
 
            dataList = new ArrayList<SmsBean>();

            while (res.next()) {

                SmsBean dataBean = new SmsBean();
                dataBean.setProId(res.getString("CODE"));
                dataBean.setProName(res.getString("DESCRIPTION"));
                dataBean.setStatus(res.getString("STATUS")); 
                dataBean.setDefaultStatus(res.getString("DEFAULT_STATUS"));
                
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
    
      public void findData(SmsProfileInputBean bean) throws Exception {
        
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "select CODE,DESCRIPTION,STATUS from CLA_SMS_TEMPLATE_PROFILE Where CODE=?";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1,bean.getUpproId());
            res = prepSt.executeQuery();
            while (res.next()) {
                bean.setUpproId(res.getString("CODE"));
                bean.setUpproName(res.getString("DESCRIPTION"));
                bean.setUpstatus(res.getString("STATUS"));
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


    public boolean updateData(SmsProfileInputBean inputBean) throws Exception {

        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            sql = "UPDATE CLA_SMS_TEMPLATE_PROFILE SET DESCRIPTION=?,STATUS=?  Where CODE=?";
            prepSt = con.prepareStatement(sql);
            prepSt.setString(1, inputBean.getUpproName());
            prepSt.setInt(2, Integer.parseInt(inputBean.getUpstatus()));
            prepSt.setInt(3, Integer.parseInt(inputBean.getUpproId()));
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
    
    
        
    public boolean deleteData(SmsProfileInputBean bean) throws Exception {

        PreparedStatement prepSt = null;
        Connection con = null;
        String deleteUser = null;
        boolean ok=false;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            deleteUser = "DELETE FROM CLA_SMS_TEMPLATE_PROFILE  WHERE CODE= ?";
            prepSt = con.prepareStatement(deleteUser);
            prepSt.setString(1, bean.getUpproId());            
            int n= prepSt.executeUpdate();
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


    public boolean checkUserName(String username) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        boolean ok=false;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(true);
            String sql = "SELECT * FROM CLA_SMS_TEMPLATE_PROFILE where DESCRIPTION=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
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

    public boolean addData(SmsProfileInputBean inputBean) throws Exception{
        Connection con = null;
        String sql;
        PreparedStatement preStat=null;
        boolean ok=false;
        
        
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sql="INSERT INTO CLA_SMS_TEMPLATE_PROFILE(DESCRIPTION,STATUS)  VALUES(?,?)";
            
            preStat = con.prepareStatement(sql);
            preStat.setString(1, inputBean.getProName());
            preStat.setInt(2,Status.ACTIVE);


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
  
}
