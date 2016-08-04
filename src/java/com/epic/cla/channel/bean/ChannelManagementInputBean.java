/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.channel.bean;

import com.epic.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author dimuthu_h
 */
public class ChannelManagementInputBean {
    
    

    private String searchname = "";
    private boolean search;

    //Add New Recipient
    private String id;
    private String channeltype;
    private Map<Integer, String> channeltypeList;
    private String name;
    private String ip;
    private String port;
    private String contimeout;
    private String rtimeout;
    private String contype;
    private String isoheader;
    private Map<Integer, String> contypeList;
    private String headersize;
    private TreeMap<String, String> headersizeList = Util.getHeaderSize();
    
    private Map<Integer, String> statusList = Util.getBasicStatus();
    private String status;

    //Delete Data
    private String message;
    private boolean success;

    //Update Data
    private String upid;
    private String upchanneltype;
    private Map<Integer, String> upchanneltypeList;
    private String upname;
    private String upip;
    private String upport;
    private String upcontimeout;
    private String upisoheader;
    private String uprtimeout;
    private String upcontype;
    private Map<Integer, String> upcontypeList;
    private String upheadersize;
    
    private Map<Integer, String> upstatusList = Util.getBasicStatus();
    private String upstatus;

    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdate;
    private boolean vdelete;
    private boolean vdownload;
    private boolean vresetpass;
    //    private boolean vactdct;
    /*-------for access control-----------*/

    //Table data
    private List<ChannelBean> gridModel = new ArrayList<ChannelBean>();
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;

    public String getSearchname() {
        return searchname;
    }

    public void setSearchname(String searchname) {
        this.searchname = searchname;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

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

    public String getIsoheader() {
        return isoheader;
    }

    public void setIsoheader(String isoheader) {
        this.isoheader = isoheader;
    }

    public String getUpisoheader() {
        return upisoheader;
    }

    public void setUpisoheader(String upisoheader) {
        this.upisoheader = upisoheader;
    }

    public Map<Integer, String> getChanneltypeList() {
        return channeltypeList;
    }

    public void setChanneltypeList(Map<Integer, String> channeltypeList) {
        this.channeltypeList = channeltypeList;
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

    public Map<Integer, String> getContypeList() {
        return contypeList;
    }

    public void setContypeList(Map<Integer, String> contypeList) {
        this.contypeList = contypeList;
    }

    
    public Map<Integer, String> getStatusList() {
        return statusList;
    }

    public void setStatusList(Map<Integer, String> statusList) {
        this.statusList = statusList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUpid() {
        return upid;
    }

    public void setUpid(String upid) {
        this.upid = upid;
    }

    public String getUpchanneltype() {
        return upchanneltype;
    }

    public void setUpchanneltype(String upchanneltype) {
        this.upchanneltype = upchanneltype;
    }

    public Map<Integer, String> getUpchanneltypeList() {
        return upchanneltypeList;
    }

    public void setUpchanneltypeList(Map<Integer, String> upchanneltypeList) {
        this.upchanneltypeList = upchanneltypeList;
    }

    public String getUpname() {
        return upname;
    }

    public void setUpname(String upname) {
        this.upname = upname;
    }

    public String getUpip() {
        return upip;
    }

    public void setUpip(String upip) {
        this.upip = upip;
    }

    public String getUpport() {
        return upport;
    }

    public void setUpport(String upport) {
        this.upport = upport;
    }

    public String getUpcontimeout() {
        return upcontimeout;
    }

    public void setUpcontimeout(String upcontimeout) {
        this.upcontimeout = upcontimeout;
    }

    public String getUprtimeout() {
        return uprtimeout;
    }

    public void setUprtimeout(String uprtimeout) {
        this.uprtimeout = uprtimeout;
    }

    public String getUpcontype() {
        return upcontype;
    }

    public void setUpcontype(String upcontype) {
        this.upcontype = upcontype;
    }

    public Map<Integer, String> getUpcontypeList() {
        return upcontypeList;
    }

    public void setUpcontypeList(Map<Integer, String> upcontypeList) {
        this.upcontypeList = upcontypeList;
    }

    public String getHeadersize() {
        return headersize;
    }

    public void setHeadersize(String headersize) {
        this.headersize = headersize;
    }

    public String getUpheadersize() {
        return upheadersize;
    }

    public void setUpheadersize(String upheadersize) {
        this.upheadersize = upheadersize;
    }

    public TreeMap<String, String> getHeadersizeList() {
        return headersizeList;
    }

    public void setHeadersizeList(TreeMap<String, String> headersizeList) {
        this.headersizeList = headersizeList;
    }
    public Map<Integer, String> getUpstatusList() {
        return upstatusList;
    }

    public void setUpstatusList(Map<Integer, String> upstatusList) {
        this.upstatusList = upstatusList;
    }

    public String getUpstatus() {
        return upstatus;
    }

    public void setUpstatus(String upstatus) {
        this.upstatus = upstatus;
    }

    public boolean isVadd() {
        return vadd;
    }

    public void setVadd(boolean vadd) {
        this.vadd = vadd;
    }

    public boolean isVupdate() {
        return vupdate;
    }

    public void setVupdate(boolean vupdate) {
        this.vupdate = vupdate;
    }

    public boolean isVdelete() {
        return vdelete;
    }

    public void setVdelete(boolean vdelete) {
        this.vdelete = vdelete;
    }

    public boolean isVdownload() {
        return vdownload;
    }

    public void setVdownload(boolean vdownload) {
        this.vdownload = vdownload;
    }

    public boolean isVresetpass() {
        return vresetpass;
    }

    public void setVresetpass(boolean vresetpass) {
        this.vresetpass = vresetpass;
    }

    public List<ChannelBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<ChannelBean> gridModel) {
        this.gridModel = gridModel;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchOper() {
        return searchOper;
    }

    public void setSearchOper(String searchOper) {
        this.searchOper = searchOper;
    }

    
    
}
