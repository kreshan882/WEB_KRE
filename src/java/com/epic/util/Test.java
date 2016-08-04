 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tharaka
 */
public class Test {

    public static void main(String[] arg) throws ParseException {
        
        try {
            Util.userADValidation("lk03337", "qwe@123");
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }

}
