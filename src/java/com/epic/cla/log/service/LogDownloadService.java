/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.log.service;

import com.epic.cla.log.bean.LogDownloadInputBean;
import com.epic.cla.log.bean.LogFileDataBean;
import com.epic.db.DBConnection;
import com.epic.init.InitConfigValue;
import static com.epic.init.InitConfigValue.LOGPATH;
import com.epic.util.Util;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LogDownloadService {

    public List<LogFileDataBean> loadData(LogDownloadInputBean bean, String orderBy, int first, int max) throws Exception {

        String LOGPATH = InitConfigValue.LOGBACKUPPATH;

        List<LogFileDataBean> dataList = null;
        long totalCount = 0;
        try {

//            String folder = "";
//
//            if ("02".equals(bean.getLogFileSelection())) {
//                folder = "errors";
//            } else {
//                folder = "infors";
//
//            }

//            String logArchivePath = LOGPATH + "logs/web/" + folder + "/";
            String logArchivePath = LOGPATH;
            File f = new File(Util.getOSLogPath(logArchivePath));
            final File files[] = f.listFiles();
            totalCount = files.length;

            dataList = new ArrayList<LogFileDataBean>();

            if ((first + max) > files.length) {
                max = files.length - first - 1;
            }

            LogFileDataBean dataBean = null;

            for (int i = first; i <= (first + max); i++) {

                dataBean = new LogFileDataBean();
                dataBean.setLogFileName(files[i].getName());
                dataBean.setSize(files[i].length() / 1024 + "kB");
                dataBean.setPath(files[i].getAbsolutePath());

                dataBean.setFullCount(totalCount);
                dataBean.setDate(getLastModified(files[i]));
                dataList.add(dataBean);
            }
        } catch (Exception e) {
            throw e;
        }
        return dataList;
    }

    public File generateTodayZip() throws Exception {

        Process createArchive;
        File outputFile = null;
        try {
            createArchive = Runtime.getRuntime().exec("/opt/epiccla/bin/epicclalogbackup.sh " + "/opt/epiccla" + "  " + "/opt/epiccla");
            createArchive.waitFor();
            outputFile = new File(LOGPATH + "tmp/" + generateTodayFileName() + ".zip");
        } catch (Exception e) {
            throw e;
        }
        return outputFile;
    }

    private String generateTodayFileName() throws Exception {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat ft = new SimpleDateFormat("MM-dd-YY");
        return ft.format(date);
    }

    private String getLastModified(File f) throws Exception {
        String df = "";
        if (f.exists()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            df = sdf.format(f.lastModified());
        }
        return df;
    }

    private String getLogPath() throws Exception {
        String logPath = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sql = "SELECT LOG_BACKUP_PATH FROM CLA_MT_CONFIGURATION WHERE VERSION='1.0'";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                logPath = rs.getString("LOG_BACKUP_PATH");
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

        return logPath;
    }

}
