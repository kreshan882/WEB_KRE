<%-- 
    Document   : systemOperation
    Created on : May 24, 2016, 12:14:41 PM
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
                jQuery("#gridtable").trigger("reloadGrid");
            }
            
              $.subscribe('reload', function(event, data) {
                $('#stopThread').val(0);
                var operationId = $('#operationId').val();
                $('#operationId2').val(operationId);
                jQuery("#gridtable").trigger("reloadGrid");
                start();
            });


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
                        <s:form id="searchForm"  method="post"  theme="simple" action="SendsysOperations">         
                            <table class="form_table">              

                            </table><table class="form_table">
                         
                                <tr>
                                    <td class="formLable">Operation Category</td> 
                                    <td class="lable">:</td>
                                    <td ><s:select  name="operationId" id="operationId" list="%{operationMap}" 
                                               listValue="value"   headerKey=""  headerValue="---Select---"      cssClass="dropdown" /></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                <br>
                                <tr>                                
                                    <td> 
                                        <s:url var="sendurl" action="SendsysOperations"/>                                       
                                        <sj:submit 
                                            id="updatebutton"
                                            button="true"
                                            href="%{sendurl}"
                                            value="Send" 
                                            targets="message"  
                                            cssClass="button_asearch"
                                            cssStyle="font-weight: normal;"
                                            onCompleteTopics="reload"
                                            />



                                    </td>
                                </tr>
                            </table>
                        </s:form>

                    </div>


                    <div class="viewuser_tbl">
                        <div id="tablediv">

                            <s:url var="listurl" action="ListsysOperations"/>
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
                                <sjg:gridColumn name="ip"            index="IP"             title="Ip" align="left"  width="18"  sortable="true"/> 
                                <sjg:gridColumn name="status" index="STATUS" title="Status" align="center" width="7" formatter="statusformatter" />  
                                <sjg:gridColumn name="readstatus"    index="READ_STATUS"    title="Read Status" align="center"  width="7" formatter="readstatusformatter" />
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
