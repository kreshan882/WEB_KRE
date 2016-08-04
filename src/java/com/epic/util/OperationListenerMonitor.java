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

public class OperationListenerMonitor {

   private static Socket getUnpermentConnection() throws Exception {

        InetAddress addr = InetAddress.getByName(InitConfigValue.SWITCH_ENGIN_IP);
        SocketAddress sockaddr = new InetSocketAddress(addr, InitConfigValue.SERVICEPORT);
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
            //System.out.println("socketttttttttttttttt");
            socket = getUnpermentConnection();
            //System.out.println("ttttttttttttttttttttttttttt");
            socket.setSoTimeout(10000);

            request = req.getBytes();

            byte[] HL = ISOUtil.hex2byte(ISOUtil.zeropad(Integer.toHexString(request.length), 8));

            request = ISOUtil.concat(HL, request);

            dataout = new DataOutputStream(socket.getOutputStream());
            datain = new DataInputStream(socket.getInputStream());
            dataout.flush();

            dataout.write(request);
            dataout.flush();
//System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrr");
            int HD_LEN = 0;
            byte[] HD = new byte[4];
            datain.readFully(HD, 0, HD.length);
            HD_LEN = Integer.parseInt(ISOUtil.hexString(HD), 16);
            byte BUFF[] = new byte[HD_LEN];
//System.out.println("ooooooooooooooooooooooooo");
            if (HD_LEN > 0) {
                datain.readFully(BUFF, 0, HD_LEN);
            }

            response = new String(BUFF);

        } catch (SocketTimeoutException t) {

            response = "Error";

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
