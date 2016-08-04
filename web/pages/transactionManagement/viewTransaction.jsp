<%-- 
    Document   : ViewTransaction
    Created on : Mar 8, 2016, 4:03:57 PM
    Author     : supun
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%> 
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>      

<html>
    <head>
    <title>View Transaction</title>
    <jsp:include page="/Styles.jsp" />

    <script type="text/javascript">

        function reverseformatter(cellvalue, options, rowObject) {
            if (rowObject.status === 'Customer Request' && rowObject.responseCode === '00.Approved') {
                return "<a href='#' onClick='javascript:reverseInit(&#34;" + cellvalue + "&#34;,&#34;" + rowObject.orderID + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/icon_reverse.png' /></a>";
            } else if (rowObject.status === 'ITM Reversed' && rowObject.responseCode === '00.Approved') {
                return "<a href='#' onClick='javascript:reverseInit(&#34;" + cellvalue + "&#34;,&#34;" + rowObject.orderID + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/icon_reverse.png' /></a>";
            } else {
                return "<a href='#'><img src ='${pageContext.request.contextPath}/resources/images/icon_cant_reverse.png' /></a>";

            }

        }

        function reverseInit(keyval) {
            $("#confirmdialogbox").data('keyval', keyval).dialog('open');
            $("#confirmdialogbox").html('<br><b><font size="3" color="red"><center>Please confirm to reverse transaction');
            return false;
        }

        function reverseNow(keyval) {
            $.ajax({
                url: '${pageContext.request.contextPath}/CanceltxnMng',
                data: {id: keyval},
                dataType: "json",
                type: "POST",
                success: function (data) {
                    jQuery("#gridtable").trigger("reloadGrid");
                },
                error: function (data) {
                    window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                }
            });
        }

        $.subscribe('onclicksearch', function (event, data) {


            var fromdate = $('#fromdate').val();
            var todate = $('#todate').val();
            var customerAccNumber = $('#customerAccNumber').val();
            var customerMobileNumber = $('#customerMobileNumber').val();
            var recepientMobile = $('#recepientMobile').val();
            var amount = $('#amount').val();
            var referenceNumber = $('#referenceNumber').val();
            var batchNumber = $('#batchNumber').val();
            var orderID = $('#orderID').val();
            var channeltype = $('#channeltype').val();
            var txntype = $('#txntype').val();

            $("#gridtable").jqGrid('setGridParam', {postData: {fromdate: fromdate, todate: todate, customerAccNumber: customerAccNumber, customerMobileNumber: customerMobileNumber, recepientMobile: recepientMobile, amount: amount, channeltype: channeltype, referenceNumber: referenceNumber, batchNumber: batchNumber, orderID: orderID, txntype: txntype, search: true}});
            $("#gridtable").jqGrid('setGridParam', {page: 1});
            jQuery("#gridtable").trigger("reloadGrid");

        });


        function historyformatter(cellvalue, options, rowObject) {
            return "<a href='#' title='Transection History' onClick='javascript:viewHistory(&#34;" + cellvalue + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/history_icon.png' /></a>";
        }


        function viewHistory(keyval) {
            $("#viewdialog").data('id', keyval).dialog('open');
        }

        $.subscribe('openview', function (event, data) {
            var $led = $("#viewdialog");

            $led.load("ListHistorytxnMng?id=" + $led.data('id'));
        });


        function smshistoryformatter(cellvalue, options, rowObject) {
            return "<a href='#' title='SMS History' onClick='javascript:viewSmsHistory(&#34;" + cellvalue + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/icon_sms.png' /></a>";
        }


        function viewSmsHistory(keyval) {
            $("#viewsmsdialog").data('id', keyval).dialog('open');
        }

        $.subscribe('opensmsview', function (event, data) {
            var $led = $("#viewsmsdialog");

            $led.load("ListSmsHistorytxnMng?id=" + $led.data('id'));
        });
        var i = 0;
        function removeStyle(data, tableid) {
            $.each(data, function (rowNo, gridData) {
                $(tableid).find('tr:eq(' + (rowNo + 1) + ')').removeAttr('style');
                $("#gridtable_frozen").find('tr:eq(' + (rowNo + 1) + ')').children().first().next().next().removeAttr('style');
                $("#gridtable_frozen").find('tr:eq(' + (rowNo + 1) + ')').children().first().next().next().attr("style", "left");
            })
        }

        $.subscribe('rowselect', function (event, data) {
            removeStyle(jQuery(data).jqGrid('getRowData'), '#gridtable')

            var grid = event.originalEvent.grid;
            var sel_id = grid.jqGrid('getGridParam', 'selrow');


            var style = $("#" + sel_id).children(1).attr("style").split(";");
            $.each(jQuery(data).jqGrid('getRowData'), function (rowNo, gridData) {
                if ($('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').attr("id") == sel_id) {
                    if (rowNo == 0) {
                        var css = style[3].split(":");
                        $("#" + sel_id).css("background-color", css[1]);
                        $("#gridtable_frozen").find("#" + sel_id).children().first().next().next().css("background-color", css[1]);
                        $("#" + sel_id).css("color", "#fff");
                        $("#gridtable_frozen").find("#" + sel_id).children().first().next().next().css("color", "#fff");

                    } else {
                        var css = style[2].split(":");
                        $("#" + sel_id).css("background-color", css[1])
                        $("#gridtable_frozen").find("#" + sel_id).children().first().next().next().css("background-color", css[1])
                        $("#" + sel_id).css("color", "#fff")
                        $("#gridtable_frozen").find("#" + sel_id).children().first().next().next().css("color", "#fff")
//                   
                    }


                }
            });
        });


        $.subscribe('gridComplete', function (event, data) {
            i = 0;
            $.each(jQuery(data).jqGrid('getRowData'), function (rowNo, gridData) {
                if ((gridData.status === 'Customer Request') && ('00.Approved' == gridData.responseCode)) {
//                    $('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').css({'background-color': '#45A161', 'color': '#FFF','border-style': 'solid','border-color': 'white'});
                    $('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').children().first().css({'background-color': '#45A161', 'color': '#fff'});
                }
                else if (gridData.responseCode != '00.Approved') {
//                    $('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').css({'background-color': '#F73944', 'color': '#fff','border-style': 'solid','border-color': 'white'});
                    $('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').children().first().css({'background-color': '#F73944', 'color': '#fff'});
                }
                else if ((gridData.status === 'Manual Reversed') && ('00.Approved' == gridData.responseCode)) {
//                    $('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').css({'background-color': '#FFA07A', 'color': '#FFF','border-style': 'solid','border-color': 'white'});
                    $('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').children().first().css({'background-color': '#FA8A61', 'color': '#fff'});
                } else if ((gridData.status === 'Auto Reversed') && ('00.Approved' == gridData.responseCode)) {
//                    $('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').css({'background-color': '#FA8A61', 'color': '#fff','border-style': 'solid','border-color': 'white'});
                    $('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').children().first().css({'background-color': '#FB9A77', 'color': '#fff'});

                } else if ((gridData.status === 'Completed') && ('00.Approved' == gridData.responseCode)) {
//                    $('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').css({'background-color': '#FB9A77', 'color': '#fff','border-style': 'solid','border-color': 'white'});
                    $('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').children().first().css({'background-color': '#87CEFA', 'color': '#fff'});

                } else if ((gridData.status === 'ITM Reversed') && ('00.Approved' == gridData.responseCode)) {
//                    $('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').css({'background-color': '#45A161', 'color': '#FFF','border-style': 'solid','border-color': 'white'});
                    $('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').children().first().css({'background-color': 'lightgreen', 'color': '#fff'});
                } else {
                    $('#gridtable').find('tr:eq(' + (rowNo + 1) + ')').css('color', '#FFA500');
                }
            });
        });

//reload grid if nessessary 
        function grideReload() {
            jQuery("#gridtable").trigger("reloadGrid");
            $('.loading').hide();
            setTimeout(grideReload, 2000);
        }
        grideReload();

    </script>
</head>
<body style="overflow:hidden">
    <div class="wrapper">

        <jsp:include page="../../header.jsp" /> 
        <div class="body_content" id="includedContent">



            <div class="watermark"></div>
            <div class="heading">View Transaction</div>

            <div class="AddUser_box ">
                <div class="message">         
                    <s:div id="divmsg">
                        <i style="color: red">  <s:actionerror theme="jquery"/></i>
                        <i style="color: green"> <s:actionmessage theme="jquery"/></i>
                    </s:div>         
                </div>
                <s:hidden  id="vadd" name="vadd" default="true"/>
                <s:set id="vadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
                <s:set var="vupdate"><s:property value="vupdate" default="true"/></s:set>
                <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>
                <s:set var="vdownload"><s:property value="vdownload" default="true"/></s:set>
                <s:set var="vresetpass"><s:property value="vresetpass" default="true"/></s:set>

                    <div class="contentcenter">


                    <s:form action="DownloadtxnMng" theme="simple">         
                        <table class="form_table">


                            <tr>
                                <td class="content_td formLable">Period From<span class="mandatory">*</span></td>
                                <td class="content_td formLable">:</td>
                                <td><sj:datepicker id="fromdate" name="fromdate" readonly="true" value="today"   changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" cssClass="textField"  /></td>

                                <td width="25px;"></td>
                                <td class="content_td formLable">Period To<span class="mandatory">*</span></td>
                                <td class="content_td formLable">:</td>
                                <td><sj:datepicker id="todate" name="todate" readonly="true" value="tomorrow"   changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" cssClass="textField"  /></td>

                            </tr>
                            <tr>
                                <td class="content_td formLable">Recipient Mobile Number</td>
                                <td class="content_td formLable">:</td>
                                <td><s:textfield id = "recepientMobile" name="recepientMobile" cssClass="textField"  /></td>
                                <td width="25px;"></td>
                                <td class="content_td formLable">Trace Number</td>
                                <td class="content_td formLable">:</td>
                                <td><s:textfield id = "referenceNumber" name="referenceNumber" cssClass="textField"/></td>
                            </tr>
                            <tr>
                                <td class="content_td formLable">Batch Number</td>
                                <td class="content_td formLable">:</td>
                                <td><s:textfield id = "batchNumber" name="batchNumber" cssClass="textField"/></td>
                                <td width="25px;"></td>
                                <td class="content_td formLable">Customer Mobile Number</td>
                                <td class="content_td formLable">:</td>
                                <td><s:textfield id = "customerMobileNumber" name="customerMobileNumber" cssClass="textField"  /></td>
                            </tr>
                            <tr>
                                <td class="content_td formLable">Customer Account Number</td>
                                <td class="content_td formLable">:</td>
                                <td><s:textfield id = "customerAccNumber" name="customerAccNumber" cssClass="textField"  /></td>
                                <td width="25px;"></td>
                                <td class="content_td formLable">Amount (LKR)</td>
                                <td class="content_td formLable">:</td>
                                <td><s:textfield id = "amount" name="amount" cssClass="textField"  /></td>
                            </tr>
                            <tr>
                                <td class="formLable">Channel Type<span class="mandatory">*</span></td> <td >:</td>
                                <td><s:select id="channeltype" name="channeltype" headerKey="-1"  headerValue="---Select---"  list="%{channeltypeList}" cssClass="textField"/></td>
                                <td width="25px;"></td>
                                <td class="formLable">Status<span class="mandatory">*</span></td> <td >:</td>
                                <td><s:select id="txntype" name="txntype" headerKey="-1"  headerValue="---Select---"  list="%{statusList}" cssClass="textField"/></td> 
                            </tr>

                            <tr>
                                <td align="left">
                                    
                                    <sj:a id="searchbut"  button="true"  onClickTopics="onclicksearch"    
                                        cssClass="button_asave">Search</sj:a> 
                                        
                                   <s:submit  cssClass="button_adownload" value="PDF Download" /> 
                                </td>
                                </tr>
                            </table>
                    </s:form>

                </div>


                <div class="viewuser_tbl">
                    <div id="tablediv">
                        <%--<s:form action="" theme="simple" method="post" id="">--%>


                        <sj:dialog 
                            id="viewsmsdialog" 
                            buttons="{
                            'OK':function() { $( this ).dialog( 'close' );}                                    
                            }" 
                            autoOpen="false" 
                            modal="true"                            
                            width="600"
                            height="500"
                            position="center"
                            title="SMS History."
                            onOpenTopics="opensmsview" 
                            loadingText="Loading .."
                            >

                        </sj:dialog>



                        <sj:dialog 
                            id="terminalInfoDialog" 
                            buttons="{
                            'OK':function() { $( this ).dialog( 'close' );}                                     
                            }" 
                            autoOpen="false" 
                            modal="true"                            
                            width="1400"
                            height="550"
                            position="center"
                            title="Terminal Details."
                            onOpenTopics="openviewtxninfo" 
                            loadingText="Loading .."
                            >

                        </sj:dialog>
                        <%--</s:form>--%>
                        <%--<s:form action="" theme="simple" method="post" id="">--%>


                        <sj:dialog 
                            id="viewdialog" 
                            buttons="{
                            'OK':function() { $( this ).dialog( 'close' );}                                    
                            }" 
                            autoOpen="false" 
                            modal="true"                            
                            width="600"
                            height="500"
                            position="center"
                            title="Trancection History."
                            onOpenTopics="openview" 
                            loadingText="Loading .."
                            >

                        </sj:dialog>

                        <sj:dialog 
                            id="terminalInfoDialog" 
                            buttons="{
                            'OK':function() { $( this ).dialog( 'close' );}                                     
                            }" 
                            autoOpen="false" 
                            modal="true"                            
                            width="1400"
                            height="550"
                            position="center"
                            title="Terminal Details."
                            onOpenTopics="openviewtxninfo" 
                            loadingText="Loading .."
                            >

                        </sj:dialog>
                        <%--</s:form>--%>
                        <sj:dialog 
                            id="confirmdialogbox" 
                            buttons="{ 
                            'OK':function() { reverseNow($(this).data('keyval'));$( this ).dialog( 'close' ); },
                            'Cancel':function() { $( this ).dialog( 'close' );} 
                            }" 
                            autoOpen="false" 
                            modal="true" 
                            title="Confirm message"
                            width="400"
                            height="150"
                            position="center"
                            />

                        <s:url var="listurl" action="ListtxnMng"/>
                        <sjg:grid
                            id="gridtable"
                            caption="Transactions"
                            dataType="json"
                            href="%{listurl}"
                            pager="true"
                            gridModel="gridModel"
                            rowList="10,15,20"
                            rowNum="10"
                            shrinkToFit = "false"
                            autowidth="true"
                            rownumbers="true"
                            onCompleteTopics="completetopics"
                            onGridCompleteTopics="gridComplete"
                            rowTotal="false"
                            viewrecords="true"
                            onSelectRowTopics="rowselect"
                            >
                            <sjg:gridColumn name="id" index="ID" title="Transaction ID" align="left" width="250" sortable="true" frozen="true" hidden="true"/>                    
                            <sjg:gridColumn name="txn_type" index="TXN_TYPE" title="Transaction Type"  align="left" width="115"  sortable="true" frozen="true"/>                            
                            <sjg:gridColumn name="channel" index="CHANNEL_TYPE" title="Channel" align="left" width="120"   sortable="true"/>                      
                            <sjg:gridColumn name="customerID" index="CUSTOMER_ID" title="Customer Name"  align="left" width="150"  sortable="true"/>
                            <sjg:gridColumn name="recepientMobile" index="RECEPIENT_MOBILE" title="Recepient Mobile" align="left"  width="120"  sortable="true"/>                            
                            <sjg:gridColumn name="amount" index="AMOUNT" title="Amount (LKR)" align="right"  width="90"  sortable="true"/>
                            <sjg:gridColumn name="orderID" hidden="true" index="ORD_ID" title="Order ID" align="left"  width="60"  sortable="true"/>
                            <sjg:gridColumn name="customerAccountNumber" index="CUSTOMER_ACCOUNT_NUMBER" title="Customer Account No" align="left" width="150"   sortable="true"/>                    
                            <sjg:gridColumn name="customerMobile" index="CUSTOMER_MOBILE" title="Customer Mobile" align="left"  width="130"  sortable="true"/>
                            <sjg:gridColumn name="refNo" index="REF_NO" title="Trace No" align="left"  width="80"  sortable="true" />
                            <sjg:gridColumn name="batchNumber" index="BATCH_NO" title="Batch No" align="left"  width="80"  sortable="true" />
                            <sjg:gridColumn name="serviceCharge" index="SERVICE_CHARGE" title="Service Charge (LKR)" align="right" width="150"   sortable="true"/>                    
                            <sjg:gridColumn name="responseCode" index="RES_DES" title="Response Description" align="left" width="200"   sortable="true"/>                      
                            <sjg:gridColumn name="status" index="STATUS" title="Status" align="left" width="200"   sortable="true"/>
                            <sjg:gridColumn name="statusCode" index="STATUS" title="Status Code" align="left" width="250" sortable="true" hidden="true"/> 
                            <sjg:gridColumn name="collectionaccount" index="COLLECTION_ACCOUNT" title="Collection Account" align="left" width="200"   sortable="true"/>
                            <sjg:gridColumn name="glaccount" index="GL_ACCOUNT" title="GL Account" align="left" width="200"   sortable="true"/>
                            <sjg:gridColumn name="costcenter" index="COST_CENTER" title="Cost Center" align="left" width="200"   sortable="true"/>
                            <sjg:gridColumn name="itmtid" index="ITM_TID" title="ATM TID" align="left" width="200"   sortable="true"/>
                            <sjg:gridColumn name="itmlocation" index="ITM_LOCATION" title="ATM Location" align="left" width="200"   sortable="true"/>

                            <sjg:gridColumn name="datetime" index="TXN_DATE" hidden="true" title="Transaction Date Time" align="left" width="150"   sortable="true"/>
                            <sjg:gridColumn name="timestamp" index="TIMESTAMP" title="Timestamp" align="left" width="150"   sortable="true"/>
                            <sjg:gridColumn name="id" index="ID" title="Cancel" align="center" width="50" align="center"  formatter="reverseformatter" sortable="false"/>
                            <sjg:gridColumn name="id" index="ID" title=" Txn History" align="center" width="80" align="center"   formatter="historyformatter" sortable="false" />
                            <sjg:gridColumn name="id" index="ID" title="SMS History" align="center" width="80" align="center"   formatter="smshistoryformatter" sortable="false" />
                        </sjg:grid> 

                    </div> 
                </div>
                <div class="contentcenter" style="padding-top: 0px;">
                    <div style="display: inline-flex;">
                        <!--                        <div style="height:20px;width:20px; background:#FFA07A;">   
                                                </div>
                                                <div style="margin-left: 7px;" class="content_td formLable">Reversed</div>-->
                        <div style="height:20px;width:20px; background:#45A161">   
                        </div>
                        <div style="margin-left: 7px;" class="content_td formLable">Customer Request</div>

                        <div style="height:20px;width:20px; background:lightgreen;margin-left: 7px;">   
                        </div>
                        <div style="margin-left: 7px;" class="content_td formLable">ITM Reversed</div>

                        <div style="height:20px;width:20px; background:#87CEFA;margin-left: 7px;">   
                        </div>
                        <div style="margin-left: 7px;" class="content_td formLable">Withdrawal Completed</div>

                        <div style="height:20px;width:20px; background:#FA8A61;margin-left: 7px;">   
                        </div>
                        <div style="margin-left: 7px;" class="content_td formLable">Manual Reversed</div>

                        <div style="height:20px;width:20px; background:#FB9A77;margin-left: 7px;">   
                        </div>
                        <div style="margin-left: 7px;" class="content_td formLable">Auto Reversed</div>



                        <div style="height:20px;width:20px; background:#F73944;margin-left: 7px;">   
                        </div>
                        <div style="margin-left: 7px;" class="content_td formLable">Request Fail</div>

                    </div>

                </div>        

            </div>

        </div><!--end of body_content-->
        <jsp:include page="../../footer.jsp" /> 

    </div> 

</body>
</html>
