<%-- 
    Document   : registerNewUser
    Created on : 29/02/2016, 4:27:44 PM
    Author     : dimuthu_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"  %>  
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>  
<script type="text/javascript">


    function statusformatter(cellvalue, options, rowObject) {
        if (cellvalue == '1') {
            var html = "<img src='${pageContext.request.contextPath}/resources/images/iconActive.png' />";
        } else {
            var html = "<img src= '${pageContext.request.contextPath}/resources/images/iconInactive.png' />";
        }
        return html;
    }


    function recdeleteformatter(cellvalue, options, rowObject) {
        return "<a href='#' onClick='recdeleteInit(&#34;" + rowObject.cid + "&#34;,&#34;" + rowObject.nic + "&#34;,&#34;" + rowObject.name + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/iconDelete.png'  /></a>";
    }

    function recdeleteInit(cid, nic, name) {
        $("#recconfirmdialogbox").data('keyval1', cid);
        $("#recconfirmdialogbox").data('keyval2', nic);
        $("#recconfirmdialogbox").dialog('open');
        $("#recconfirmdialogbox").html('<br><b><font size="3" color="red"><center>Please confirm to delete recipient : ' + name + '');

        return false;
    }

    function recbackToMain() {
        $('#editForm1').hide();
        $('#addForm1').show();

        $('#recdivmsg').empty();
        jQuery("#gridtable1").trigger("reloadGrid");

    }

    function recdeleteNow(cid, nic) {
        $.ajax({
            url: '${pageContext.request.contextPath}/DeletereciMng',
            data: {cid: cid, nic: nic},
            dataType: "json",
            type: "POST",
            success: function (data) {
                if (data.success) {
                    recbackToMain();
                    $("#dialogbox").dialog('open');
                    $("#dialogbox").html('<br><b><font size="3" color="green"><center>' + data.message + ' ');
                } else {
                    $("#dialogbox").dialog('open');
                    $("#dialogbox").html('<br><b><font size="3" color="red"><center>' + data.message + ' ');
                }
                jQuery("#gridtable2").trigger("reloadGrid");
            },
            error: function (data) {
                window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
            }
        });

    }

    function receditformatter(cellvalue, options, rowObject) {

        return "<a href='#' onClick='javascript:receditNow(&#34;" + rowObject.cid + "&#34;,&#34;" + rowObject.nic + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
    }



    function receditNow(cid, nic) {
        $('#recdivmsg').empty();
        $.ajax({
            url: '${pageContext.request.contextPath}/FindreciMng',
            data: {cid: cid, nic: nic},
            dataType: "json",
            type: "POST",
            success: function (data) {
                $('#editForm1').show();
                $('#addForm1').hide();
                $('#recupcname').attr('readOnly', true);
                $('#recupcname').val(data.upcname);
                $('#recupcid').val(data.upcid);
                $('#recupname').val(data.upname);
                $('#recupstatus').val(data.upstatus);
                $('#recupaddress').val(data.upaddress);
                $('#recupmobile').val(data.upmobile);
                $('#recupnic').attr('readOnly', true);
                $('#recupnic').val(data.upnic);

            },
            error: function (data) {
                window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
            }
        });
    }

    function recresetUpdateForm(cid, nic) {
        receditNow(cid, nic);
        $('#recdivmsg').empty();
        jQuery("#gridtable2").trigger("reloadGrid");

    }


    //reset Datas


</script>
<s:hidden  id="recvadd" name="vadd" default="true"/>
<s:set id="recvadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
<s:set var="recvupdate"><s:property value="vupdate" default="true"/></s:set>
<s:set var="recvdelete"><s:property value="vdelete" default="true"/></s:set>
    <div class="message">         
    <s:div id="recdivmsg">
        <i style="color: red">  <s:actionerror theme="jquery"/></i>
        <i style="color: green"> <s:actionmessage theme="jquery"/></i>
    </s:div>         
</div>
<div class="contentcenter">
    <s:form  id="addForm1"  theme="simple" method="post"  >
        <table class="form_table">
            <tr>
                <td class="formLable">Customer Name<span class="mandatory"></span></td> <td >:</td>
                <td><s:select cssStyle="background:#B4C6CC;" disabled="true" id="cid" name="cid" headerKey="-1"  headerValue="---Select---"  list="%{customerList}" cssClass="textField"/></td>                                    
                <td width="25px;"></td>
                <td class="formLable">Recipient Name<span class="mandatory">*</span></td> <td>:</td>
                <td><s:textfield id="recname" name="name" cssClass="textField" /></td>
                <s:hidden id="cid" name="cid" value="%{cid}" />
            </tr>    
            <tr>
                <td class="formLable">NIC<span class="mandatory">*</span></td> <td >:</td>
                <td><s:textfield id="recnic" name="nic" cssClass="textField" /></td>                                    
                <td width="25px;"></td>
                <td class="formLable">Mobile No <span class="mandatory">*</span></td> <td>:</td>
                <td><s:textfield id="recmobile_no" name="mobile_no" cssClass="textField" placeholder="+94777804455"/></td>
            </tr>
            <tr>
                <td class="formLable">Address</td> <td>:</td>
                <td><s:textfield id="recaddress" name="address"  cssClass="textField"/></td>
                <td width="25px;"></td>
                <td class="formLable"></td> <td></td>
                <td></td>
            </tr>

            <tr>
                <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
            </tr>
        </table><table class="form_table">
            </br>
            <tr>                                
                <td><s:url var="addurl1" action="AddreciMng"/>                                   
                    <sj:submit   button="true" href="%{addurl1}" value="Add"   targets="recdivmsg"  cssClass="button_sadd" disabled="#recvadd"/> 
                    <sj:submit id="resetida" button="true" value="Reset" onclick="ResetAddForm()"   cssClass="button_aback" disabled="false" />
                    <sj:submit id="backida" button="true" value="Back" onclick="recPopupClose()"   cssClass="button_aback" disabled="false" /> 
                </td>
            </tr>
        </table>
    </s:form>
    <s:form id="editForm1"  theme="simple" method="post" cssStyle="display:none" >
        <table class="form_table">

            <tr>
                <td hidden="true"><s:textfield name="upcid" id="recupcid" cssClass="textField"  /></td>
                <td  class="formLable">Customer Name</td><td>:</td>
                <td ><s:textfield name="upcname" id="recupcname" cssClass="textField" /></td>
                <td width="25px"></td>
                <td class="formLable">NIC</td> <td>:</td>
                <td ><s:textfield name="upnic" id="recupnic" cssClass="textField"  />  </td>

            </tr>
            <tr>
                <td class="formLable">Recipient Name<span class="mandatory">*</span></td> <td>:</td>
                <td ><s:textfield name="upname" id="recupname" cssClass="textField" /></td>
                <td width="25px"></td>
                <td class="formLable">Mobile<span class="mandatory">*</span></td> <td>:</td>
                <td ><s:textfield name="upmobile" id="recupmobile" cssClass="textField" placeholder="+94777804455" /></td>
            </tr>
            <tr>
                <td class="formLable">Address</td> <td>:</td>
                <td ><s:textfield name="upaddress" id="recupaddress" cssClass="textField" /></td>
                <td width="25px"></td>
                <td class="formLable">Status<span class="mandatory">*</span></td><td>:</td>
                <td ><s:select  name="upstatus" id="recupstatus" list="%{upstatusList}" 
                           listKey="key" listValue="value"    headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" />
                </td>
            </tr>

            <tr>

                <td width="25px"></td>
                <td class="formLable"></td> <td></td>
                <td ></td>
            </tr>
            <tr>
                <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
            </tr>
        </table><table class="form_table">
            </br>
            <tr>                                
                <td> <s:url var="updatereciurl" action="UpdatereciMng"/>                                   
                    <sj:submit   button="true" href="%{updatereciurl}" value="Update"   targets="recdivmsg"  cssClass="button_sadd" disabled="#recvupdate"/>
                    <sj:submit button="true" value="Reset" onClick="recresetUpdateForm($('#recupcid').val(),$('#recupnic').val())" cssClass="button_aback"/>
                    <sj:a href="#" name="back" button="true" onclick="recbackToMain()"  cssClass="button_aback" >Back</sj:a>    
                    </td>
                </tr>
            </table>
    </s:form>  

</div>

<div class="viewuser_tbl">
    <div id="tablediv">
        <sj:dialog 
            id="recconfirmdialogbox" 
            buttons="{ 
            'OK':function() { recdeleteNow($(this).data('keyval1'),$(this).data('keyval2'));$( this ).dialog( 'close' ); },
            'Cancel':function() { $( this ).dialog( 'close' );} 
            }" 
            autoOpen="false" 
            modal="true" 
            title="Confirm message"
            width="400"
            height="150"
            position="center"
            />


        <!-- End delete dialog box -->

        <s:url var="listurl1" action="ListreciMng">
            <s:param name="cid"><%=session.getAttribute("cid")%></s:param>
        </s:url>
        <sjg:grid
            id="gridtable2"
            caption="Recipient Management"
            dataType="json"
            href="%{listurl1}"
            pager="true"
            gridModel="gridModel"
            rowList="10,15,20"
            rowNum="10"
            autowidth="true"
            shrinkToFit= "true"
            rownumbers="true"
            onCompleteTopics="completetopics"
            rowTotal="false"
            viewrecords="true"
            >
            <sjg:gridColumn name="cid" index="CUSTOMERID" title="Customer Id" frozen="true" hidden="true"/>
            <sjg:gridColumn name="name" index="NAME" title="Recipient Name" frozen="true" align="left" width="20" sortable="true"/>
            <sjg:gridColumn name="cname" index="CUSTOMERNAME" title="Customer Name" align="left"  width="18"  sortable="true"/>
            <sjg:gridColumn name="address" index="ADDRESS" title="Address" align="left"  width="18"  sortable="true"/>
            <sjg:gridColumn name="mobile_no" index="MOBILE" title="Mobile" align="left"  width="18"  sortable="true"/>
            <sjg:gridColumn name="nic" index="NIC" title="Nic" align="left"  width="18"  sortable="true"/>
            <sjg:gridColumn name="regDate" index="CREATE_DATE" title="Reg Date" align="center"  width="18"  sortable="true"/>
            <sjg:gridColumn name="status" index="STATUS" title="Status" align="center" width="10" formatter="statusformatter" sortable="true"/>  

            <sjg:gridColumn name="nic" index="NIC" title="Edit" align="center" width="7" align="center"  formatter="receditformatter" sortable="false" hidden="#recvupdate"/>
            <sjg:gridColumn name="nic" index="NIC" title="Delete" align="center" width="7" align="center"   formatter="recdeleteformatter" sortable="false" hidden="#recvdelete"/>

        </sjg:grid> 

    </div> 



</div>
