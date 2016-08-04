/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.transactionmgt.service;

import com.epic.cla.transactionmgt.bean.UserActivityAuditDataBean;
import com.epic.cla.transactionmgt.bean.UserActivityAuditInputBean;
import com.epic.db.DBConnection;
import com.epic.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tharaka
 */
public class UserActivityAuditService {

    public List<UserActivityAuditDataBean> loadHistory(UserActivityAuditInputBean bean, String orderBy, int max, int first) throws Exception {

        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getHistoryRecordsQuery = null;
        List<UserActivityAuditDataBean> dataList = null;
        long totalCount = 0;

        if (bean.isSearch()) {

            try {

                con = DBConnection.getConnection();
                //con.setAutoCommit(true);

                String sqlCount = "SELECT COUNT(*) AS TOTAL FROM  CLA_AUDIT_HISTORY HR WHERE HR.USER_ID LIKE ? AND HR.TIMESTAMP BETWEEN ? AND ? ";

                prepSt = con.prepareStatement(sqlCount);

                prepSt.setString(1, "%" + bean.getUsername() + "%");
                prepSt.setTimestamp(2, getTimeStamp(bean.getFromdate()));
                prepSt.setTimestamp(3, getTimeStamp(bean.getTodate()));

                res = prepSt.executeQuery();
                while (res.next()) {

                    totalCount = res.getLong("TOTAL");
                }

                if (res != null) {
                    res.close();
                }
                if (prepSt != null) {
                    prepSt.close();
                }

                getHistoryRecordsQuery = "select * from(SELECT distinct HR.ID AS ID,HR.USER_ID AS USER_ID,HR.MODULE_ID AS MODULE_ID,\n"
                        + "HR.SECTION_ID AS SECTION_ID,HR.TASK_ID AS TASK_ID,HR.DESCRIPTION AS DESCRIPTION,\n"
                        + "HR.IP AS IP,HR.TIMESTAMP AS TIMESTAMP \n"
                        + "FROM CLA_AUDIT_HISTORY HR, CLA_MT_SECTION S,\n"
                        + "  CLA_MT_TASKS T WHERE HR.USER_ID LIKE ? AND  HR.TIMESTAMP BETWEEN ? AND ?)t1\n"
                        + "left outer join(select module_id,description as MD from CLA_MT_MODULES) t2\n"
                        + "on t1.MODULE_ID=t2.module_id\n"
                        + "left outer join(select SECTION_ID,section_name as SD from CLA_MT_SECTION) t3\n"
                        + "on t1.SECTION_ID=t3.SECTION_ID\n"
                        + "left outer join(select task_id,description as TD from CLA_MT_TASKS) t4\n"
                        + "on t1.TASK_ID=t4.task_id"
                        + orderBy + " LIMIT " + first + "," + max;

                prepSt = con.prepareStatement(getHistoryRecordsQuery);

                prepSt.setString(1, "%" + bean.getUsername() + "%");
                prepSt.setTimestamp(2, getTimeStamp(bean.getFromdate()));
                prepSt.setTimestamp(3, getTimeStamp(bean.getTodate()));

                res = prepSt.executeQuery();

                dataList = new ArrayList<UserActivityAuditDataBean>();

                UserActivityAuditDataBean dataBean = null;

                while (res.next()) {

                    dataBean = new UserActivityAuditDataBean();

                    dataBean.setId(res.getInt("ID") + "");
                    dataBean.setUsername(res.getString("USER_ID"));
                    dataBean.setModule(res.getString("MD"));
                    dataBean.setSection(res.getString("SD"));
                    dataBean.setOperation(res.getString("TD"));
                    dataBean.setMessage(res.getString("DESCRIPTION"));
                    dataBean.setIp(res.getString("IP"));
                    dataBean.setDatetime(Util.formatTimestamp(res.getTimestamp("TIMESTAMP")).toString());
                    dataBean.setFullCount(totalCount);
                    dataList.add(dataBean);

                }
                
            } catch (Exception e) {
                if (con != null) {
                    con.rollback();
                }
                throw e;

            } finally {
                if (res != null) {
                    res.close();
                }
                if (prepSt != null) {
                    prepSt.close();
                }
                if (con != null) {
                    con.close();
                }

            }

        } else {

            try {

                con = DBConnection.getConnection();
                //con.setAutoCommit(true);

                String sqlCount = "SELECT COUNT(*) AS TOTAL FROM  CLA_AUDIT_HISTORY ";
                prepSt = con.prepareStatement(sqlCount);

                res = prepSt.executeQuery();
                while (res.next()) {

                    totalCount = res.getLong("TOTAL");
                }

                if (res != null) {
                    res.close();
                }
                if (prepSt != null) {
                    prepSt.close();
                }

                getHistoryRecordsQuery = "select * from(SELECT distinct HR.ID AS ID,HR.USER_ID AS USER_ID,HR.MODULE_ID AS MODULE_ID,\n"
                        + "HR.SECTION_ID AS SECTION_ID,HR.TASK_ID AS TASK_ID,HR.DESCRIPTION AS DESCRIPTION,\n"
                        + "HR.IP AS IP,HR.TIMESTAMP AS TIMESTAMP \n"
                        + "FROM CLA_AUDIT_HISTORY HR, CLA_MT_SECTION S,\n"
                        + "  CLA_MT_TASKS T )t1\n"
                        + "left outer join(select module_id,description as MD from CLA_MT_MODULES) t2\n"
                        + "on t1.MODULE_ID=t2.module_id\n"
                        + "left outer join(select SECTION_ID,section_name as SD from CLA_MT_SECTION) t3\n"
                        + "on t1.SECTION_ID=t3.SECTION_ID\n"
                        + "left outer join(select task_id,description as TD from CLA_MT_TASKS) t4\n"
                        + "on t1.TASK_ID=t4.task_id " + orderBy + " LIMIT " + first + "," + max;
                prepSt = con.prepareStatement(getHistoryRecordsQuery);

                res = prepSt.executeQuery();
                dataList = new ArrayList<UserActivityAuditDataBean>();

                UserActivityAuditDataBean dataBean = null;

                while (res.next()) {

                    dataBean = new UserActivityAuditDataBean();
                    dataBean.setId(res.getInt("ID") + "");
                    dataBean.setUsername(res.getString("USER_ID"));
                    dataBean.setModule(res.getString("MD"));
                    dataBean.setSection(res.getString("SD"));
                    dataBean.setOperation(res.getString("TD"));
                    dataBean.setMessage(res.getString("DESCRIPTION"));
                    dataBean.setIp(res.getString("IP"));
                    dataBean.setDatetime(Util.formatTimestamp(res.getTimestamp("TIMESTAMP")).toString());
                    dataBean.setFullCount(totalCount);
                    dataList.add(dataBean);

                }

                
            } catch (Exception e) {
                if (con != null) {
                    con.rollback();
                }
                throw e;

            } finally {
                if (res != null) {
                    res.close();
                }
                if (prepSt != null) {
                    prepSt.close();
                }
                if (con != null) {
                    con.close();
                }

            }

        }

        return dataList;
    }

    private Timestamp getTimeStamp(String date) throws Exception {

        SimpleDateFormat formtter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dtt = formtter.parse(date);

        return new java.sql.Timestamp(dtt.getTime());
    }
}
