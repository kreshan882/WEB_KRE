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
                $('#name').val("");
                $('#username').val("");
                $('#password').val("");
                $('#repassword').val("");
                $('#userPro').val("-1");
                $('#usertype').val("-1");
                $('#email').val("");
                $('#address').val("");
                $('#mobile').val("");
                $('#nic').val("");

                $('#upname').val("");
                $('#upusername').val("");
                $('#upuserPro').val("-1");
                $('#upusertype').val("-1");
                $('#upstatus').val("-1");
                $('#upemail').val("");
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
                return "<a href='#' onClick='deleteInit(&#34;" + cellvalue + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/iconDelete.png'  /></a>";
            }

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='javascript:editNow(&#34;" + cellvalue + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
            }
//            function pdchangeformatter(cellvalue, options, rowObject) {
//                return "<a href='#' onClick='javascript:pdchangeNow(&#34;" + cellvalue + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
//            }

            function deleteInit(keyval) {
                $("#confirmdialogbox").data('keyval', keyval).dialog('open');
                $("#confirmdialogbox").html('<br><b><font size="3" color="red"><center>Please confirm to delete user : ' + keyval + '');
                return false;
            }

//             function pdchangeNow(keyval){
//                $('#message').empty();
//
//                $('#pwresetForm').show();
//                $('#editForm').hide();
//                $('#searchForm').hide();
//                
//                $('#rusername').val(keyval);
//            }

            function deleteNow(keyval) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/DeleteusrMng',
                    data: {username: keyval},
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
                $('#divmsg').empty();
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindusrMng',
                    data: {username: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        $('#editForm').show();
                        $('#pwresetForm').hide();
                        $('#searchForm').hide();
                        $('#addForm').hide();

                        $('#upusername').attr('readOnly', true);
                        $('#upusername').val(data.upusername);

                        $('#upname').val(data.upname);
                        $('#upuserPro').val(data.upuserPro);
                        $('#upusertype').val(data.upusertype);
                        $('#upstatus').val(data.upstatus);
                        $('#upemail').val(data.upemail);
                        $('#upaddress').val(data.upaddress);
                        $('#upmobile').val(data.upmobile);
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
                var searchname = $('#searchname').val();
                $("#gridtable").jqGrid('setGridParam', {postData: {searchname: searchname, search: true}});
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
                $('#searchname').val("");
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");
            }
            function ResetAddForm() {
                resetData();
                $('#divmsg').empty();
            }

            function resetUpdateForm() {
                var upusername = $('#upusername').val();
                editNow(upusername);
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");

            }

        </script>
    </head>

    <body style="overflow:hidden" onload="load()">
        <div class="wrapper">

            <div class="body_content" id="includedContent" >

                <div class="watermark"></div>
                <div class="heading">User Management</div>
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
                        <s:form id="searchForm" action="usrMng" method="post"  theme="simple">         
                            <table class="form_table">              
                                <tr>
                                    <td class="content_td formLable">User Name</td>
                                    <td>:</td>
                                    <td colspan="2"><s:textfield name="searchname"  id="searchname" cssClass="textField" /> </td>
                                    <td class="content_td formLable">

                                    </td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> 
                                        <%--<sj:submit   button="true"  value="Adddsub"   onClickTopics="loadAddForm"  cssClass="button_sadd" disabled="#vadd"/>--%>
                                        <sj:a id="addid" button="true" onClickTopics="loadAddForm"  cssClass="button_sadd" disabled="#vadd" > Add </sj:a>
                                        <sj:a   id="searchid"  button="true"    onClickTopics="onclicksearch"  cssClass="button_aback"  role="button" aria-disabled="false" >Search</sj:a>
                                        <sj:submit id="resetid" button="true" value="Reset" onclick="ResetSearchForm()"   cssClass="button_aback" disabled="false" />

                                    </td>
                                </tr>
                            </table>
                        </s:form>

                        <s:form  id="addForm"  theme="simple" method="post"  cssStyle="display:none">
                            <table class="form_table">
                                <tr>
                                    <td class="formLable">Name<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="name" name="name" cssClass="textField" /></td>                                    
                                    <td width="25px;"></td>
                                    <td class="formLable">User Name<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="username" name="username" cssClass="textField" /></td>
                                </tr>    
                                <tr>
                                    <td class="formLable">Password<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:password id="password" name="password" cssClass="textField" /></td>                                    
                                    <td width="25px;"></td>
                                    <td class="formLable">Re Password<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:password id="repassword" name="repassword" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">User Profile<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="userPro" name="userPro" headerKey="-1"  headerValue="---Select---"  list="%{userProList}" cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">User Type<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="usertype" name="usertype" headerKey="-1"  headerValue="---Select---"  list="%{usertypeList}" cssClass="textField"/></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Email<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="email" name="email" cssClass="textField" /></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">NIC<span class="mandatory">*</span></td>  <td >:</td>
                                    <td><s:textfield id="nic" name="nic" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Mobile</td> <td >:</td>
                                    <td><s:textfield id="mobile" name="mobile" cssClass="textField" placeholder="+94778906755" /></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Address</td>  <td >:</td>
                                    <td><s:textfield id="address" name="address" cssClass="textField" /></td>
                                </tr>

                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> <s:url var="addurl" action="AddusrMng"/>                                   
                                        <sj:submit   button="true" href="%{addurl}" value="Add"   targets="divmsg"  cssClass="button_sadd" disabled="#vadd"/> 
                                        <sj:submit id="resetida" button="true" value="Reset" onclick="ResetAddForm()"   cssClass="button_aback" disabled="false" />
                                        <sj:submit id="backida" button="true" value="Back" onclick="backToMain()"   cssClass="button_aback" disabled="false" /> 
                                    </td>
                                </tr>
                            </table>
                        </s:form>

                        <s:form id="pwresetForm" theme="simple" method="post"  cssStyle="display:none">
                            <table>
                                <tr><td></td></tr>
                                <tr><td>User name</td><td><s:textfield  name="username" id="rusername" disabled="true" maxLength="50" onkeyup="$(this).val($(this).val().replace(/[^a-zA-Z0-9 ]/g,''))"/><s:hidden name="hrusername" id="hrusername"/></td></tr>
                                <tr><td>New Password</td><td><s:password name="password" id="rpassword" maxLength="100" autocomplete="off"/></td></tr>
                                <tr><td>Confirm Password</td><td><s:password name="confirmpw" id="rconfirmpw" maxLength="100" autocomplete="off"/></td></tr>
                                <tr><td><span class="mandatoryfield">All fields are mandatory</span></td><td></td></tr>
                                <tr>
                                    <td></td>
                                    <td>
                                        <s:url var="pwreseturl" action="PwresetUser"/>
                                        <sj:submit button="true" value="Password Reset" href="%{pwreseturl}" id="pwresetbut" targets="message" disabled="#vresetpass"/>
                                        <sj:submit button="true" value="Reset"  onClickTopics="resetButton"/>
                                        <sj:a href="#" name="back" button="true" onclick="backToMain()"  cssClass="button_aback" >Back</sj:a> 
                                        </td>
                                    </tr>
                                </table>
                        </s:form>

                        <s:form id="editForm"  theme="simple" method="post" cssStyle="display:none" >
                            <table class="form_table">

                                <tr>
                                    <td class="formLable">User Name</td><td>:</td>
                                    <td ><s:textfield name="upusername" id="upusername" cssClass="textField" /></td>
                                    <td width="25px"></td>
                                    <td class="formLable">Name<span class="mandatory">*</span></td> <td>:</td>
                                    <td ><s:textfield name="upname" id="upname" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">User Profile<span class="mandatory">*</span></td> <td>:</td>
                                    <td ><s:select  name="upuserPro" id="upuserPro" list="%{userProList}" 
                                               listKey="key" listValue="value"    headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" />
                                    </td>
                                    <td width="25px"></td>
                                    <td class="formLable">User Type<span class="mandatory">*</span></td> <td>:</td>
                                    <td ><s:select  name="upusertype" id="upusertype" list="%{usertypeList}" 
                                               listKey="key" listValue="value"    headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="formLable">NIC<span class="mandatory">*</span></td> <td>:</td>
                                    <td ><s:textfield name="upnic" id="upnic" cssClass="textField"  />  </td>
                                    <td width="25px"></td>
                                    <td class="formLable">Email<span class="mandatory">*</span></td> <td>:</td>
                                    <td ><s:textfield name="upemail" id="upemail" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Address</td> <td>:</td>
                                    <td ><s:textfield name="upaddress" id="upaddress" cssClass="textField" /></td>
                                    <td width="25px"></td>
                                    <td class="formLable">Mobile</td> <td>:</td>
                                    <td ><s:textfield name="upmobile" id="upmobile" cssClass="textField" placeholder="+94778906755"/></td>
                                </tr>
                                <tr> 
                                    <td class="formLable">Status<span class="mandatory">*</span></td><td>:</td>
                                    <td ><s:select  name="upstatus" id="upstatus" list="%{upstatusList}" 
                                               listKey="key" listValue="value"    headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" />
                                    </td>
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> <s:url var="updateuserurl" action="UpdateusrMng"/>                                   
                                        <sj:submit   button="true" href="%{updateuserurl}" value="Update"   targets="divmsg"  cssClass="button_sadd" disabled="#vupdate"/>
                                        <sj:submit button="true" value="Reset" onClick="resetUpdateForm()" cssClass="button_aback"/>
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
                            <!-- End delete dialog box -->

                            <s:url var="listurl" action="ListusrMng"/>
                            <sjg:grid
                                id="gridtable"
                                caption="User Management"
                                dataType="json"
                                href="%{listurl}"
                                pager="true"
                                gridModel="gridModel"
                                rowList="10,15,20"
                                rowNum="10"
                                autowidth="true"
                                shrinkToFit = "false"
                                rownumbers="true"
                                onCompleteTopics="completetopics"
                                rowTotal="false"
                                viewrecords="true"
                                >
                                <sjg:gridColumn name="profileId" index="PROFILE_ID" title="ProfileId"  frozen="true" hidden="true"/>
                                <sjg:gridColumn name="usertypeId" index="USER_TYPE" title="userTypeId" frozen="true" hidden="true"/>

                                <sjg:gridColumn name="name" index="NAME" title="Name" align="left" width="100" frozen="true" sortable="true"/>
                                <sjg:gridColumn name="username" index="USERNAME" title="User Name" align="left" width="100" sortable="true"/>                    
                                <sjg:gridColumn name="profilename" index="PROFILENAME" title="Profile Name" align="left" width="100" sortable="true"/>
                                <sjg:gridColumn name="usertype" index="USERTYPENAME" title="User Type" align="left"  width="100"  sortable="true"/>
                                <sjg:gridColumn name="email" index="EMAIL" title="Email" align="left"  width="100"  sortable="true"/>
                                <sjg:gridColumn name="address" index="ADDRESS" title="Address" align="left"  width="100"  sortable="true"/>
                                <sjg:gridColumn name="mobile" index="MOBILE" title="Mobile" align="left"  width="100"  sortable="true"/>
                                <sjg:gridColumn name="nic" index="NIC" title="NIC" align="left"  width="100"  sortable="true"/>


                                <sjg:gridColumn name="regDate" index="CREATE_DATE" title="Reg Date" align="center"  width="100"  sortable="true"/>
                                <sjg:gridColumn name="status" index="STATUS" title="Status" align="center" width="50" formatter="statusformatter" sortable="true"/>  

                                <%--<sjg:gridColumn name="username" index="USERNAME" title="Reset Pw" align="center" width="7" align="center"  formatter="pdchangeformatter" sortable="false" hidden="#vresetpass"/>--%>
                                <sjg:gridColumn name="username" index="USERNAME" title="Edit" align="center" width="50" align="center"  formatter="editformatter" sortable="false" hidden="#vupdate"/>
                                <sjg:gridColumn name="username" index="USERNAME" title="Delete" align="center" width="50" align="center"   formatter="deleteformatter" sortable="false" hidden="#vdelete"/>

                            </sjg:grid> 

                        </div> 



                    </div>
                </div>              

            </div>
        </div>
        <jsp:include page="../../footer.jsp" /> 

    </body>
</html>
