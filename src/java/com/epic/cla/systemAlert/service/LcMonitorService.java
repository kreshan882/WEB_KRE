/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.systemAlert.service;

import com.epic.cla.systemAlert.bean.LcMonitorBean;
import com.epic.cla.systemAlert.bean.MonitorInputBean;
import com.epic.cla.user.bean.UserBean;
import com.epic.cla.user.bean.UserManagementInputBean;
import com.epic.db.DBConnection;
import com.epic.init.Status;
import com.epic.util.Util;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chalaka_n
 */
public class LcMonitorService {

    public List<LcMonitorBean> loadData(MonitorInputBean bean, String orderBy, int from, int rows) throws Exception {

        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        List<LcMonitorBean> dataList = null;
        long totalCount = 0;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sqlCount = "select count(*) AS TOTAL FROM CLA_LC_MONITOR";
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

            getUsersListQuery = "SELECT cm.LC_ID AS ID, cm.LC_CONNNECTIVITY_STATUS AS CONNECT_STATUS,cm.SIGNONSTATUS,cm.LC_LAN_STATUS AS LAN_STATUS "
                    + "FROM CLA_LC_MONITOR  cm  "
                    + orderBy + " LIMIT " + from + "," + rows;

            prepSt = con.prepareStatement(getUsersListQuery);
            res = prepSt.executeQuery();

            dataList = new ArrayList<LcMonitorBean>();

            while (res.next()) {
                LcMonitorBean dataBean = new LcMonitorBean();
//                String name=res.getString("CHANNEL_NAME");
//                name = (!res.wasNull())?res.getString("CHANNEL_NAME"):res.getString("LISTENER_NAME");
                dataBean.setLcId(res.getString("ID"));
                dataBean.setLanSta(res.getString("LAN_STATUS"));
                
                //System.out.println(res.getString("LAN_STATUS"));
                //System.out.println(res.getString("SIGNONSTATUS"));
                //System.out.println(res.getString("CONNECT_STATUS"));

                dataBean.setType(res.getString("SIGNONSTATUS"));

                dataBean.setConnectSta(res.getString("CONNECT_STATUS"));

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

    public static boolean upadateCNLstatus() throws Exception {

        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            sql = "UPDATE CLA_LC_MONITOR SET LC_CONNNECTIVITY_STATUS=?,SIGNONSTATUS=?,LC_LAN_STATUS=? WHERE LC_ID=?";
            prepSt = con.prepareStatement(sql);

            prepSt.setInt(1, 2);
            prepSt.setInt(2, 2);
            prepSt.setInt(3, 2);
            prepSt.setString(4, "ITM(ATM)");

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
                DBConnection.dbConnectionClose(con);
            }
        }
        return ok;
    }

}
