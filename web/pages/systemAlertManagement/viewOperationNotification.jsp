<%-- 
    Document   : viewOperationNotification
    Created on : 08/03/2016, 3:37:09 PM
    Author     : dimuthu_h
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"  %>  
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>      

<html>
    <head>
        <jsp:include page="/Styles.jsp" />
        <jsp:include page="../../header.jsp" />            
        <style>
            .tip {
                background: #eee;
                border: 1px solid #ccc;
                padding: 10px;
                border-radius: 8px;
                box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
                position: relative;
                //width: 200px;
                border:1px solid #444546;
                background: #eee;
                border: 0px;
                width: 40%;
                height:25px;
                margin: 0px;
                padding:5px;
                overflow: hidden;
                display:inline;
                color:#03a1fc;
                font-size:14px;
                resize: none;
            }
        </style>
        <script type="text/javascript">

            function load() {
                if ('true' === $('#vadd').val()) {
                    $('#addid').hide();
                }
            }
            function statusformatter(cellvalue, options, rowObject) {
                if (cellvalue == 'Active') {
                    var html = "<img src='${pageContext.request.contextPath}/resources/images/iconActive.png' />";
                } else {
                    var html = "<img src= '${pageContext.request.contextPath}/resources/images/iconInactive.png' />";
                }
                return html;
            }

            function readstatusformatter(cellvalue, options, rowObject) {
                if (cellvalue == 'Read') {
                    var html = "<img src='${pageContext.request.contextPath}/resources/images/normal_1.png' />";
                } else {
                    var html = "<img src= '${pageContext.request.contextPath}/resources/images/risk_critical_s.gif' />";
                }
                return html;
            }
            $.subscribe('onclicksearch', function (event, data) {
                var fromdate = $('#fromdate').val();
                var todate = $('#todate').val();
                var operation = $('#operation').val();
                $("#gridtable").jqGrid('setGridParam', {postData: {fromdate: fromdate, todate: todate, operation: operation, search: true}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            });


            //reset Datas
            function ResetSearchForm() {
                $('#operation').val("-1");
                $("#tooltip").hide();
                jQuery("#gridtable").trigger("reloadGrid");
            }

            $.subscribe('reload', function (event, data) {
                $('#stopThread').val(0);
                var operationId = $('#operation').val();
                $('#operationId2').val(operationId);
                jQuery("#gridtable").trigger("reloadGrid");
                start();
            });
            
            
             function OperationExecute() {
                 var op = $('#operation').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/SendoperationnotifiMng',
                    data: {operation: op},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.success) {
                               $("#tooltip").show();
                               document.getElementById('tooltipData').style.color = '#008000';
                               $('#tooltipData').val( data.message );
                               
                        }else {
                               $("#tooltip").show();
                               document.getElementById('tooltipData').style.color = '#ff0000';
                               $('#tooltipData').val( data.message );
                             
                        }
                        jQuery("#gridtable").trigger("reloadGrid");
                    },error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                    
                });

            }


        </script>
    </head>

    <body style="overflow:hidden" onload="load()">
        <div class="wrapper">

            <div class="body_content" id="includedContent" >

                <div class="watermark"></div>
                <div class="heading">Operation Notification</div>
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
                        <s:form id="searchForm" action="DownloadoperationnotifiMng" method="post" theme="simple">         
                            <table class="form_table">              

                            </table><table class="form_table">
                                <tr>
                                    <td class="formLable">Start Date<span class="mandatory">*</span></td>
                                    <td class="lable">:</td>
                                    <td><sj:datepicker id="fromdate" name="fromdate" readonly="true"  value="today" changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" cssClass="textField" cssStyle="margin-right:5px;"/></td>
                                </tr>
                                <tr>
                                    <td class="formLable">End Date<span class="mandatory">*</span></td>
                                    <td class="lable">:</td>
                                    <td><sj:datepicker id="todate" name="todate" readonly="true"  value="today" changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" cssClass="textField" cssStyle="margin-right:5px;"/></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Operation Category</td> 
                                    <td class="lable">:</td>
                                    <td ><s:select  name="operation" id="operation" list="%{operationList}" 
                                               listValue="value"   headerKey=""  headerValue="---Select---"      cssClass="dropdown" /></td>

                                    <td>
                                        <sj:a   id="sendbtn"  button="true"  onCompleteTopics="reload"  onclick="OperationExecute()"  cssClass="button_asearch"  role="button" aria-disabled="false" >Send</sj:a>
                                    </td>
                                    <td rowspan="2" width="100%" valign="center">
                                        <div id="tooltip" style="display: none; margin-left: 15px;margin-right: 0px; margin-bottom: 20px;" >
                                            <s:textarea name="tooltipData" id="tooltipData" cssClass="tip" readonly="true"   />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                <br>
                                <tr>                                
                                    <td> 
                                        <sj:a   id="searchid"  button="true"    onClickTopics="onclicksearch"  cssClass="button_aback"  role="button" aria-disabled="false" >Search</sj:a>
                                        <sj:a id="resetid" button="true"  onclick="ResetSearchForm()"   cssClass="button_aback" role="button" aria-disabled="false" >Reset</sj:a>
                                        <s:submit  id="btndownload" name="btndownload" targets="divmsg" value="XL VIEW"    cssClass="button_sadd" disabled="#vdownload" />
                                    </td>
                                </tr>
                            </table>
                        </s:form>

                    </div>


                    <div class="viewuser_tbl">
                        <div id="tablediv">

                            <s:url var="listurl" action="ListoperationnotifiMng"/>
                            <sjg:grid
                                id="gridtable"
                                caption="Operation Notification"
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



                                <sjg:gridColumn name="operationcode" index="OPERATION_CODE" title="Operation Name" align="left" width="20" sortable="true"/>
                                <sjg:gridColumn name="username"      index="USERNAME"       title="User Name" align="left"  width="18"  sortable="true"/>
                                <sjg:gridColumn name="ip"            index="IP"             title="IP" align="left"  width="18"  sortable="true"/> 
                                <sjg:gridColumn name="status" index="STATUS" title="Status" align="center" width="7" formatter="statusformatter"/>  
                                <sjg:gridColumn name="readstatus"    index="READ_STATUS"    title="Read Status" align="center"  width="7" formatter="readstatusformatter" hidden="true"/>
                                <sjg:gridColumn name="timestamp"     index="TIME_STAMP"      title="Timestamp" align="left"  width="18"  sortable="true"/>


                            </sjg:grid> 

                        </div> 



                    </div>
                </div>              

            </div>
        </div>
        <jsp:include page="../../footer.jsp" /> 

    </body>
</html>
