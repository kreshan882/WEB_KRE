/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.configuration.service;

import com.epic.cla.configuration.bean.PasswordPolicyInputBean;
import com.epic.cla.customer.bean.CustomerManagementBean;
import com.epic.db.DBConnection;
import com.epic.init.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author nipun_t
 */
public class PasswordPolicyService {

    public void getProfileList(PasswordPolicyInputBean inputBean) throws Exception {
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

    public PasswordPolicyInputBean getPasswordPolicyDetails(PasswordPolicyInputBean bean) throws Exception {

        
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getQuery = null;
        long totalCount = 0;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            getQuery = "SELECT * FROM CLA_PASSWORD_POLICY";
            prepSt = con.prepareStatement(getQuery);
            res = prepSt.executeQuery();
            while (res.next()) {
                bean.setId(Integer.toString(res.getInt("PASSWORDPOLICY_ID")));
                bean.setMinlength(Integer.toString(res.getInt("MIN_LENGTH")));
                bean.setMaxlength(Integer.toString(res.getInt("MAX_LENGTH")));
                bean.setAllowspecialcharacters(res.getString("ALLOW_SPECIAL_CHARACTER"));
                bean.setMinimumspecialcharacters(Integer.toString(res.getInt("MIN_SPECIAL_CHARACTER")));
                bean.setMaximumspecialcharacters(Integer.toString(res.getInt("MAX_SPECIAL_CHARACTER")));
                bean.setMinimumuppercasecharacters(Integer.toString(res.getInt("MIN_UPPERCASE_CHARACTER")));
                bean.setMinimumnumericalcharacters(Integer.toString(res.getInt("MIN_NUMERICAL_CHARACTER")));
                bean.setDescription(res.getString("DESCRIPTION"));

                bean.setUpid(Integer.toString(res.getInt("PASSWORDPOLICY_ID")));
                bean.setUpminlength(Integer.toString(res.getInt("MIN_LENGTH")));
                bean.setUpmaxlength(Integer.toString(res.getInt("MAX_LENGTH")));
                bean.setUpallowspecialcharacters(res.getString("ALLOW_SPECIAL_CHARACTER"));
                bean.setUpminimumspecialcharacters(Integer.toString(res.getInt("MIN_SPECIAL_CHARACTER")));
                bean.setUpmaximumspecialcharacters(Integer.toString(res.getInt("MAX_SPECIAL_CHARACTER")));
                bean.setUpminimumuppercasecharacters(Integer.toString(res.getInt("MIN_UPPERCASE_CHARACTER")));
                bean.setUpminimumnumericalcharacters(Integer.toString(res.getInt("MIN_NUMERICAL_CHARACTER")));
                bean.setUpdescription(res.getString("DESCRIPTION"));
            }
            return bean;
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

    public boolean updateData(PasswordPolicyInputBean inputBean) throws Exception {
        boolean ok = false;
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String sql = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);

            sql = "UPDATE CLA_PASSWORD_POLICY SET MIN_LENGTH=?,MAX_LENGTH=?,ALLOW_SPECIAL_CHARACTER=?,MIN_SPECIAL_CHARACTER=?,"
                    + "MIN_UPPERCASE_CHARACTER=?,MIN_NUMERICAL_CHARACTER=?,DESCRIPTION=?,MAX_SPECIAL_CHARACTER=?  Where PASSWORDPOLICY_ID=?";
            prepSt = con.prepareStatement(sql);
            prepSt.setInt(1, Integer.parseInt(inputBean.getUpminlength()));
            prepSt.setInt(2, Integer.parseInt(inputBean.getUpmaxlength()));
            prepSt.setString(3, inputBean.getUpallowspecialcharacters());
            prepSt.setInt(4, Integer.parseInt(inputBean.getUpminimumspecialcharacters()));
            prepSt.setInt(5, Integer.parseInt(inputBean.getUpminimumuppercasecharacters()));
            prepSt.setInt(6, Integer.parseInt(inputBean.getUpminimumnumericalcharacters()));
            prepSt.setString(7, inputBean.getUpdescription());
            prepSt.setInt(8, Integer.parseInt(inputBean.getUpmaximumspecialcharacters()));
            prepSt.setInt(9, Integer.parseInt(inputBean.getUpid()));
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

    public void findData(PasswordPolicyInputBean bean) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String getUsersListQuery = null;
        try {

            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            getUsersListQuery = "SELECT * FROM CLA_PASSWORD_POLICY where PASSWORDPOLICY_ID=?";

            prepSt = con.prepareStatement(getUsersListQuery);
            prepSt.setInt(1, Integer.parseInt(bean.getUpid()));
            res = prepSt.executeQuery();
            while (res.next()) {
                bean.setUpid(Integer.toString(res.getInt("PASSWORDPOLICY_ID")));
                bean.setUpminlength(Integer.toString(res.getInt("MIN_LENGTH")));
                bean.setUpmaxlength(Integer.toString(res.getInt("MAX_LENGTH")));
                bean.setUpallowspecialcharacters(res.getString("ALLOW_SPECIAL_CHARACTER"));
                bean.setUpminimumspecialcharacters(Integer.toString(res.getInt("MIN_SPECIAL_CHARACTER")));
                bean.setUpmaximumspecialcharacters(Integer.toString(res.getInt("MAX_SPECIAL_CHARACTER")));
                bean.setUpminimumuppercasecharacters(Integer.toString(res.getInt("MIN_UPPERCASE_CHARACTER")));
                bean.setUpminimumnumericalcharacters(Integer.toString(res.getInt("MIN_NUMERICAL_CHARACTER")));
                bean.setUpdescription(res.getString("DESCRIPTION"));
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
}
