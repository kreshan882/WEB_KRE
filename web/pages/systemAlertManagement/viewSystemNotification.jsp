<%-- 
    Document   : viewSystemNotification
    Created on : Mar 8, 2016, 10:06:35 AM
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

            function ResetSearchForm() {
                $('#operation').val("-1");
                $('#readstatusform').val("-1");
                $('#alertidform').val("");
                jQuery("#gridtable").trigger("reloadGrid");
            }


            $.subscribe('onclicksearch', function (event, data) {
                var fromdate = $('#fromdate').val();
                var todate = $('#todate').val();
                var risklevelform = $('#risklevelform').val();
                var readstatusform = $('#readstatusform').val();
                var alertidform = $('#alertidform').val();
                $("#gridtable").jqGrid('setGridParam', {postData: {fromdate: fromdate, todate: todate, risklevelform: risklevelform, readstatusform: readstatusform, alertidform: alertidform, search: true}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            });

            function risklevelformatter(cellvalue, options, rowObject) {
                if (cellvalue == 'Critical') {
                    var html = "<img src='${pageContext.request.contextPath}/resources/images/risk_warning.PNG' />";
                } else {
                    var html = "<img src= '${pageContext.request.contextPath}/resources/images/risk_critical_s.gif' />";
                }
                return html;
            }

            function statusformatter(cellvalue, options, rowObject) {


                if (cellvalue == '15') {

                    var html = "<img src='${pageContext.request.contextPath}/resources/images/viewed.png' />";
                    return html;
                } else if (cellvalue == '16') {

                    var html = "<img src= '${pageContext.request.contextPath}/resources/images/not_viewed.png' />";
                    return html;
                }



            }

        </script>

        <style>

            .ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {
                /* border: 1px solid #cccccc; */
                /* background: #eeeeee url("images/ui-bg_glass_60_eeeeee_1x400.png") 50% 50% repeat-x; */
                font-weight: normal; 

            }
            .ui-jqgrid-labels {
                border: 1px solid #cccccc; 
                background: #eeeeee url("images/ui-bg_glass_60_eeeeee_1x400.png") 50% 50% repeat-x; 
                font-weight: normal;
            }

            .ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {
                border: 1px solid #cccccc;
            }


            .text_field1{
                border: 0px;
                height: 25px;
                width: 70px;
                /*vertical-align: middle;*/
                text-align: center;
                /*box-shadow: 0 0 8px #e4e2e1 inset;*/
                //transition: 500ms all ease;
                padding: 3px 3px 3px 3px;
                margin: 0px;
                font-family: Arial;
                font-size: 13px;
                font-weight: normal;
            }
            .text_field1:hover,
            .text_field1:focus{
                width: 70px;
                /*box-shadow: 0 0 8px #ffffff inset;*/
                box-shadow: 0 0 8px #9f9e9e;
                //background: url(../images/writing_file.png) no-repeat right;
                //transition: 500ms all ease;
                //background-size: 24px 24px;
                //background-position: 100% 60%;
                //padding: 3px 32px 3px 3px;
            }



            .rowbg{
                background: #eeefef; /* Old browsers */
                background: -moz-linear-gradient(top,  #eeefef 0%, #e3e6e6 50%, #eeefef 100%); /* FF3.6+ */
                background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#eeefef), color-stop(50%,#e3e6e6), color-stop(100%,#eeefef)); /* Chrome,Safari4+ */
                background: -webkit-linear-gradient(top,  #eeefef 0%,#e3e6e6 50%,#eeefef 100%); /* Chrome10+,Safari5.1+ */
                background: -o-linear-gradient(top,  #eeefef 0%,#e3e6e6 50%,#eeefef 100%); /* Opera 11.10+ */
                background: -ms-linear-gradient(top,  #eeefef 0%,#e3e6e6 50%,#eeefef 100%); /* IE10+ */
                background: linear-gradient(to bottom,  #eeefef 0%,#e3e6e6 50%,#eeefef 100%); /* W3C */
                filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#eeefef', endColorstr='#eeefef',GradientType=0 ); /* IE6-9 */

            }


        </style>
    </head>

    <body style="overflow:hidden" onload="load()">
        <div class="wrapper">

            <div class="body_content" id="includedContent" >

                <div class="watermark"></div>
                <div class="heading">System Notification</div>
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
                        <s:form id="searchForm" action="XSLcreatsystemnotifiMng" method="post"  theme="simple">         
                            <table class="form_table">              

                            </table><table class="form_table">
                                <tr>
                                    <td class="content_td formLable">Start Date<span class="mandatory">*</span></td>
                                    <td class="content_td formLable">:</td>
                                    <td><sj:datepicker id="fromdate" name="fromdate" readonly="true"  value="today" changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" cssClass="textField" cssStyle="margin-right:5px;"/></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable">End Date<span class="mandatory">*</span></td>
                                    <td class="content_td formLable">:</td>
                                    <td><sj:datepicker id="todate" name="todate" readonly="true"  value="today" changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" cssClass="textField" cssStyle="margin-right:5px;"/></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable">Risk Level</td>
                                    <td>:</td>
                                    <td><s:select id="risklevelform" name="risklevelform" headerKey=""  headerValue="---Select---"  list="%{riskLevelList}" cssClass="textField"/></td>
                                    <td class="content_td formLable">

                                    </td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable">Read Status</td>
                                    <td>:</td>
                                    <td><s:select id="readstatusform" name="readstatusform" headerKey=""  headerValue="---Select---"  list="%{readStatusList}" cssClass="textField"/></td>
                                    <td class="content_td formLable">

                                    </td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table"> 
                                <br>
                                <tr>                                
                                    <td> 

                                        <sj:a   id="searchid" button="true" onClickTopics="onclicksearch"  cssClass="button_aback"  role="button" aria-disabled="false" >Search</sj:a>
                                        <sj:submit id="resetid" button="true" value="Reset" onclick="ResetSearchForm()"   cssClass="button_aback" disabled="false" />
                                        <s:submit  id="xldwnbtn" button="true"  value="XL VIEW"   targets="divmsg"  cssClass="button_sadd" disabled="#vdownload"/>

                                    </td>
                                </tr>
                            </table>
                        </s:form>

                    </div>
                    <div class="count1" style="bottom:10px; margin-left: auto; margin-right: 10; margin-top:auto;margin-bottom:0px;display: table;">

                        <s:form theme="simple">
                            <table border="0" class="rowbg" style="white-space:nowrap;margin:0px;" height="30px">
                                <tr>
                                    <td align="center" style="background: #ffb200;font-family: Arial; font-size: 13px;color:#ffffff; padding-left: 8px;padding-right:8px;">SUMMARY</td>
                                    <td class="content_td formLable" align="center" style="font-family: Arial; font-size: 13px;margin: 0px;padding: 0px;padding-left: 5px;">Critical</td>
                                    <td class="content_td formLable" style="padding: 0px;margin: 0px;border-right: 1px solid #a0a0a0;"><s:textfield name="criticalAlertCountSysAlert" id="criticalAlertCountSysAlert" readonly="true" cssClass="text_field1" cssStyle="color:#fc0d0d; font-weight:bold; font-size:14;font-family: Arial;background-color:rgba(0,0,0,0);" /></td>

                                    <td class="content_td formLable" align="center" style="font-family: Arial; font-size: 13px;margin: 0px;padding: 0px;padding-left: 5px;">Warning</td>
                                    <td class="content_td formLable" style="padding: 0px;margin: 0px;border-right: 1px solid #a0a0a0;"><s:textfield name="warningAlertCountSysAlert" id="warningAlertCountSysAlert" readonly="true"  cssClass="text_field1" cssStyle=" color:#d97500; font-weight:bold; font-size:14;font-family: Arial;background-color:rgba(0,0,0,0);"/></td>

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

                            <s:url var="listurl" action="ListsystemnotifiMng"/>
                            <sjg:grid
                                id="gridtable"
                                caption="System Notification"
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

                                <sjg:gridColumn name="id" index="ID" title="Alert ID" align="center" width="20" sortable="true" hidden="true"/>

                                <sjg:gridColumn name="risklevel" index="RISK_LEVEL" title="Risk Level" align="center" width="5" sortable="true" formatter="risklevelformatter"/>
                                <sjg:gridColumn name="readstatus" index="READ_STATUS" title="Read Status" align="center"  width="5"  sortable="true" formatter="statusformatter"/>
                                <sjg:gridColumn name="timestamp" index="TIMESTAMP" title="Date Time" align="left"  width="10"  sortable="true"/>
                                <sjg:gridColumn name="description" index="DESCRIPTION" title="Description" align="left"  width="15"  sortable="true"/>                    

                            </sjg:grid> 

                        </div> 



                    </div>
                </div>              

            </div>
        </div>
        <jsp:include page="../../footer.jsp" /> 

    </body>
</html>
