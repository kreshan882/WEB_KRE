/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.channel.bean;

/**
 *
 * @author dimuthu_h
 */
public class ChannelBean {
    private String id;
    private String channeltype;
    private String name;
    private String ip;
    private String port;
    private String contimeout;
    private String rtimeout;
    private String isoheader;
    private String contype;
    private String headersize;
   
   
    private String status;
    private String regDate;
    
    private long fullCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChanneltype() {
        return channeltype;
    }

    public void setChanneltype(String channeltype) {
        this.channeltype = channeltype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public String getIsoheader() {
        return isoheader;
    }

    public void setIsoheader(String isoheader) {
        this.isoheader = isoheader;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getContimeout() {
        return contimeout;
    }

    public void setContimeout(String contimeout) {
        this.contimeout = contimeout;
    }

    public String getRtimeout() {
        return rtimeout;
    }

    public void setRtimeout(String rtimeout) {
        this.rtimeout = rtimeout;
    }

    public String getContype() {
        return contype;
    }

    public void setContype(String contype) {
        this.contype = contype;
    }

    public String getHeadersize() {
        return headersize;
    }

    public void setHeadersize(String headersize) {
        this.headersize = headersize;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    
   
}
