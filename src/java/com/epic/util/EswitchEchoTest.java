/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.util;

import com.epic.cla.systemAlert.service.EsMonitorService;
import com.epic.cla.systemAlert.service.LcMonitorService;
import com.epic.init.Status;

/**
 *
 * @author Kreshan
 */
public class EswitchEchoTest {

    public static void checkEcho() {

        String msg = "9|" + Util.getDateEcho() + "|" + Util.getTimeEcho();
        try {
            String esResponce = CommunicationChannelHandler.ReqAndResponse(msg);
            String success[] = esResponce.split("\\|");
            if ("00".equals(success[0])) {
                if (EsMonitorService.upadateESstatus(Status.ACTIVE)) {
                }

            }

        } catch (Exception ex) {
            try {
                if (EsMonitorService.upadateESstatus(Status.INACTIVE)) {
                    if (LcMonitorService.upadateCNLstatus()) {
                    } else {
                    }

                }
            } catch (Exception ex1) {
            }
            ex.printStackTrace();
            LogFileCreator.writeErrorToLog(ex);

        }

    }

//    @Override
//    public void run() {
//
//        while (true) {
//            try {
//                checkEcho();
//                
//            } catch (Exception ex) {
//
//            } finally {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException ex) {
//                   
//                }
//            }
//        }
//
//    }
}
