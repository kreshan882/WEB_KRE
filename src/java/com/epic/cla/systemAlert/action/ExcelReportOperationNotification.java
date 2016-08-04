/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.systemAlert.action;

import com.epic.cla.systemAlert.bean.ViewOperationNotificationBean;
import com.epic.cla.systemAlert.bean.ViewOperationNotificationInputBean;
import com.epic.db.DBConnection;
import com.epic.util.ExcelCommon;
import com.epic.util.Util;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author dimuthu_h
 */
public class ExcelReportOperationNotification {
    
    private static final int columnCount = 4;
    private static final int headerRowCount = 0;

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static Object generateExcelReport(ViewOperationNotificationInputBean bean) throws Exception {
        PreparedStatement prepSt = null;
        ResultSet res = null;
        Connection con = null;
        String query = null;
        Object returnObject = null;
        List<ViewOperationNotificationBean> dataList = null;
        try {
            String directory = Util.getOSLogPath("/opt/clatmpFE/operationAlertsTemporary");

            File file = new File(directory);
            deleteDir(file);

            int count = 0;
            con = DBConnection.getConnection();
            //con.setAutoCommit(true);
            String sqlCount = "SELECT COUNT(*) AS TOTAL FROM CLA_OPERATION_ALERT WHERE OPERATION_CODE LIKE ? AND  date(TIME_STAMP) BETWEEN ? AND ?";
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
                prepSt.setString(1, "%" + bean.getOperation() + "%");
                prepSt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
                prepSt.setTimestamp(3, new java.sql.Timestamp(date2.getTime()));

            res = prepSt.executeQuery();
            if (res.next()) {
                count = res.getInt("TOTAL");
            }
            if (prepSt != null) {
                prepSt.close();
            }
            if (res != null) {
                res.close();
            }

            if (count == 0) {
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Operation Alert Report");
                sheet.autoSizeColumn(count);
                ExcelReportOperationNotification.createExcelTableHeaderSection(workbook, 0);
                returnObject = workbook;
            }

            if (count > 0) {
                long maxRow = Long.parseLong("10000");
                int currRow = headerRowCount;
                int fileCount = 0;
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Operation Alert Report");
                currRow = ExcelReportOperationNotification.createExcelTableHeaderSection(workbook, currRow);
                int selectRow = 10000;
                int numberOfTimes = count / selectRow;
                if ((count % selectRow) > 0) {
                    numberOfTimes += 1;
                }
                int from = 0;
                int listrownumber = 1;

                query = "SELECT OP.DESCRIPTION AS OPERATION_CODE, OA.USERNAME,OA.IP,S.DESCRIPTION AS STATUS,OA.READ_STATUS,OA.DESCRIPTION,OA.TIME_STAMP"
                        + " FROM CLA_MT_OPERATION AS OP, CLA_OPERATION_ALERT AS OA,CLA_MT_STATUS AS S"
                        + " WHERE OP.CODE = OA.OPERATION_CODE AND S.CODE = OA.STATUS AND OA.OPERATION_CODE LIKE ?  AND date(OA.TIME_STAMP) BETWEEN ? AND ? ";
                       

                prepSt = con.prepareStatement(query);
                prepSt.setString(1, "%" + bean.getOperation() + "%");
                prepSt.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
                prepSt.setTimestamp(3, new java.sql.Timestamp(date2.getTime()));

                res = prepSt.executeQuery();

                dataList = new ArrayList<ViewOperationNotificationBean>();
                for (int i = 0; i < numberOfTimes; i++) {
                    while (res.next()) {

                        ViewOperationNotificationBean dataBean = new ViewOperationNotificationBean();
                       
                        dataBean.setOperationcode(res.getString("OPERATION_CODE"));
                        dataBean.setUsername(res.getString("USERNAME"));
                        dataBean.setIp(res.getString("IP"));
                        dataBean.setTimestamp(Util.formatTimestamp(res.getTimestamp("TIME_STAMP")).toString());
                        
                        dataBean.setFullCount(count);
//                        dataList.add(dataBean);

                        if (currRow + 1 > maxRow) {
                            fileCount++;
                            ExcelReportOperationNotification.writeTemporaryFile(workbook, fileCount, directory);
                            workbook = ExcelReportOperationNotification.createExcelTopSection(bean);
                            sheet = workbook.getSheetAt(0);
                            currRow = headerRowCount;
                            
                            ExcelReportOperationNotification.createExcelTableHeaderSection(workbook, currRow);
                        }
                        currRow = ExcelReportOperationNotification.createExcelTableBodySection(workbook, dataBean, currRow, listrownumber);
                        
                        listrownumber++;
                    }

                    from = from + selectRow;
                }

                if (fileCount > 0) {
                    fileCount++;
                    ExcelReportOperationNotification.writeTemporaryFile(workbook, fileCount, directory);
                    ByteArrayOutputStream outputStream = ExcelCommon.zipFiles(file.listFiles());
                    returnObject = outputStream;
                } else {
                    for (int i = 0; i < columnCount; i++) {
                        //to auto size all column in the sheet
                        sheet.autoSizeColumn(i);
                    }
                    returnObject = workbook;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
                if (prepSt != null) {
                    prepSt.close();
                }
                if (res != null) {
                    res.close();
                }
            }
        }
        return returnObject;
    }

    private static XSSFWorkbook createExcelTopSection(ViewOperationNotificationInputBean inputBean) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("System Notification Report");
        return workbook;
    }

    private static int createExcelTableHeaderSection(XSSFWorkbook workbook, int currrow) throws Exception {
        XSSFCellStyle columnHeaderCell = ExcelCommon.getColumnHeadeCell(workbook);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = sheet.createRow(currrow++);

        XSSFCell cell = row.createCell(0);
        cell.setCellValue("OPERATION NAME");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(1);
        cell.setCellValue("USER NAME");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(2);
        cell.setCellValue("IP");
        cell.setCellStyle(columnHeaderCell);

        cell = row.createCell(3);
        cell.setCellValue("TIMESTAMP");
        cell.setCellStyle(columnHeaderCell);

        return currrow;
    }

    private static void writeTemporaryFile(XSSFWorkbook workbook, int fileCount, String directory) throws Exception {
        File file;
        FileOutputStream outputStream = null;
        try {
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 0; i < columnCount; i++) {
                //to auto size all column in the sheet
                sheet.autoSizeColumn(i);
            }

            file = new File(directory);
            if (!file.exists()) {
                //System.out.println("Directory created or not : " + file.mkdirs());
            }
            if (fileCount > 0) {
                file = new File(directory + File.separator + "Syatem Notification Report_" + fileCount + ".xlsx");
            } else {
                file = new File(directory + File.separator + "Syatem Notification Report.xlsx");
            }
            outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
        } catch (IOException e) {
            throw e;
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    private static int createExcelTableBodySection(XSSFWorkbook workbook, ViewOperationNotificationBean dataBean, int currrow, int rownumber) throws Exception {
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFCellStyle rowColumnCell = ExcelCommon.getRowColumnCell(workbook);
        XSSFRow row = sheet.createRow(currrow++);

        XSSFCell cell = row.createCell(0);
        cell.setCellValue(dataBean.getOperationcode());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(1);
        cell.setCellValue(dataBean.getUsername());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(2);
        cell.setCellValue(dataBean.getIp());
        cell.setCellStyle(rowColumnCell);

        cell = row.createCell(3);
        cell.setCellValue(dataBean.getTimestamp());
        cell.setCellStyle(rowColumnCell);

        return currrow;
    }
    
}
