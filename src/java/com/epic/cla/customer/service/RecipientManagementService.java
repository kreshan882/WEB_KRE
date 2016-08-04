/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.customer.service;

import com.epic.cla.customer.bean.RecipientManagementBean;
import com.epic.cla.customer.bean.RecipientManagementInputBean;
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
 * @author dimuthu_h
 */
public class RecipientManagementService {
    
    
    public List<RecipientManagementBean> loadData(RecipientManagementInputBean bean, String orderBy, int from, int rows) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getRecipientsListQuery = null;
        List<RecipientManagementBean> dataList = null;
        long totalCount = 0;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sqlCount = "";
                    if(bean.getCid().isEmpty()){
                        sqlCount="select count(*) AS TOTAL FROM CLA_RECIPIENT where NAME LIKE ?";
                        prepSt = con.prepareStatement(sqlCount);
                        prepSt.setString(1, "%" + bean.getReciname() + "%");                    
                    }else{
                        sqlCount="select count(*) AS TOTAL FROM CLA_RECIPIENT where NAME LIKE ? AND CUSTOMER_ID=?";
                        prepSt = con.prepareStatement(sqlCount);
                        prepSt.setString(1, "%" + bean.getReciname() + "%");
                        prepSt.setString(2,  bean.getCid());
                    }
                       
            res = prepSt.executeQuery();
            if (res.next()) {
                totalCount = res.getLong("TOTAL");
            }

            
            if (prepSt != null)   prepSt.close(); 
            if (res != null)  res.close();        
            if(bean.getCid().isEmpty()){
                        getRecipientsListQuery = "SELECT CUSTOMER_ID,NAME,MOBILE_NO,NIC,ADDRESS,STATUS,REG_DATE_TIME "             
                    + " FROM CLA_RECIPIENT where NAME LIKE ? "
                    + orderBy + " LIMIT " + from + "," + rows;

                    prepSt = con.prepareStatement(getRecipientsListQuery);
                    prepSt.setString(1, "%" + bean.getReciname() + "%");                   
                    }else{
                        
                        getRecipientsListQuery = "SELECT CUSTOMER_ID,NAME,MOBILE_NO,NIC,ADDRESS,STATUS,REG_DATE_TIME "             
                    + " FROM CLA_RECIPIENT where NAME LIKE ? AND CUSTOMER_ID=?"
                    + orderBy + " LIMIT " + from + "," + rows;

                        prepSt = con.prepareStatement(getRecipientsListQuery);
                         prepSt.setString(1, "%" + bean.getReciname() + "%");
                         prepSt.setString(2,bean.getCid());
                    }
            
            
            res = prepSt.executeQuery();
 
            dataList = new ArrayList<RecipientManagementBean>();

            while (res.next()) {

                RecipientManagementBean dataBean = new RecipientManagementBean();
                dataBean.setCid(""+res.getInt("CUSTOMER_ID"));
                dataBean.setCname(getCustomerList().get(res.getInt("CUSTOMER_ID")));
                dataBean.setName(res.getString("NAME"));
                dataBean.setMobile_no(res.getString("MOBILE_NO"));
                dataBean.setNic(res.getString("NIC"));
                
                dataBean.setAddress(res.getString("ADDRESS"));
                dataBean.setStatus(res.getString("STATUS")); 
                
                dataBean.setRegDate(res.getString("REG_DATE_TIME"));
                
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
    

    public boolean addData(RecipientManagementInputBean inputBean) throws Exception{
        Connection con = null;
        String sql;
        PreparedStatement preStat=null;
        boolean ok=false;
        
        
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sql="INSERT INTO CLA_RECIPIENT(CUSTOMER_ID, NAME,NIC,ADDRESS,MOBILE_NO,"
                    + "STATUS,REG_DATE_TIME) VALUES(?,?,?,?,?,?,?)";
                 
            
            preStat = con.prepareStatement(sql);
                                
            
            preStat.setInt(1, Integer.parseInt(inputBean.getCid()));
            preStat.setString(2, inputBean.getName());
            preStat.setString(3, inputBean.getNic());
            preStat.setString(4, inputBean.getAddress());
            preStat.setString(5, inputBean.getMobile_no());
            
            preStat.setInt(6,Status.ACTIVE);
            preStat.setDate(7, (Date) Util.getLocalDate());

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
    
    
     public static Map<Integer, String> getCustomerList() throws Exception {
        Map<Integer, String> customersList = new HashMap<Integer, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sql = "SELECT CUSTOMER_ID,NAME FROM CLA_CUSTOMER";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                customersList.put(rs.getInt("CUSTOMER_ID"), rs.getString("NAME"));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (ps != null)   ps.close(); 
            if (rs != null)  rs.close(); 
            if (con != null) con.close(); 

        }
        
        return customersList;
    }
     public void findData(RecipientManagementInputBean bean) throws Exception {
        
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "SELECT CUSTOMER_ID,NAME,STATUS,ADDRESS,MOBILE_NO,NIC "
                    + " FROM CLA_RECIPIENT WHERE CUSTOMER_ID=? AND NIC=?";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1,bean.getCid());
            prepSt.setString(2,bean.getNic());
            res = prepSt.executeQuery();
            while (res.next()) {
                bean.setUpcid(res.getString("CUSTOMER_ID"));
                bean.setUpcname(getCustomerList().get(Integer.parseInt(bean.getUpcid())));
                bean.setUpname(res.getString("NAME"));
                bean.setUpstatus(res.getString("STATUS"));
                bean.setUpaddress(res.getString("ADDRESS"));
                bean.setUpmobile(res.getString("MOBILE_NO"));
                bean.setUpnic(res.getString("NIC"));  
               
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
     
     public boolean updateData(RecipientManagementInputBean inputBean) throws Exception {

        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sql = "UPDATE CLA_RECIPIENT SET NAME=?,STATUS=?,ADDRESS=?,MOBILE_NO=?,NIC=?"
                    + " Where (NIC =? AND CUSTOMER_ID =?)";
            prepSt = con.prepareStatement(sql);
            prepSt.setString(1, inputBean.getUpname());
            prepSt.setInt(2, Integer.parseInt(inputBean.getUpstatus()));
            prepSt.setString(3, inputBean.getUpaddress());
            prepSt.setString(4, inputBean.getUpmobile());
            prepSt.setString(5, inputBean.getUpnic());

            prepSt.setString(6,inputBean.getUpnic());
            prepSt.setString(7,inputBean.getUpcid());

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
    
     
     public boolean deleteData(RecipientManagementInputBean bean) throws Exception {

        PreparedStatement prepSt = null;
        Connection con = null;
        String deleteRecipient = null;
        boolean ok=false;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            deleteRecipient = "DELETE FROM CLA_RECIPIENT  WHERE NIC= ? AND CUSTOMER_ID=?";
            prepSt = con.prepareStatement(deleteRecipient);
            prepSt.setString(1, bean.getNic());
            prepSt.setString(2, bean.getCid());
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
    
}
