/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.util;

/**
 *
 * @author kreshan
 */
public interface AccessControlService {
    public boolean checkAccess(String method,int userRole);
}