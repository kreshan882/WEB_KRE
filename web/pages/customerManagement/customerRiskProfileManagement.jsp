<%-- 
    Document   : customerRiskManagement
    Created on : Mar 2, 2016, 12:15:41 PM
    Author     : nipun_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"  %>  
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>      

<html>
    <head>
        <jsp:include page="/Styles.jsp" />
        <jsp:include page="../../header.jsp" />            

        <script type="text/javascript">


            function load() {
                if ('true' === $('#vadd').val()) {
                    $('#addid').hide();
                }
            }


            function resetData() {
                $('#name').val("");
                $('#maxamountperday').val("");
                $('#transfermode').val("-1");
                $('#msgdeliverymodeord').val("-1");
                $('#chargesmode').val("-1");
                $('#msgvalidityperiod').val("");
//                $('#globalamount').val("");
                $('#nooftxnallowedperdayrecipient').val("");
                $('#minamountpertxn').val("");
                $('#maxamountpertxn').val("");
                $('#feecalculationmethod').val("-1");
                $('#feevalue').val("");
                $('#msgdeliverymodesec').val("-1");

                $('#upid').val("");
                $('#upname').val("");
                $('#upmaxamountperday').val("");
                $('#uptransfermode').val("-1");
                $('#upmsgdeliverymodeord').val("-1");
                $('#upchargesmode').val("-1");
                $('#upmsgvalidityperiod').val("");
//                $('#upglobalamount').val("");
                $('#upnooftxnallowedperdayrecipient').val("");
                $('#upminamountpertxn').val("");
                $('#upmaxamountpertxn').val("");
                $('#upfeecalculationmethod').val("-1");
                $('#upfeevalue').val("");
                $('#upmsgdeliverymodesec').val("-1");
                $('#upstatus').val("");


                jQuery("#gridtable").trigger("reloadGrid");
            }

            function statusformatter(cellvalue, options, rowObject) {
                if (cellvalue == 'Active') {
                    var html = "<img src='${pageContext.request.contextPath}/resources/images/iconActive.png' />";
                } else {
                    var html = "<img src= '${pageContext.request.contextPath}/resources/images/iconInactive.png' />";
                }
                return html;
            }


            function deleteformatter(cellvalue, options, rowObject) {
                if (rowObject.defaultStatus == 0) {
                    return "<a href='#' onClick='deleteInit(&#34;" + cellvalue + "&#34;,&#34;" + rowObject.name + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/iconDelete.png'  /></a>";
                } else {
                    return "<a href='#'><img src='${pageContext.request.contextPath}/resources/images/iconDelete.png'  /></a>";
                }
            }

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='javascript:editNow(&#34;" + cellvalue + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
            }
//            function pdchangeformatter(cellvalue, options, rowObject) {
//                return "<a href='#' onClick='javascript:pdchangeNow(&#34;" + cellvalue + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
//            }

            function deleteInit(keyval, nameVal) {
                $("#confirmdialogbox").data('keyval', keyval).dialog('open');
                $("#confirmdialogbox").html('<br><b><font size="3" color="red"><center>Please confirm to delete customer : ' + nameVal + '');
                return false;
            }

//             function pdchangeNow(keyval){
//                $('#message').empty();
//
//                $('#pwresetForm').show();
//                $('#editForm').hide();
//                $('#searchForm').hide();
//                
//                $('#rusername').val(keyval);
//            }

            function deleteNow(keyval) {
                $('#divmsg').empty();
                $.ajax({
                    url: '${pageContext.request.contextPath}/DeletecusriskMng',
                    data: {id: keyval},
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


            function editNow(keyval) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/FindcusriskMng',
                    data: {id: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        $('#editForm').show();
                        $('#searchForm').hide();
                        $('#addForm').hide();

                        $('#upid').attr('readOnly', true).val(data.upid);
                        $('#upname').val(data.upname);
                        $('#upmaxamountperday').val(data.upmaxamountperday);
                        $('#uptransfermode').val(data.uptransfermode);
                        $('#upmsgdeliverymodeord').val(data.upmsgdeliverymodeord);
                        $('#upmsgvalidityperiod').val(data.upmsgvalidityperiod);
//                        $('#upglobalamount').val(data.upglobalamount);
                        $('#upchargesmode').val(data.upchargesmode);
                        $('#upnooftxnallowedperdayrecipient').val(data.upnooftxnallowedperdayrecipient);
                        $('#upminamountpertxn').val(data.upminamountpertxn);
                        $('#upmaxamountpertxn').val(data.upmaxamountpertxn);
                        $('#upfeecalculationmethod').val(data.upfeecalculationmethod);
                        $('#upfeevalue').val(data.upfeevalue);
                        $('#upmsgdeliverymodesec').val(data.upmsgdeliverymodesec);
                        $('#upstatus').val(data.upstatus);


                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });
            }


            function backToMain() {
                $('#editForm').hide();
                $('#searchForm').show();
                $('#addForm').hide();

                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");

            }




            $.subscribe('onclicksearch', function (event, data) {
                var rpname = $('#rpname').val();
                $("#gridtable").jqGrid('setGridParam', {postData: {rpname: rpname, search: true}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            });

            $.subscribe('loadAddForm', function (event, data) {
                $('#editForm').hide();
                $('#searchForm').hide();
                $('#addForm').show();
            });

            //reset Datas
            function ResetSearchForm() {
                $('#rpname').val("");
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");
            }
            function ResetAddForm() {
                resetData();
                $('#divmsg').empty();
            }

            function resetUpdateForm() {
                var id = $('#upid').val();
                editNow(id);
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");

            }

        </script>
    </head>

    <body style="overflow:hidden" onload="load()">
        <div class="wrapper">

            <div class="body_content" id="includedContent" >

                <div class="watermark"></div>
                <div class="heading">Customer Risk Profile Management</div>
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
                        <s:form id="searchForm" action="cusriskMng" method="post"  theme="simple">         
                            <table class="form_table">              
                                <tr>
                                    <td class="content_td formLable">Risk Profile Name</td>
                                    <td>:</td>
                                    <td colspan="2"><s:textfield name="rpname"  id="rpname" cssClass="textField" /> </td>
                                    <td class="content_td formLable">

                                    </td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> 
                                        <%--<sj:submit   button="true"  value="Adddsub"   onClickTopics="loadAddForm"  cssClass="button_sadd" disabled="#vadd"/>--%>
                                        <sj:a id="addid" button="true" onClickTopics="loadAddForm"  cssClass="button_sadd" disabled="#vadd" > Add </sj:a>
                                        <sj:a   id="searchid"  button="true"    onClickTopics="onclicksearch"  cssClass="button_aback"  role="button" aria-disabled="false" >Search</sj:a>
                                        <sj:submit id="resetid" button="true" value="Reset" onclick="ResetSearchForm()"   cssClass="button_aback" disabled="false" />

                                    </td>
                                </tr>
                            </table>
                        </s:form>

                        <s:form  id="addForm"  theme="simple" method="post"  cssStyle="display:none">
                            <table class="form_table">
                                <tr>
                                    <td class="formLable">Name<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="name" name="name" cssClass="textField" /></td>                                    
                                    <td width="25px;"></td>

                                </tr> 
                                <tr>
                                    <td class="formLable">Min Amount Per Transaction (LKR)<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="minamountpertxn" name="minamountpertxn" cssClass="textField" onkeyup="keyup()" onkeydown="keydown()"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Max Amount Per Transaction (LKR)<span class="mandatory">*</span></td>  <td >:</td>
                                    <td><s:textfield id="maxamountpertxn" name="maxamountpertxn" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Max Amount Per Day (LKR)<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="maxamountperday" name="maxamountperday" cssClass="textField" /></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">No of Transaction Allowed Per Day Recipient<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="nooftxnallowedperdayrecipient" name="nooftxnallowedperdayrecipient" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Fee Calculation Method<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:select id="feecalculationmethod" name="feecalculationmethod" headerKey="-1"  headerValue="---Select---"  list="%{feeCalMethodList}" cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Fee Value (LKR)<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="feevalue" name="feevalue" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Transfer Mode<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:select id="transfermode" name="transfermode" headerKey="-1"  headerValue="---Select---"  list="%{transferModeList}" cssClass="textField"/></td> 
                                    <td width="25px;"></td>
                                    <td class="formLable">Message Delivery Mode Order<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="msgdeliverymodeord" name="msgdeliverymodeord" headerKey="-1"  headerValue="---Select---"  list="%{messegeDeliveryList}" cssClass="textField"/></td>

                                </tr>
                                <tr>
                                    <td class="formLable">Message Validity Period<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="msgvalidityperiod" name="msgvalidityperiod" headerKey="-1"  headerValue="---Select---"  list="%{msgvalidityperiodList}" cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Message Delivery Mode Secrete<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="msgdeliverymodesec" name="msgdeliverymodesec" headerKey="-1"  headerValue="---Select---"  list="%{messegeDeliveryList}" cssClass="textField"/></td>

                                </tr>
                                <tr>
                                    <td class="formLable">Charges Mode<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:select id="chargesmode" name="chargesmode" headerKey="-1"  headerValue="---Select---"  list="%{messegeDeliveryList}" cssClass="textField"/></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> <s:url var="addurl" action="AddcusriskMng"/>                                   
                                        <sj:submit   button="true" href="%{addurl}" value="Add"   targets="divmsg"  cssClass="button_sadd" disabled="#vadd"/> 
                                        <sj:submit id="resetida" button="true" value="Reset" onclick="ResetAddForm()"   cssClass="button_aback" disabled="false" />
                                        <sj:submit id="backida" button="true" value="Back" onclick="backToMain()"   cssClass="button_aback" disabled="false" /> 
                                    </td>
                                </tr>
                            </table>
                        </s:form>


                        <s:form id="editForm"  theme="simple" method="post" cssStyle="display:none" >
                            <table class="form_table">
                                <tr><td hidden="true"><s:textfield id="upid" name="upid" cssClass="textField" /></td>
                                    <td class="formLable">Name<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upname" name="upname" cssClass="textField" /></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Status<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="upstatus" name="upstatus" headerKey="-1"  headerValue="---Select---"  list="%{upstatusList}" cssClass="textField"/></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Min Amount Per Transaction (LKR)<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="upminamountpertxn" name="upminamountpertxn" cssClass="textField" /></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Max Amount Per Transaction (LKR)<span class="mandatory">*</span></td>  <td >:</td>
                                    <td><s:textfield id="upmaxamountpertxn" name="upmaxamountpertxn" cssClass="textField" /></td>

                                </tr>
                                <tr>
                                    <td class="formLable">Max Amount Per Day (LKR)<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="upmaxamountperday" name="upmaxamountperday" cssClass="textField" /></td>                                   
                                    <td width="25px;"></td>
                                    <td class="formLable">No of Transaction Allowed Per Day Recipient<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="upnooftxnallowedperdayrecipient" name="upnooftxnallowedperdayrecipient" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Fee Calculation Method<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:select id="upfeecalculationmethod" name="upfeecalculationmethod" headerKey="-1"  headerValue="---Select---"  list="%{feeCalMethodList}" cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Fee Value (LKR)<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upfeevalue" name="upfeevalue" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Transfer Mode<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:select id="uptransfermode" name="uptransfermode" headerKey="-1"  headerValue="---Select---"  list="%{transferModeList}" cssClass="textField"/></td> 
                                    <td width="25px;"></td>
                                    <td class="formLable">Message Delivery Mode Order<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="upmsgdeliverymodeord" name="upmsgdeliverymodeord" headerKey="-1"  headerValue="---Select---"  list="%{messegeDeliveryList}" cssClass="textField"/></td>

                                </tr>
                                <tr>
                                    <td class="formLable">Message Validity Period<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="upmsgvalidityperiod" name="upmsgvalidityperiod" headerKey="-1"  headerValue="---Select---"  list="%{msgvalidityperiodList}" cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Message Delivery Mode Secrete<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="upmsgdeliverymodesec" name="upmsgdeliverymodesec" headerKey="-1"  headerValue="---Select---"  list="%{messegeDeliveryList}" cssClass="textField"/></td>
                                </tr>

                                <tr>
                                    <td class="formLable">Charges Mode<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:select id="upchargesmode" name="upchargesmode" headerKey="-1"  headerValue="---Select---"  list="%{messegeDeliveryList}" cssClass="textField"/></td>
                                </tr>

                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> <s:url var="updatecusturl" action="UpdatecusriskMng"/>                                   
                                        <sj:submit   button="true" href="%{updatecusturl}" value="Update"   targets="divmsg"  cssClass="button_sadd" disabled="#vupdate"/>
                                        <sj:submit button="true" value="Reset" onClick="resetUpdateForm()" cssClass="button_aback"/>
                                        <sj:a href="#" name="back" button="true" onclick="backToMain()"  cssClass="button_aback" >Back</sj:a>    
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
                                'OK':function() { deleteNow($(this).data('keyval'));$( this ).dialog( 'close' ); },
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
                            <!-- End delete dialog box -->

                            <s:url var="listurl" action="ListcusriskMng"/>
                            <sjg:grid
                                id="gridtable"
                                caption="Customer Risk Profile Management"
                                dataType="json"
                                href="%{listurl}"
                                pager="true"
                                gridModel="gridModel"
                                rowList="10,15,20"
                                rowNum="10"
                                autowidth="true"
                                shrinkToFit="false"
                                rownumbers="true"
                                onCompleteTopics="completetopics"
                                rowTotal="false"
                                viewrecords="true"
                                >

                                <sjg:gridColumn name="id" index="id" title="ID" frozen="true" hidden="true"/>

                                <sjg:gridColumn name="name" index="NAME" title="Name" align="left" width="100" frozen="true" sortable="true"/>
                                <sjg:gridColumn name="minamountpertxn" index="MIN_AMOUNT_PER_TXN" title="Min amount per transaction (LKR)" align="right"  width="250"  sortable="true"/>
                                <sjg:gridColumn name="maxamountpertxn" index="MAX_AMOUNT_PER_TXN" title="Max amount per transaction (LKR)" align="right"  width="250"  sortable="true"/>
                                <sjg:gridColumn name="maxamountperday" index="MAX_AMOUNT_PER_DAY" title="Max Amount Per Day (LKR)" align="right" width="200"  sortable="true"/>                    
                                <sjg:gridColumn name="nooftxnallowedperdayrecipient" index="NO_OF_TXN_ALLOWED_PER_DAY_RECIPIENT" title="No of Transaction allowed per day recipient" align="left"  width="300"  sortable="true"/>
                                <sjg:gridColumn name="feecalculationmethod" index="FEE_CALCULATION_METHOD" title="Fee calculation method" align="left"  width="150"  sortable="true"/>
                                <sjg:gridColumn name="feevalue" index="FEE_VALUE" title="Fee value (LKR)" align="right"  width="150"  sortable="true"/>
                                <sjg:gridColumn name="globalamount" hidden="true" index="GLOBAL_AMOUNT" title="Global Amount (LKR)" align="right"  width="200"  sortable="true"/>
                                <sjg:gridColumn name="transfermode" index="TRANSFER_MODE" title="Transfer Mode" align="left"  width="100"  sortable="true"/>
                                <sjg:gridColumn name="msgdeliverymodeord" index="MSG_DELIVERY_MODE_ORD" title="Messege Delivery Mode Order" align="left"  width="200"  sortable="true"/>
                                <sjg:gridColumn name="msgvalidityperiod" index="MSG_VALIDITY_PERIOD" title="Messege Validity Period" align="left"  width="150"  sortable="true"/>
                                <sjg:gridColumn name="msgdeliverymodesec" index="MSG_DELIVERY_MODE_SEC" title="Messege delivery mode sec" align="left"  width="150"  sortable="true"/>
                                <sjg:gridColumn name="chargesmode" index="CHARGES_MODE" title="Charges Mode" align="left" width="100" sortable="true"/>
                                <sjg:gridColumn name="status" index="STATUS" title="Status" align="center" width="50" formatter="statusformatter" sortable="true"/> 
                                <sjg:gridColumn name="defaultStatus" index="DEFAULT_STATUS" title="Deafult Status"  hidden="true"/>
                                <%--<sjg:gridColumn name="username" index="USERNAME" title="Reset Pw" align="center" width="7" align="center"  formatter="pdchangeformatter" sortable="false" hidden="#vresetpass"/>--%>
                                <sjg:gridColumn name="id" index="ID" title="Edit" align="center" width="50" align="center"  formatter="editformatter" sortable="false" hidden="#vupdate"/>
                                <sjg:gridColumn name="id" index="ID" title="Delete" align="center" width="50" align="center"   formatter="deleteformatter" sortable="false" hidden="#vdelete"/>

                            </sjg:grid> 

                        </div> 



                    </div>
                </div>              

            </div>
        </div>
        <jsp:include page="../../footer.jsp" /> 

    </body>
</html>
