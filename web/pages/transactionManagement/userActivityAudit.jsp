<%-- 
    Document   : addNewUserISA
    Created on : Aug 6, 2014, 4:03:57 PM
    Author     : kreshan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%> 
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>      

<html>
    <head>
        <title>System Audit</title>
        <jsp:include page="/Styles.jsp" />
        
        <script type="text/javascript">
            
               $.subscribe('onclicksearch', function(event, data) {

                   
                    var fromdate= $('#fromdate').val();
                    var todate= $('#todate').val();
                    var username=$('#username').val();
                    $("#gridtable").jqGrid('setGridParam', {postData: {fromdate:fromdate ,todate:todate,username:username,search: true}});
                    $("#gridtable").jqGrid('setGridParam', {page: 1});
                    jQuery("#gridtable").trigger("reloadGrid");

                });
//            $.subscribe('gridComplete', function (event, data) {
//                $.each(jQuery(data).jqGrid('getRowData'), function (i, item) {
//                    if (item.operation == 'Delete') { //deleted                        
//                      $('#gridtable').find('tr:eq('+(i+1)+')').css({'background-color':'#F53871','color':'#fff'});
//                      $('#gridtable').find('tr:eq('+(i+1)+')').children().first().css({'color':'#fff'});
//                    }else if (item.operation == 'Add'){
//                      $('#gridtable').find('tr:eq('+(i+1)+')').css({'background-color':'#45A161','color':'#fff'});
//                      $('#gridtable').find('tr:eq('+(i+1)+')').children().first().css({'color':'#fff'});
//                    }
//                    else if (item.operation == 'Update'){
//                      $('#gridtable').find('tr:eq('+(i+1)+')').css({'background-color':'#4563BF','color':'#fff'});
//                      $('#gridtable').find('tr:eq('+(i+1)+')').children().first().css({'color':'#fff'});
//                    }
//                });
//            });
            
            
        </script>
    </head>
    <body style="overflow:hidden">
        <div class="wrapper">

            <jsp:include page="../../header.jsp" /> 
            <div class="body_content" id="includedContent">


                
                    <div class="watermark"></div>
                    <div class="heading">User Activity Audit</div>
                <div class="AddUser_box ">
                    
                    <div class="contentcenter">
                        

                        <s:form action="loginCall" theme="simple">         
                            <table class="form_table">
                                <div class="message2">
                                    <s:div id="message1">
                                        <i style="color: red">  <s:actionerror theme="jquery"/></i>
                                        <i style="color: green"> <s:actionmessage theme="jquery"/></i>
                                    </s:div>
                            </div>
                                <tr>
                                    <td class="content_td formLable">Period From<span class="mandatory">*</span></td>
                                    <td class="content_td formLable">:</td>
                                    <td><sj:datepicker id="fromdate" name="fromdate" readonly="true" value="today"   changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" cssClass="textField"  /></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable">Period To<span class="mandatory">*</span></td>
                                    <td class="content_td formLable">:</td>
                                    <td><sj:datepicker id="todate" name="todate" readonly="true" value="tomorrow"   changeYear="true" buttonImageOnly="true" displayFormat="yy-mm-dd" cssClass="textField"  /></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable">User Name</td>
                                    <td class="content_td formLable">:</td>
                                    <td><s:textfield id = "username" name="username" cssClass="textField"  /></td>
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


                        <s:url var="listurl" action="ListuseractivityMng"/>
                        <sjg:grid
                            id="gridtable"
                            caption="Audit History"
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
                            <sjg:gridColumn name="id" index="id" title="ID" align="left" width="100" sortable="true" hidden="true"/>                    
                            <sjg:gridColumn name="username" index="USER_ID" title="User Name"  align="left" width="100"  sortable="true" frozen="true"/>                            
                            <sjg:gridColumn name="module" index="MODULE_ID" title="Module"  align="left" width="200"  sortable="true" frozen="true"/>
                            <sjg:gridColumn name="section" index="SECTION_ID" title="Section" align="left"  width="200"  sortable="true" frozen="true"/>                            
                            <sjg:gridColumn name="operation" index="TASK_ID" title="Operation" align="left"  width="100"  sortable="true" frozen="true"/>
                            <sjg:gridColumn name="ip" index="ip" title="IP" align="left"  width="100"  sortable="true"/>
                            <sjg:gridColumn name="message" index="DESCRIPTION" title="Description" align="left" width="250"   sortable="true"/>                    
                            <sjg:gridColumn name="datetime" index="TIMESTAMP" title="Date and Time" align="left" width="160"   sortable="true"/>                      

                        </sjg:grid> 

                    </div> 
                    </div>
                </div>
            </div><!--end of body_content-->
            <jsp:include page="../../footer.jsp" /> 

        </div> 

    </body>
</html>
