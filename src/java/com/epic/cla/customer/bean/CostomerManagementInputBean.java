/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cla.customer.bean;

import com.epic.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nipun_t
 */
public class CostomerManagementInputBean {

    //Search Data
    private String username = "";
    private String cname = "";
    private String customerid = "";
    private boolean search;

    //Add data
//    private String username;
    private String name;
    private String nic;
    private String cif;
    private String mobile;
    private String accountnumber;
    private HashMap<String, String> accountnumberList = new HashMap<String, String>();
    private String userPro;
    private HashMap<String, String> userProList = new HashMap<String, String>();
    private String usertype;
    private HashMap<String, String> usertypeList = new HashMap<String, String>();
    private String merchantId;
    private HashMap<String, String> merchantList = new HashMap<String, String>();
    private String riskprofileid;
    private HashMap<String, String> riskProfileList = new HashMap<String, String>();
    private String smsprofileid;
    private HashMap<String, String> smsProfileList = new HashMap<String, String>();
    private String validaterecipient;
    private HashMap<String, String> validaterecipientList = new HashMap<String, String>();
    private String address;
    private String actualaddress;
    private HashMap<String, String> addressList = new HashMap<String, String>();
    private String branch;
    private String dateofbirth;
    private String status;
    private String count;

    //Delete Data
    private String message;
    private boolean success;

    //Update Data
    private String upcustomerid;
    private String upname;
    private String updateofbirth;
    private String upnic;
    private String upcif;
    private String upmobile;
    private String upaccountnumber;
    private String upusertype;
    private String upmerchantId;
    private String oldupmerchantId;
    private HashMap<String, String> upmerchantList = new HashMap<String, String>();
    private String upriskprofileid;
    private String upsmsprofileid;
    private String upvalidaterecipient;
    private String upaddress;
    private String upbranch;
    private String upstatus;
    private String upchanneltype;
    private Map<Integer, String> upstatusList = Util.getBasicStatus();

    /*-----------ToolTip data --------------------*/
    private String tstatus;
    private String tamount;
    private String tacctype;

    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdate;
    private boolean vdelete;
    private boolean vdownload;
    private boolean vresetpass;
    private boolean vrecipientadd;
    //    private boolean vactdct;
    /*-------for access control-----------*/

    //Table data
    private List<CustomerManagementBean> gridModel = new ArrayList<CustomerManagementBean>();
    private Integer rows = 0;
    private Integer page = 0;
    private Integer total = 0;
    private Long records = 0L;
    private String sord;
    private String sidx;
    private String searchField;
    private String searchString;
    private String searchOper;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public String getOldupmerchantId() {
        return oldupmerchantId;
    }

    public void setOldupmerchantId(String oldupmerchantId) {
        this.oldupmerchantId = oldupmerchantId;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public HashMap<String, String> getAccountnumberList() {
        return accountnumberList;
    }

    public void setAccountnumberList(HashMap<String, String> accountnumberList) {
        this.accountnumberList = accountnumberList;
    }

    public String getUserPro() {
        return userPro;
    }

    public void setUserPro(String userPro) {
        this.userPro = userPro;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public HashMap<String, String> getUserProList() {
        return userProList;
    }

    public void setUserProList(HashMap<String, String> userProList) {
        this.userProList = userProList;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public HashMap<String, String> getUsertypeList() {
        return usertypeList;
    }

    public void setUsertypeList(HashMap<String, String> usertypeList) {
        this.usertypeList = usertypeList;
    }

    public String getRiskprofileid() {
        return riskprofileid;
    }

    public void setRiskprofileid(String riskprofileid) {
        this.riskprofileid = riskprofileid;
    }

    public HashMap<String, String> getRiskProfileList() {
        return riskProfileList;
    }

    public void setRiskProfileList(HashMap<String, String> riskProfileList) {
        this.riskProfileList = riskProfileList;
    }

    public String getSmsprofileid() {
        return smsprofileid;
    }

    public void setSmsprofileid(String smsprofileid) {
        this.smsprofileid = smsprofileid;
    }

    public HashMap<String, String> getSmsProfileList() {
        return smsProfileList;
    }

    public void setSmsProfileList(HashMap<String, String> smsProfileList) {
        this.smsProfileList = smsProfileList;
    }

    public String getValidaterecipient() {
        return validaterecipient;
    }

    public void setValidaterecipient(String validaterecipient) {
        this.validaterecipient = validaterecipient;
    }

    public HashMap<String, String> getValidaterecipientList() {
        return validaterecipientList;
    }

    public void setValidaterecipientList(HashMap<String, String> validaterecipientList) {
        this.validaterecipientList = validaterecipientList;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public HashMap<String, String> getMerchantList() {
        return merchantList;
    }

    public void setMerchantList(HashMap<String, String> merchantList) {
        this.merchantList = merchantList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActualaddress() {
        return actualaddress;
    }

    public void setActualaddress(String actualaddress) {
        this.actualaddress = actualaddress;
    }

    public HashMap<String, String> getAddressList() {
        return addressList;
    }

    public void setAddressList(HashMap<String, String> addressList) {
        this.addressList = addressList;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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

    public String getUpcustomerid() {
        return upcustomerid;
    }

    public void setUpcustomerid(String upcustomerid) {
        this.upcustomerid = upcustomerid;
    }

    public String getUpname() {
        return upname;
    }

    public void setUpname(String upname) {
        this.upname = upname;
    }

    public String getUpnic() {
        return upnic;
    }

    public void setUpnic(String upnic) {
        this.upnic = upnic;
    }

    public String getUpcif() {
        return upcif;
    }

    public void setUpcif(String upcif) {
        this.upcif = upcif;
    }

    public String getUpmobile() {
        return upmobile;
    }

    public void setUpmobile(String upmobile) {
        this.upmobile = upmobile;
    }

    public String getUpdateofbirth() {
        return updateofbirth;
    }

    public void setUpdateofbirth(String updateofbirth) {
        this.updateofbirth = updateofbirth;
    }

    public String getUpmerchantId() {
        return upmerchantId;
    }

    public void setUpmerchantId(String upmerchantId) {
        this.upmerchantId = upmerchantId;
    }

    public HashMap<String, String> getUpmerchantList() {
        return upmerchantList;
    }

    public void setUpmerchantList(HashMap<String, String> upmerchantList) {
        this.upmerchantList = upmerchantList;
    }

    public String getUpaccountnumber() {
        return upaccountnumber;
    }

    public void setUpaccountnumber(String upaccountnumber) {
        this.upaccountnumber = upaccountnumber;
    }

    public String getUpusertype() {
        return upusertype;
    }

    public void setUpusertype(String upusertype) {
        this.upusertype = upusertype;
    }

    public String getUpriskprofileid() {
        return upriskprofileid;
    }

    public void setUpriskprofileid(String upriskprofileid) {
        this.upriskprofileid = upriskprofileid;
    }

    public String getUpsmsprofileid() {
        return upsmsprofileid;
    }

    public void setUpsmsprofileid(String upsmsprofileid) {
        this.upsmsprofileid = upsmsprofileid;
    }

    public String getUpvalidaterecipient() {
        return upvalidaterecipient;
    }

    public void setUpvalidaterecipient(String upvalidaterecipient) {
        this.upvalidaterecipient = upvalidaterecipient;
    }

    public String getUpaddress() {
        return upaddress;
    }

    public void setUpaddress(String upaddress) {
        this.upaddress = upaddress;
    }

    public String getUpbranch() {
        return upbranch;
    }

    public void setUpbranch(String upbranch) {
        this.upbranch = upbranch;
    }

    public String getUpstatus() {
        return upstatus;
    }

    public void setUpstatus(String upstatus) {
        this.upstatus = upstatus;
    }

    public Map<Integer, String> getUpstatusList() {
        return upstatusList;
    }

    public void setUpstatusList(Map<Integer, String> upstatusList) {
        this.upstatusList = upstatusList;
    }
    public String getUpchanneltype() {
        return upchanneltype;
    }

    public void setUpchanneltype(String upchanneltype) {
        this.upchanneltype = upchanneltype;
    }

    public String getTstatus() {
        return tstatus;
    }

    public void setTstatus(String tstatus) {
        this.tstatus = tstatus;
    }

    public String getTamount() {
        return tamount;
    }

    public void setTamount(String tamount) {
        this.tamount = tamount;
    }

    public String getTacctype() {
        return tacctype;
    }

    public void setTacctype(String tacctype) {
        this.tacctype = tacctype;
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

    public boolean isVrecipientadd() {
        return vrecipientadd;
    }

    public void setVrecipientadd(boolean vrecipientadd) {
        this.vrecipientadd = vrecipientadd;
    }

    public List<CustomerManagementBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<CustomerManagementBean> gridModel) {
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
