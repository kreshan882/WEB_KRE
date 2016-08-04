/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.bulksender.service;

import com.epic.cla.bulksender.bean.BulkSenderBean;
import com.epic.cla.bulksender.bean.BulkSenderInputBean;
import com.epic.db.DBConnection;
import com.epic.init.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dimuthu_h
 */
public class BulkSenderService {

    public List<BulkSenderBean> loadData(BulkSenderInputBean bean, String orderBy, int from, int rows) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        List<BulkSenderBean> dataList = null;
        long totalCount = 0;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            String sqlCount = "SELECT COUNT(*) AS TOTAL FROM CLA_MERCHANT_BULK WHERE CUSTOMER_ID=? AND BATCH_NO =? AND STATUS =?";
            prepSt = con.prepareStatement(sqlCount);
            prepSt.setInt(1, Integer.parseInt(bean.getCus_id()));
            prepSt.setString(2, String.format("%06d", Integer.parseInt(bean.getBatch_no())));
            prepSt.setInt(3, Status.INACTIVE);
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

            getUsersListQuery = "SELECT CUSTOMER_ID,RECIPIENT_MOBILE_NUM,AMOUNT,STATUS FROM CLA_MERCHANT_BULK WHERE CUSTOMER_ID=? AND BATCH_NO =? AND STATUS =?" + orderBy + " LIMIT " + from + "," + rows;
            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setInt(1, Integer.parseInt(bean.getCus_id()));
            prepSt.setString(2, String.format("%06d", Integer.parseInt(bean.getBatch_no())));
            prepSt.setInt(3, Status.INACTIVE);
            res = prepSt.executeQuery();

            dataList = new ArrayList<BulkSenderBean>();

            while (res.next()) {
                BulkSenderBean dataBean = new BulkSenderBean();
                dataBean.setCustomerId(bean.getCus_id());
                dataBean.setBatchNo(bean.getBatch_no());
                dataBean.setRecipient_mobile(res.getString("RECIPIENT_MOBILE_NUM"));
                dataBean.setAmount(Double.toString(res.getDouble("AMOUNT")));
                dataBean.setStatus(Integer.toString(res.getInt("STATUS")));

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

    public boolean addData(BulkSenderInputBean inputBean) throws Exception {
        Connection con = null;
        String sql;
        PreparedStatement preStat = null;
        boolean ok = false;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sql = "INSERT INTO CLA_MERCHANT_BULK(RECIPIENT_MOBILE_NUM,AMOUNT,CUSTOMER_ID,BATCH_NO,STATUS)  VALUES(?,?,?,?,?)";

            preStat = con.prepareStatement(sql);
            preStat.setString(1, "+94"+inputBean.getReci_mobile());
            preStat.setDouble(2, Double.parseDouble(inputBean.getAmount()));
            preStat.setInt(3, Integer.parseInt(inputBean.getCus_id()));
            preStat.setString(4, String.format("%06d", (this.getMaxBatchID(inputBean.getCus_id()) + 1)));
            preStat.setInt(5, Status.INACTIVE);

            int n = preStat.executeUpdate();
            if (n >= 0) {
                ok = true;
            }

            

        } catch (Exception e) {
            throw e;
        } finally {
            if (preStat != null) {
                preStat.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return ok;
    }

    public boolean updateData(BulkSenderInputBean bean) throws Exception {

        boolean ok = false;
        PreparedStatement newPst = null;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        String updateQuery = null;
        int queryExecuteVal = 0;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            sql = "SELECT RECIPIENT_MOBILE_NUM FROM CLA_MERCHANT_BULK  WHERE STATUS = ? AND CUSTOMER_ID = ? AND BATCH_NO =?";
            prepSt = con.prepareStatement(sql);
            prepSt.setInt(1, Status.INACTIVE);
            prepSt.setInt(2, Integer.parseInt(bean.getCus_id()));
            prepSt.setString(3, bean.getBatch_no());
            res = prepSt.executeQuery();

            while (res.next()) {
                updateQuery = "UPDATE  CLA_MERCHANT_BULK SET STATUS =? WHERE RECIPIENT_MOBILE_NUM = ? ";
                newPst = con.prepareStatement(updateQuery);
                newPst.setInt(1, Status.PENDING);
                newPst.setString(2, res.getString("RECIPIENT_MOBILE_NUM"));
                int n = newPst.executeUpdate();
                queryExecuteVal = n;
                if (newPst != null) {
                    newPst.close();
                }

            }
            if (queryExecuteVal > 0) {
                if (prepSt != null) {
                    prepSt.close();
                }
                sql = "UPDATE  CLA_CUSTOMER SET BATCH_NO =? WHERE  CUSTOMER_ID = ?";
                prepSt = con.prepareStatement(sql);
                prepSt.setString(1, String.format("%06d", (this.getMaxBatchID(bean.getCus_id()) + 1)));
                prepSt.setString(2, bean.getCus_id());
                prepSt.executeUpdate();
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

    public boolean deleteData(BulkSenderInputBean bean) throws Exception {
        PreparedStatement prepSt = null;
        Connection con = null;
        String deleteRecipient = null;
        boolean ok = false;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            deleteRecipient = "DELETE FROM CLA_MERCHANT_BULK  WHERE RECIPIENT_MOBILE_NUM = ? AND BATCH_NO =? AND CUSTOMER_ID =?";
            prepSt = con.prepareStatement(deleteRecipient);
            prepSt.setString(1, bean.getReci_mobile());
            prepSt.setString(2, bean.getBatch_no());
            prepSt.setInt(3, Integer.parseInt(bean.getCus_id()));
            int n = prepSt.executeUpdate();
            if (n > 0) {
                ok = true;
            }
            
        } catch (Exception e) {
            con.rollback();
            ok = false;
            throw e;
        } finally {
            if (prepSt != null) {
                prepSt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return ok;
    }

    public static boolean checkDuplicateMobile(BulkSenderInputBean bean) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getMobileNumListQuery = null;
        boolean ok = false;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getMobileNumListQuery = "SELECT RECIPIENT_MOBILE_NUM FROM CLA_MERCHANT_BULK  WHERE CUSTOMER_ID = ? AND BATCH_NO =?";
            prepSt = con.prepareStatement(getMobileNumListQuery);
            prepSt.setInt(1, Integer.parseInt(bean.getCus_id()));
            prepSt.setString(2, bean.getBatch_no());
            res = prepSt.executeQuery();
            while(res.next()) {
                if (res.getString("RECIPIENT_MOBILE_NUM").equals(("+94"+bean.getReci_mobile()))) {
                    ok = true;
                }
            }

        } catch (Exception e) {
            con.rollback();
            ok = false;
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

    public int getMaxBatchID(String customer_id) throws Exception {

        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getQuery = null;
        int max = 0;
        String sqlCount = "";

        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sqlCount = "SELECT MAX(BATCH_NO) AS MAX FROM CLA_CUSTOMER WHERE CUSTOMER_ID = ?";

            prepSt = con.prepareStatement(sqlCount);
            prepSt.setInt(1, Integer.parseInt(customer_id));

            res = prepSt.executeQuery();

            if (res.next()) {
                max = res.getInt("MAX");
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
        return max;

    }

}
