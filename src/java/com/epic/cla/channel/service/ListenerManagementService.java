/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.channel.service;

import com.epic.cla.channel.bean.ListenerBean;
import com.epic.cla.channel.bean.ListenerManagementInputBean;
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
public class ListenerManagementService {

    public List<ListenerBean> loadData(ListenerManagementInputBean bean, String orderBy, int from, int rows) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getChannelListQuery = null;
        List<ListenerBean> dataList = null;
        long totalCount = 0;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sqlCount = "select count(*) AS TOTAL FROM CLA_LISTENERS WHERE NAME LIKE ?";
            prepSt = con.prepareStatement(sqlCount);
            prepSt.setString(1, "%" + bean.getSearchname() + "%");

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

            getChannelListQuery = "SELECT L.ID,CL.DESCRIPTION AS LISTNER_TYPE,L.NAME,L.UIDS,L.PORT,L.READ_TIMEOUT,L.BACKLOG_SIZE,\n"
                    + "CO.DESCRIPTION AS CONN_TYPE,S.DESCRIPTION AS STATUS,L.KEEP_ALIVE_STATUS,L.HEADER_SIZE\n"
                    + "FROM CLA_MT_LISTENER_TYPE AS CL, CLA_LISTENERS AS L,\n"
                    + "CLA_MT_CONNECTION_TYPE AS CO , \n"
                    + "CLA_MT_STATUS AS S\n"
                    + " WHERE CL.CODE = L.LISTNER_TYPE AND CO.CODE = L.CONN_TYPE  AND S.CODE = L.STATUS AND L.NAME LIKE ?"
                    + orderBy + " LIMIT " + from + "," + rows;

            prepSt = con.prepareStatement(getChannelListQuery);
            prepSt.setString(1, "%" + bean.getSearchname() + "%");
            res = prepSt.executeQuery();

            dataList = new ArrayList<ListenerBean>();

            while (res.next()) {

                ListenerBean dataBean = new ListenerBean();
                dataBean.setId(Integer.toString(res.getInt("ID")));
                dataBean.setListenertype(res.getString("LISTNER_TYPE"));
                dataBean.setName(res.getString("NAME"));
                dataBean.setBacklogsize(Integer.toString(res.getInt("BACKLOG_SIZE")));
                dataBean.setPort(Integer.toString(res.getInt("PORT")));
                dataBean.setConntype(res.getString("CONN_TYPE"));
                dataBean.setRtimeout(Integer.toString(res.getInt("READ_TIMEOUT")));
                dataBean.setHeadersize(Util.getHeaderSize().get(Integer.toString(res.getInt("HEADER_SIZE"))));
                dataBean.setUids(res.getString("UIDS"));
                dataBean.setStatus(res.getString("STATUS"));
                dataBean.setKalivestatus(Util.getAliveStatus().get(res.getInt("KEEP_ALIVE_STATUS")));

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

    public boolean addData(ListenerManagementInputBean inputBean) throws Exception {
        Connection con = null;
        String sql;
        PreparedStatement preStat = null;
        boolean ok = false;

        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sql = "INSERT INTO CLA_LISTENERS(NAME, LISTNER_TYPE,UIDS,PORT,CONN_TYPE,READ_TIMEOUT,BACKLOG_SIZE,"
                    + "KEEP_ALIVE_STATUS,HEADER_SIZE,STATUS) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";

            preStat = con.prepareStatement(sql);
            preStat.setString(1, inputBean.getName());
            preStat.setInt(2, Integer.parseInt(inputBean.getListenertype()));
            preStat.setString(3, inputBean.getUids());
            preStat.setInt(4, Integer.parseInt(inputBean.getPort()));
            preStat.setInt(5, Integer.parseInt(inputBean.getContype()));
            preStat.setInt(6, Integer.parseInt(inputBean.getRtimeout()));
            preStat.setInt(7, Integer.parseInt(inputBean.getBacklogsize()));
            preStat.setInt(8, Integer.parseInt(inputBean.getKalivestatus()));
            preStat.setInt(9, Integer.parseInt(inputBean.getHeadersize()));
            preStat.setInt(10, Status.ACTIVE);
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

    public void findData(ListenerManagementInputBean bean) throws Exception {

        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "SELECT ID,LISTNER_TYPE,NAME,UIDS,PORT,CONN_TYPE,"
                    + "READ_TIMEOUT,BACKLOG_SIZE,KEEP_ALIVE_STATUS,HEADER_SIZE,STATUS"
                    + " FROM CLA_LISTENERS WHERE ID = ?";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1, bean.getId());
            res = prepSt.executeQuery();
            while (res.next()) {
                bean.setUpid(Integer.toString(res.getInt("ID")));
                bean.setUplistenertype(Integer.toString(res.getInt("LISTNER_TYPE")));
                bean.setUpname(res.getString("NAME"));
                bean.setUpuids(res.getString("UIDS"));
                bean.setUpport(Integer.toString(res.getInt("PORT")));
                bean.setUpcontype(Integer.toString(res.getInt("CONN_TYPE")));
                bean.setUprtimeout(Integer.toString(res.getInt("READ_TIMEOUT")));
                bean.setUpbacklogsize(Integer.toString(res.getInt("BACKLOG_SIZE")));
                bean.setUpheadersize(res.getString("HEADER_SIZE"));
                bean.setUpstatus(Integer.toString(res.getInt("STATUS")));
                bean.setUpkalivestatus(Integer.toString(res.getInt("KEEP_ALIVE_STATUS")));

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

    public boolean updateData(ListenerManagementInputBean inputBean) throws Exception {

        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sql = "UPDATE CLA_LISTENERS SET LISTNER_TYPE=?,NAME=?,UIDS=?,PORT=?,CONN_TYPE=?,"
                    + "READ_TIMEOUT=?,BACKLOG_SIZE=?,KEEP_ALIVE_STATUS=?,HEADER_SIZE=?,STATUS=?"
                    + " WHERE ID = ?";
            prepSt = con.prepareStatement(sql);
            prepSt.setInt(1, Integer.parseInt(inputBean.getUplistenertype()));
            prepSt.setString(2, inputBean.getUpname());
            prepSt.setString(3, inputBean.getUpuids());
            prepSt.setInt(4, Integer.parseInt(inputBean.getUpport()));
            prepSt.setInt(5, Integer.parseInt(inputBean.getUpcontype()));
            prepSt.setInt(6, Integer.parseInt(inputBean.getUprtimeout()));
            prepSt.setInt(7, Integer.parseInt(inputBean.getUpbacklogsize()));
            prepSt.setInt(8, Integer.parseInt(inputBean.getUpkalivestatus()));
            prepSt.setInt(9, Integer.parseInt(inputBean.getUpheadersize()));
            prepSt.setInt(10, Integer.parseInt(inputBean.getUpstatus()));
            prepSt.setInt(11, Integer.parseInt(inputBean.getUpid()));

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

    public boolean deleteData(ListenerManagementInputBean bean) throws Exception {

        PreparedStatement prepSt = null;
        Connection con = null;
        String deleteRecipient = null;
        boolean ok = false;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            deleteRecipient = "DELETE FROM CLA_LISTENERS  WHERE ID= ?";
            prepSt = con.prepareStatement(deleteRecipient);
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

    public static Map<Integer, String> getListenerTypeList() throws Exception {
        Map<Integer, String> channelTypeList = new HashMap<Integer, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sql = "SELECT CODE,DESCRIPTION FROM CLA_MT_LISTENER_TYPE WHERE LISTNER_CATEGORY = 1";//LISTNER_CATEGORY 1 = ENGIN_LISTNER
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                channelTypeList.put(rs.getInt("CODE"), rs.getString("DESCRIPTION"));
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

        return channelTypeList;
    }

    public static Map<Integer, String> getConTypeList() throws Exception {
        Map<Integer, String> conTypeList = new HashMap<Integer, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sql = "SELECT CODE,DESCRIPTION FROM CLA_MT_CONNECTION_TYPE";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                conTypeList.put(rs.getInt("CODE"), rs.getString("DESCRIPTION"));
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

        return conTypeList;
    }

}
