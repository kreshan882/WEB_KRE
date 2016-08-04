package com.epic.util;

import com.epic.cla.configuration.bean.PasswordPolicyInputBean;
import com.epic.cla.configuration.service.PasswordPolicyService;
import com.epic.login.bean.SessionUserBean;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dushantha
 */
public class PasswordValidator {

    public synchronized String validatePassword(String password) {

        char characters[] = password.toCharArray();

        PasswordPolicyInputBean passPolcyBean = getPasswordPolicys();

        if (null != passPolcyBean) {

            if (Integer.parseInt(passPolcyBean.getMinlength()) <= characters.length && Integer.parseInt(passPolcyBean.getMaxlength()) >= characters.length) {

                if (isValidSpecialChars(characters, passPolcyBean)) {

                    if (Integer.parseInt(passPolcyBean.getMinimumuppercasecharacters()) <= numOfUpperCases(characters)) {

                        if (Integer.parseInt(passPolcyBean.getMinimumnumericalcharacters().trim()) <= numOfNumerics(characters)) {

                            return "Successful";

                        } else {
                            return "Invalid password : insert minimum " + passPolcyBean.getMinimumnumericalcharacters() + " numeric values";
                        }

                    } else {
                        return "Invalid password : insert minimum " + passPolcyBean.getMinimumuppercasecharacters() + " uppercase values";
                    }

                } else {
                    return "Invalid password : insert special character count between ( " + passPolcyBean.getMinimumspecialcharacters() + " , " + passPolcyBean.getMaximumspecialcharacters() + " ) : Allowed characters ( " + passPolcyBean.getAllowspecialcharacters() + " ) ";
                }

            } else {
                return "Invalid password legnth. Please Insert " + passPolcyBean.getMinlength() + " to " + passPolcyBean.getMaxlength() + " values.";
            }
        } else {
            return "Error please create password policy first";
        }
    }

    private PasswordPolicyInputBean getPasswordPolicys() {
        PasswordPolicyInputBean pass = new PasswordPolicyInputBean();
        try {

            pass = new PasswordPolicyService().getPasswordPolicyDetails(pass);

        } catch (Exception e) {

            try {
                LogFileCreator.writeErrorToLog(e);
            } catch (Exception ex) {
                Logger.getLogger(PasswordValidator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pass;
    }

    private int numOfUpperCases(char[] charArray) {
        int numbers = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (Character.isUpperCase(charArray[i])) {
                numbers++;
            }
        }
        return numbers;
    }

//    private int numOflowerCases(char[] charArray) {
//        int numbers = 0;
//        for (int i = 0; i < charArray.length; i++) {
//            if (Character.isLowerCase(charArray[i])) {
//                numbers++;
//            }
//        }
//        return numbers;
//    }
    private int numOfNumerics(char[] charArray) {
        int numbers = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (String.valueOf(charArray[i]).matches("[0-9]")) {
                numbers++;
            }
        }
        return numbers;
    }

    private boolean isValidSpecialChars(char[] pwdArray, PasswordPolicyInputBean passpolBean) {
        
        String validstring = allowSpecialChars("~@#$&!", passpolBean.getAllowspecialcharacters());
        int counter = 0;
        if (!validstring.isEmpty()) {

            
            char[] validspclchar = validstring.toCharArray();

            for (int i = 0; i < validspclchar.length; i++) {
                for (int j = 0; j < pwdArray.length; j++) {
                    if (validspclchar[i] == pwdArray[j]) {
                        counter++;
                    }
                }

            }
            if (counter >= Integer.parseInt(passpolBean.getMinimumspecialcharacters())&& (! (counter > 0 && Integer.parseInt(passpolBean.getMinimumspecialcharacters())== 0 )) && counter <= Integer.parseInt(passpolBean.getMaximumspecialcharacters())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private String allowSpecialChars(String alwspcl, String givenspclchar) {
        String spclchar = "";
        char[] allowspclcharArray = alwspcl.toCharArray();
        char[] givenspclcharArray = givenspclchar.toCharArray();

        for (int i = 0; i < givenspclcharArray.length; i++) {
            for (int j = 0; j < allowspclcharArray.length; j++) {
                if (givenspclcharArray[i] == allowspclcharArray[j]) {
                    spclchar += givenspclcharArray[i];
                }

            }

        }
        return spclchar;
    }

}
