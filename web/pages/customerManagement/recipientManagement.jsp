<%-- 
    Document   : registerNewUser
    Created on : 29/02/2016, 4:27:44 PM
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

        <script type="text/javascript">

            function load() {
                if('true'===$('#vadd').val()){   $('#addid').hide();   }
            }
            function resetData() {
                $('#upcid').attr('readOnly', false);
                $('#upnic').attr('readOnly', false);
                
                $('#name').val("");
                $('#address').val("");
                $('#mobile_no').val("");
                $('#nic').val("");
                $('#cid').val("-1");
                $('#cname').val("");
                $('#status').val("-1");

                $('#upname').val("");
                $('#upcname').val("");
                $('#upstatus').val("-1");
                $('#upcid').val("");
                $('#upaddress').val("");
                $('#upmobile').val("");
                $('#upnic').val("");

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
                return "<a href='#' onClick='deleteInit(&#34;" + rowObject.cid + "&#34;,&#34;" + rowObject.nic + "&#34;,&#34;" + rowObject.name + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/iconDelete.png'  /></a>";
            }

            function editformatter(cellvalue, options, rowObject) {

                return "<a href='#' onClick='javascript:editNow(&#34;" + rowObject.cid + "&#34;,&#34;" + rowObject.nic + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
            }


            function deleteInit(cid, nic, name) {
                $("#confirmdialogbox").data('keyval1', cid);
                $("#confirmdialogbox").data('keyval2', nic);
                $("#confirmdialogbox").dialog('open');
                $("#confirmdialogbox").html('<br><b><font size="3" color="red"><center>Please confirm to delete recipient : ' + name + '');
              
                return false;
            }

            function deleteNow(cid, nic) {
               
                $.ajax({
                    url: '${pageContext.request.contextPath}/DeletereciMng',
                    data: {cid: cid, nic: nic},
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


            function editNow(cid, nic) {
                $('#divmsg').empty();
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindreciMng',
                    data: {cid: cid, nic: nic},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#editForm').show();
                        $('#searchForm').hide();
                        $('#addForm').hide();
                        $('#upcname').attr('readOnly', true);
                        $('#upcname').val(data.upcname);
                        $('#upcid').val(data.upcid);
                        $('#upname').val(data.upname);
                        $('#upstatus').val(data.upstatus);
                        $('#upaddress').val(data.upaddress);
                        $('#upmobile').val(data.upmobile);
                        $('#upnic').attr('readOnly', true);
                        $('#upnic').val(data.upnic);

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });
            }


            function backToMain() {
                $('#editForm').hide();
                $('#pwresetForm').hide();
                $('#searchForm').show();
                $('#addForm').hide();

                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");

            }




            $.subscribe('onclicksearch', function (event, data) {
                var name = $('#reciname').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {reciname: name, search: true}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            });

            $.subscribe('loadAddForm', function (event, data) {
                $('#editForm').hide();
                $('#pwresetForm').hide();
                $('#searchForm').hide();
                $('#addForm').show();
            });

            //reset Datas
            function ResetSearchForm() {
                $('#reciname').val("");
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");
            }
            function ResetAddForm() {
                resetData();
                $('#divmsg').empty();
            }

            function resetUpdateForm(cid,nic) {
                editNow(cid, nic);
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");

            }

        </script>
    </head>

    <body style="overflow:hidden" onload="load()">
        <div class="wrapper">

            <div class="body_content" id="includedContent" >

                <div class="watermark"></div>
                <div class="heading">Recipient Management</div>
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
                        <s:form id="searchForm"  method="post"  theme="simple">         
                            <table class="form_table">              
                                <tr>
                                    <td class="content_td formLable">Name</td>
                                    <td>:</td>
                                    <td colspan="2"><s:textfield name="reciname"  id="reciname" cssClass="textField" /> </td>
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
                                    <td class="formLable">Customer Name<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:select id="cid" name="cid" headerKey="-1"  headerValue="---Select---"  list="%{customerList}" cssClass="textField"/></td>                                    
                                    <td width="25px;"></td>
                                    <td class="formLable">Recipient Name<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="name" name="name" cssClass="textField" /></td>
                                </tr>    
                                <tr>
                                    <td class="formLable">NIC<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="nic" name="nic" cssClass="textField" /></td>                                    
                                    <td width="25px;"></td>
                                    <td class="formLable">Mobile No <span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="mobile_no" name="mobile_no" cssClass="textField" placeholder="+94777804455"/></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Address</td> <td>:</td>
                                    <td><s:textfield id="address" name="address"  cssClass="textField"/></td>
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
                                    <td> <s:url var="addurl" action="AddreciMng"/>                                   
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
                                    <td hidden="true"><s:textfield name="upcid" id="upcid" cssClass="textField"  /></td>
                                    <td  class="formLable">Customer Name</td><td>:</td>
                                    <td ><s:textfield name="upcname" id="upcname" cssClass="textField" /></td>
                                    <td width="25px"></td>
                                    <td class="formLable">NIC</td> <td>:</td>
                                    <td ><s:textfield name="upnic" id="upnic" cssClass="textField"  />  </td>
                                    
                                </tr>
                                <tr>
                                    <td class="formLable">Recipient Name<span class="mandatory">*</span></td> <td>:</td>
                                    <td ><s:textfield name="upname" id="upname" cssClass="textField" /></td>
                                    <td width="25px"></td>
                                    <td class="formLable">Mobile<span class="mandatory">*</span></td> <td>:</td>
                                    <td ><s:textfield name="upmobile" id="upmobile" cssClass="textField" placeholder="+94777804455" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Address</td> <td>:</td>
                                    <td ><s:textfield name="upaddress" id="upaddress" cssClass="textField" /></td>
                                    <td width="25px"></td>
                                    <td class="formLable">Status<span class="mandatory">*</span></td><td>:</td>
                                    <td ><s:select  name="upstatus" id="upstatus" list="%{upstatusList}" 
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
                                        <sj:submit   button="true" href="%{updatereciurl}" value="Update"   targets="divmsg"  cssClass="button_sadd" disabled="#vupdate"/>
                                        <sj:submit button="true" value="Reset" onClick="resetUpdateForm($('#upcid').val(),$('#upnic').val())" cssClass="button_aback"/>
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
                                'OK':function() { deleteNow($(this).data('keyval1'),$(this).data('keyval2'));$( this ).dialog( 'close' ); },
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

                            <s:url var="listurl" action="ListreciMng"/>
                            <sjg:grid
                                id="gridtable"
                                caption="Recipient Management"
                                dataType="json"
                                href="%{listurl}"
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

                                <sjg:gridColumn name="nic" index="NIC" title="Edit" align="center" width="7" align="center"  formatter="editformatter" sortable="false" hidden="#vupdate"/>
                                <sjg:gridColumn name="nic" index="NIC" title="Delete" align="center" width="7" align="center"   formatter="deleteformatter" sortable="false" hidden="#vdelete"/>

                            </sjg:grid> 

                        </div> 



                    </div>
                </div>              

            </div>
        </div>
        <jsp:include page="../../footer.jsp" /> 

    </body>
</html>

