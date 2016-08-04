<%-- 
    Document   : callCenterManagement
    Created on : 05/04/2016, 11:56:53 AM
    Author     : dimuthu_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%> 
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>      

<html>
    <head>
        <title>Call Center</title>
        <jsp:include page="/Styles.jsp" />

        <script type="text/javascript">

            $.subscribe('onclicksearch', function (event, data) {


                var fromdate = $('#fromdate').val();
                var todate = $('#todate').val();
                var customerAccNumber = $('#customerAccNumber').val();
                var customerMobileNumber = $('#customerMobileNumber').val();
                var recepientMobile = $('#recepientMobile').val();
                var amount = $('#amount').val();
                var referenceNumber = $('#referenceNumber').val();
                var orderID = $('#orderID').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {fromdate: fromdate, todate: todate, customerAccNumber: customerAccNumber, customerMobileNumber: customerMobileNumber, recepientMobile: recepientMobile, amount: amount, referenceNumber: referenceNumber, orderID: orderID, search: true}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            });



        </script>>
    </head>
    <body style="overflow:hidden">
        <div class="wrapper">

            <jsp:include page="../../header.jsp" /> 
            <div class="body_content" id="includedContent">



                <div class="watermark"></div>
                <div class="heading">Call Center</div>
                <div class="AddUser_box ">

                    <div class="contentcenter">


                        <s:form action="DownloadtxnMng" theme="simple">         
                            <table class="form_table">
                                <div class="message2">
                                    <s:div id="message1">
                                        <i style="color: red">  <s:actionerror theme="jquery"/></i>
                                        <i style="color: green"> <s:actionmessage theme="jquery"/></i>
                                    </s:div>
                                </div>

                                <tr>
                                    <td class="content_td formLable">Reference Number</td>
                                    <td class="content_td formLable">:</td>
                                    <td><s:textfield id = "referenceNumber" name="referenceNumber" cssClass="textField"  /></td>
                                    <td width="25px;"></td>
                                    <td class="content_td formLable">Customer Account Number</td>
                                    <td class="content_td formLable">:</td>
                                    <td><s:textfield id = "customerAccNumber" name="customerAccNumber" cssClass="textField"  /></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable">Order ID</td>
                                    <td class="content_td formLable">:</td>
                                    <td><s:textfield id = "orderID" name="orderID" cssClass="textField"  /></td>
                                    <td width="25px;"></td>
                                    <td class="content_td formLable">Recipient Mobile Number</td>
                                    <td class="content_td formLable">:</td>
                                    <td><s:textfield id = "recepientMobile" name="recepientMobile" cssClass="textField"  /></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable">Period From<span class="mandatory">*</span></td>
                                    <td class="content_td formLable">:</td>
                                    <td><sj:datepicker id="fromdate" name="fromdate" readonly="true" value="today"   changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" cssClass="textField"  /></td>
                                    <td width="25px;"></td>
                                    <td class="content_td formLable">Customer Mobile Number</td>
                                    <td class="content_td formLable">:</td>
                                    <td><s:textfield id = "customerMobileNumber" name="customerMobileNumber" cssClass="textField"  /></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable">Period To<span class="mandatory">*</span></td>
                                    <td class="content_td formLable">:</td>
                                    <td><sj:datepicker id="todate" name="todate" readonly="true" value="tomorrow"   changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" cssClass="textField"  /></td>
                                    <td width="25px;"></td>
                                    <td class="content_td formLable">Amount</td>
                                    <td class="content_td formLable">:</td>
                                    <td><s:textfield id = "amount" name="amount" cssClass="textField"  /></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="3"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                                <tr>
                                    <td align="left" colspan="3"><sj:a 
                                            id="searchbut" 
                                            button="true" 

                                            onClickTopics="onclicksearch"    
                                            cssClass="button_asave"

                                            >Search</sj:a> </td>

                                    </tr>
                                </table>
                        </s:form>

                    </div>
                    <div class="viewuser_tbl">
                        <div id="tablediv">


                            <s:url var="listurl" action="ListcallcenterMng"/>
                            <sjg:grid
                                id="gridtable"
                                caption="Transactions"
                                dataType="json"
                                href="%{listurl}"
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
                                <%--<sjg:gridColumn name="id" index="ID" title="ID" align="left" width="15" sortable="true" />--%>                    
                                <sjg:gridColumn name="txn_type" index="TXN_TYPE" title="Transaction Type"  align="left" width="15"  sortable="true"/>                            
                                <sjg:gridColumn name="channel" index="CHANNEL_TYPE" title="Channel" align="left" width="15"   sortable="true"/>                      
                                <sjg:gridColumn name="customerID" index="CUSTOMER_ID" title="Customer ID"  align="left" width="7"  sortable="true"/>
                                <sjg:gridColumn name="recepientMobile" index="RECEPIENT_MOBILE" title="Recepient Mobile" align="left"  width="7"  sortable="true"/>                            
                                <sjg:gridColumn name="amount" index="AMOUNT" title="Amount" align="left"  width="7"  sortable="true"/>
                                <sjg:gridColumn name="orderID" index="ORD_ID" title="Order ID" align="left"  width="7"  sortable="true"/>
                                <sjg:gridColumn name="customerAccountNumber" index="CUSTOMER_ACCOUNT_NUMBER" title="Customer Account No" align="left" width="15"   sortable="true"/>                    
                                <sjg:gridColumn name="customerMobile" index="CUSTOMER_MOBILE" title="Customer Mobile" align="left"  width="15"  sortable="true"/>
                                <sjg:gridColumn name="refNo" index="REF_NO" title="Ref No" align="left"  width="7"  sortable="true"/>
                                <sjg:gridColumn name="serviceCharge" index="SERVICE_CHARGE" title="Service Charge" align="left" width="7"   sortable="true"/>                    
                                <sjg:gridColumn name="responseCode" index="RESPCODE" title="Response Code" align="left" width="7"   sortable="true"/>                      
                                <sjg:gridColumn name="datetime" index="TXN_DATE" title="Transaction Date" align="left" width="15"   sortable="true"/>                      

                            </sjg:grid> 

                        </div> 
                    </div>
                </div>
            </div><!--end of body_content-->
            <jsp:include page="../../footer.jsp" /> 

        </div> 

    </body>
</html>
