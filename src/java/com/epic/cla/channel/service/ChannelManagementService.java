/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.channel.service;

import com.epic.cla.channel.bean.ChannelBean;
import com.epic.cla.channel.bean.ChannelManagementInputBean;
import com.epic.db.DBConnection;
import com.epic.init.Status;
import com.epic.util.Util;
import java.sql.Connection;
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
public class ChannelManagementService {

    public List<ChannelBean> loadData(ChannelManagementInputBean bean, String orderBy, int from, int rows) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getChannelListQuery = null;
        List<ChannelBean> dataList = null;
        long totalCount = 0;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sqlCount = "select count(*) AS TOTAL FROM CLA_CHANNEL where NAME LIKE ?";
            prepSt = con.prepareStatement(sqlCount);
            prepSt.setString(1, "%" + bean.getName() + "%");

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

            getChannelListQuery = "SELECT C.ID,CH.DESCRIPTION AS CHANNEL_TYPE,C.NAME,C.IP,C.PORT,C.CONN_TIMEOUT,C.READ_TIMEOUT,\n"
                    + " CO.DESCRIPTION AS CONN_TYPE,C.HEADER_SIZE,S.DESCRIPTION AS STATUS,C.ISO_HEADER \n"
                    + " FROM CLA_MT_CHANNEL_TYPE AS CH, CLA_CHANNEL AS C,CLA_MT_CONNECTION_TYPE AS CO ,\n"
                    + "  CLA_MT_STATUS AS S\n"
                    + " WHERE CH.CODE = C.CHANNEL_TYPE AND CO.CODE = C.CONN_TYPE  AND S.CODE = C.STATUS AND C.NAME LIKE ?"
                    + orderBy + " LIMIT " + from + "," + rows;

            prepSt = con.prepareStatement(getChannelListQuery);
            prepSt.setString(1, "%" + bean.getSearchname() + "%");
            res = prepSt.executeQuery();

            dataList = new ArrayList<ChannelBean>();

            while (res.next()) {

                ChannelBean dataBean = new ChannelBean();
                dataBean.setId(Integer.toString(res.getInt("ID")));
                dataBean.setChanneltype(res.getString("CHANNEL_TYPE"));
                dataBean.setName(res.getString("NAME"));
                dataBean.setIp(res.getString("IP"));
                dataBean.setPort(Integer.toString(res.getInt("PORT")));
                dataBean.setIsoheader(res.getString("ISO_HEADER"));
                dataBean.setContimeout(Integer.toString(res.getInt("CONN_TIMEOUT")));
                dataBean.setRtimeout(Integer.toString(res.getInt("READ_TIMEOUT")));
                dataBean.setContype(res.getString("CONN_TYPE"));
                dataBean.setHeadersize(Util.getHeaderSize().get(Integer.toString(res.getInt("HEADER_SIZE"))));
                dataBean.setStatus(res.getString("STATUS"));
//                dataBean.setRegDate(res.getString("REGISTER_DATE"));

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

    public boolean addData(ChannelManagementInputBean inputBean) throws Exception {
        Connection con = null;
        String sql;
        PreparedStatement preStat = null;
        boolean ok = false;

        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sql = "INSERT INTO CLA_CHANNEL(NAME,CHANNEL_TYPE,IP,PORT,CONN_TIMEOUT,READ_TIMEOUT,CONN_TYPE,STATUS,HEADER_SIZE,ISO_HEADER"
                    + " ) VALUES(?,?,?,?,?,?,?,?,?,?)";

            preStat = con.prepareStatement(sql);

            preStat.setString(1, inputBean.getName());
            preStat.setInt(2, Integer.parseInt(inputBean.getChanneltype()));
            preStat.setString(3, inputBean.getIp());
            preStat.setInt(4, Integer.parseInt(inputBean.getPort()));
            preStat.setInt(5, Integer.parseInt(inputBean.getContimeout()));
            preStat.setInt(6, Integer.parseInt(inputBean.getRtimeout()));
            preStat.setInt(7, Integer.parseInt(inputBean.getContype()));
            preStat.setInt(8, Status.ACTIVE);
            preStat.setInt(9, Integer.parseInt(inputBean.getHeadersize()));
            preStat.setInt(10, Integer.parseInt(inputBean.getIsoheader()));
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

    public void findData(ChannelManagementInputBean bean) throws Exception {

        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "SELECT ID,CHANNEL_TYPE,NAME,IP,PORT,CONN_TIMEOUT,READ_TIMEOUT,CONN_TYPE,STATUS,HEADER_SIZE,ISO_HEADER"
                    + " FROM CLA_CHANNEL WHERE ID = ?";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1, bean.getId());

            res = prepSt.executeQuery();
            while (res.next()) {
                bean.setUpid(Integer.toString(res.getInt("ID")));
                bean.setUpchanneltype(Integer.toString(res.getInt("CHANNEL_TYPE")));
                bean.setUpname(res.getString("NAME"));
                bean.setUpip(res.getString("IP"));
                bean.setUpport(Integer.toString(res.getInt("PORT")));
                bean.setUpcontimeout(Integer.toString(res.getInt("CONN_TIMEOUT")));
                bean.setUprtimeout(Integer.toString(res.getInt("READ_TIMEOUT")));
                bean.setUpcontype(Integer.toString(res.getInt("CONN_TYPE")));
                bean.setUpisoheader(res.getString("ISO_HEADER"));
                bean.setUpstatus(Integer.toString(res.getInt("STATUS")));
                bean.setUpheadersize(Integer.toString(res.getInt("HEADER_SIZE")));

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

    public boolean updateData(ChannelManagementInputBean inputBean) throws Exception {

        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sql = "UPDATE CLA_CHANNEL SET CHANNEL_TYPE=?,NAME=?,IP=?,PORT=?,CONN_TIMEOUT=?"
                    + ",READ_TIMEOUT=?,CONN_TYPE=?,STATUS=?,HEADER_SIZE=?,ISO_HEADER=?"
                    + " WHERE ID = ?";
            prepSt = con.prepareStatement(sql);
            prepSt.setInt(1, Integer.parseInt(inputBean.getUpchanneltype()));
            prepSt.setString(2, inputBean.getUpname());
            prepSt.setString(3, inputBean.getUpip());
            prepSt.setInt(4, Integer.parseInt(inputBean.getUpport()));
            prepSt.setInt(5, Integer.parseInt(inputBean.getUpcontimeout()));
            prepSt.setInt(6, Integer.parseInt(inputBean.getUprtimeout()));
            prepSt.setInt(7, Integer.parseInt(inputBean.getUpcontype()));
            prepSt.setInt(8, Integer.parseInt(inputBean.getUpstatus()));
            prepSt.setInt(9, Integer.parseInt(inputBean.getUpheadersize()));
            prepSt.setString(10, inputBean.getUpisoheader());
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

    public boolean deleteData(ChannelManagementInputBean bean) throws Exception {

        PreparedStatement prepSt = null;
        Connection con = null;
        String deleteRecipient = null;
        boolean ok = false;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            deleteRecipient = "DELETE FROM CLA_CHANNEL  WHERE ID= ?";
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

    public static Map<Integer, String> getChannelTypeList() throws Exception {
        Map<Integer, String> channelTypeList = new HashMap<Integer, String>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sql = "SELECT CODE,DESCRIPTION FROM CLA_MT_CHANNEL_TYPE";
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
