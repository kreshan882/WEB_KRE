<%-- 
    Document   : viewTxnHistory
    Created on : Mar 24, 2016, 12:21:14 PM
    Author     : nipun_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<!DOCTYPE html>


 
        <s:form action="" theme="simple" method="post" id="form">


            <div id="table">
                <s:url var="listurl" action="ListTransectionSmsHistory"/>
                <sjg:grid
                    id="gridtableHistory"
                    caption="SMS History"
                    dataType="json"
                    href="%{listurl}"
                    pager="true"
                    gridModel="gridModel"
                    rowList="10,15,20"
                    rowNum="10"
                    autowidth="true"
                    shrinkToFit = "false"
                    rownumbers="true"
                    onCompleteTopics="completetopics"
                    rowTotal="false"
                    viewrecords="true"
                    >
                    <sjg:gridColumn name="id" index="ID" title="Transection ID" align="left" width="50" sortable="false"  hidden="true"/> 
                    <sjg:gridColumn name="txnid" index="TXN_ID" title="Transection ID" align="left" width="130" sortable="false" hidden="true" /> 
                    <sjg:gridColumn name="deliveryparty" index="DELIVERY_PARTY" title="Delivery Party" align="left" width="100" sortable="true"/>                    
                    <sjg:gridColumn name="mobileno" index="MOBILE_NO" title="Mobile No" align="left" width="100" sortable="false"  /> 
                    <sjg:gridColumn name="smsmsg" index="SMS_MSG" title="Message" align="left" width="900" sortable="true"/> 
                    <sjg:gridColumn name="timestamp" index="TIMESTAMP" title="Timestamp" align="left"  width="150"  sortable="true"/>

                </sjg:grid> 
            </div> 



        </s:form>

