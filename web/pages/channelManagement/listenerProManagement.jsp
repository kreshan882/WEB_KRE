<%-- 
    Document   : listenerProManagement
    Created on : 11/05/2016, 12:32:50 PM
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
                $('#listenerName').val("");
                $('#collectionacc').val("");
                $('#glacc').val("");
                $('#costCenter').val("");
                $('#amountHold').val("-1");
                $('#senderValidation').val("-1");
                $('#txnFee').val("-1");
                jQuery("#gridtable").trigger("reloadGrid");
            }

            function statusformatter(cellvalue, options, rowObject) {
                if (cellvalue == '19') {
                    var html = "<img src='${pageContext.request.contextPath}/resources/images/iconActive.png' />";
                } else {
                    var html = "<img src= '${pageContext.request.contextPath}/resources/images/iconInactive.png' />";
                }
                return html;
            }


            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='javascript:editNow(&#34;" + rowObject.id + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
            }
            function editNow(id) {

                $('#divmsg').empty();
                $.ajax({
                    url: '${pageContext.request.contextPath}/FindlistenerProMng',
                    data: {id: id},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#editForm').show();
                        $('#id').val(data.id);
                        $('#listenerName').val(data.listenerName);
                        $('#collectionacc').val(data.collectionacc);
                        $('#glacc').val(data.glacc);
                        $('#costCenter').val(data.costCenter);
                        $('#amountHold').val(data.amountHold);
                        $('#senderValidation').val(data.senderValidation);
                        $('#txnFee').val(data.txnFee);
                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });
            }


            function backToMain() {
                $('#divmsg').empty();
                $('#editForm').hide();
                jQuery("#gridtable").trigger("reloadGrid");

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
                <div class="heading">Listener Profile Management</div>
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
                        <s:form id="editForm"  theme="simple" method="post" cssStyle="display:none" >
                            <table class="form_table">

                                <tr>
                                    <td hidden="true"><s:textfield id="id" name="id" cssClass="textField" /></td>
                                    <td class="formLable">Listener Name<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:textfield id="listenerName" name="listenerName" cssClass="textField" disabled="true"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Collection Account Number<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="collectionacc" name="collectionacc"  cssClass="textField"/></td>                                    
                                </tr>    
                                <tr>
                                    <td class="formLable">GL Account Number<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="glacc" name="glacc" cssClass="textField" /></td>                                    
                                    <td width="25px;"></td>
                                    <td class="formLable">Fee Status<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:select id="txnFee" name="txnFee" headerKey="-1"  headerValue="---Select---"  list="%{txnFeeMap}" cssClass="textField"/></td>                                 

                                    <!--<td class="formLable">Cost Center<span class="mandatory">*</span></td> <td>:</td>-->
                                    <!--<td>-->
                                    <%--<s:textfield id="costCenter" name="costCenter" cssClass="textField" />--%>
                                    <!--</td>-->
                                </tr>
                                <tr>
                                    <td class="formLable">Amount Hold Status<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="amountHold" name="amountHold" headerKey="-1"  headerValue="---Select---"  list="%{amountHoldMap}" cssClass="textField"/></td>
                                    <td width="25px;"></td>
                                    <td class="formLable">Sender Validation Status<span class="mandatory">*</span></td> <td>:</td>
                                    <td><s:select id="senderValidation" name="senderValidation" headerKey="-1"  headerValue="---Select---"  list="%{senderValidationMap}" cssClass="textField"/></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                            </table><table class="form_table">
                                </br>
                                <tr>                                
                                    <td> <s:url var="updatelistenerurl" action="UpdatelistenerProMng"/>                                   
                                        <sj:submit   button="true" href="%{updatelistenerurl}" value="Update"   targets="divmsg"  cssClass="button_sadd" disabled="#vupdate"/>
                                        <sj:submit button="true" value="Reset" onClick="resetUpdateForm($('#id').val())" cssClass="button_sreset"/>
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

                            <s:url var="listurl" action="ListlistenerProMng"/>
                            <sjg:grid
                                id="gridtable"
                                caption="Listener Profile Management"
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

                                <sjg:gridColumn name="id" index="CODE" title="Id"  frozen="true" hidden="true"/>
                                <sjg:gridColumn name="listenerName" index="DESCRIPTION" title="Listener Name " align="left" width="200"  frozen="true"/>
                                <sjg:gridColumn name="collectionacc" index="COLLECTION_ACCOUNT" title="Collection Account Number" align="left"  width="200"  sortable="true" />                                
                                <sjg:gridColumn name="glacc" index="GL_ACCOUNT" title="GL Account Number" align="left"  width="200"  sortable="true"/>
                                <sjg:gridColumn name="costCenter" index="COST_CENTER" title="Cost Center" align="left"  width="200"  sortable="true" hidden="true"/>

                                <sjg:gridColumn name="amountHold" index="AMOUNT_HOLD_STATUS" title="Amount Hold Status" align="center"  width="150"  formatter="statusformatter" sortable="fale" />
                                <sjg:gridColumn name="senderValidation" index="CUST_VALID_STATUS" title="Sender Validation Status" align="center" width="154" formatter="statusformatter"sortable="fale"/>
                                <sjg:gridColumn name="txnFee" index="TXN_FEE_STATUS" title="Fee Status" align="center" width="150" formatter="statusformatter" sortable="false"/> 

                                <sjg:gridColumn name="id" index="ID" title="Edit" align="center" width="50" align="center"  formatter="editformatter" sortable="false" hidden="#vupdate"/>


                            </sjg:grid> 

                        </div> 



                    </div>
                </div>              

            </div>
        </div>
        <jsp:include page="../../footer.jsp" /> 

    </body>
</html>