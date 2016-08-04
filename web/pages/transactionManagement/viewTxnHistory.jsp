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
                <s:url var="listurl1" action="ListTransectionHistory"/>
                <sjg:grid
                    id="gridtableHistory1"
                    caption="Transaction History"
                    dataType="json"
                    href="%{listurl1}"
                    pager="true"
                    gridModel="gridModel"
                    rowList="10,15,20"
                    rowNum="10"
                    autowidth="true"
                    rownumbers="true"
                    onCompleteTopics="completetopics"
                    rowTotal="false"
                    viewrecords="true"
                    >
                    <sjg:gridColumn name="id" index="ID" title="Transection ID" align="left" width="20" sortable="false"  hidden="true"/> 
                    <sjg:gridColumn name="txnid" index="TXN_ID" title="Transection ID" align="left" width="20" sortable="false"  hidden="true"/> 
                    <sjg:gridColumn name="status" index="STATUS" title="Status" align="left" width="35" sortable="true"/>                    

                    <sjg:gridColumn name="timestamp" index="TIMESTAMP" title="Timestamp" align="left"  width="20"  sortable="true"/>

                </sjg:grid> 
            </div> 



        </s:form>
   