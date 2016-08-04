<%-- 
    Document   : userProfileManagement
    Created on : Feb 29, 2016, 4:52:32 PM
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
                $('#upmoduleId').val("-1");
                $('#uppageId').val("-1");
                $('#currentBox').empty();
                $('#newBox').empty();
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
                return "<a href='#' onClick='deleteInit(&#34;" + rowObject.profileId + "&#34;,&#34;" + rowObject.profileName + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/iconDelete.png'  /></a>";
            }

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='javascript:editNow(&#34;" + cellvalue + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
            }
            function taskassineformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='javascript:assineTaskNow(&#34;" + cellvalue + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
            }


            function deleteInit(keyval, keyval2) {
                $("#confirmdialogbox").data('keyval', keyval).dialog('open');
                $("#confirmdialogbox").html('<br><b><font size="3" color="red"><center>Please confirm to delete user profile : ' + keyval2 + '');
                return false;
            }


            function deleteNow(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/DeleteusrprofileMng',
                    data: {upprofileId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.success) {
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

            function assineTaskNow(keyval) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/FindusrprofileMng',
                    data: {upprofileId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        $('#taskasinForm').show();
                        $('#editForm').hide();
                        $('#searchForm').hide();
                        $('#addForm').hide();

                        $('#upprofileId').val(data.upprofileId);
                        $('#upprofilename').attr('readOnly', true);
                        $('#upprofilename').val(data.upprofilename);

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });
            }

            function editNow(keyval) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/FindusrprofileMng',
                    data: {upprofileId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#editForm').show();
                        $('#searchForm').hide();
                        $('#addForm').hide();
                        $('#taskasinForm').hide();

                        $('#upeprofileId').val(data.upprofileId);
                        $('#upeprofilename').attr('readOnly', true);
                        $('#upeprofilename').val(data.upprofilename);
                        $('#upestatus').val(data.upestatus);

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });
            }


            function backToMain() {
                $('#editForm').hide();
                $('#taskasinForm').hide();
                $('#searchForm').show();
                $('#addForm').hide();

                $('#divmsg').empty();
                resetData();//newly added to the backToMain function
                jQuery("#gridtable").trigger("reloadGrid");

            }




            $.subscribe('onclicksearch', function (event, data) {
                var profilename = $('#profilename').val();
                $("#gridtable").jqGrid('setGridParam', {postData: {profilename: profilename, search: true}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            });

            $.subscribe('loadAddForm', function (event, data) {
                $('#editForm').hide();
                $('#searchForm').hide();
                $('#taskasinForm').hide();
                $('#addForm').show();
            });

            //reset Datas
            function ResetSearchForm() {
                $('#profilename').val("");
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");
            }
            function ResetAddForm() {
                $('#profilenamea').val("");
                $('#divmsg').empty();
            }
            function ResetEditForm() {
                
                var upeprofileId = $('#upeprofileId').val();
                editNow(upeprofileId);
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function ResetTaskAsinForm() {
                resetData();
                $('#divmsg').empty();
            }
            function loadModuleSection(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/loadModuleSectionusrprofileMng',
                    data: {upmoduleId: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#uppageId').empty();
                        $('#uppageId').append($('<option></option>').val("-1").html("--Select--"));

                        $.each(data.pageIdList, function (key, value) {
                            $('#uppageId').append($('<option></option>').val(key).html(value));
                        });
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });
            }

            function loadSectionTask(keyval) {
                var upprofileId = $('#upprofileId').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/loadSectionTaskusrprofileMng',
                    data: {uppageId: keyval, upprofileId: upprofileId},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        $('#currentBox').empty();
                        $('#newBox').empty();

                        $.each(data.taskList, function (val, text) {
                            $('#currentBox').append($('<option></option>').val(val).html(text));
                        });
                        $.each(data.selectedtaskList, function (val, text) {
                            $('#newBox').append($('<option></option>').val(val).html(text));
                        });
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });
            }

            function toleft() {
                $("#newBox option:selected").each(function () {

                    $("#currentBox").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()

                    }));
                    $(this).remove();
                });
            }


            function toright() {
                $("#currentBox option:selected").each(function () {

                    $("#newBox").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }

            function toleftall() {
                $("#newBox option").each(function () {

                    $("#currentBox").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function torightall() {
                $("#currentBox option").each(function () {

                    $("#newBox").append($('<option>', {
                        value: $(this).val(),
                        text: $(this).text()
                    }));
                    $(this).remove();
                });
            }
            function loadModulePage(selectedModule) {

                $('#newBox option').prop('selected', true);
                $('#currentBox option').prop('selected', true);
                $("#assignbut").click();
            }
        </script>
    </head>

    <body style="overflow:hidden" onload="load()">
        <div class="wrapper">

            <div class="body_content" id="includedContent" >

                <div class="watermark"></div>
                <div class="heading">User Profile Management</div>
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
                        <s:form id="searchForm" action="usrprofileMng" method="post"  theme="simple">         
                            <table class="form_table">              
                                <tr>
                                    <td class="content_td formLable">Profile Name</td>
                                    <td>:</td>
                                    <td colspan="2"><s:textfield name="profilename"  id="profilename" cssClass="textField" /> </td>
                                    <td class="content_td formLable">

                                    </td>
                                </tr>
                            </table><table class="form_table">
                                <tr>                                
                                    <td> 
                                        <%--<sj:submit   button="true"  value="Adddsub"   onClickTopics="loadAddForm"  cssClass="button_sadd" disabled="#vadd"/>--%>
                                        <sj:a id="addid" button="true" onClickTopics="loadAddForm"  cssClass="button_aback" disabled="#vadd" > Add </sj:a>
                                        <sj:a   id="searchid"  button="true"    onClickTopics="onclicksearch"  cssClass="button_aback"  role="button" aria-disabled="false" >Search</sj:a>
                                        <sj:submit id="resetid" button="true" value="Reset" onclick="ResetSearchForm()"   cssClass="button_aback" disabled="false" />

                                    </td>
                                </tr>
                            </table>
                        </s:form>

                        <s:form  id="addForm"  theme="simple" method="post"  cssStyle="display:none">
                            <table class="form_table">
                                <tr>
                                    <td class="formLable">Profile Name<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="profilenamea" name="profilename" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table>
                            <table class="form_table">
                                <br>
                                <tr>                                
                                    <td> 
                                        <s:url var="addurl" action="AddusrprofileMng"/>                                
                                        <sj:submit   button="true" href="%{addurl}" value="Add"   targets="divmsg"  cssClass="button_sadd" disabled="#vadd"/>   
                                        <sj:submit id="resetida" button="true" value="Reset" onclick="ResetAddForm()"   cssClass="button_aback" disabled="false" />
                                        <sj:submit id="backida" button="true" value="Back" onclick="backToMain()"   cssClass="button_aback" disabled="false" /> 
                                    </td>
                                </tr>
                            </table>
                        </s:form>

                        <s:form id="taskasinForm"  theme="simple" method="post" cssStyle="display:none" >
                            <table class="form_table">
                                <s:hidden id="upprofileId" name="upprofileId" />     
                                <tr>
                                    <td class="formLable">Profile Name</td> <td >:</td>
                                    <td><s:textfield id="upprofilename" name="profilename" cssClass="textField" /></td>                                    
                                    <td width="25px;"></td>
                                    <td class="formLable"></td> <td></td>
                                    <td></td>
                                </tr>  
                                <tr>
                                    <td class="formLable">Select Module<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select  name="upmoduleId" id="upmoduleId" list="%{moduleIdList}" 
                                               listKey="key" listValue="value"  onchange="loadModuleSection(this.value)"  headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" /></td>                           
                                    <td width="25px;"></td>
                                    <td class="formLable">Select Section<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select  name="uppageId" id="uppageId" list="%{pageIdList}" 
                                               listKey="key" listValue="value"  onchange="loadSectionTask(this.value)"  headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" /></td>
                                </tr>   
                            </table><table class="form_table">
                                <tr>
                                    <td colspan="4" height="5px"></td>
                                </tr>
                                <tr>
                                    <td rowspan="4" colspan="2"><s:select multiple="true" name="currentBox" id="currentBox" list="taskList"  ondblclick="toright()" cssClass="select_box" /></td>
                                    <td><sj:a  id="right" onClick="toright()" button="true" cssClass="select_boxBtn"> > </sj:a></td>
                                    <td rowspan="4"><s:select multiple="true"  name="newBox" id="newBox" list="selectedtaskList"   ondblclick="toleft()" cssClass="select_box" /></td>
                                </tr>

                                <tr> 
                                    <td><sj:a  id="rightall" onClick="torightall()" button="true"  cssClass="select_boxBtn"> >> </sj:a></td>
                                    </tr>
                                    <tr>
                                        <td><sj:a  id="left" onClick="toleft()" button="true" cssClass="select_boxBtn"><</sj:a></td>
                                    </tr>
                                    <tr>
                                        <td><sj:a  id="leftall" onClick="toleftall()" button="true" cssClass="select_boxBtn"><<</sj:a>
                                        </td>
                                    </tr>


                                    <tr>
                                        <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                    </tr>
                                </table><table class="form_table">
                                    </br>
                                    <tr>                                
                                        <td> 
                                        <%--<s:url var="updateuserurl" action="UpdateusrMng"/>--%>                                   
                                        <%--<sj:submit   button="true" href="%{updateuserurl}" value="Update"   targets="divmsg"  cssClass="button_sadd" disabled="#vupdate"/>--%>

                                        <s:url var="updateuserurl" action="UpdateTaskusrprofileMng"/>                                   
                                        <sj:submit id="assignbut"  href="%{updateuserurl}"  targets="divmsg"  value="Add"  button="true"  cssStyle="display: none; visibility: hidden;"  />
                                        <sj:a button="true" onclick="loadModulePage()" cssClass="button_sadd" >Update</sj:a></td>
                                    </td> <td>  

                                <sj:submit button="true" value="Reset" onClick="ResetTaskAsinForm()" cssClass="button_aback"/>
                                <sj:a href="#" name="back" button="true" onclick="backToMain()"  cssClass="button_aback" >Back</sj:a>    
                                </td>
                            </tr>
                        </table>
                </s:form>  

                <s:form  id="editForm"  theme="simple" method="post"  cssStyle="display:none">
                    <table class="form_table">
                        <s:hidden id="upeprofileId" name="upeprofileId" />  
                        <tr>
                            <td >Profile Name</td> <td >:</td>
                            <td><s:textfield id="upeprofilename" name="upeprofilename" cssClass="textField" /></td>                                    
                            <td width="25px;"></td>
                            <td class="formLable">Status<span class="mandatory">*</span></td> <td>:</td>
                            <td><s:select  name="upestatus" id="upestatus" list="%{upstatusList}" 
                                       listKey="key" listValue="value"  headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" /></td>
                        </tr>  
                        <tr>
                            <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                        </tr>
                    </table><table lass="form_table">
                        <br>
                        <tr>                                
                            <td> 
                                <s:url var="editturl" action="UpdateusrprofileMng"/>                                
                                <sj:submit   button="true" href="%{editturl}" value="Update"   targets="divmsg"  cssClass="button_sadd" disabled="#vadd"/> 

                                <sj:submit id="resetide" button="true" value="Reset" onclick="ResetEditForm()"   cssClass="button_aback" disabled="false" />
                                <sj:submit id="backide" button="true" value="Back" onclick="backToMain()"   cssClass="button_aback" disabled="false" /> 
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

                    <s:url var="listurl" action="ListusrprofileMng"/>
                    <sjg:grid
                        id="gridtable"
                        caption="User Profile Management"
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
                        <sjg:gridColumn name="profileId" index="PROFILE_ID" title="ProfileId"  hidden="true"/>                          
                        <sjg:gridColumn name="profileName" index="DESCRIPTION" title="Profile Name" align="left" width="20" sortable="true"/>                     
                        <sjg:gridColumn name="regDate" index="CREATE_DATE" title="Reg Date" align="center"  width="7"  sortable="true"/>
                        <sjg:gridColumn name="status" index="STATUS" title="Status" align="center" width="7" formatter="statusformatter" sortable="true"/>  

                        <sjg:gridColumn name="profileId" index="PROFILE_ID" title="Assign Task" align="center" width="7" align="center"  formatter="taskassineformatter" sortable="false" hidden="#vupdate"/>
                        <sjg:gridColumn name="profileId" index="PROFILE_ID" title="Edit" align="center" width="7" align="center"  formatter="editformatter" sortable="false" hidden="#vupdate"/>
                        <sjg:gridColumn name="profileId" index="PROFILE_ID" title="Delete" align="center" width="7" align="center"   formatter="deleteformatter" sortable="false" hidden="#vdelete"/>

                    </sjg:grid> 

                </div> 



            </div>
        </div>              

    </div>
</div>
<jsp:include page="../../footer.jsp" /> 

</body>
</html>