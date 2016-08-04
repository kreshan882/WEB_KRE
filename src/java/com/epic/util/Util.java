/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.util;

import com.epic.init.InitConfigValue;
import com.epic.init.Status;
import com.epic.init.UserType;
import com.epic.login.bean.SessionUserBean;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author tharaka
 */
public class Util {

    public static boolean validateNAME(String text) throws Exception {
        return text.matches("^[A-Za-z]+(?:[ _-][A-Za-z]+)*$") && text.length() <= 50;
    }

    public static boolean validateNUMBER(String numericString) throws Exception {
        return numericString.matches("^[0-9]*$") && numericString.length() <= 15;
    }

    public static boolean validateEMAIL(String email) throws Exception {  //   VF2
        return email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$") && email.length() <= 50;
    }

    public static boolean validatePHONENO(String numericString) throws Exception { //   VF1
        return numericString.matches("^\\+(?:[0-9])*$") && numericString.length() <= 15;
    }

    public static boolean validatePORT(String numericString) throws Exception { //   VF1
        return numericString.matches("([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])");
    }

    public static boolean validateMOBILE(String numericString) throws Exception { //   VF1
        return numericString.matches("^[0-9]*$") && numericString.length() <= 10;
    }

    public static boolean validateAMOUNT(String numericString) throws Exception {
        return numericString.matches("^[0-9]+([.][0-9]{1,2})?");
    }

    public static boolean validateNIC(String nic) {
        return nic.matches("^[0-9]{9}[vVxX]$") && nic.length() >= 10;
    }

    public static boolean validateSPECIALCHAR(String specialChars) throws Exception {       // VF5
        return specialChars.matches("[~@#$&!~]+");
    }

    public static boolean validateDESCRIPTION(String text) {
        return text.matches("^(.*/)?(?:$|(.+?)(?:(\\.[^.]*$)|$))") && text.length() <= 150;
    }

    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static boolean validateIP(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    public static boolean validateURL(String text) {
        return text.matches("\\b(https?|ftp|file|ldap)://"
                + "[-A-Za-z0-9+&@#/%?=~_|!:,.;]"
                + "*[-A-Za-z0-9+&@#/%=~_|]") && text.length() <= 150;
    }

    public static boolean validateSTRING(String text) throws Exception {
        return text.matches("^[a-zA-Z0-9]+$") && text.length() <= 200;
    }

    public static Date convertStringToDate(String dateString) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date date = format.parse(dateString);
        return date;
    }

    public static java.sql.Date convertStringToDBDate(String date) throws Exception {

        SimpleDateFormat formtter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dtt = formtter.parse(date);
        return new java.sql.Date(dtt.getTime());

    }

    public static Map<Integer, String> getBasicStatus() {
        Map<Integer, String> basicStatus = new HashMap<Integer, String>();
        basicStatus.put(Status.ACTIVE, "ACTIVE");
        basicStatus.put(Status.INACTIVE, "INACTIVE");
        return basicStatus;
    }

    public static Map<Integer, String> getAliveStatus() {
        Map<Integer, String> basicStatus = new HashMap<Integer, String>();
        basicStatus.put(Status.ENABLE, "ENABLE");
        basicStatus.put(Status.DISABLE, "DISABLE");
        return basicStatus;
    }

    public static Map<String, String> getWebUserTypeList() {
        Map<String, String> basicStatus = new HashMap<String, String>();
        basicStatus.put(UserType.BANK, "Bank");
        basicStatus.put(UserType.MERCHANT, "Merchant");
        return basicStatus;
    }

    public static Map<String, String> getCustomerUserTypeList() {
        Map<String, String> basicStatus = new HashMap<String, String>();
        basicStatus.put(UserType.MERCHANT, "Merchant");
        basicStatus.put(UserType.CUST, "Customer");
        return basicStatus;
    }

    public static Map<String, String> getValidateStatusList() {
        Map<String, String> basicStatus = new HashMap<String, String>();
        basicStatus.put("1", "Yes");
        basicStatus.put("2", "No");
        return basicStatus;
    }

    public static Map<String, String> getFeeMethodList() {
        Map<String, String> basicStatus = new HashMap<String, String>();
        basicStatus.put("1", "Percentage");
        basicStatus.put("2", "Fat Rate");
        return basicStatus;
    }

    public static Map<String, String> getRiskLevelList() {
        Map<String, String> basicStatus = new HashMap<String, String>();
        basicStatus.put("1", "Warning");
        basicStatus.put("2", "Critical");
        return basicStatus;
    }

    public static Map<String, String> getReadStatusList() {
        Map<String, String> basicStatus = new HashMap<String, String>();
        basicStatus.put("15", "Read");
        basicStatus.put("16", "Unread");
        return basicStatus;
    }

    public static Map<String, String> getMessegeFormatCodeList() {
        Map<String, String> basicStatus = new HashMap<String, String>();
        basicStatus.put("1", "Order Only");
        basicStatus.put("2", "Secret Only");
        basicStatus.put("3", "Order + Secret");
        return basicStatus;
    }

    public static Map<String, String> getMessegeDeliveryPartyList() {
        Map<String, String> basicStatus = new HashMap<String, String>();
        basicStatus.put("1", "Sender");
        basicStatus.put("2", "Receiver");
        return basicStatus;
    }

    public static Map<String, String> getLogLevelList() {

        Map<String, String> logFileTypes = new HashMap<String, String>();
        logFileTypes.put("0", "Level0");
        logFileTypes.put("1", "Level1");
        logFileTypes.put("2", "Level2");
        logFileTypes.put("3", "Level3");
        return logFileTypes;
    }

    public static Map<String, String> getTransferModeList() {
        Map<String, String> basicStatus = new HashMap<String, String>();
        basicStatus.put("1", "One to One");
        basicStatus.put("2", "One to Many");
        return basicStatus;
    }

    public static TreeMap<String, String> getValidityPeriod() {
        TreeMap<String, String> valperiod = new TreeMap<String, String>();
        valperiod.put("1", "1 Hour");
        valperiod.put("2", "2 Hour");
        valperiod.put("3", "3 Hour");
        valperiod.put("4", "4 Hour");
        valperiod.put("5", "5 Hour");
        valperiod.put("6", "6 Hour");
        valperiod.put("7", "7 Hour");
        valperiod.put("8", "8 Hour");
        valperiod.put("9", "9 Hour");
        valperiod.put("10", "10 Hour");
        valperiod.put("11", "11 Hour");
        valperiod.put("12", "12 Hour");
        valperiod.put("13", "13 Hour");
        valperiod.put("14", "14 Hour");
        valperiod.put("16", "16 Hour");
        valperiod.put("17", "17 Hour");
        valperiod.put("18", "18 Hour");
        valperiod.put("19", "19 Hour");
        valperiod.put("20", "20 Hour");
        valperiod.put("21", "21 Hour");
        valperiod.put("22", "22 Hour");
        valperiod.put("23", "23 Hour");
        valperiod.put("24", "24 Hour");
        return valperiod;
    }

    public static TreeMap<String, String> getHeaderSize() {
        TreeMap<String, String> hsize = new TreeMap<String, String>();
        hsize.put("1", "2 Byte");
        hsize.put("2", "4 Byte");
        return hsize;
    }

    public static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(Map<K, V> map) {
        List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(map.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {

            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        Map<K, V> sortedMap = new LinkedHashMap<K, V>();

        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static Date getLocalDate() {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        java.sql.Date date2 = new java.sql.Date(d.getTime());
        return date2;
    }

    public static String getOSPathCert(SessionUserBean sub) throws Exception {
        // returs path if log path as  "/opt/e24ocm/logs/epic"
        String path = null;

        try {
//            String logpath = "/opt/e24ocm/logs/epic";
            String logpath = sub.getLogFilePath();

            String linuxPath = logpath + "downloads";
            String removeFirstSlash = linuxPath.substring(linuxPath.indexOf("/") + 1);
            String removeToSecondSlash = removeFirstSlash.substring(removeFirstSlash.indexOf("/"));
            String conForwordToBack = removeToSecondSlash.replace("/", "\\");

            if (System.getProperty("os.name").startsWith("Windows")) {
                path = "C:" + conForwordToBack;
            } else if (System.getProperty("os.name").startsWith("Linux")) {
                path = linuxPath;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return path;
    }

    public static String getOSLogPath(String logpath) throws Exception {

        String path = null;

        try {
            String linuxPath = logpath + "/";
            //System.out.println("linuxpath="+linuxPath);
            String removeFirstSlash = linuxPath.substring(linuxPath.indexOf("/") + 1);
            String removeToSecondSlash = removeFirstSlash.substring(removeFirstSlash.indexOf("/"));
            String conForwordToBack = removeToSecondSlash.replace("/", "\\\\");

            if (System.getProperty("os.name").startsWith("Windows")) {
                path = "C:" + conForwordToBack;
            } else if (System.getProperty("os.name").startsWith("Linux")) {
                path = linuxPath;
            }

        } catch (Exception ex) {
            throw ex;
        }
        return path;
    }

    public static String generateHash(String password) throws Exception {

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(password.getBytes());
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);
        return hashtext;
    }

    public static Map<String, String> getLogTypeList() {

        Map<String, String> logFileTypes = new HashMap<String, String>();
        logFileTypes.put("01", "Infor");
        logFileTypes.put("02", "Error");
        return logFileTypes;
    }

    public static String changeDateFormat(String date) throws ParseException {

        Date tradeDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(date);
        String krwtrDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(tradeDate);
        return krwtrDate;
    }

    public static String getDateEcho() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        Date date1 = new Date();
        return sdf.format(date1);
    }

    public static String getTimeEcho() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        Date date1 = new Date();
        return sdf.format(date1);
    }

    public static String round(String in) {
        double value = Double.parseDouble(in);
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        Double ret = (double) tmp / factor;
        return ret.toString();
    }

    public static String formatTimestamp(Timestamp in) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(in);
    }
    
     public static boolean userADValidation(String username,String password) throws Exception{
        boolean isvalid=false;
        
         try {
            Hashtable<String, String> env = new Hashtable<String, String>();
                env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
                env.put(Context.SECURITY_AUTHENTICATION, "simple");
                env.put(Context.PROVIDER_URL, InitConfigValue.AD_URL);
                env.put(Context.SECURITY_PRINCIPAL, username.toUpperCase()+"@TSTDOMANITRD"); //BNGL0125
                env.put(Context.SECURITY_CREDENTIALS, password);

                DirContext ctx;
            	ctx = new InitialDirContext(env);
            	ctx.close();
            	isvalid=true; 
             } catch (AuthenticationException ex) {
                    isvalid=false;
                    System.out.println("bank user validation fail");
             }catch (Exception e) {
                   isvalid=false;
                   throw e;
              }
        return isvalid;
     }
}
