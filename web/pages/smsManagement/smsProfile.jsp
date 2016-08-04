<%-- 
    Document   : smsProfile
    Created on : Mar 3, 2016, 11:09:38 AM
    Author     : kreshan
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
                $('#proId').val("");
                $('#proName').val("");
                $('#upproId').val("");
                $('#upproName').val("");
                $('#upstatus').val("");
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function statusformatter(cellvalue, options, rowObject) {
                if (cellvalue == '1') {
                    var html = "<img src='${pageContext.request.contextPath}/resources/images/iconActive.png' />";
                } else {
                    var html = "<img src= '${pageContext.request.contextPath}/resources/images/iconInactive.png' />";
                }
                return html;
            }


            function deleteformatter(cellvalue, options, rowObject) {
                if (rowObject.defaultStatus == 0) {
                    return "<a href='#' onClick='deleteInit(&#34;" + rowObject.proId + "&#34;,&#34;" + rowObject.proName + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/iconDelete.png'  /></a>";
                } else {
                    return "<a href='#'><img src='${pageContext.request.contextPath}/resources/images/iconDelete.png'  /></a>";
                }

            }

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='javascript:editNow(&#34;" + cellvalue + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
            }

            function deleteInit(keyval, keyval2) {
                $("#confirmdialogbox").data('keyval', keyval).dialog('open');
                $("#confirmdialogbox").html('<br><b><font size="3" color="red"><center>Please confirm to delete sms profile : ' + keyval2 + '');
                return false;
            }

            function deleteNow(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/DeletesmstempprofileMng',
                    data: {upproId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.success) {
                            resetData();
                            backToMain();
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
                    url: '${pageContext.request.contextPath}/FindsmstempprofileMng',
                    data: {upproId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        $('#editForm').show();
                        $('#searchForm').hide();
                        $('#addForm').hide();

                        $('#upproId').val(data.upproId);
                        $('#upproName').attr('readOnly', true);
                        $('#upproName').val(data.upproName);
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
                var proName = $('#proName').val();
                $("#gridtable").jqGrid('setGridParam', {postData: {proName: proName, search: true}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            });

            $.subscribe('loadAddForm', function (event, data) {
                $('#editForm').hide();
                $('#searchForm').hide();
                $('#addForm').show();
            });

            function ResetSearchForm() {
                $('#proName').val("");
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");
            }
            function ResetAddForm() {
                resetData();
                $('#divmsg').empty();
            }

            function resetUpdateForm() {
                var upproName = $('#upproName').val();
                editNow(upproName);
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");

            }



            function smstempformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='SMS Template' onClick='javascript:viewTemp(&#34;" + cellvalue + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
            }


            function viewTemp(keyval) {
//            alert(keyval)
                $("#viewdialog").data('proId', keyval).dialog('open');
            }

            $.subscribe('openview', function (event, data) {
                var $led = $("#viewdialog");
                $led.load("ListTemplatessmstempMng?proId=" + $led.data('proId'));
            });

        </script>
    </head>

    <body style="overflow:hidden" onload="load()">
        <div class="wrapper">

            <div class="body_content" id="includedContent" >

                <div class="watermark"></div>
                <div class="heading">SMS Template Profile Management</div>
                <div class="AddUser_box ">

                    <div class="message">         
                        <s:div id="divmsg">
                            <i style="color: red">  <s:actionerror theme="jquery"/></i>
                            <i style="color: green"> <s:actionmessage theme="jquery"/></i>
                        </s:div>         
                    </div>
                    <s:hidden  id="vadd" name="vadd" default="true"/>
                    <s:set id="vadd" var="vadd"><s:property  value="vadd" default="true"/></s:set>
                    <s:set var="vupdate"><s:property value="vupdate" default="true"/></s:set>
                    <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>
                    <s:set var="vdownload"><s:property value="vdownload" default="true"/></s:set>
                    <s:set var="vresetpass"><s:property value="vresetpass" default="true"/></s:set>

                        <div class="contentcenter">
                        <s:form id="searchForm" action="smstempprofileMng" method="post"  theme="simple">         
                            <table class="form_table">              
                                <tr>
                                    <td class="content_td formLable">Search Profile Name</td>
                                    <td>:</td>
                                    <td colspan="2"><s:textfield name="proName"  id="proName" cssClass="textField" /> </td>
                                    <td class="content_td formLable">

                                    </td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> 
                                        <sj:a id="addid" button="true" onClickTopics="loadAddForm"  cssClass="button_asearch" disabled="#vadd" > Add </sj:a>
                                        <sj:a   id="searchid"  button="true"    onClickTopics="onclicksearch"  cssClass="button_asearch"  role="button" aria-disabled="false" >Search</sj:a>
                                        <sj:submit id="resetid" button="true" value="Reset" onclick="ResetSearchForm()"   cssClass="button_aback" disabled="false" />

                                    </td>
                                </tr>
                            </table>
                        </s:form>

                        <s:form  id="addForm"  theme="simple" method="post"  cssStyle="display:none">
                            <table class="form_table">
                                <tr>
                                    <td class="formLable">SMS Profile Name<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="proName" name="proName" cssClass="textField" /></td>                                    
                                    <td width="25px;"></td>
                                    <td class="formLable"><td></td>
                                    <td></td>
                                </tr>    
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                <br>
                                <tr>                                
                                    <td> <s:url var="addurl" action="AddsmstempprofileMng"/>                                   
                                        <sj:submit   button="true" href="%{addurl}" value="Add"   targets="divmsg"  cssClass="button_sadd" disabled="#vadd"/> 
                                        <sj:submit id="resetida" button="true" value="Reset" onclick="ResetAddForm()"   cssClass="button_aback" disabled="false" />
                                        <sj:submit id="backida" button="true" value="Back" onclick="backToMain()"   cssClass="button_aback" disabled="false" /> 
                                    </td>
                                </tr>
                            </table>
                        </s:form>

                        <s:form id="editForm"  theme="simple" method="post" cssStyle="display:none" >
                            <table class="form_table">
                                <s:hidden name="upproId" id="upproId"/>
                                <tr>
                                    <td class="formLable">SMS Profile Name</td><td>:</td>
                                    <td ><s:textfield name="upproName" id="upproName" cssClass="textField" /></td>
                                    <td width="25px"></td>
                                    <td class="formLable">Status<span class="mandatory">*</span></td> <td>:</td>
                                    <td ><s:select  name="upstatus" id="upstatus" list="%{upstatusList}" 
                                               listKey="key" listValue="value"    headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" /></td>
                                </tr>


                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> <s:url var="updateuserurl" action="UpdatesmstempprofileMng"/>                                   
                                        <sj:submit   button="true" href="%{updateuserurl}" value="Update"   targets="divmsg"  cssClass="button_sadd" disabled="#vupdate"/>
                                        <sj:submit button="true" value="Reset" onClick="editNow($('#upproId').val())" cssClass="button_aback"/>
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

                            <sj:dialog 
                                id="viewdialog" 
                                buttons="{
                                'OK':function() { $( this ).dialog( 'close' );}                                    
                                }" 
                                autoOpen="false" 
                                modal="true"                            
                                width="1000"
                                height="500"
                                position="center"
                                title="SMS Templates"
                                onOpenTopics="openview" 
                                loadingText="Loading .."
                                >

                            </sj:dialog>
                            <!-- End delete dialog box -->

                            <s:url var="listurl" action="ListsmstempprofileMng"/>
                            <sjg:grid
                                id="gridtable"
                                caption="SMS Profile Management"
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
                                <sjg:gridColumn name="proId" index="CODE" title="ProfileId"  hidden="true"/>

                                <sjg:gridColumn name="proName" index="DESCRIPTION" title="SMS Profile Name" align="left" width="20" sortable="true"/>
                                <sjg:gridColumn name="status" index="STATUS" title="Status" align="center" width="10" formatter="statusformatter" sortable="true"/>  
                                <sjg:gridColumn name="defaultStatus" index="DEFAULT_STATUS" title="Deafult Status" hidden="true"/>
                                <sjg:gridColumn name="proId" index="CODE" title="Assign Template" align="center" width="7" align="center"  formatter="smstempformatter" sortable="false"/>
                                <sjg:gridColumn name="proId" index="CODE" title="Edit" align="center" width="7" align="center"  formatter="editformatter" sortable="false" hidden="#vupdate"/>
                                <sjg:gridColumn name="proId" index="CODE" title="Delete" align="center" width="7" align="center"   formatter="deleteformatter" sortable="false" hidden="#vdelete"/>

                            </sjg:grid> 

                        </div> 



                    </div>
                </div>              

            </div>
        </div>
        <jsp:include page="../../footer.jsp" /> 

    </body>
</html>
