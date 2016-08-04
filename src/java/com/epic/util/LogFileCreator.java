/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.util;

import com.epic.login.bean.ConfigurationBean;
import com.epic.login.bean.SessionUserBean;
import com.epic.util.Util;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author tharaka
 */
public class LogFileCreator {

//    private static LogFilePathBean logFilePathBean = null;
    private static String path = null;
    private static Exception ioe;

    /**
     * Writing the success log file
     *
     * @param msg - message which is required to be written
     */
    public static void writeInfoToLog(String msg) throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        SessionUserBean sub = (SessionUserBean) session.getAttribute("SessionObject");
        String errorpath = sub.getLogFilePath() + "infors";
        //System.out.println("logpath = " + sub.getLogFilePath());

        BufferedWriter bw = null;
        String newLine = "";
        msg = newLine + "\n" + getTime() + "\n" + msg;

        try {
            String osinfopath = Util.getOSLogPath(errorpath);
            String filename = osinfopath + Util.getLocalDate() + "_EpicCA_Infor";
            //System.out.println("osinfopath=" + osinfopath);
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();

        } catch (Exception ioe) {
            ioe.printStackTrace();
            throw ioe;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * Writing error logs in the file
     *
     * @param aThrowable - Exception which is required to be written
     */
    public static void writeErrorToLog(Throwable aThrowable) {
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        SessionUserBean sub = (SessionUserBean) session.getAttribute("SessionObject");
        String infopath = sub.getLogFilePath() + "errors";
        //System.out.println("logpath = " + sub.getLogFilePath());

        BufferedWriter bw = null;
        String msg = "";
        String newLine = "";

        try {
            String osinfopath = Util.getOSLogPath(infopath);
            //System.out.println("osinfopath=" + osinfopath);
            String filename = osinfopath + Util.getLocalDate() + "_EpicCA_Error";

            msg = newLine + "\n" + getTime() + "\n" + getStackTrace(aThrowable);
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            ioe.printStackTrace();
            //throw ioe;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    //throw ioe2;
                }
            }
        }
    }

    /**
     * Retrieve the time
     *
     * @return time
     */
    private static String getTime() {

        GregorianCalendar now = new GregorianCalendar();
        return now.getTime().toString();
    }

    /**
     * Retrieve the stack traces of errors
     *
     * @param aThrowable - exception
     * @return re - String exception
     */
    private static String getStackTrace(Throwable aThrowable) throws Exception {
        String re = "";
        Writer result = null;
        PrintWriter printWriter = null;
        try {
            result = new StringWriter();
            printWriter = new PrintWriter(result);

            aThrowable.printStackTrace(printWriter);
            re = result.toString();
            result.close();
            printWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {

            try {
                if (result != null) {
                    result.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return re;
    }
}
