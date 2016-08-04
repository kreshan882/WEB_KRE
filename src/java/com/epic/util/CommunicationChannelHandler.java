/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.util;

import com.epic.init.InitConfigValue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import org.jpos.iso.ISOUtil;

/**
 *
 * @author dimuthu_h
 */
public class CommunicationChannelHandler {

    private static Socket getUnpermentConnection() throws Exception {

        InetAddress addr = InetAddress.getByName(InitConfigValue.SWITCH_ENGIN_IP);
        SocketAddress sockaddr = new InetSocketAddress(addr, InitConfigValue.SWITCH_ENGIN_PORT);
        Socket bankSocket = new Socket();

        bankSocket.connect(sockaddr, 15000);

        return bankSocket;

    }

    public static String ReqAndResponse(String req) throws Exception {

        Socket socket = null;
        DataInputStream datain = null;
        DataOutputStream dataout = null;
        byte[] request = null;
        String response = null;

        try {

            socket = getUnpermentConnection();
            socket.setSoTimeout(5000);

            request = req.getBytes();

            byte[] HL = ISOUtil.hex2byte(ISOUtil.zeropad(Integer.toHexString(request.length), 8));

            request = ISOUtil.concat(HL, request);

            dataout = new DataOutputStream(socket.getOutputStream());
            datain = new DataInputStream(socket.getInputStream());
            dataout.flush();

            dataout.write(request);
            //System.out.println("req = "+req);
            dataout.flush();

            int HD_LEN = 0;
            byte[] HD = new byte[4];
            datain.readFully(HD, 0, HD.length);
            HD_LEN = Integer.parseInt(ISOUtil.hexString(HD), 16);

            byte BUFF[] = new byte[HD_LEN];

            if (HD_LEN > 0) {
                datain.readFully(BUFF, 0, HD_LEN);
            }

            response = new String(BUFF);
            //System.out.println("res"+response);

        } catch (Exception e) {
            throw e;
        } finally {
            if (socket != null) {
                socket.close();
            }
            if (datain != null) {
                datain.close();
            }
            if (dataout != null) {
                dataout.close();
            }

        }

        return response;

    }
}
