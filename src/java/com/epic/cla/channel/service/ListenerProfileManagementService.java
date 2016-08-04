/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.channel.service;

import com.epic.cla.channel.bean.ListenerBean;
import com.epic.cla.channel.bean.ListenerProfileManagementBean;
import com.epic.cla.channel.bean.ListenerProfileManagementInputBean;
import com.epic.db.DBConnection;
import com.epic.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dimuthu_h
 */
public class ListenerProfileManagementService {

    public List<ListenerProfileManagementBean> loadData(ListenerProfileManagementInputBean bean, String orderBy, int from, int rows) throws Exception {
       
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getListenerListQuery = null;
        List<ListenerProfileManagementBean> dataList = null;
        long totalCount = 0;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sqlCount = "select count(*) AS TOTAL FROM CLA_MT_LISTENER_TYPE WHERE LISTNER_CATEGORY = 2";//listener ct=ategory 2 = Bank Listener
            prepSt = con.prepareStatement(sqlCount);
            res = prepSt.executeQuery();
            if (res.next()) {
                totalCount = res.getLong("TOTAL");
            }

            if (prepSt != null) {
                prepSt.close();
            }
            if (res != null) {
                res.close();
            }

            getListenerListQuery = "SELECT * FROM CLA_MT_LISTENER_TYPE WHERE LISTNER_CATEGORY = 2 " + orderBy + " LIMIT " + from + "," + rows;

            prepSt = con.prepareStatement(getListenerListQuery);

            res = prepSt.executeQuery();

            dataList = new ArrayList<ListenerProfileManagementBean>();

            while (res.next()) {

                ListenerProfileManagementBean dataBean = new ListenerProfileManagementBean();
                dataBean.setId(Integer.toString(res.getInt("CODE")));
                dataBean.setListenerName(res.getString("DESCRIPTION"));
                dataBean.setAmountHold(Integer.toString(res.getInt("AMOUNT_HOLD_STATUS")));
                dataBean.setSenderValidation(Integer.toString(res.getInt("CUST_VALID_STATUS")));
                dataBean.setTxnFee(Integer.toString(res.getInt("TXN_FEE_STATUS")));
                dataBean.setCollectionacc(res.getString("COLLECTION_ACCOUNT"));
                dataBean.setGlacc(res.getString("GL_ACCOUNT"));
                dataBean.setCostCenter(res.getString("COST_CENTER"));

                dataBean.setFullCount(totalCount);
                dataList.add(dataBean);
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
        return dataList;
    }

    public void findData(ListenerProfileManagementInputBean bean) throws Exception {
       
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "SELECT * FROM CLA_MT_LISTENER_TYPE WHERE CODE = ?";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setInt(1, Integer.parseInt(bean.getId()));
            res = prepSt.executeQuery();
            while (res.next()) {
                bean.setId(Integer.toString(res.getInt("CODE")));
                bean.setListenerName(res.getString("DESCRIPTION"));
                bean.setAmountHold(Integer.toString(res.getInt("AMOUNT_HOLD_STATUS")));
                bean.setSenderValidation(Integer.toString(res.getInt("CUST_VALID_STATUS")));
                bean.setTxnFee(Integer.toString(res.getInt("TXN_FEE_STATUS")));
                bean.setCollectionacc(res.getString("COLLECTION_ACCOUNT"));
                bean.setGlacc(res.getString("GL_ACCOUNT"));
                bean.setCostCenter(res.getString("COST_CENTER"));

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

    public boolean updateData(ListenerProfileManagementInputBean bean) throws Exception {
        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sql = "UPDATE CLA_MT_LISTENER_TYPE SET COLLECTION_ACCOUNT=?,GL_ACCOUNT=?,AMOUNT_HOLD_STATUS=?,"
                    + "CUST_VALID_STATUS=?,TXN_FEE_STATUS=? WHERE CODE = ?";
            prepSt = con.prepareStatement(sql);
            
            prepSt.setString(1, bean.getCollectionacc());
            prepSt.setString(2, bean.getGlacc());
            prepSt.setInt(3, Integer.parseInt(bean.getAmountHold()));
            prepSt.setInt(4, Integer.parseInt(bean.getSenderValidation()));
            prepSt.setInt(5, Integer.parseInt(bean.getTxnFee()));
            prepSt.setInt(6, Integer.parseInt(bean.getId()));
            int n = prepSt.executeUpdate();

            if (n > 0) {
                ok = true;
            }
            

        } catch (Exception e) {
            con.rollback();
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
