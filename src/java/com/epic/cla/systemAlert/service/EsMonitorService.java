/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.systemAlert.service;

import com.epic.cla.systemAlert.bean.EsMonitorBean;
import com.epic.cla.systemAlert.bean.LcMonitorBean;
import com.epic.cla.systemAlert.bean.MonitorInputBean;
import com.epic.db.DBConnection;
import com.epic.init.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chalaka_n
 */
public class EsMonitorService {

    public List<EsMonitorBean> loadData(MonitorInputBean lcInputBean, String orderBy, int from, int rows) throws Exception {

        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        List<EsMonitorBean> dataList = null;
        long totalCount = 0;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sqlCount = "select count(*) AS TOTAL FROM CLA_ES_MONITOR";
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

            getUsersListQuery = "SELECT es.ES_TRAFFIC AS TRAFFIC, es.CHECKOUT_DB_CON AS CHECKOUT , es.FREE_DB_CON AS DBCON, es.ES_STATUS AS STATUS  "
                    + "FROM CLA_ES_MONITOR es "
                    + orderBy + " LIMIT " + from + "," + rows;

            prepSt = con.prepareStatement(getUsersListQuery);
            res = prepSt.executeQuery();

            dataList = new ArrayList<EsMonitorBean>();

            while (res.next()) {
                EsMonitorBean dataBean = new EsMonitorBean();
                dataBean.setTrafic(res.getString("TRAFFIC"));
                dataBean.setCheckout(res.getString("CHECKOUT"));
                dataBean.setDbcon(res.getString("DBCON"));
                dataBean.setStatus(res.getString("STATUS"));

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

    public static boolean upadateESstatus(int Status) throws Exception {

        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            sql = "UPDATE CLA_ES_MONITOR SET ES_STATUS=?";
            prepSt = con.prepareStatement(sql);
            prepSt.setInt(1,Status );//Status.INACTIVE

            int n = prepSt.executeUpdate();
            if (n > 0) {
                ok = true;
            }

        } catch (Exception e) {
//            con.rollback();
//            throw e;
            e.printStackTrace();

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
