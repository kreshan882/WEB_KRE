<%-- 
    Document   : channelManagement
    Created on : 02/03/2016, 1:54:40 PM
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
                if ('true' === $('#vadd').val()) {
                    $('#addid').hide();
                }
            }
            function resetData() {

                $('#channeltype').val("-1");
                $('#name').val("");
                $('#ip').val("");
                $('#port').val("");
                $('#contimeout').val("");
                $('#rtimeout').val("");
                $('#contype').val("-1");
                $('#headersize').val("");
                $('#isoheader').val("");


                $('#upchanneltype').val("-1");
                $('#upname').val("");
                $('#upip').val("");
                $('#upport').val("");
                $('#upcontimeout').val("");
                $('#uprtimeout').val("");
                $('#upstatus').val("-1");
                $('#upcontype').val("-1");
                $('#upheadersize').val("");
                $('#upisoheader').val("");

                $('#searchname').val("");

                jQuery("#gridtable").trigger("reloadGrid");
            }

            function statusformatter(cellvalue, options, rowObject) {
                if (cellvalue == 'Active') {
                    var html = "<img src='${pageContext.request.contextPath}/resources/images/iconActive.png' />";
                } else {
                    var html = "<img src= '${pageContext.request.contextPath}/resources/images/iconInactive.png' />";
                }
                return html;
            }


            function deleteformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='deleteInit(&#34;" + rowObject.id + "&#34;,&#34;" + rowObject.name + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/iconDelete.png'  /></a>";
            }

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='javascript:editNow(&#34;" + rowObject.id + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
            }


            function deleteInit(id, name) {

                $("#confirmdialogbox").data('keyval1', id);
                $("#confirmdialogbox").dialog('open');
                $("#confirmdialogbox").html('<br><b><font size="3" color="red"><center>Please confirm to delete channel : ' + name + '');
                backToMain();
                return false;
            }

            function deleteNow(id) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/DeletechannelMng',
                    data: {id: id},
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
                        jQuery("#gridtable").trigger("reloadGrid");
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });

            }


            function editNow(id) {

                $('#divmsg').empty();
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindchannelMng',
                    data: {id: id},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#editForm').show();
                        $('#searchForm').hide();
                        $('#addForm').hide();
                        $('#upid').val(data.upid);
                        $('#upchanneltype').val(data.upchanneltype);
                        $('#upname').val(data.upname);
                        $('#upip').val(data.upip);
                        $('#upport').val(data.upport);
                        $('#upcontimeout').val(data.upcontimeout);
                        $('#upisoheader').val(data.upisoheader);
                        $('#uprtimeout').val(data.uprtimeout);
                        $('#upstatus').val(data.upstatus);
                        $('#upcontype').val(data.upcontype);
                        $('#upheadersize').val(data.upheadersize);


                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });
            }


            function backToMain() {
                resetData();
                jQuery("#gridtable").trigger("reloadGrid");
                $('#editForm').hide();
                $('#searchForm').show();
                $('#addForm').hide();
                $('#divmsg').empty();
            }




            $.subscribe('onclicksearch', function (event, data) {
                var name = $('#searchname').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {searchname: name, search: true}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");
            });

            $.subscribe('loadAddForm', function (event, data) {
                $('#editForm').hide();
                $('#searchForm').hide();
                $('#addForm').show();
            });

            //reset Datas
            function ResetSearchForm() {
                $('#divmsg').empty();
                $('#searchname').val("");

                jQuery("#gridtable").trigger("reloadGrid");
            }
            function ResetAddForm() {
                resetData();
                $('#divmsg').empty();
            }

            function resetUpdateForm(id) {
                editNow(id);
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");

            }

        </script>
    </head>

    <body style="overflow:hidden" onload="load()">
        <div class="wrapper">

            <div class="body_content" id="includedContent" >

                <div class="watermark"></div>
                <div class="heading">Channel Management</div>
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
                        <s:form id="searchForm"  method="post"  theme="simple" cssStyle="display:none">         
                            <table class="form_table">              
                                <tr>
                                    <td class="content_td formLable">Channel Name</td>
                                    <td>:</td>
                                    <td colspan="2"><s:textfield name="searchname"  id="searchname" cssClass="textField" /> </td>
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
                                    <td class="formLable">Channel Name<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="name" name="name" cssClass="textField" /></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Channel Type<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:select id="channeltype" name="channeltype" headerKey="-1"  headerValue="---Select---"  list="%{channeltypeList}" cssClass="textField"/></td>                                    
                                </tr>    
                                <tr>
                                    <td class="formLable">IP<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="ip" name="ip" cssClass="textField" /></td>                                    
                                    <td width="25px;"></td>
                                    <td class="formLable">Port<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="port" name="port" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Connection Timeout<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="contimeout" name="contimeout"  cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Connection Type<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="contype" name="contype" headerKey="-1"  headerValue="---Select---"  list="%{contypeList}" cssClass="textField"/></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Read Timeout<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="rtimeout" name="rtimeout" cssClass="textField" /></td>                                    
                                    <td width="25px;"></td>
                                    <td class="formLable">Header Size<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:select id="headersize" name="headersize" headerKey="-1"  headerValue="---Select---" list="%{headersizeList}" cssClass="textField" /></td> 
                                </tr>
                                   <tr>
                                    <td class="formLable">ISO Header<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="isoheader" name="isoheader" cssClass="textField" /></td> 
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> <s:url var="addurl" action="AddchannelMng"/>                                   
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
                                    <td hidden="true"><s:textfield id="upid" name="upid" cssClass="textField" /></td>
                                    <td class="formLable">Channel Name<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="upname" name="upname" cssClass="textField" /></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Channel Type<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:select id="upchanneltype" name="upchanneltype" headerKey="-1"  headerValue="---Select---"  list="%{channeltypeList}" cssClass="textField"/></td>                                    
                                </tr>    
                                <tr>
                                    <td class="formLable">IP<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upip" name="upip" cssClass="textField" /></td>                                    
                                    <td width="25px;"></td>
                                    <td class="formLable">Port<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="upport" name="upport" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Connection Timeout<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="upcontimeout" name="upcontimeout"  cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Connection Type</td> <td>:</td>
                                    <td><s:select id="upcontype" name="upcontype" headerKey="-1"  headerValue="---Select---"  list="%{contypeList}" cssClass="textField"/></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Read Timeout<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="uprtimeout" name="uprtimeout" cssClass="textField" /></td> 
                                    <td width="25px;"></td>
                                    <td class="formLable">Header Size<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:select id="upheadersize" name="upheadersize" headerKey="-1"  headerValue="---Select---" list="%{headersizeList}" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Status<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="upstatus" name="upstatus" headerKey="-1"  headerValue="---Select---"  list="%{statusList}" cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">ISO Header<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upisoheader" name="upisoheader" cssClass="textField" /></td> 
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> <s:url var="updatechnlurl" action="UpdatechannelMng"/>                                   
                                        <sj:submit   button="true" href="%{updatechnlurl}" value="Update"   targets="divmsg"  cssClass="button_sadd" disabled="#vupdate"/>
                                        <sj:submit button="true" value="Reset" onClick="resetUpdateForm($('#upid').val())" cssClass="button_sreset"/>
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

                            <s:url var="listurl" action="ListchannelMng"/>
                            <sjg:grid
                                id="gridtable"
                                caption="Channel Management"
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

                                <sjg:gridColumn name="id" index="ID" title="Id"  hidden="true"/>
                                <sjg:gridColumn name="name" index="NAME" title="Channel Name" align="left" width="20" sortable="true"/>
                                <sjg:gridColumn name="channeltype" index="CHANNEL_TYPE" title="Channel Type" align="left"  width="18"  sortable="true" />                                
                                <sjg:gridColumn name="ip" index="IP" title="IP" align="left"  width="18"  sortable="true"/>
                                <sjg:gridColumn name="port" index="PORT" title="Port" align="left"  width="18"  sortable="true"/>
                                <sjg:gridColumn name="contimeout" index="CONN_TIMEOUT" title="Connection Timeout" align="left"  width="25"  sortable="true"/>
                                <sjg:gridColumn name="rtimeout" index="READ_TIMEOUT" title="Read Timeout" align="left"  width="18"  sortable="true" />
                                <sjg:gridColumn name="headersize" index="HEADER_SIZE" title="Header Size" align="left"  width="18"  sortable="true" />
                                <sjg:gridColumn name="isoheader" index="ISO_HEADER" title="ISO Header" align="left"  width="18"  sortable="true" />
                                <%--<sjg:gridColumn name="regDate" index="REGISTER_DATE" title="Reg Date" align="left"  width="18"  sortable="true"/>--%>
                                <sjg:gridColumn name="status" index="STATUS" title="Status" align="center" width="10" formatter="statusformatter" sortable="false"/> 

                                <sjg:gridColumn name="id" index="ID" title="Edit" align="center" width="7" align="center"  formatter="editformatter" sortable="true" hidden="#vupdate"/>
                                <sjg:gridColumn name="id" index="ID" title="Delete" align="center" width="7" align="center"   formatter="deleteformatter" sortable="false" hidden="true"/>

                            </sjg:grid> 

                        </div> 



                    </div>
                </div>              

            </div>
        </div>
        <jsp:include page="../../footer.jsp" /> 

    </body>
</html>

