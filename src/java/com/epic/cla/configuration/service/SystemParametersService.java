/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.configuration.service;

import com.epic.cla.configuration.bean.SystemParametersInputBean;
import com.epic.db.DBConnection;
import com.epic.init.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author nipun_t
 */
public class SystemParametersService {

    public void getProfileList(SystemParametersInputBean inputBean) throws Exception {
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

    public void getConfiguration(SystemParametersInputBean inputBean) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(true);
            String sql = " SELECT * FROM CLA_MT_CONFIGURATION";
            ps = connection.prepareStatement(sql);
            result = ps.executeQuery();

            while (result.next()) {
                inputBean.setVersion(result.getString("VERSION"));
                inputBean.setLoglevel(Integer.toString(result.getInt("LOG_LEVEL")));
                inputBean.setMinpoolsize(Integer.toString(result.getInt("MIN_POOL")));
                inputBean.setMaxpoolsize(Integer.toString(result.getInt("MAX_POOL")));
                inputBean.setMaxqueuesize(Integer.toString(result.getInt("MAX_POOL_QUEUE")));
                inputBean.setNooflogfile(Integer.toString(result.getInt("NO_OF_LOG_FILE")));
                inputBean.setServiceport(Integer.toString(result.getInt("SERVICE_PORT")));
                inputBean.setOperationport(Integer.toString(result.getInt("OPERATION_PORT")));
                inputBean.setServiceportreadtimeout(Integer.toString(result.getInt("SERVICE_READ_TIMEOUT")));
                inputBean.setLogbackupstatus(Integer.toString(result.getInt("LOG_BACKUP_STATUS")));
                inputBean.setLogbackuppath(result.getString("LOG_BACKUP_PATH"));
                inputBean.setOrdlength(result.getString("ORD_LENTH"));
                inputBean.setSeclength(result.getString("SEC_LENTH"));
                inputBean.setMaxpinretry(result.getString("MAX_PIN_RETRY"));
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

    public boolean updateData(SystemParametersInputBean inputBean) throws SQLException, Exception {
        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            sql = "UPDATE CLA_MT_CONFIGURATION SET LOG_LEVEL=?,MIN_POOL=?,MAX_POOL=?,MAX_POOL_QUEUE=?,"
                    + "NO_OF_LOG_FILE=?,SERVICE_PORT=?,OPERATION_PORT=?,SERVICE_READ_TIMEOUT=?,LOG_BACKUP_STATUS=?,LOG_BACKUP_PATH=?,ORD_LENTH=?,SEC_LENTH=?,MAX_PIN_RETRY=?  WHERE VERSION=?";
            prepSt = con.prepareStatement(sql);
            
            prepSt.setInt(1, Integer.parseInt(inputBean.getLoglevel()));
            prepSt.setInt(2, Integer.parseInt(inputBean.getMinpoolsize()));
            prepSt.setInt(3, Integer.parseInt(inputBean.getMaxpoolsize()));
            prepSt.setInt(4, Integer.parseInt(inputBean.getMaxqueuesize()));
            prepSt.setInt(5, Integer.parseInt(inputBean.getNooflogfile()));
            prepSt.setInt(6, Integer.parseInt(inputBean.getServiceport()));
            prepSt.setInt(7, Integer.parseInt(inputBean.getOperationport()));
            prepSt.setInt(8, Integer.parseInt(inputBean.getServiceportreadtimeout()));
            prepSt.setInt(9, Integer.parseInt(inputBean.getLogbackupstatus()));
            prepSt.setString(10, inputBean.getLogbackuppath());
            prepSt.setString(11, inputBean.getOrdlength());
            prepSt.setString(12, inputBean.getSeclength());
            prepSt.setString(13, inputBean.getMaxpinretry());
            prepSt.setString(14, inputBean.getVersion());

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
