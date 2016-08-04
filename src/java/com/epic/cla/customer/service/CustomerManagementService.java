/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.customer.service;

import com.epic.cla.customer.bean.CostomerManagementInputBean;
import com.epic.cla.customer.bean.CustomerManagementBean;
import com.epic.db.DBConnection;
import com.epic.init.Status;
import com.epic.util.Util;
import java.sql.Connection;
import java.sql.Date;
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
public class CustomerManagementService {

    public void getProfileList(CostomerManagementInputBean inputBean) throws Exception {
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

    public boolean addData(CostomerManagementInputBean inputBean) throws Exception {
        Connection con = null;
        String sql;
        PreparedStatement preStat = null;
        boolean ok = false;

        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
                sql = "INSERT INTO CLA_CUSTOMER(NAME,DOB,NIC,CIF,MOBILE_NO,ACCOUNT_NUMBER,"
                        + "USER_TYPE,RISK_PROFILE_ID,BATCH_NO,SMS_PROFILE_ID,VALIDATE_RECIPIENT,ADDRESS,BRANCH,STATUS,REG_DATE_TIME,LISTENER_TYPE) "
                        + " VALUES(?,?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?)";

            preStat = con.prepareStatement(sql);

            preStat.setString(1, inputBean.getName());
            preStat.setString(2, inputBean.getDateofbirth());
            preStat.setString(3, inputBean.getNic());
            preStat.setString(4, inputBean.getCif());
            preStat.setString(5, inputBean.getMobile());
            preStat.setString(6, inputBean.getAccountnumber());

            preStat.setString(7, inputBean.getUsertype());
            preStat.setInt(8, Integer.parseInt(inputBean.getRiskprofileid()));
            preStat.setString(9, "000000");
            preStat.setInt(10, Integer.parseInt(inputBean.getSmsprofileid()));
            preStat.setInt(11, Integer.parseInt(inputBean.getValidaterecipient()));
            preStat.setString(12, inputBean.getActualaddress());//newly edited by dimuthu from Address to Actualaddress
            preStat.setString(13, inputBean.getBranch());
            preStat.setInt(14, Status.ACTIVE);
            preStat.setDate(15, (Date) Util.getLocalDate());
            preStat.setInt(16, 2);
            //System.out.println(inputBean.getName());
            //System.out.println(inputBean.getDateofbirth());
            //System.out.println(inputBean.getNic());
            //System.out.println(inputBean.getCif());
            //System.out.println(inputBean.getMobile());
            //System.out.println(inputBean.getAccountnumber());
            //System.out.println(inputBean.getUsertype());
            //System.out.println(inputBean.getRiskprofileid());
            //System.out.println("000000");
            //System.out.println(inputBean.getSmsprofileid());
            //System.out.println(inputBean.getValidaterecipient());
            //System.out.println(inputBean.getActualaddress());
            //System.out.println(inputBean.getBranch());
            //System.out.println(Status.ACTIVE);
            //System.out.println((Date) Util.getLocalDate());
            //System.out.println("2");
            //System.out.println(sql);
            int n = preStat.executeUpdate();
            if (n >= 0) {

                preStat.close();
                preStat = null;
                if (inputBean.getUsertype().equals("02")) {// 02 = user merchant
                    sql = "UPDATE CLA_USER SET CUSTOMER_ID=? Where USERNAME=?";
                    preStat = con.prepareStatement(sql);
                    preStat.setInt(1, (this.getMaxCustomerID()));
                    preStat.setString(2, inputBean.getMerchantId());

                    int k = preStat.executeUpdate();
                    if (k > 0) {
                        ok = true;
                    }
                } else {
                    ok = true;
                }

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

    public List<CustomerManagementBean> loadData(CostomerManagementInputBean bean, String orderBy, int from, int rows) throws Exception {

        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        List<CustomerManagementBean> dataList = null;
        long totalCount = 0;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sqlCount = "select count(*) AS TOTAL FROM CLA_CUSTOMER where NAME LIKE ? AND CHANNEL_TYPE=0";
            prepSt = con.prepareStatement(sqlCount);
            prepSt.setString(1, "%" + bean.getCname() + "%");
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

            getUsersListQuery = "SELECT * FROM (SELECT ut.DESCRIPTION AS USERTYPENAME,rp.NAME AS RISKPROFILEID,stp.DESCRIPTION AS SMSPROFILEID,u.CUSTOMER_ID,u.NAME,u.NIC,u.CIF,"
                    + " u.MOBILE_NO,u.ACCOUNT_NUMBER,u.USER_TYPE,u.RISK_PROFILE_ID,u.SMS_PROFILE_ID,u.VALIDATE_RECIPIENT,u.ADDRESS,u.BRANCH,u.STATUS,u.REG_DATE_TIME,u.REG_SMS"
                    + " FROM CLA_CUSTOMER u,CLA_CUSTOMER_RISK_PROFILE rp,CLA_MT_USER_TYPE ut,CLA_MT_STATUS st,CLA_SMS_TEMPLATE_PROFILE stp"
                    + " where u.USER_TYPE=ut.CODE AND u.RISK_PROFILE_ID=rp.ID AND u.SMS_PROFILE_ID=stp.CODE AND u.STATUS= st.CODE  AND CHANNEL_TYPE=0) as t1"
                    + " left outer join (select NAME AS USERNAME ,CUSTOMER_ID AS USERID FROM CLA_USER) as ua"
                    + " on t1.CUSTOMER_ID=ua.USERID"
                    + " WHERE t1.NAME LIKE ? " + orderBy + " LIMIT " + from + "," + rows;

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1, "%" + bean.getCname() + "%");
            res = prepSt.executeQuery();

            dataList = new ArrayList<CustomerManagementBean>();

            while (res.next()) {

                CustomerManagementBean dataBean = new CustomerManagementBean();
                dataBean.setCustomerid(Integer.toString(res.getInt("CUSTOMER_ID")));
                dataBean.setName(res.getString("NAME"));
                dataBean.setUsername(res.getString("USERNAME"));
                dataBean.setNic(res.getString("NIC"));
                dataBean.setCif(res.getString("CIF"));
                dataBean.setMobile(res.getString("MOBILE_NO"));
                dataBean.setAccountnumber(res.getString("ACCOUNT_NUMBER"));
                dataBean.setUsertype(Util.getCustomerUserTypeList().get(res.getString("USER_TYPE")));
                dataBean.setRiskprofileid(getRiskProfileIDList().get(res.getString("RISK_PROFILE_ID")));
                dataBean.setSmsprofileid(getSmsProfileList().get(res.getString("SMS_PROFILE_ID")));
                dataBean.setValidaterecipient(Util.getValidateStatusList().get(res.getString("VALIDATE_RECIPIENT")));
                dataBean.setRegmsg(res.getString("REG_SMS"));

                dataBean.setAddress(res.getString("ADDRESS"));
                dataBean.setBranch(res.getString("BRANCH"));
                dataBean.setStatus(res.getString("STATUS"));

                dataBean.setRegDate(res.getString("REG_DATE_TIME"));

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

    public static Map<String, String> getRiskProfileIDList() throws SQLException, Exception {
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

//            getUsersListQuery = "select * from CLA_CUSTOMER u where u.CUSTOMER_ID LIKE ?";
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

    public static Map<String, String> getRiskProfileIDList(CostomerManagementInputBean bean) throws SQLException, Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        Map<String, String> basicStatus = new HashMap<String, String>();
        List<CustomerManagementBean> dataList = null;
        String WHERE = "";
        long totalCount = 0;
        try {
            if ("02".equals(bean.getUsertype())) {//02 merchant
                WHERE = " WHERE TRANSFER_MODE = 2";
                bean.setUsertype("02");
            } else {
                WHERE = " WHERE TRANSFER_MODE = 1";
                bean.setUsertype("03");
            }
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "SELECT * FROM CLA_CUSTOMER_RISK_PROFILE" + WHERE + " AND STATUS=1";

//            getUsersListQuery = "select * from CLA_CUSTOMER u where u.CUSTOMER_ID LIKE ?";
            prepSt = con.prepareStatement(getUsersListQuery);
            res = prepSt.executeQuery();
            basicStatus.clear();
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

    public Map<? extends String, ? extends String> getSmsProfileList() throws SQLException, Exception {
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

//            getUsersListQuery = "select * from CLA_CUSTOMER u where u.CUSTOMER_ID LIKE ?";
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

    public boolean updateData(CostomerManagementInputBean inputBean) throws SQLException, Exception {
        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            sql = "UPDATE CLA_CUSTOMER SET RISK_PROFILE_ID=?,SMS_PROFILE_ID=?,VALIDATE_RECIPIENT=?,ADDRESS=?,STATUS=?,MOBILE_NO=?  Where CUSTOMER_ID=?";
            prepSt = con.prepareStatement(sql);
            prepSt.setInt(1, Integer.parseInt(inputBean.getUpriskprofileid()));
            prepSt.setInt(2, Integer.parseInt(inputBean.getUpsmsprofileid()));
            prepSt.setInt(3, Integer.parseInt(inputBean.getUpvalidaterecipient()));
            prepSt.setString(4, inputBean.getUpaddress());
//            prepSt.setString(10, inputBean.getUpbranch());
            prepSt.setInt(5, Integer.parseInt(inputBean.getUpstatus()));
            prepSt.setString(6, inputBean.getUpmobile());
            prepSt.setInt(7, Integer.parseInt(inputBean.getUpcustomerid()));

            int n = prepSt.executeUpdate();
            if (n > 0) {
                ok = true;
//                
//                prepSt.close();
//                prepSt = null;
//                System.out.println(" cid " + inputBean.getUpcustomerid() + "  mid " + inputBean.getUpmerchantId() + "  omid " + inputBean.getOldupmerchantId());
//                if (inputBean.getUpusertype().equals("02")) {// 02 = user merchant
//                    sql = "UPDATE CLA_USER SET CUSTOMER_ID=? Where USERNAME=?";
//                    prepSt = con.prepareStatement(sql);
//                    prepSt.setInt(1, Integer.parseInt(inputBean.getUpcustomerid()));
//                    prepSt.setString(2, inputBean.getUpmerchantId());
//
//                    int k = prepSt.executeUpdate();
//                    if (k > 0) {
//                        
//                        prepSt.close();
//                        prepSt = null;
//                        sql = "UPDATE CLA_USER SET CUSTOMER_ID=? Where USERNAME=?";
//                        prepSt = con.prepareStatement(sql);
//                        prepSt.setInt(1, -1);
//                        prepSt.setString(2, inputBean.getOldupmerchantId());
//                        int r = prepSt.executeUpdate();
//                        if (r > 0) {
//                            ok = true;
//                        }
//                    }
//                } else if (inputBean.getUpusertype().equals("03")) {// 03 = user type customer
//                    sql = "UPDATE CLA_USER SET CUSTOMER_ID=? Where USERNAME=?";
//                    prepSt = con.prepareStatement(sql);
//                    prepSt.setInt(1, -1);
//                    prepSt.setString(2, inputBean.getOldupmerchantId());
//
//                    int k = prepSt.executeUpdate();
//                    if (k > 0) {
//                        ok = true;
//                    }
//                } else {
//                    ok = true;
//                }
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

    public void findData(CostomerManagementInputBean bean) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "SELECT u.CUSTOMER_ID,u.NAME,u.DOB,u.NIC,u.CIF,"
                    + "u.MOBILE_NO,u.ACCOUNT_NUMBER,u.USER_TYPE,u.RISK_PROFILE_ID,u.SMS_PROFILE_ID,"
                    + "u.VALIDATE_RECIPIENT,u.ADDRESS,u.BRANCH,u.STATUS,u.REG_DATE_TIME"
                    + " FROM CLA_CUSTOMER u"
                    + " where u.CUSTOMER_ID = ? ";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1, bean.getCustomerid());
            res = prepSt.executeQuery();
            while (res.next()) {
                bean.setUpcustomerid(Integer.toString(res.getInt("CUSTOMER_ID")));
                bean.setUpname(res.getString("NAME"));
                bean.setUpdateofbirth(res.getString("DOB"));
                bean.setUpnic(res.getString("NIC"));
                bean.setUpcif(res.getString("CIF"));
                bean.setUpmobile(res.getString("MOBILE_NO"));
                bean.setUpaccountnumber(res.getString("ACCOUNT_NUMBER"));
                bean.setUpusertype(res.getString("USER_TYPE"));
                bean.setUsertype(res.getString("USER_TYPE"));
                bean.setUpriskprofileid(res.getString("RISK_PROFILE_ID"));
                bean.setUpsmsprofileid(res.getString("SMS_PROFILE_ID"));
                bean.setUpvalidaterecipient(res.getString("VALIDATE_RECIPIENT"));
                bean.setUpaddress(res.getString("ADDRESS"));
                bean.setUpbranch(res.getString("BRANCH"));
                bean.setUpstatus(res.getString("STATUS"));
            }

            if (prepSt != null) {
                prepSt.close();
            }
            if (res != null) {
                res.close();
            }

            prepSt = null;
            res = null;

            getUsersListQuery = "SELECT USERNAME FROM CLA_USER WHERE CUSTOMER_ID = ?";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setString(1, bean.getUpcustomerid());
            res = prepSt.executeQuery();
            while (res.next()) {
                bean.setUpmerchantId(res.getString("USERNAME"));
                bean.setOldupmerchantId(res.getString("USERNAME"));
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

    public boolean deleteData(CostomerManagementInputBean bean) throws SQLException, Exception {
        PreparedStatement prepSt = null;
        Connection con = null;
        String deleteUser = null;
        boolean ok = false;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            deleteUser = "DELETE FROM CLA_CUSTOMER  WHERE CUSTOMER_ID= ?";
            prepSt = con.prepareStatement(deleteUser);
            prepSt.setString(1, bean.getCustomerid());
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

    public void getMerchantList(CostomerManagementInputBean inputBean) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(true);
            String sql = " SELECT USERNAME,NAME FROM CLA_USER where STATUS=? and CUSTOMER_ID =" + -1 + " and USER_TYPE = " + 2;
            ps = connection.prepareStatement(sql);
            ps.setInt(1, Status.ACTIVE);
            result = ps.executeQuery();

            while (result.next()) {
                inputBean.getMerchantList().put(result.getString("USERNAME"), result.getString("NAME"));
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

    public int getMaxCustomerID() throws Exception {

        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getQuery = null;
        int max = 0;
        String sqlCount = "";

        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            sqlCount = "SELECT MAX(CUSTOMER_ID) AS MAX FROM CLA_CUSTOMER";

            prepSt = con.prepareStatement(sqlCount);

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

    public boolean checkDublicateMobile (String mobile) throws Exception {

        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String query = null;
        boolean ok = false;

        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            query = "SELECT NIC FROM CLA_CUSTOMER Where MOBILE_NO =? ";

            prepSt = con.prepareStatement(query);
            prepSt.setString(1, mobile);
            res = prepSt.executeQuery();
            if (res.next()) {
                ok = true;
            }
//            while (res.next()) {
//                res.getString("BIN");
//
//            }

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
        return ok;
    }

      public boolean checkDublicateMobileUpdate (String mobile,String ID) throws Exception {

        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String query = null;
        boolean ok = false;

        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            query = "SELECT * FROM CLA_CUSTOMER Where MOBILE_NO =? AND CUSTOMER_ID!=?";
            System.out.println(mobile+" "+ID);

            prepSt = con.prepareStatement(query);
            prepSt.setString(1, mobile);
            prepSt.setInt(2, Integer.parseInt(ID));
            res = prepSt.executeQuery();
            if (res.next()) {
                ok = true;
            }
//            while (res.next()) {
//                res.getString("BIN");
//
//            }

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
            String sql = "SELECT CODE,DESCRIPTION FROM CLA_MT_LISTENER_TYPE WHERE LISTNER_CATEGORY = 2";//LISTNER_CATEGORY 1 = ENGIN_LISTNER
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

}
