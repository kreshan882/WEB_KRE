<%-- 
    Document   : bulkSenderManagement
    Created on : 05/04/2016, 3:55:57 PM
    Author     : dimuthu_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%> 
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>      

<html>
    <head>
        <title>Bulk Message Sender</title>
        <jsp:include page="/Styles.jsp" />



        <script type="text/javascript">
            function load() {
                if ('true' === $('#vadd').val()) {
                    $('#addid').hide();
                }
            }
            function resetData() {

                $('#reci_mobile').val("");
                $('#amount').val("");

                jQuery("#gridtable").trigger("reloadGrid");
            }

            function zeroPAD(number, length) {
                var str = '' + number;
                while (str.length < length) {
                    str = '0' + str;
                }
                return str;
            }

            function createBatchNo() {
                var batchNo = $('#batch_no').val();
                var newBatchNo = parseInt(batchNo) + 1;
                var paddingNumber = zeroPAD(newBatchNo , 6);
//                alert(paddingNumber);
                $('#batch_no').val(paddingNumber);
            }

            function statusformatter(cellvalue, options, rowObject) {
                var html = "<img src='${pageContext.request.contextPath}/resources/images/risk_warning.PNG' />";
                return html;
            }


            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='deleteInit(&#34;" + rowObject.recipient_mobile + "&#34;,&#34;" + rowObject.customerId + "&#34;,&#34;" + rowObject.batchNo + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/iconDelete.png'  /></a>";
            }

            function deleteInit(recipient_mobile, customerId, batchNo) {
                $("#confirmdialogbox").data('recipient_mobile', recipient_mobile);
                $("#confirmdialogbox").data('cusId', customerId);
                $("#confirmdialogbox").data('batchNo', batchNo);
                $("#confirmdialogbox").dialog('open');
                $("#confirmdialogbox").html('<br><b><font size="3" color="red"><center>Please confirm to delete message : ' + recipient_mobile + '');

                return false;
            }

            function deleteNow(recipient_mobile, cusId, batchNo) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/DeletebulkMsgMng',
                    data: {reci_mobile: recipient_mobile, cus_id: cusId, batch_no: batchNo},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.success) {
                            $("#dialogbox").dialog('open');
                            $("#dialogbox").html('<br><b><font size="3" color="green"><center>' + data.message + ' ');
                        } else {
                            $("#dialogbox").dialog('open');
                            $("#dialogbox").html('<br><b><font size="3" color="red"><center>' + data.message + ' ');
                        }
                        jQuery("#gridtable").trigger("reloadGrid");
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });

            }

            function ResetAddForm() {
                resetData();
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");
            }

        </script>>
    </head>
    <body style="overflow:hidden">
        <div class="wrapper">

            <jsp:include page="../../header.jsp" /> 
            <div class="body_content" id="includedContent">



                <div class="watermark"></div>
                <div class="heading">Bulk Messages Sender</div>
                <div class="AddUser_box ">
                    <div class="message">
                        <s:div id="divmsg">
                            <i style="color: red">  <s:actionerror theme="jquery"/></i>
                            <i style="color: green"> <s:actionmessage theme="jquery"/></i>
                        </s:div>
                    </div>
                    <div class="contentcenter">


                        <s:form  theme="simple" id="addForm">         
                            <table class="form_table">
                                <tr>
                                    <td class="content_td formLable">Your Batch Number</td>
                                    <td class="content_td formLable">:</td>
                                    <td><s:textfield id = "batch_no" name="batch_no" cssClass="textField" disabled="true" /></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable">Mobile Number<span class="mandatory">*</span></td>
                                    <td class="content_td formLable">:+94</td>
                                    <td><s:textfield id = "reci_mobile" name="reci_mobile" cssClass="textField" placeholder="778804455"/></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable">Amount<span class="mandatory">*</span></td>
                                    <td class="content_td formLable">:</td>
                                    <td><s:textfield id = "amount" name="amount" cssClass="textField"  placeholder="100.00"/></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="3"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                                <tr>                                
                                    <td> <s:url var="bulkAddurl" action="AddbulkMsgMng"/>                                   
                                        <sj:submit   button="true" href="%{bulkAddurl}" value="Add"   targets="divmsg"  cssClass="button_sadd" disabled="#vadd"/>
                                        <sj:submit button="true" value="Reset" onClick="ResetAddForm()" cssClass="button_sreset"/>

                                    </td>
                                </tr>
                            </table>
                        </s:form>

                    </div>
                    <div class="viewuser_tbl">
                        <div id="tablediv">
                            <sj:dialog 
                                id="confirmdialogbox" 
                                buttons="{ 
                                'OK':function() { deleteNow($(this).data('recipient_mobile'),$(this).data('cusId'),$(this).data('batchNo'));$( this ).dialog( 'close' ); },
                                'Cancel':function() { $( this ).dialog( 'close' );} 
                                }" 
                                autoOpen="false" 
                                modal="true" 
                                title="Confirm message"
                                width="400"
                                height="150"
                                position="center"
                                />

                            <sj:dialog 
                                id="dialogbox" 
                                buttons="{
                                'OK':function() { $( this ).dialog( 'close' );}
                                }"  
                                autoOpen="false" 
                                modal="true" 
                                title="Delete messae" 
                                width="400"
                                height="150"
                                position="center"
                                />


                            <s:url var="listurl" action="ListbulkMsgMng"/>
                            <sjg:grid
                                id="gridtable"
                                caption="Messages"
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
                                <sjg:gridColumn name="batchNo" index="BATCH_NO"  title="" hidden="true"/>
                                <sjg:gridColumn name="customerId" index="CUSTOMER_ID"  title="" hidden="true"/>
                                <sjg:gridColumn name="recipient_mobile" index="RECIPIENT_MOBILE_NUM" title="Mobile Number" align="left" width="12" sortable="true"/>
                                <sjg:gridColumn name="amount" index="AMOUNT" title="Amount (LKR)" align="right" width="12" sortable="true"/>
                                <sjg:gridColumn name="status" hidden="true" index="STATUS" title="Status" align="center" width="7" formatter="statusformatter" sortable="true"/>  

                                <sjg:gridColumn name="recipient_mobile" index="RECIPIENT_MOBILE_NUM" title="Delete" align="center" width="7" align="center"   formatter="deleteformatter" sortable="false" hidden="#vdelete"/>                     

                            </sjg:grid> 

                        </div> 
                        <div style=" position: absolute;right: 10px;padding-top:20px;" >
                            <s:url var="bulkSendurl" action="UpdatebulkMsgMng" />                                 
                            <sj:submit   button="true" href="%{bulkSendurl}" value="Send"   targets="divmsg" onclick="createBatchNo()" cssClass="button_sadd" disabled="#vupdate"/>
                        </div>

                    </div>
                </div>
            </div><!--end of body_content-->
            <jsp:include page="../../footer.jsp" /> 

        </div> 

    </body>
</html>