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
                if ('true' === $('#vadd').val()) {
                    $('#addid').hide();
                }
                $('#name').attr('readOnly', true).val();
                $('#cif').attr('readOnly', true).val();
                $('#mobile').val();
                $('#dateofbirth').attr('readOnly', true).val();
                $('#branch').attr('readOnly', true).val();
                $('#upname').attr('readOnly', true).val();
                $('#upcif').attr('readOnly', true).val();
                $('#upnic').attr('readOnly', true).val();
                $('#upaccountnumber').attr('readOnly', true).val();
                $('#upmobile').val().attr('readOnly', true).val();
                $('#updateofbirth').attr('readOnly', true).val();
                $('#upbranch').attr('readOnly', true).val();
            }

            function resetData() {
//                recipient popup data reset 
//                
//                $('#upcid').attr('readOnly', false);
                $('#recupnic').attr('readOnly', false);

                $('#recname').val("");
                $('#recaddress').val("");
                $('#recmobile_no').val("");
                $('#recnic').val("");
//                $('#cid').val("-1");
                $('#cname').val("");
                $('#status').val("-1");

                $('#recupname').val("");
                $('#recupcname').val("");
                $('#recupstatus').val("-1");
                $('#recupcid').val("");
                $('#recupaddress').val("");
                $('#recupmobile').val("");
                $('#recupnic').val("");

                jQuery("#gridtable2").trigger("reloadGrid");
//              end  recipient popup data reset 



                $('#name').val("");
                $('#nic').val("");
                $('#cif').val("");
                $('#mobile').val("");
                $('#usertype').val("-1");
                $('#merchantId').val("-1");
                $('#riskprofileid').val("-1");
                $('#smsprofileid').val("-1");
                $('#validaterecipient').val("");
                $('#dateofbirth').val("");
                $('#branch').val("");
                $('#status').val("");


                $('#accountnumber').empty();
                $('#accountnumber').append($('<option></option>').val("-1").html("--Select--"));
                $('#address').val("");

                $('#upcustomerid').val("");
                $('#upname').val("");
                $('#upnic').val("");
                $('#upcif').val("");
                $('#upmobile').val("");
                $('#upmerchantId').val("-1");
                $('#upaccountnumber').val("");
                $('#upusertype').val("-1");
                $('#upriskprofileid').val("-1");
                $('#upsmsprofileid').val("-1");
                $('#upvalidaterecipient').val("");
                $('#updateofbirth').val("");
                $('#upaddress').val("");
                $('#upbranch').val("");
                $('#upstatus').val("");

                $("#tooltip").val("");
                $("#tooltip").hide();

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
                return "<a href='#' onClick='deleteInit(&#34;" + cellvalue + "&#34;,&#34;" + rowObject.name + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/iconDelete.png'  /></a>";
            }

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='javascript:editNow(&#34;" + cellvalue + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
            }

            function deleteInit(keyval, nameVal) {
                $("#confirmdialogbox").data('keyval', keyval).dialog('open');
                $("#confirmdialogbox").html('<br><b><font size="3" color="red"><center>Please confirm to delete customer : ' + nameVal + '');
                return false;
            }
            function deleteNow(keyval) {
                $('#divmsg').empty();
                $.ajax({
                    url: '${pageContext.request.contextPath}/DeletecusMng',
                    data: {customerid: keyval},
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


            function editNow(keyval) {
                $('#divmsg').empty();
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindcusMng',
                    data: {customerid: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#editForm').show();
                        $('#searchForm').hide();
                        $('#addForm').hide();

                        $('#upriskprofileid').empty();
                        $('#upriskprofileid').append($('<option></option>').val("-1").html("--Select--"));

                        $.each(data.riskProfileList, function (key, value) {
                            $('#upriskprofileid').append($('<option></option>').val(key).html(value));
                        });


                        $('#upcustomerid').val(data.customerid);
                        $('#upname').val(data.upname);
                        $('#updateofbirth').val(data.updateofbirth);
                        $('#upnic').val(data.upnic);
                        $('#upcif').val(data.upcif);
                        $('#upmobile').val(data.upmobile);
                        $('#upaccountnumber').val(data.upaccountnumber);
                        $('#upusertype').val(data.upusertype);
                        $('#upchanneltype').val(data.upchanneltype);
                        $('#upmerchantId').val(data.upmerchantId);
                        $('#oldupmerchantId').val(data.oldupmerchantId);
                        $('#upriskprofileid').val(data.upriskprofileid);
                        $('#upsmsprofileid').val(data.upsmsprofileid);
                        $('#upvalidaterecipient').val(data.upvalidaterecipient);
                        $('#upaddress').val(data.upaddress);
                        $('#upbranch').val(data.upbranch);
                        $('#upstatus').val(data.upstatus);


                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });
            }

            function sendNIC() {
                $('#divmsg').empty();
                var nic = $('#nic').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/CustomerIQRcusMng',
                    data: {nic: nic},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#editForm').hide();
                        $('#searchForm').hide();
                        $('#addForm').show();
                        if (data.success) {

                            $('#name').val(data.name);
                            $('#nic').val(data.nic);
                            $('#cif').val(data.cif);
                            $('#mobile').val(data.mobile);
                            $('#dateofbirth').val(data.dateofbirth);
                            $('#branch').val(data.branch);

                            $('#accountnumber').empty();
                            $('#accountnumber').append($('<option></option>').val("-1").html("--Select--"));
                            $.each(data.accountnumberList, function (key, value) {
                                $('#accountnumber').append($('<option></option>').val(key).html(value));
                            });

                            $('#address').empty();

                            var newaddress = "";
                            var address = "";

                            $.each(data.addressList, function (key, value) {
                                newaddress += value + ',';
                                address += value + " \n";
                            });

                            $('#actualaddress').val(newaddress.substring(0, newaddress.length - 1));
                            $('#address').val(address);
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

            function sendAcountNumber() {
                var accno = $('#accountnumber').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/AccountINQRcusMng',
                    data: {accountnumber: accno},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        if (data.success) {
                            $("#tooltip").show();
                            document.getElementById('tooltipData').style.color = '#008000';
                            $('#tooltipData').val(" Account type: " + data.tacctype + "\n Account Status: " + data.tstatus + "\n Available Balance: " + data.tamount);

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

            function loadMerchant(keyval) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/LoadcusMng',
                    data: {usertype: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {

                        if (data.usertype === "03") {
                            $("#m1").hide();
                            $("#m2").hide();
                            $("#m3").hide();
                        } else {
                            $("#m1").show();
                            $("#m2").show();
                            $("#m3").show();
                        }
                        $('#merchantId').empty();
                        $('#merchantId').append($('<option></option>').val("-1").html("--Select--"));

                        $.each(data.merchantList, function (key, value) {
                            $('#merchantId').append($('<option></option>').val(key).html(value));
                        });

                        $('#upmerchantId').empty();
                        $('#upmerchantId').append($('<option></option>').val("-1").html("--Select--"));

                        $.each(data.merchantList, function (key, value) {
                            $('#upmerchantId').append($('<option></option>').val(key).html(value));
                        });


                        $('#riskprofileid').empty();
                        $('#riskprofileid').append($('<option></option>').val("-1").html("--Select--"));

                        $.each(data.riskProfileList, function (key, value) {
                            $('#riskprofileid').append($('<option></option>').val(key).html(value));
                        });

                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });

//                } else {
//                    $('#merchantId').empty();
//                    $('#merchantId').append($('<option></option>').val("-1").html("--Select--"));
//                    $('#upmerchantId').empty();
//                    $('#upmerchantId').append($('<option></option>').val("-1").html("--Select--"));
//
//
//
//                }
            }

            function backToMain() {
                $('#editForm').hide();
                $('#searchForm').show();
                $('#addForm').hide();
                $("#tooltip").hide();

                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");

            }




            $.subscribe('onclicksearch', function (event, data) {
                var cname = $('#cname').val();
                $("#gridtable").jqGrid('setGridParam', {postData: {cname: cname, search: true}});
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
                $('#cname').val("");
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");
            }
            function ResetAddForm() {
                resetData();
                $('#divmsg').empty();
                $('#recdivmsg').empty();
            }
//            popup dialog recipient close
            function recPopupClose() {
                $("#viewdialog").dialog('close');
            }

            function resetUpdateForm() {
                var customerid = $('#upcustomerid').val();
                editNow(customerid);
                $('#divmsg').empty();
                jQuery("#gridtable").trigger("reloadGrid");

            }

            function recipientformatter(cellvalue, options, rowObject) {
                return "<a href='#' title='Recipient Manager' onClick='javascript:viewRecipient(&#34;" + cellvalue + "&#34;)'><img src='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
            }


            function viewRecipient(keyval) {
//            alert(keyval)
                $("#viewdialog").data('cid', keyval).dialog('open');
            }

            $.subscribe('openview', function (event, data) {
                var $led = $("#viewdialog");
                $led.load("RecipientListreciMng?cid=" + $led.data('cid'));
            });

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
                    <s:set var="vresetpass"><s:property value="vresetpass" default="true"/></s:set>
                    <s:set var="vrecipientadd"><s:property value="vrecipientadd" default="true"/></s:set>

                        <div class="contentcenter">
                        <s:form id="searchForm"  method="post"  theme="simple">         
                            <table class="form_table">              
                                <tr>
                                    <td class="content_td formLable">Customer Name</td>
                                    <td>:</td>
                                    <td colspan="2"><s:textfield name="cname"  id="cname" cssClass="textField" /> </td>
                                    <td class="content_td formLable">

                                    </td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> 
                                        <%--<sj:submit   button="true"  value="Adddsub"   onClickTopics="loadAddForm"  cssClass="button_sadd" disabled="#vadd"/>--%>
                                        <sj:a id="addid" button="true" onClickTopics="loadAddForm"  cssClass="button_sadd" disabled="#vadd" > Add </sj:a>
                                        <sj:a   id="searchid"  button="true"    onClickTopics="onclicksearch"  cssClass="button_aback"  disabled="false" >Search</sj:a>
                                        <sj:submit id="resetid" button="true" value="Reset" onclick="ResetSearchForm()"   cssClass="button_aback" disabled="false" />

                                    </td>
                                </tr>
                            </table>
                        </s:form>

                        <s:form  id="addForm"  theme="simple" method="post"  cssStyle="display:none">
                            <table class="form_table">
                                <tr>
                                    <td class="formLable">NIC<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="nic" name="nic" cssClass="textField" /></td>
                                    <td width="25px;"></td>
                                    <td> <sj:submit id="sendnic" button="true" value="Verify" onclick="sendNIC()"   cssClass="button_aback" disabled="false" />
                                    </td>
                                </tr>    
                                <tr>
                                    <td class="formLable">Name<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="name" name="name" cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">CIF<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="cif" name="cif" cssClass="textField" /></td>
                                </tr>
                                <tr>

                                    <td class="formLable">Mobile Number<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="mobile" name="mobile" placeholder="+94771234567" cssClass="textField" /></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Date Of Birth<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="dateofbirth" name="dateofbirth" cssClass="textField" /></td>                                    
                                </tr>
                                <tr>
                                    <td hidden="true"><s:textfield id="actualaddress" name="actualaddress" cssClass="textField" /></td>
                                    <td class="formLable">Address<span class="mandatory">*</span></td>  <td >:</td>
                                    <td><s:textarea   id="address" name="address"    cssClass="textField" /></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Account Number<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="accountnumber" name="accountnumber"   list="%{accountnumberList}" onchange="sendAcountNumber()" cssClass="textField"/></td>
                                    <td rowspan="2" width="100%" valign="center">
                                        <div id="tooltip" style="display: none; margin-left: 15px;margin-right: 0px; margin-bottom: 0px;" >
                                            <s:textarea name="tooltipData" id="tooltipData" cssClass="tip" readonly="true"   />
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="formLable">Validate Recipient<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="validaterecipient" name="validaterecipient" headerKey="-1"  headerValue="---Select---"  list="%{validaterecipientList}" cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">SMS Profile ID<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="smsprofileid" name="smsprofileid" headerKey="-1"  headerValue="---Select---"  list="%{smsProfileList}" cssClass="textField"/></td>
                                </tr>
                                <tr>
                                    <td class="formLable">User Type<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="usertype" name="usertype" onchange="loadMerchant(this.value)" listValue="value" listKey="key" headerKey="-1"  headerValue="---Select---"  list="%{usertypeList}" cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Branch<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="branch" name="branch" cssClass="textField" /></td>
                                </tr>
                                <tr><td class="formLable">Risk Profile ID<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="riskprofileid" name="riskprofileid" headerKey="-1"  headerValue="---Select---"  list="%{riskProfileList}" cssClass="textField"/></td>
                                    <td width="25px;" ></td>
                                    <td class="formLable" id="m1">Merchant</td> <td id="m2">:</td>
                                    <td id="m3"><s:select id="merchantId" listValue="value" listKey="key" name="merchantId" headerKey="-1"  headerValue="---Select---"  list="%{merchantList}" cssClass="textField"/></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> <s:url var="addurl" action="AddcusMng"/>                                   
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
                                    <td hidden="true"><s:textfield id="oldupmerchantId" name="oldupmerchantId" cssClass="textField" /></td> 
                                    <td hidden="true"><s:textfield id="upcustomerid" name="upcustomerid" cssClass="textField" /></td>     
                                    <td class="formLable">Name<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upname" name="upname" cssClass="textField" /></td>                                    
                                    <td width="25px;"></td>
                                    <td class="formLable">NIC<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="upnic" name="upnic" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">CIF<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upcif" name="upcif" cssClass="textField" /></td>                                    
                                    <td width="25px;"></td>
                                    <td class="formLable">Mobile Number<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="upmobile" name="upmobile" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Account Number<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upaccountnumber" name="upaccountnumber" cssClass="textField" /></td> 
                                    <td width="25px;"></td>
                                    <td class="formLable">Date of Birth<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="updateofbirth" name="updateofbirth" cssClass="textField" /></td> 
                                </tr>
                                <tr>
                                    <td class="formLable">Risk Profile ID<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="upriskprofileid" name="upriskprofileid" headerKey="-1"  headerValue="---Select---"  list="%{riskProfileList}" cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">SMS Profile ID<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="upsmsprofileid" name="upsmsprofileid" headerKey="-1"  headerValue="---Select---"  list="%{smsProfileList}" cssClass="textField"/></td>
                                </tr>
                                <tr>
                                    <td class="formLable">Validate Recipient<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="upvalidaterecipient" name="upvalidaterecipient" headerKey="-1"  headerValue="---Select---"  list="%{validaterecipientList}" cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Address<span class="mandatory">*</span></td>  <td >:</td>
                                    <td><s:textfield id="upaddress" name="upaddress" cssClass="textField" /></td>
                                </tr>
                                <tr hidden="true">
                                    <%--                                    <td class="formLable">User Type<span class="mandatory">*</span></td> <td>:</td>
                                                                        <td><s:select id="upusertype" name="upusertype" onchange="loadMerchant(this.value)" listValue="value" listKey="key" headerKey="-1"  headerValue="---Select---"  list="%{usertypeList}" cssClass="textField"/></td>
                                                                        <td width="25px;"></td>
                                                                        <td class="formLable">User Merchant<span class="mandatory">*</span></td> <td>:</td>
                                                                        <td><s:select id="upmerchantId" listValue="value" listKey="key" name="upmerchantId" headerKey="-1"  headerValue="---Select---"  list="%{merchantList}" cssClass="textField"/></td>--%>
                                </tr>
                                <tr>
                                    <td class="formLable">Branch<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upbranch" name="upbranch" cssClass="textField" /></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Status<span class="mandatory">*</span></td><td>:</td>
                                    <td ><s:select  name="upstatus" id="upstatus" list="%{upstatusList}" 
                                               listKey="key" listValue="value"    headerKey="-1"    headerValue="---Select---"     cssClass="dropdown" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> <s:url var="updatecusturl" action="UpdatecusMng"/>                                   
                                        <sj:submit   button="true" href="%{updatecusturl}" value="Update"   targets="divmsg"  cssClass="button_sadd" disabled="#vupdate"/>
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
                                title="Recipient Manager."
                                onOpenTopics="openview" 
                                loadingText="Loading .."
                                >

                            </sj:dialog>
                            <!-- End delete dialog box -->

                            <s:url var="listurl" action="ListcusMng"/>
                            <sjg:grid
                                id="gridtable"
                                caption="Customer Management"
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

                                <sjg:gridColumn name="customerid" index="CUSTOMER_ID" title="customerid" frozen="true" hidden="true"/>

                                <sjg:gridColumn name="name" index="NAME" title="Name" align="left" width="150" frozen="true" sortable="true"/>
                                <sjg:gridColumn name="username" index="USERNAME" title="User Name" align="left" width="150" frozen="true" sortable="true"/>
                                <sjg:gridColumn name="nic" index="NIC" title="NIC" align="left"  width="100"  frozen="true" sortable="true"/>                    
                                <sjg:gridColumn name="cif" index="CIF" title="CIF" align="left"  width="100"  sortable="true"/>
                                <sjg:gridColumn name="mobile" index="MOBILE_NO" title="Mobile" align="left"  width="100"  sortable="true"/>
                                <sjg:gridColumn name="address" index="ADDRESS" title="Address" align="left"  width="100"  sortable="true"/>
                                <sjg:gridColumn name="accountnumber" index="ACCOUNT_NUMBER" title="Account No" align="left" width="100" sortable="true"/>
                                <sjg:gridColumn name="usertype" index="USERTYPENAME" title="User Type" align="left"  width="100"  sortable="true"/>                                
                                <sjg:gridColumn name="riskprofileid" index="RISKPROFILEID" title="Risk Profile ID" align="left"  width="100"  sortable="true"/>
                                <sjg:gridColumn name="smsprofileid" index="SMSPROFILEID" title="SMS Profile ID" align="left"  width="100"  sortable="true"/>
                                <sjg:gridColumn name="validaterecipient" index="VALIDATE_RECIPIENT" title="Validate Recipient" align="left"  width="100"  sortable="true"/>                                
                                <sjg:gridColumn name="branch" index="BRANCH" title="Branch" align="left"  width="100"  sortable="true"/>
                                <sjg:gridColumn name="regmsg" index="REG_SMS" title="Registration SMS" align="left"  width="200"  sortable="false"/>


                                <sjg:gridColumn name="regDate" index="REG_DATE_TIME" title="Reg Date" align="center"  width="100"  sortable="true"/>
                                <sjg:gridColumn name="customerid" index="CUSTOMER_ID" title="Assign Recipient" align="center" width="150" align="center"  formatter="recipientformatter" sortable="false" hidden="#vrecipientadd"/>
                                <sjg:gridColumn name="status" index="STATUS" title="Status" align="center" width="50" formatter="statusformatter" sortable="false"/>  

                                <%--<sjg:gridColumn name="username" index="USERNAME" title="Reset Pw" align="center" width="7" align="center"  formatter="pdchangeformatter" sortable="false" hidden="#vresetpass"/>--%>
                                <sjg:gridColumn name="customerid" index="CUSTOMER_ID" title="Edit" align="center" width="50" align="center"  formatter="editformatter" sortable="false" hidden="#vupdate"/>
                                <sjg:gridColumn name="customerid" index="CUSTOMER_ID" title="Delete" align="center" width="50" align="center"   formatter="deleteformatter" sortable="false" hidden="#vdelete"/>

                            </sjg:grid> 

                        </div> 



                    </div>
                </div>              

            </div>
        </div>
        <jsp:include page="../../footer.jsp" /> 

    </body>
</html>