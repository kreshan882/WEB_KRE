/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author kreshan
 */

public class ExcelCommon {


    public static XSSFCellStyle getFontBoldedCell(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }


    public static XSSFCellStyle getFontBoldedUnderlinedCell(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            font.setUnderline(XSSFFont.U_SINGLE);
            
        XSSFCellStyle style = workbook.createCellStyle();
            style.setFont(font);
            
        return style;
    }


    public static XSSFCellStyle getColumnHeadeCell(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            
        XSSFCellStyle style = workbook.createCellStyle();
            style.setFont(font);
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            style.setBorderTop(XSSFCellStyle.BORDER_THIN);
            style.setBorderRight(XSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            
        return style;
    }



    public static XSSFCellStyle getRowColumnCell(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
            style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            style.setBorderTop(XSSFCellStyle.BORDER_THIN);
            style.setBorderRight(XSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            
        return style;
    }

    /**
     * return XSSFCellSytyle witch contains the alignment according to the
     * parameter value ' short alignment ' contain and if XSSFCellStyle
     * parameter is null then it create new style with the alignment that came
     * with the alignment parameter. Parameter cellStyel is not mandatory and if
     * cellStyle not null then it clone and set the alignment. Both workbook and
     * alignment is mandatory.
     *
     * @param workbook
     * @param cellStyle
     * @param alignment
     * @return XSSFCellStyle
     */
    public static XSSFCellStyle getAligneCell(XSSFWorkbook workbook, XSSFCellStyle cellStyle, short alignment) {
        XSSFCellStyle style = workbook.createCellStyle();
        if (cellStyle != null) {
            style.cloneStyleFrom(cellStyle);
        }
        style.setAlignment(alignment);
        return style;
    }

    
    
        public static ByteArrayOutputStream zipFiles(File[] listFiles) throws Exception {
        byte[] buffer;
        ByteArrayOutputStream outputStream = null;
        ZipOutputStream zipOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            zipOutputStream = new ZipOutputStream(outputStream);
            for (File file : listFiles) {
                buffer = new byte[(int) file.length()];
                fileInputStream = new FileInputStream(file);
                fileInputStream.read(buffer, 0, (int) file.length());
                ZipEntry ze = new ZipEntry(file.getName());

                zipOutputStream.putNextEntry(ze);
                zipOutputStream.write(buffer);
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (zipOutputStream != null) {
                zipOutputStream.finish();
                zipOutputStream.close();
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
        return outputStream;
    }
}
