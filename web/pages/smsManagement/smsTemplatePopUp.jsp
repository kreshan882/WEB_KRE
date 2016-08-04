<%-- 
    Document   : smsTemplatePopUp
    Created on : Jun 15, 2016, 3:27:32 PM
    Author     : nipun_t
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"  %>  
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>      

<script type="text/javascript">

    function load() {
        if ('true' === $('#vadd1').val()) {
            $('#addid1').hide();
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
        $('#upstatus1').val("-1");
        jQuery("#gridtable1").trigger("reloadGrid");
    }

    function statusformatter1(cellvalue, options, rowObject) {
        if (cellvalue == '1') {
            var html = "<img src='${pageContext.request.contextPath}/resources/images/iconActive.png' />";
        } else {
            var html = "<img src= '${pageContext.request.contextPath}/resources/images/iconInactive.png' />";
        }
        return html;
    }


    function deleteformatter1(cellvalue, options, rowObject) {
        return "<a href='#' onClick='deleteInit1(&#34;" + rowObject.profileId + "&#34;,&#34;" + rowObject.categoryId + "&#34;,&#34;" + rowObject.desc + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/iconDelete.png'  /></a>";
    }

    function editformatter1(cellvalue, options, rowObject) {
        return "<a href='#' onClick='javascript:editNow1(&#34;" + rowObject.profileId + "&#34;,&#34;" + rowObject.categoryId + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
    }

    function deleteInit1(proid, ctgryid, msg) {
        $("#confirmdialogbox1").data('keyval', proid);
        $("#confirmdialogbox1").data('keyva2', ctgryid);
        $("#confirmdialogbox1").dialog('open');
        $("#confirmdialogbox1").html('<br><b><font size="3" color="red"><center>Please confirm to delete sms template : ' + msg + '');
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

    function deleteNow1(proid, ctgryid) {
        $.ajax({
            url: '${pageContext.request.contextPath}/DeletesmstempMng',
            data: {smstprofileId: proid, smstemplatecId: ctgryid},
            dataType: "json",
            type: "POST",
            success: function (data) {
                if (data.success) {
                    $("#dialogbox1").dialog('open');
                    $("#dialogbox1").html('<br><b><font size="3" color="green"><center>' + data.message + ' ');
                } else {
                    $("#dialogbox1").dialog('open');
                    $("#dialogbox1").html('<br><b><font size="3" color="red"><center>' + data.message + ' ');
                }
                backToMain()
                jQuery("#gridtable1").trigger("reloadGrid");
            },
            error: function (data) {
                window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
            }
        });

    }


    function editNow1(keyval1, keyval2) {
        $.ajax({
            url: '${pageContext.request.contextPath}/FindsmstempMng',
            data: {smstprofileId: keyval1, smstemplatecId: keyval2},
            dataType: "json",
            type: "POST",
            success: function (data) {

                $('#editForm1').show();
                $('#searchForm1').hide();
                $('#addForm1').hide();

                $('#upsmstprofileId').val(data.upsmstprofileId);
                $('#upsmstemplatecId').val(data.upsmstemplatecId);
                $('#upmsg').val(data.upmsg);
                $('#upstatus1').val(data.upstatus1);
                $('#upsmsproname').attr('readOnly', true).val(data.upsmsproname);
                $('#upsmstcname').attr('readOnly', true).val(data.upsmstcname);

            },
            error: function (data) {
                window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
            }
        });
    }


//    function backToMain1() {
//        $('#editForm1').hide();
//        $('#searchForm1').show();
//        $('#addForm').show();
//
//        $('#divmsg1').empty();
//        jQuery("#gridtable1").trigger("reloadGrid");
//
//    }




    $.subscribe('onclicksearch1', function (event, data) {
        var profileId = $('#profileId').val();
        $("#gridtable1").jqGrid('setGridParam', {postData: {profileId: profileId, search: true}});
        $("#gridtable1").jqGrid('setGridParam', {page: 1});
        jQuery("#gridtable1").trigger("reloadGrid");
    });

    $.subscribe('loadAddForm1', function (event, data) {
        $('#editForm1').hide();
        $('#searchForm1').hide();
        $('#addForm1').show();
    });

    function ResetSearchForm1() {
        $('#profileId').val("-1");
    }
    function ResetAddForm1() {
        resetData();
        $('#divmsg1').empty();
    }

    function resetUpdateForm1() {
        var proId = $('#upsmstprofileId').val();
        var ctgoryId = $('#upsmstemplatecId').val();
        editNow(proId, ctgoryId);
        $('#divmsg1').empty();
        jQuery("#gridtable1").trigger("reloadGrid");

    }

</script>

<s:hidden  id="vadd1" name="vadd" default="true"/>
<s:set id="vadd1" var="vadd"><s:property  value="vadd" default="true"/></s:set>
<s:set var="vupdate1"><s:property value="vupdate" default="true"/></s:set>
<s:set var="vdelete1"><s:property value="vdelete" default="true"/></s:set>
<s:set var="vdownload1"><s:property value="vdownload" default="true"/></s:set>
<s:set var="vresetpass1"><s:property value="vresetpass" default="true"/></s:set>
    <div class="message">         
    <s:div id="divmsg1">
        <i style="color: red">  <s:actionerror theme="jquery"/></i>
        <i style="color: green"> <s:actionmessage theme="jquery"/></i>
    </s:div>         
</div>
<div class="contentcenter_sms_template">
   
    <s:form  id="addForm1"  theme="simple" method="post">
        <table class="form_table">
            <!--            <tr>
                            <td class="formLable">SMS Profile Name<span class="mandatory">*</span></td> <td>:</td>
                            <td >
            <%--<s:select  name="smstprofileId" id="smstprofileId" list="%{smstprofileList}"  listKey="key" listValue="value"    headerKey="-1"    headerValue="---Select---"   disabled="true"  cssClass="dropdown" />--%>
        </td>                                  
    </tr>-->
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
                <td> <s:url var="addurl1" action="AddsmstempMng"/>                                   
                    <sj:submit   button="true" href="%{addurl1}" value="Add"   targets="divmsg1"  cssClass="button_sadd" disabled="#vadd"/> 
                    <sj:submit id="resetida" button="true" value="Reset" onclick="ResetAddForm1()"   cssClass="button_aback" disabled="false" />
                    <%--<sj:submit id="backida" button="true" value="Back" onclick="backToMain1()"   cssClass="button_aback" disabled="false" />--%> 
                </td>
            </tr>
        </table>
    </s:form>

    <s:form id="editForm1"  theme="simple" method="post" cssStyle="display:none" >
        <table class="form_table">
            <tr>
                <td hidden="true"><s:textfield  name="upsmstprofileId" id="upsmstprofileId"    cssClass="textField" /></td> 
                <td hidden="true"><s:textfield  name="upsmstemplatecId" id="upsmstemplatecId"     cssClass="textField" /></td>

                <td class="formLable">SMS Category Name</td> <td>:</td>
                <td ><s:textfield  name="upsmstcname" id="upsmstcname"     cssClass="textField" /></td>  
                <td width="25px;"></td>
                <td class="formLable">Status<span class="mandatory">*</span></td> <td>:</td>
                <td ><s:select  name="upstatus1" id="upstatus1" list="%{upstatusList1}" 
                           listKey="key" listValue="value"    headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" /></td> 
            </tr>
            <tr>


                <td class="formLable">Message<span class="mandatory">*</span></td><td>:</td>
                <td ><s:textarea name="upmsg" id="upmsg" cssClass="textField" /></td>
            </tr> 
            <tr>
                <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
            </tr>
        </table><table class="form_table">
            </br>
            <tr>                                
                <td> <s:url var="updateuserurl1" action="UpdatesmstempMng"/>                                   
                    <sj:submit   button="true" href="%{updateuserurl1}" value="Update"   targets="divmsg1"  cssClass="button_sadd" disabled="#vupdate"/>
                    <sj:submit button="true" value="Reset" onClick="resetUpdateForm1()" cssClass="button_sreset"/>
                    <%--<sj:a href="#" name="back" button="true" onclick="backToMain()"  cssClass="button_aback" >Back</sj:a>--%>    
                </td>
            </tr>
        </table>
    </s:form>  

</div>

<div class="viewuser_tbl">
    <div id="tablediv1">

        <sj:dialog 
            id="confirmdialogbox1" 
            buttons="{ 
            'OK':function() { deleteNow1($(this).data('keyval'),$(this).data('keyva2'));$( this ).dialog( 'close' ); },
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
            id="dialogbox1" 
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

        <s:url var="listurl1" action="ListsmstempMng">
            <s:param name="tempid"><%=session.getAttribute("tempid")%></s:param>
        </s:url>
        <sjg:grid
            id="gridtable1"
            caption="SMS Template Management"
            dataType="json"
            href="%{listurl1}"
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

            <sjg:gridColumn name="profileName" index="PRO_NAME" title="SMS Profile Name" align="left" width="120" frozen="true" sortable="true" hidden="true"/>
            <sjg:gridColumn name="categoryName" index="CATEG_NAME" title="SMS Category Name" align="left" width="150" sortable="true"/>
            <sjg:gridColumn name="desc" index="MESSAGE" title="Message" align="left" width="750" sortable="true"/>

            <sjg:gridColumn name="status" index="STATUS" title="Status" align="center" width="50" formatter="statusformatter1" sortable="true"/>  
            <sjg:gridColumn name="profileId" index="PRO_ID" title="Edit" align="center" width="50" align="center"  formatter="editformatter1" sortable="false" hidden="#vupdate1"/>
            <sjg:gridColumn name="profileId" index="PRO_ID" title="Delete" align="center" width="50" align="center"   formatter="deleteformatter1" sortable="false" hidden="#vdelete1"/>

        </sjg:grid> 

    </div> 



</div>            

</div>
</div>

