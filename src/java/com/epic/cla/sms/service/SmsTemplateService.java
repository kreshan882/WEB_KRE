/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cla.sms.service;

import com.epic.cla.sms.bean.SmsBean;
import com.epic.cla.sms.bean.SmsTemplateInputBean;
import com.epic.db.DBConnection;
import com.epic.init.Status;
import java.sql.Connection;
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
public class SmsTemplateService {
   
    public List<SmsBean> loadData(SmsTemplateInputBean bean, String orderBy, int from, int rows,String profileID) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        List<SmsBean> dataList = null;
        long totalCount = 0;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            
            if(bean.getProfileId().equals("-1")){
                bean.setProfileId("");
            }
            String sqlCount = "select count(*) AS TOTAL FROM CLA_SMS_TEMPLATE where SMS_PROFILE_ID LIKE ? AND SMS_PROFILE_ID=?";   
            prepSt = con.prepareStatement(sqlCount);
            prepSt.setString(1, "%" + bean.getProfileId()+"%");
            prepSt.setString(2,  profileID);
            res = prepSt.executeQuery();
            if (res.next()) {
                totalCount = res.getLong("TOTAL");
            }

            
            if (prepSt != null)   prepSt.close(); 
            if (res != null)  res.close();        
            
            
            getUsersListQuery = "select TEM.SMS_PROFILE_ID as PRO_ID,tem.SMS_TEMPLATE_CATEGORY as CATEG_ID,TEM.STATUS,TEM.DESCRIPTION AS MESSAGE,"
                    + "pro.DESCRIPTION as PRO_NAME,cat.DESCRIPTION AS CATEG_NAME "
                    + "from CLA_SMS_TEMPLATE tem ,CLA_SMS_TEMPLATE_PROFILE pro,CLA_MT_SMS_TEMPLATE_CATEGORY cat "
                    + "where tem.SMS_PROFILE_ID=pro.CODE AND tem.SMS_TEMPLATE_CATEGORY=cat.CODE AND tem.SMS_PROFILE_ID LIKE ? " + orderBy + " LIMIT " + from + "," + rows;

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1,  profileID);
            res = prepSt.executeQuery();
 
            dataList = new ArrayList<SmsBean>();

            while (res.next()) {

                SmsBean dataBean = new SmsBean();
                dataBean.setProfileId(res.getString("PRO_ID"));
                dataBean.setProfileName(res.getString("PRO_NAME"));
                dataBean.setCategoryId(res.getString("CATEG_ID"));
                dataBean.setCategoryName(res.getString("CATEG_NAME"));
                dataBean.setDesc(res.getString("MESSAGE"));
                dataBean.setStatus(res.getString("STATUS")); 
                
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
    
    
    public boolean addData(SmsTemplateInputBean inputBean,String profileID) throws Exception{
        Connection con = null;
        String sql;
        PreparedStatement preStat=null;
        boolean ok=false;
        
        
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sql="INSERT INTO CLA_SMS_TEMPLATE(SMS_PROFILE_ID,SMS_TEMPLATE_CATEGORY,STATUS,DESCRIPTION)  VALUES(?,?,?,?)";
            
            preStat = con.prepareStatement(sql);
            preStat.setInt(1, Integer.parseInt(profileID));
            preStat.setInt(2, Integer.parseInt(inputBean.getSmstemplatecId()));
            preStat.setInt(3,Status.ACTIVE);
            preStat.setString(4, inputBean.getMsg());


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
    
      public void findData(SmsTemplateInputBean bean,String ProfileID) throws Exception {
        
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "SELECT SMS_PROFILE_ID,SMS_TEMPLATE_CATEGORY,STATUS,DESCRIPTION FROM CLA_SMS_TEMPLATE WHERE SMS_PROFILE_ID =? AND SMS_TEMPLATE_CATEGORY=?";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1,ProfileID);
            prepSt.setString(2,bean.getSmstemplatecId());
            res = prepSt.executeQuery();
            while (res.next()) {
                bean.setUpsmstprofileId(Integer.toString(res.getInt("SMS_PROFILE_ID")));
                bean.setUpsmsproname(getProfileList().get(ProfileID));
                bean.setUpsmstemplatecId(Integer.toString(res.getInt("SMS_TEMPLATE_CATEGORY")));
                bean.setUpsmstcname(getAllSMSTemplateList().get(bean.getUpsmstemplatecId()));
                bean.setUpstatus1(Integer.toString(res.getInt("STATUS")));
                bean.setUpmsg(res.getString("DESCRIPTION"));
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


    public boolean updateData(SmsTemplateInputBean inputBean,String profileID) throws Exception {

        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            sql = "UPDATE CLA_SMS_TEMPLATE SET STATUS =?,DESCRIPTION =? WHERE (SMS_PROFILE_ID =? AND SMS_TEMPLATE_CATEGORY =?)";
            prepSt = con.prepareStatement(sql);
            prepSt.setInt(1, Integer.parseInt(inputBean.getUpstatus1()));
            prepSt.setString(2, inputBean.getUpmsg());
            prepSt.setInt(3, Integer.parseInt(profileID));
            prepSt.setInt(4, Integer.parseInt(inputBean.getUpsmstemplatecId()));
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
    
    
        
    public boolean deleteData(SmsTemplateInputBean bean,String profileID) throws Exception {

        PreparedStatement prepSt = null;
        Connection con = null;
        String deleteTemplate = null;
        boolean ok=false;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            deleteTemplate = "DELETE FROM CLA_SMS_TEMPLATE  WHERE SMS_PROFILE_ID= ? AND SMS_TEMPLATE_CATEGORY =?";
            prepSt = con.prepareStatement(deleteTemplate);
            prepSt.setString(1,profileID);  
            prepSt.setString(2, bean.getSmstemplatecId());
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

    
    public boolean checkPNameANDTCategory(String proname , String tcategory) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        boolean ok=false;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(true);
            String sql = "SELECT * FROM CLA_SMS_TEMPLATE WHERE SMS_PROFILE_ID=? AND SMS_TEMPLATE_CATEGORY =?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, proname);
            ps.setString(2, tcategory);
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


    
    public static Map<String, String> getProfileList() throws Exception {
        Map<String, String> smsProList = new HashMap<String, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sql = "SELECT CODE,DESCRIPTION from CLA_SMS_TEMPLATE_PROFILE WHERE STATUS=1";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                smsProList.put(Integer.toString(rs.getInt("CODE")), rs.getString("DESCRIPTION"));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }

        }

        return smsProList;
    }
    
//    public Map<String,String> getSMSTemplateList(SmsTemplateInputBean inputBean) throws Exception {
//        
//        Map<String,String> smsTList=new HashMap<String,String>();
//        
//        PreparedStatement prepSt = null;
//        ResultSet res = null;
//        Connection con = null;
//        String getSMSTListQuery = null;
//        try {
//
//            con = DBConnection.getConnection();
//            //con.setAutoCommit(true);
//            getSMSTListQuery = "SELECT CODE,DESCRIPTION FROM CLACA_SCHEMA.CLA_MT_SMS_TEMPLATE_CATEGORY WHERE DELIVERY_PARTY=?";
//            
//            prepSt = con.prepareStatement(getSMSTListQuery);
//            prepSt.setString(1, inputBean.getSenderType());
//            res = prepSt.executeQuery();
//            while (res.next()) {
//                smsTList.put(res.getString("CODE"), res.getString("DESCRIPTION"));
//            }
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            if (prepSt != null) {
//                prepSt.close();
//            }
//            if (res != null) {
//                res.close();
//            }
//            if (con != null) {
//                con.close();
//            }
//            
//        }
//        return smsTList;
//    }
    public Map<String,String> getAllSMSTemplateList() throws Exception {
        
        Map<String,String> smsTList=new HashMap<String,String>();
        
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getSMSTListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getSMSTListQuery = "SELECT CODE,DESCRIPTION FROM CLA_MT_SMS_TEMPLATE_CATEGORY";
            prepSt = con.prepareStatement(getSMSTListQuery);
            res = prepSt.executeQuery();
            while (res.next()) {
                smsTList.put(res.getString("CODE"), res.getString("DESCRIPTION"));
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
        return smsTList;
    }
}
