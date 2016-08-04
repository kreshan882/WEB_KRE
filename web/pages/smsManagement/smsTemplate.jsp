<%-- 
    Document   : smsTemplate
    Created on : Mar 3, 2016, 1:46:08 PM
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
                $('#smstprofileId').val("-1");
                $('#senderType').val("-1");
                $('#smstemplatecId').val("-1");
                $('#msg').val("");


                $('#upsmsproname').val("");
                $('#upsmstcname').val("");
                $('#upmsg').val("");
                $('#upstatus').val("-1");
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
                return "<a href='#' onClick='deleteInit(&#34;" + rowObject.profileId + "&#34;,&#34;" + rowObject.categoryId + "&#34;,&#34;" + rowObject.desc + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/iconDelete.png'  /></a>";
            }

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='javascript:editNow(&#34;" + rowObject.profileId + "&#34;,&#34;" + rowObject.categoryId + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
            }

            function deleteInit(proid, ctgryid, msg) {
                $("#confirmdialogbox").data('keyval', proid);
                $("#confirmdialogbox").data('keyva2', ctgryid);
                $("#confirmdialogbox").dialog('open');
                $("#confirmdialogbox").html('<br><b><font size="3" color="red"><center>Please confirm to delete sms template : ' + msg + '');
                return false;
            }

//            function loadSMSCategory(keyval) {
//                $.ajax({
//                    url: '${pageContext.request.contextPath}/LoadsmstempMng',
//                    data: {senderType: keyval},
//                    dataType: "json",
//                    type: "POST",
//                    success: function (data) {
//                        $('#smstemplatecId').empty();
//                        $('#smstemplatecId').append($('<option></option>').val("-1").html("--Select--"));
//
//                        $.each(data.smstemplatecList, function (key, value) {
//                            $('#smstemplatecId').append($('<option></option>').val(key).html(value));
//                        });
//                    },
//                    error: function (data) {
//                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
//                    }
//                });
//            }

            function deleteNow(proid, ctgryid) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/DeletesmstempMng',
                    data: {smstprofileId: proid, smstemplatecId: ctgryid},
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
                        backToMain()
                        jQuery("#gridtable").trigger("reloadGrid");
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });

            }


            function editNow(keyval1, keyval2) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindsmstempMng',
                    data: {smstprofileId: keyval1, smstemplatecId: keyval2},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        $('#editForm').show();
                        $('#searchForm').hide();
                        $('#addForm').hide();

                        $('#upsmstprofileId').val(data.upsmstprofileId);
                        $('#upsmstemplatecId').val(data.upsmstemplatecId);
                        $('#upmsg').val(data.upmsg);
                        $('#upstatus').val(data.upstatus);
                        $('#upsmsproname').attr('readOnly', true).val(data.upsmsproname);
                        $('#upsmstcname').attr('readOnly', true).val(data.upsmstcname);

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
                var profileId = $('#profileId').val();
                $("#gridtable").jqGrid('setGridParam', {postData: {profileId: profileId, search: true}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            });

            $.subscribe('loadAddForm', function (event, data) {
                $('#editForm').hide();
                $('#searchForm').hide();
                $('#addForm').show();
            });

            function ResetSearchForm() {
                $('#profileId').val("-1");
            }
            function ResetAddForm() {
                resetData();
                $('#divmsg').empty();
            }

            function resetUpdateForm() {
                var proId = $('#upsmstprofileId').val();
                var ctgoryId = $('#upsmstemplatecId').val();
                editNow(proId, ctgoryId);
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");

            }

        </script>
    </head>

    <body style="overflow:hidden" onload="load()">
        <div class="wrapper">

            <div class="body_content" id="includedContent" >

                <div class="watermark"></div>
                <div class="heading">SMS Template Management</div>
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
                        <s:form id="searchForm" action="smstempMng" method="post"  theme="simple">         
                            <table class="form_table">              
                                <tr>
                                    <td class="content_td formLable">SMS Profile Name</td>
                                    <td>:</td>
                                    <td colspan="2"><s:select  name="profileId" id="profileId" list="%{smstprofileList}" 
                                               listKey="key" listValue="value"    headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" /> </td>
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
                                    <td class="formLable">SMS Profile Name<span class="mandatory">*</span></td> <td>:</td>
                                    <td ><s:select  name="smstprofileId" id="smstprofileId" list="%{smstprofileList}" 
                                               listKey="key" listValue="value"    headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" /></td>                                  
                                </tr>
                                <tr>
                                    <td class="formLable">SMS Category Name<span class="mandatory">*</span></td> <td>:</td>
                                    <td ><s:select  name="smstemplatecId" id="smstemplatecId" list="%{smstemplatecList}" 
                                               listKey="key" listValue="value"    headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" /></td> 
                                </tr>
                                <tr>
                                    <td class="formLable">Message<span class="mandatory">*</span></td><td>:</td>
                                    <td width="150px;"><s:textarea name="msg" id="msg" cssClass="textFieldMsgs" /></td>

                                </tr>  
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> <s:url var="addurl" action="AddsmstempMng"/>                                   
                                        <sj:submit   button="true" href="%{addurl}" value="Add"   targets="divmsg"  cssClass="button_sadd" disabled="#vadd"/> 
                                        <sj:submit id="resetida" button="true" value="Reset" onclick="ResetAddForm()"   cssClass="button_aback" disabled="false" />
                                        <sj:submit id="backida" button="true" value="Back" onclick="backToMain()"   cssClass="button_aback" disabled="false" /> 
                                    </td>
                                </tr>
                            </table>
                        </s:form>

                        <s:form id="editForm"  theme="simple" method="post" cssStyle="display:none" >
                            <table class="form_table">
                                <tr>
                                    <td hidden="true"><s:textfield  name="upsmstprofileId" id="upsmstprofileId"    cssClass="textField" /></td> 
                                    <td hidden="true"><s:textfield  name="upsmstemplatecId" id="upsmstemplatecId"     cssClass="textField" /></td>
                                    <td class="formLable">SMS Profile Name</td> <td>:</td>
                                    <td ><s:textfield  name="upsmsproname" id="upsmsproname"    cssClass="textField" /></td>                                  
                                    <td width="25px;"></td>
                                    <td class="formLable">SMS Category Name</td> <td>:</td>
                                    <td ><s:textfield  name="upsmstcname" id="upsmstcname"     cssClass="textField" /></td>  

                                </tr>
                                <tr>
                                    <td class="formLable">Status<span class="mandatory">*</span></td> <td>:</td>
                                    <td ><s:select  name="upstatus" id="upstatus" list="%{upstatusList}" 
                                               listKey="key" listValue="value"    headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" /></td>                                  
                                    <td width="25px;"></td>
                                    <td class="formLable">Message<span class="mandatory">*</span></td><td>:</td>
                                    <td ><s:textarea name="upmsg" id="upmsg" cssClass="textField" /></td>
                                </tr> 
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> <s:url var="updateuserurl" action="UpdatesmstempMng"/>                                   
                                        <sj:submit   button="true" href="%{updateuserurl}" value="Update"   targets="divmsg"  cssClass="button_sadd" disabled="#vupdate"/>
                                        <sj:submit button="true" value="Reset" onClick="resetUpdateForm()" cssClass="button_sreset"/>
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
                                'OK':function() { deleteNow($(this).data('keyval'),$(this).data('keyva2'));$( this ).dialog( 'close' ); },
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

                            <s:url var="listurl" action="ListsmstempMng"/>
                            <sjg:grid
                                id="gridtable"
                                caption="SMS Template Management"
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
                                <sjg:gridColumn name="profileId" index="PRO_ID" title="Profile Id" frozen="true" hidden="true"/>
                                <sjg:gridColumn name="categoryId" index="CATEG_ID" title="category Id" frozen="true" hidden="true"/>

                                <sjg:gridColumn name="profileName" index="PRO_NAME" title="SMS Profile Name" align="left" width="120" frozen="true" sortable="true"/>
                                <sjg:gridColumn name="categoryName" index="CATEG_NAME" title="SMS Category Name" align="left" width="150" sortable="true"/>
                                <sjg:gridColumn name="desc" index="MESSAGE" title="Message" align="left" width="750" sortable="true"/>

                                <sjg:gridColumn name="status" index="STATUS" title="Status" align="center" width="50" formatter="statusformatter" sortable="true"/>  
                                <sjg:gridColumn name="profileId" index="PRO_ID" title="Edit" align="center" width="50" align="center"  formatter="editformatter" sortable="false" hidden="#vupdate"/>
                                <sjg:gridColumn name="profileId" index="PRO_ID" title="Delete" align="center" width="50" align="center"   formatter="deleteformatter" sortable="false" hidden="#vdelete"/>

                            </sjg:grid> 

                        </div> 



                    </div>
                </div>              

            </div>
        </div>
        <jsp:include page="../../footer.jsp" /> 

    </body>
</html>
