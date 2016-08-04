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
public class ListenerBean {

    private String id;
    private String listenertype;
    private String name;
    private String uids;
    private String port;
    private String contimeout;
    private String rtimeout;
    private String conntype;
    private String backlogsize;

    private String kalivestatus;
    private String status;
    private String headersize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListenertype() {
        return listenertype;
    }

    public void setListenertype(String listenertype) {
        this.listenertype = listenertype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getKalivestatus() {
        return kalivestatus;
    }

    public void setKalivestatus(String kalivestatus) {
        this.kalivestatus = kalivestatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getFullCount() {
        return fullCount;
    }

    public String getUids() {
        return uids;
    }

    public void setUids(String uids) {
        this.uids = uids;
    }

    public String getConntype() {
        return conntype;
    }

    public void setConntype(String conntype) {
        this.conntype = conntype;
    }

    public String getBacklogsize() {
        return backlogsize;
    }

    public void setBacklogsize(String backlogsize) {
        this.backlogsize = backlogsize;
    }

    public String getHeadersize() {
        return headersize;
    }

    public void setHeadersize(String headersize) {
        this.headersize = headersize;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }

    private long fullCount;

}
