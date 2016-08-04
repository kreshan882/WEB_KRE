/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.systemAlert.service;

import com.epic.cla.systemAlert.bean.ViewSystemNotificationBean;
import com.epic.cla.systemAlert.bean.ViewSystemNotificationInputBean;
import com.epic.db.DBConnection;
import com.epic.init.Status;
import com.epic.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nipun_t
 */
public class ViewSystemNotificationService {

    public void getProfileList(ViewSystemNotificationInputBean inputBean) throws Exception {
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

    public List<ViewSystemNotificationBean> loadData(ViewSystemNotificationInputBean bean, String orderBy, int from, int rows) throws Exception {

        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        List<ViewSystemNotificationBean> dataList = null;
        long totalCount = 0;
        if (bean.getFromdate().equals("") && bean.getTodate().equals("")) {
            try {

                con = DBConnection.getConnection();
                //con.setAutoCommit(true);
                String sqlCount = "select count(*) AS TOTAL FROM CLA_SYSTEM_ALERT";
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

                getUsersListQuery = "SELECT sa.id,rl.description as RISK_LEVEL,sa.description,sa.READ_STATUS,sa.timestamp FROM\n"
                        + "CLA_SYSTEM_ALERT sa,CLA_MT_RISK_LEVEL rl\n"
                        + "where sa.RISK_LEVEL=rl.risk_code" + orderBy + " LIMIT " + from + "," + rows;

                prepSt = con.prepareStatement(getUsersListQuery);

                res = prepSt.executeQuery();

                dataList = new ArrayList<ViewSystemNotificationBean>();

                while (res.next()) {

                    ViewSystemNotificationBean dataBean = new ViewSystemNotificationBean();
                    dataBean.setId(Integer.toString(res.getInt("ID")));
                    dataBean.setRisklevel(res.getString("RISK_LEVEL"));
                    dataBean.setDescription(res.getString("DESCRIPTION"));
                    dataBean.setReadstatus(Integer.toString(res.getInt("READ_STATUS")));
                    dataBean.setTimestamp(Util.formatTimestamp(res.getTimestamp("TIMESTAMP")).toString());

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
        } else {
            try {

                con = DBConnection.getConnection();
                //con.setAutoCommit(true);
                String sqlCount = "select count(*) AS TOTAL FROM CLA_SYSTEM_ALERT where RISK_LEVEL like ? AND READ_STATUS like ? AND  date(TIMESTAMP) BETWEEN ? AND ?";
                prepSt = con.prepareStatement(sqlCount);
                String string = bean.getFromdate();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(string);

                String string2 = bean.getTodate();
                DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                Date date2 = format2.parse(string2);

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.set(Calendar.MILLISECOND, 0);

                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(date2);
                cal2.set(Calendar.MILLISECOND, 0);

//                prepSt.setString(1, "%" + bean.getAlertidform() + "%");
                prepSt.setString(1, "%" + bean.getRisklevelform() + "%");
                prepSt.setString(2, "%" + bean.getReadstatusform() + "%");
                prepSt.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
                prepSt.setTimestamp(4, new java.sql.Timestamp(date2.getTime()));

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

                getUsersListQuery = "SELECT sa.id,rl.description as RISK_LEVEL,sa.description,s.description as READ_STATUS,sa.timestamp FROM \n"
                        + "CLA_SYSTEM_ALERT sa,CLA_MT_RISK_LEVEL rl,CLA_MT_STATUS s\n"
                        + " where sa.RISK_LEVEL=rl.risk_code and sa.READ_STATUS=s.code AND  sa.RISK_LEVEL like ? AND sa.READ_STATUS like ? AND date(sa.TIMESTAMP) BETWEEN ? AND ?" + orderBy + " LIMIT " + from + "," + rows;

                prepSt = con.prepareStatement(getUsersListQuery);
//                prepSt.setString(1, "%" + bean.getAlertidform() + "%");
                prepSt.setString(1, "%" + bean.getRisklevelform() + "%");
                prepSt.setString(2, "%" + bean.getReadstatusform() + "%");
                prepSt.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
                prepSt.setTimestamp(4, new java.sql.Timestamp(date2.getTime()));
                res = prepSt.executeQuery();

                dataList = new ArrayList<ViewSystemNotificationBean>();

                while (res.next()) {

                    ViewSystemNotificationBean dataBean = new ViewSystemNotificationBean();
                    dataBean.setId(Integer.toString(res.getInt("ID")));
                    dataBean.setRisklevel(res.getString("RISK_LEVEL"));
                    dataBean.setDescription(res.getString("DESCRIPTION"));
                    dataBean.setReadstatus(res.getString("READ_STATUS"));
                    dataBean.setTimestamp(Util.formatTimestamp(res.getTimestamp("TIMESTAMP")).toString());

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

    }

    public void getCriticalAlerts(ViewSystemNotificationInputBean inputBean) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(true);
            String sql = "SELECT COUNT(RISK_LEVEL) FROM CLA_SYSTEM_ALERT WHERE RISK_LEVEL=2";
            ps = connection.prepareStatement(sql);
            result = ps.executeQuery();

            while (result.next()) {
                inputBean.setCriticalAlertCountSysAlert(result.getString(1));
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

    public void getWarnings(ViewSystemNotificationInputBean inputBean) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(true);
            String sql = "SELECT COUNT(RISK_LEVEL) FROM CLA_SYSTEM_ALERT WHERE RISK_LEVEL=1";
            ps = connection.prepareStatement(sql);
            result = ps.executeQuery();

            while (result.next()) {
                inputBean.setWarningAlertCountSysAlert(result.getString(1));
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

}
