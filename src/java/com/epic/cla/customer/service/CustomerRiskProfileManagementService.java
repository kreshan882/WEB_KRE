/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.customer.service;

import com.epic.cla.customer.bean.CostomerRiskProfileManagementInputBean;
import com.epic.cla.customer.bean.CustomerManagementBean;
import com.epic.cla.customer.bean.CustomerRiskProfileManagementBean;
import com.epic.db.DBConnection;
import com.epic.init.Status;
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
 * @author nipun_t
 */
public class CustomerRiskProfileManagementService {

    public void getProfileList(CostomerRiskProfileManagementInputBean inputBean) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(true);
            String sql = " SELECT PROFILE_ID,DESCRIPTION FROM CLA_USER_PROFILE where STATUS=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, Status.ACTIVE);
            result = ps.executeQuery();

            while (result.next()) {
                inputBean.getUserProList().put(result.getString("PROFILE_ID"), result.getString("DESCRIPTION"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
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

    }

    public Map<? extends String, ? extends String> getRiskProfileIDList() throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        Map<String, String> basicStatus = new HashMap<String, String>();
        List<CustomerManagementBean> dataList = null;
        long totalCount = 0;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "SELECT * FROM CLA_CUSTOMER_RISK_PROFILE";

            prepSt = con.prepareStatement(getUsersListQuery);
            res = prepSt.executeQuery();

            while (res.next()) {
                basicStatus.put(res.getString("ID"), res.getString("NAME"));
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
        return basicStatus;
    }

    public Map<? extends String, ? extends String> getSmsProfileList() throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        Map<String, String> basicStatus = new HashMap<String, String>();
        List<CustomerManagementBean> dataList = null;
        long totalCount = 0;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "SELECT * FROM CLA_SMS_TEMPLATE_PROFILE";
            prepSt = con.prepareStatement(getUsersListQuery);
            res = prepSt.executeQuery();

            while (res.next()) {
                basicStatus.put(res.getString("CODE"), res.getString("DESCRIPTION"));
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
        return basicStatus;
    }

    public List<CustomerRiskProfileManagementBean> loadData(CostomerRiskProfileManagementInputBean bean, String orderBy, int from, int rows) throws SQLException, Exception {
       
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        List<CustomerRiskProfileManagementBean> dataList = null;
        long totalCount = 0;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sqlCount = "select count(*) AS TOTAL FROM CLA_CUSTOMER_RISK_PROFILE where NAME LIKE ?";
            prepSt = con.prepareStatement(sqlCount);
            prepSt.setString(1, "%" + bean.getRpname() + "%");
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

            getUsersListQuery = "SELECT rp.ID,rp.NAME,rp.MAX_AMOUNT_PER_DAY,tm.description as TRANSFER_MODE,"
                    + "rp.MSG_DELIVERY_MODE_ORD,rp.CHARGES_MODE,rp.MSG_VALIDITY_PERIOD,rp.NO_OF_TXN_ALLOWED_PER_DAY_RECIPIENT,"
                    + "rp.MIN_AMOUNT_PER_TXN,rp.MAX_AMOUNT_PER_TXN,fcm.description AS FEE_CALCULATION_METHOD,rp.FEE_VALUE,s.description as STATUS,rp.MSG_DELIVERY_MODE_SEC,rp.DEFAULT_STATUS"
                    + " FROM CLA_CUSTOMER_RISK_PROFILE rp,CLA_MT_FEE_CAL_METHOD fcm,CLA_MT_TRANSFER_MODE tm,CLA_MT_STATUS s"
                    + " where rp.FEE_CALCULATION_METHOD=fcm.code and rp.status=s.code and rp.TRANSFER_MODE=tm.code and rp.NAME LIKE ? " + orderBy + " LIMIT " + from + "," + rows;

//           
            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1, "%" + bean.getRpname() + "%");
            res = prepSt.executeQuery();

            dataList = new ArrayList<CustomerRiskProfileManagementBean>();

            while (res.next()) {

                CustomerRiskProfileManagementBean dataBean = new CustomerRiskProfileManagementBean();
                dataBean.setId(Integer.toString(res.getInt("ID")));
                dataBean.setName(res.getString("NAME"));
                dataBean.setMaxamountperday(Util.round(res.getString("MAX_AMOUNT_PER_DAY")));
                dataBean.setTransfermode(res.getString("TRANSFER_MODE"));
                dataBean.setMsgdeliverymodeord(Util.getMessegeDeliveryPartyList().get(Integer.toString(res.getInt("MSG_DELIVERY_MODE_ORD"))));//bacause DB2 doesnt support
                dataBean.setChargesmode(Util.getMessegeDeliveryPartyList().get(Integer.toString(res.getInt("CHARGES_MODE"))));//bacause DB2 doesnt support

                dataBean.setMsgvalidityperiod(Integer.toString(res.getInt("MSG_VALIDITY_PERIOD")) + " Hour");
                dataBean.setNooftxnallowedperdayrecipient(Integer.toString(res.getInt("NO_OF_TXN_ALLOWED_PER_DAY_RECIPIENT")));
                dataBean.setMinamountpertxn(Util.round(res.getString("MIN_AMOUNT_PER_TXN")));
                dataBean.setMaxamountpertxn(Util.round(res.getString("MAX_AMOUNT_PER_TXN")));
                dataBean.setFeecalculationmethod(res.getString("FEE_CALCULATION_METHOD"));

                dataBean.setFeevalue(Util.round(res.getString("FEE_VALUE")));
                dataBean.setMsgdeliverymodesec(Util.getMessegeDeliveryPartyList().get(Integer.toString(res.getInt("MSG_DELIVERY_MODE_SEC"))));//bacause DB2 doesnt support

                dataBean.setStatus(res.getString("STATUS"));
                dataBean.setDefaultStatus(res.getString("DEFAULT_STATUS"));

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

    public boolean addData(CostomerRiskProfileManagementInputBean inputBean) throws Exception {
        Connection con = null;
        String sql;
        PreparedStatement preStat = null;
        boolean ok = false;

        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sql = "INSERT INTO CLA_CUSTOMER_RISK_PROFILE(NAME, MAX_AMOUNT_PER_DAY,TRANSFER_MODE,MSG_DELIVERY_MODE_ORD,CHARGES_MODE,"
                    + "MSG_VALIDITY_PERIOD,MSG_FORMAT_CODE,NO_OF_TXN_ALLOWED_PER_DAY_RECIPIENT,MIN_AMOUNT_PER_TXN,MAX_AMOUNT_PER_TXN,FEE_CALCULATION_METHOD,FEE_VALUE,MSG_DELIVERY_MODE_SEC,STATUS)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            preStat = con.prepareStatement(sql);

            preStat.setString(1, inputBean.getName());
            preStat.setString(2, inputBean.getMaxamountperday());
            preStat.setInt(3, Integer.parseInt(inputBean.getTransfermode()));
            preStat.setInt(4, Integer.parseInt(inputBean.getMsgdeliverymodeord()));
            preStat.setInt(5, Integer.parseInt(inputBean.getChargesmode()));

            preStat.setInt(6, Integer.parseInt(inputBean.getMsgvalidityperiod()));
            preStat.setInt(7, 3);
            preStat.setInt(8, Integer.parseInt(inputBean.getNooftxnallowedperdayrecipient()));
            preStat.setDouble(9, Double.parseDouble(inputBean.getMinamountpertxn()));
            preStat.setDouble(10, Double.parseDouble(inputBean.getMaxamountpertxn()));
            preStat.setInt(11, Integer.parseInt(inputBean.getFeecalculationmethod()));
            preStat.setDouble(12, Double.parseDouble(inputBean.getFeevalue()));
            preStat.setInt(13, Integer.parseInt(inputBean.getMsgdeliverymodesec()));
            preStat.setInt(14, Status.ACTIVE);

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
                DBConnection.dbConnectionClose(con);
            }
        }
        return ok;
    }

    public void findData(CostomerRiskProfileManagementInputBean bean) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "SELECT rp.ID,rp.NAME,rp.MAX_AMOUNT_PER_DAY,rp.TRANSFER_MODE,"
                    + "rp.MSG_DELIVERY_MODE_ORD,rp.CHARGES_MODE,rp.MSG_VALIDITY_PERIOD,rp.NO_OF_TXN_ALLOWED_PER_DAY_RECIPIENT,"
                    + "rp.MIN_AMOUNT_PER_TXN,rp.MAX_AMOUNT_PER_TXN,rp.FEE_CALCULATION_METHOD,rp.FEE_VALUE,rp.STATUS,rp.MSG_DELIVERY_MODE_SEC "
                    + " FROM CLA_CUSTOMER_RISK_PROFILE rp"
                    + " where rp.ID LIKE ? ";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1, bean.getId());
            res = prepSt.executeQuery();
            while (res.next()) {
                bean.setUpid(Integer.toString(res.getInt("ID")));
                bean.setUpname(res.getString("NAME"));
                bean.setUpmaxamountperday(res.getString("MAX_AMOUNT_PER_DAY"));
                bean.setUptransfermode(Integer.toString(res.getInt("TRANSFER_MODE")));
                bean.setUpmsgdeliverymodeord(Integer.toString(res.getInt("MSG_DELIVERY_MODE_ORD")));
                bean.setUpchargesmode(Integer.toString(res.getInt("CHARGES_MODE")));
                bean.setUpmsgvalidityperiod(res.getString("MSG_VALIDITY_PERIOD"));
                bean.setUpnooftxnallowedperdayrecipient(res.getString("NO_OF_TXN_ALLOWED_PER_DAY_RECIPIENT"));
                bean.setUpminamountpertxn(res.getString("MIN_AMOUNT_PER_TXN"));
                bean.setUpmaxamountpertxn(res.getString("MAX_AMOUNT_PER_TXN"));
                bean.setUpfeecalculationmethod(Integer.toString(res.getInt("FEE_CALCULATION_METHOD")));
                bean.setUpfeevalue(res.getString("FEE_VALUE"));
                bean.setUpmsgdeliverymodesec(Integer.toString(res.getInt("MSG_DELIVERY_MODE_SEC")));
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

    public boolean updateData(CostomerRiskProfileManagementInputBean inputBean) throws SQLException, Exception {
        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            sql = "UPDATE CLA_CUSTOMER_RISK_PROFILE SET NAME=?,MAX_AMOUNT_PER_DAY=?,TRANSFER_MODE=?,MSG_DELIVERY_MODE_ORD=?,"
                    + "CHARGES_MODE=?,MSG_VALIDITY_PERIOD=?,NO_OF_TXN_ALLOWED_PER_DAY_RECIPIENT=?,MIN_AMOUNT_PER_TXN=?,MAX_AMOUNT_PER_TXN=?,FEE_CALCULATION_METHOD=?,FEE_VALUE=?,MSG_DELIVERY_MODE_SEC=?,STATUS=?  Where ID=?";
            prepSt = con.prepareStatement(sql);
            prepSt.setString(1, inputBean.getUpname());
            prepSt.setString(2, inputBean.getUpmaxamountperday());
            prepSt.setInt(3, Integer.parseInt(inputBean.getUptransfermode()));
            prepSt.setInt(4, Integer.parseInt(inputBean.getUpmsgdeliverymodeord()));
            prepSt.setInt(5, Integer.parseInt(inputBean.getUpchargesmode()));
            prepSt.setString(6, inputBean.getUpmsgvalidityperiod());
            prepSt.setString(7, inputBean.getUpnooftxnallowedperdayrecipient());
            prepSt.setString(8, inputBean.getUpminamountpertxn());
            prepSt.setString(9, inputBean.getUpmaxamountpertxn());
            prepSt.setString(10, inputBean.getUpfeecalculationmethod());
            prepSt.setString(11, inputBean.getUpfeevalue());
            prepSt.setInt(12, Integer.parseInt(inputBean.getUpmsgdeliverymodesec()));
            prepSt.setInt(13, Integer.parseInt(inputBean.getUpstatus()));
            prepSt.setInt(14, Integer.parseInt(inputBean.getUpid()));

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
    
    public boolean deleteData(CostomerRiskProfileManagementInputBean bean) throws SQLException, Exception {
        PreparedStatement prepSt = null;
        Connection con = null;
        String deleteUser = null;
        boolean ok = false;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            deleteUser = "DELETE FROM CLA_CUSTOMER_RISK_PROFILE  WHERE ID= ?";
            prepSt = con.prepareStatement(deleteUser);
            prepSt.setString(1, bean.getId());
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

}
