<%-- 
    Document   : customerManagement
    Created on : Feb 29, 2016, 5:32:10 PM
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
        <style>
            .tip {
                background: #eee;
                border: 1px solid #ccc;
                padding: 10px;
                border-radius: 8px;
                box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
                position: relative;
                //width: 200px;
                border:1px solid #444546;
                background: #eee;
                border: 0px;
                width: 100%;
                height:60px;
                margin: 0px;
                padding:5px;
                overflow: hidden;
                display:inline;
                color:#03a1fc;
                font-size:14px;
                resize: none;
            }
        </style>
        <script type="text/javascript">

            function load() {
                
            }

            function resetData() {
              $("#accountNo").val("");
              $("#mobileNo").val("");
              $("#amount").val("");
            }

            
            function ResetAddForm() {
                resetData();
                $('#divmsg').empty();
            }
            
            function sendMOBILE() {
                $('#divmsg').empty();
                var mobileNo = $('#mobileNo').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/getAccountcusTMng',
                    data: {mobileNo: mobileNo},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#editForm').hide();
                        $('#searchForm').hide();
                        $('#addForm').show();
                        if (data.success) {

                            $('#accountNo').val(data.accountNo);
                        }
                        else {
                            $("#errorbox").dialog('open');
                            $("#errorbox").html('<br><b><font size="3" color="red"><center>' + data.message + ' ');
                        }


                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });
            }
//            popup dialog recipient close
        </script>
    </head>

    <body style="overflow:hidden" onload="load()">
        <div class="wrapper">

            <div class="body_content" id="includedContent" >

                <div class="watermark"></div>
                <div class="heading">Customer Management</div>
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

                        <div class="contentcenter">    
                        <s:form  id="addForm"  theme="simple" method="post" >
                            <table class="form_table">
                                
                                <tr>
                                    <td class="formLable">Mobile Number<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="mobileNo" name="mobileNo" placeholder="+94771234567" cssClass="textField" /></td>
                                    <td width="5px;"></td>
                                    <td> <sj:submit id="sendnic" button="true" value="Verify" onclick="sendMOBILE()"   cssClass="button_aback" disabled="false" />
                                    </td>
                                </tr> 
                                <tr>
                                    <td class="formLable">Account Number<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="accountNo" name="accountNo" cssClass="textField" /></td>
                                    <td width="5px;"></td>
                                    <td class="formLable"></td>
                                    <td></td>
                                </tr>    
                                <tr>
                                    <td class="formLable">Amount<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="amount" name="amount" cssClass="textField" /></td>
                                    <td width="5px;"></td>
                                    <td class="formLable"></td>
                                    <td></td>
                                </tr>  
       
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> <s:url var="addurl" action="AddcusTMng"/>                                   
                                        <sj:submit   button="true" href="%{addurl}" value="Add"   targets="divmsg"  cssClass="button_sadd" /> 
                                        <sj:submit id="resetida" button="true" value="Reset" onclick="ResetAddForm()"   cssClass="button_aback" disabled="false" />
                                        <%--<sj:submit id="backida" button="true" value="Back" onclick="backToMain()"   cssClass="button_aback" disabled="false" />--%> 
                                    </td>
                                </tr>
                            </table>
                        </s:form>
    
                    </div>
                    
                                <sj:dialog 
                                id="errorbox" 
                                buttons="{
                                'OK':function() { $( this ).dialog( 'close' );}
                                }"  
                                autoOpen="false" 
                                modal="true" 
                                title="Error messae" 
                                width="400"
                                height="150"
                                position="center"
                                />
                </div>              

            </div>
        </div>
        <jsp:include page="../../footer.jsp" /> 

    </body>
</html>