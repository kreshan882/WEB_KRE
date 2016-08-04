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
                if ('true' === $('#vupdate').val()) {
                    $('#editbtn').hide();
                }
            }

            function editButton() {
                document.getElementById("divClear").style.display = "block";
                document.getElementById("divEdit").style.display = "none";
                document.getElementById('divSave').style.display = "block";
                document.getElementById('divBack').style.display = "block";
                $("#message").empty();
                $("#loglevel").prop('disabled', false);
                $("#minpoolsize").prop('disabled', false);
                $("#maxpoolsize").prop('disabled', false);
                $("#maxqueuesize").prop('disabled', false);
                $("#nooflogfile").prop('disabled', false);
                $("#serviceport").prop('disabled', false);
                $("#operationport").prop('disabled', false);
                $("#serviceportreadtimeout").prop('disabled', false);
                $("#logbackupstatus").prop('disabled', false);
                $("#logbackuppath").prop('disabled', false);
                $("#ordlength").prop('disabled', false);
                $("#seclength").prop('disabled', false);
                $("#maxpinretry").prop('disabled', false);
                $("#divmsg").empty();
            }

            function clearData() {
                $("#loglevel").val("-1");
                $("#minpoolsize").val("");
                $("#maxpoolsize").val("");
                $("#maxqueuesize").val("");
                $("#nooflogfile").val("");
                $("#serviceport").val("");
                $("#operationport").val("");
                $("#serviceportreadtimeout").val("");
                $("#logbackupstatus").val("-1");
                $("#logbackuppath").val("");
                $("#ordlength").val("");
                $("#seclength").val("");
                $("#maxpinretry").val("");

            }

            function backToMain() {

                document.getElementById("divClear").style.display = "none";
                document.getElementById('divSave').style.display = "none";
                document.getElementById("divEdit").style.display = "block";
                document.getElementById('divBack').style.display = "none";
                $("#loglevel").prop('disabled', true);
                $("#minpoolsize").prop('disabled', true);
                $("#maxpoolsize").prop('disabled', true);
                $("#maxqueuesize").prop('disabled', true);
                $("#nooflogfile").prop('disabled', true);
                $("#serviceport").prop('disabled', true);
                $("#operationport").prop('disabled', true);
                $("#serviceportreadtimeout").prop('disabled', true);
                $("#logbackupstatus").prop('disabled', true);
                $("#logbackuppath").prop('disabled', true);
                $("#ordlength").prop('disabled', true);
                $("#seclength").prop('disabled', true);
                $("#maxpinretry").prop('disabled', true);
                resetFieldData();

            }

            function resetFieldData() {
                var id = $('#id').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindsystemParaMng',
                    data: {id: id},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#divmsg').empty();
                        $("#loglevel").val(data.loglevel);
                        $("#minpoolsize").val(data.minpoolsize);
                        $("#maxpoolsize").val(data.maxpoolsize);
                        $("#maxqueuesize").val(data.maxqueuesize);
                        $("#nooflogfile").val(data.nooflogfile);
                        $("#serviceport").val(data.serviceport);
                        $("#operationport").val(data.operationport);
                        $("#serviceportreadtimeout").val(data.serviceportreadtimeout);
                        $("#logbackupstatus").val(data.logbackupstatus);
                        $("#logbackuppath").val(data.logbackuppath);
                        $("#ordlength").val(data.ordlength);
                        $("#seclength").val(data.seclength);
                        $("#maxpinretry").val(data.maxpinretry);


                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });
            }


        </script>
    </head>

    <body style="overflow:hidden" onload="load()">
        <div class="wrapper">

            <div class="body_content" id="includedContent" >

                <div class="watermark"></div>
                <div class="heading">System Configuration</div>
                <div class="AddUser_box ">

                    <div class="message">         
                        <s:div id="divmsg">
                            <i style="color: red">  <s:actionerror theme="jquery"/></i>
                            <i style="color: green"> <s:actionmessage theme="jquery"/></i>
                        </s:div>         
                    </div>
                    <s:hidden  id="vadd" name="vadd" default="true"/>
                    <s:hidden  id="vupdate" name="vupdate" default="true"/>
                    <s:set id="vadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
                    <s:set var="vupdate"><s:property value="vupdate" default="true"/></s:set>
                    <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>
                    <s:set var="vdownload"><s:property value="vdownload" default="true"/></s:set>
                    <s:set var="vresetpass"><s:property value="vresetpass" default="true"/></s:set>

                        <div class="contentcenter">
                        <s:form  id="editForm"  theme="simple">
                            <table class="form_table"> 
                                <tr>
                                    <td>
                                        <fieldset style="border-radius: 5px;width: 400px;">
                                            <legend>Thread Pool Configuration</legend>
                                            <table class="form_table" >
                                                <tr>
                                                    <td hidden="true"><s:textfield id="id" name="id" cssClass="textField"/></td>
                                                    <td class="content_td formLable">Minimum Pool Size<span class="mandatory">*</span></td>
                                                    <td class="content_td formLable">:</td>
                                                    <td><s:textfield name="minpoolsize" id="minpoolsize" cssClass="textField" disabled="true" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="content_td formLable">Maximum Pool Size<span class="mandatory">*</span></td>
                                                    <td class="content_td formLable">:</td>
                                                    <td><s:textfield name="maxpoolsize" id="maxpoolsize" cssClass="textField" disabled="true" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="content_td formLable">Maximum Queue Size<span class="mandatory">*</span></td>
                                                    <td class="content_td formLable">:</td>
                                                    <td><s:textfield name="maxqueuesize" id="maxqueuesize" cssClass="textField" disabled="true" /></td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </td>
                                    <td class="content_td formLable" width="10px"></td>
                                    <td>
                                        <fieldset style="border-radius: 5px;width: 400px; height: 118px;">
                                            <legend>Bank Configuration</legend>
                                            <table class="form_table" >
                                                <tr>
                                                    <td class="content_td formLable">Length of ORD<span class="mandatory">*</span></td>
                                                    <td class="content_td formLable">:</td>
                                                    <td><s:textfield name="ordlength" id="ordlength" cssClass="textField" disabled="true" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="content_td formLable">Length Of Secrete Code<span class="mandatory">*</span></td>
                                                    <td class="content_td formLable">:</td>
                                                    <td><s:textfield name="seclength" id="seclength" cssClass="textField" disabled="true" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="content_td formLable">Maximum PIN Retry<span class="mandatory">*</span></td>
                                                    <td class="content_td formLable">:</td>
                                                    <td><s:textfield name="maxpinretry" id="maxpinretry" cssClass="textField" disabled="true" /></td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </td>
                                </tr>
                                <tr style="height: 10px;"></tr>
                                <tr>
                                    <td>
                                        <fieldset style="border-radius: 5px;width: 400px;">
                                            <legend>Log Configuration</legend>
                                            <table class="form_table" >
                                                <tr>
                                                    <td class="content_td formLable">Log Backup Path<span class="mandatory">*</span></td>
                                                    <td class="content_td formLable">:</td>
                                                    <td><s:textfield name="logbackuppath" id="logbackuppath" cssClass="textField" disabled="true" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="content_td formLable">Log Backup Status<span class="mandatory">*</span></td>
                                                    <td class="content_td formLable">:</td>
                                                    <td><s:select id="logbackupstatus" name="logbackupstatus" headerKey="-1"  headerValue="---Select---"  list="%{logBackupStatusList}" cssClass="textField" disabled="true"/></td>
                                                </tr>
                                                <tr>
                                                    <td class="content_td formLable">Log Level<span class="mandatory">*</span></td>
                                                    <td class="content_td formLable">:</td>
                                                    <td><s:select id="loglevel" name="loglevel" headerKey="-1"  headerValue="---Select---"  list="%{logLevelList}" cssClass="textField" disabled="true"/></td>
                                                </tr>
                                                <tr>
                                                    <td class="content_td formLable">No of Log File<span class="mandatory">*</span></td>
                                                    <td class="content_td formLable">:</td>
                                                    <td><s:textfield name="nooflogfile" id="nooflogfile" cssClass="textField" disabled="true" /></td>
                                                </tr>

                                            </table>
                                        </fieldset>
                                    </td>
                                    <td class="content_td formLable" width="10px"></td>
                                    <td>
                                        <fieldset style="border-radius: 5px;width: 400px; height: 157px;">
                                            <legend>Operation Configuration</legend>
                                            <table class="form_table">
                                                <tr>
                                                    <td class="content_td formLable">Service Port<span class="mandatory">*</span></td>
                                                    <td class="content_td formLable">:</td>
                                                    <td><s:textfield name="serviceport" id="serviceport" cssClass="textField" disabled="true" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="content_td formLable">Service Port Read Timeout<span class="mandatory">*</span></td>
                                                    <td class="content_td formLable">:</td>
                                                    <td><s:textfield name="serviceportreadtimeout" id="serviceportreadtimeout" cssClass="textField" disabled="true" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="content_td formLable">Operation Port<span class="mandatory">*</span></td>
                                                    <td class="content_td formLable">:</td>
                                                    <td><s:textfield name="operationport" id="operationport" cssClass="textField" disabled="true" /></td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory" >*</span></td>
                                </tr>
                            </table>
                            <table class="form_table" style="margin-left: 10px;">
                                <tr>

                                    <td align="right">
                                        <s:url action="UpdatesystemParaMng" var="updateuserurl"/>
                                        <s:div id ="divSave" cssClass="collapse1" cssStyle="display: none">
                                            <sj:submit
                                                href="%{updateuserurl}"                                      
                                                button="true"                                      
                                                value="Save"
                                                id="Save"
                                                targets="divmsg"
                                                cssClass="button_asearch"

                                                />
                                        </s:div> 
                                    </td>  

                                    <td>  
                                        <s:div id ="divEdit" cssStyle="display:block">
                                            <sj:submit
                                                cssClass="button_asearch"
                                                id="editbtn"
                                                button="true"                                      
                                                onclick="editButton()"
                                                value="Edit"
                                                disabled="#vupdate"/>
                                        </s:div> 
                                    </td>

                                    <td> <s:url action="" var="loaduserurl"/>   
                                        <s:div id ="divBack" cssStyle="display: none">
                                            <sj:submit
                                                href="%{loaduserurl}"                             
                                                button="true"                                      
                                                value="Back" 
                                                id="Back1"
                                                onclick="backToMain()"
                                                cssClass="button_asearch"
                                                />
                                        </s:div>
                                    </td> 

                                    <td align="left" width="25px;">                                     
                                        <s:div id ="divClear" cssClass="collapse1" cssStyle="display: none">
                                            <sj:submit

                                                button="true"                                                                    
                                                value="Reset" 
                                                id="Clear"                                                                                                                  
                                                onclick="resetFieldData()"
                                                cssClass="button_asearch"
                                                />                                  
                                        </s:div>
                                    </td>                                 

                                </tr>                               
                            </table>
                        </s:form>
                    </div>
                </div>              

            </div>
        </div>
        <jsp:include page="../../footer.jsp" /> 

    </body>
</html>