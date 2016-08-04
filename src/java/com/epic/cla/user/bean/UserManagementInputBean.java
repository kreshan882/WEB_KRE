package com.epic.cla.user.bean;

import com.epic.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManagementInputBean {

    //Search Data
    private String searchname ="";
    private boolean search;
    
    //Add data
//    private String username;
    private String username;
    private String name;
    private String password;
    private String repassword;
    private String userPro;
    private HashMap<String,String> userProList = new HashMap<String,String>();
    private String usertype;
    private HashMap<String,String> usertypeList = new HashMap<String,String>();
    private String email;
    private String address;
    private String mobile;
    private String nic;


    
    //Delete Data
    private String message;
    private boolean success;

   //Update Data
    private String upusername;
    private String upname;
    private String upuserPro;
    private String upusertype;
    private String upstatus;     
    private String upemail;
    private String upaddress;
    private String upmobile;
    private String upnic;
    private Map<Integer,String>  upstatusList=Util.getBasicStatus();
     
    /*-------for access control-----------*/
    private boolean vadd;
    private boolean vupdate;
    private boolean vdelete;
    private boolean vdownload;
    private boolean vresetpass;
    //    private boolean vactdct;
    /*-------for access control-----------*/ 
    
    //Table data
    private List<UserBean> gridModel=new ArrayList<UserBean>();
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

    
    public boolean isVadd() {
        return vadd;
    }

    public void setVadd(boolean vadd) {
        this.vadd = vadd;
    }

    public String getUserPro() {
        return userPro;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public void setUserPro(String userPro) {
        this.userPro = userPro;
    }

    public HashMap<String, String> getUserProList() {
        return userProList;
    }

    public void setUserProList(HashMap<String, String> userProList) {
        this.userProList = userProList;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
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



    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUpusername() {
        return upusername;
    }

    public Map<Integer, String> getUpstatusList() {
        return upstatusList;
    }

    public void setUpstatusList(Map<Integer, String> upstatusList) {
        this.upstatusList = upstatusList;
    }

    public String getUpname() {
        return upname;
    }

    public void setUpname(String upname) {
        this.upname = upname;
    }

    public String getUpusertype() {
        return upusertype;
    }

    public void setUpusertype(String upusertype) {
        this.upusertype = upusertype;
    }

    public String getUpaddress() {
        return upaddress;
    }

    public void setUpaddress(String upaddress) {
        this.upaddress = upaddress;
    }

    public String getUpmobile() {
        return upmobile;
    }

    public void setUpmobile(String upmobile) {
        this.upmobile = upmobile;
    }





    public void setUpusername(String upusername) {
        this.upusername = upusername;
    }

    public String getUpstatus() {
        return upstatus;
    }

    public void setUpstatus(String upstatus) {
        this.upstatus = upstatus;
    }

    public String getUpuserPro() {
        return upuserPro;
    }

    public void setUpuserPro(String upuserPro) {
        this.upuserPro = upuserPro;
    }



    public String getUpemail() {
        return upemail;
    }

    public void setUpemail(String upemail) {
        this.upemail = upemail;
    }

    public String getUpnic() {
        return upnic;
    }

    public void setUpnic(String upnic) {
        this.upnic = upnic;
    }

    
    
    public List<UserBean> getGridModel() {
        return gridModel;
    }

    public void setGridModel(List<UserBean> gridModel) {
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

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    
    
}
