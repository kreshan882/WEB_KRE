<%-- 
    Document   : passwordPolicy
    Created on : Mar 3, 2016, 2:13:09 PM
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

        <script type="text/javascript">


            function load() {
                if ('true' === $('#vupdate').val()) {
                    $('#editformid').hide();
                }
            }

            function editformatter(cellvalue, options, rowObject) {
                return "<a href='#' onClick='javascript:editNow(&#34;" + cellvalue + "&#34;)'><img src ='${pageContext.request.contextPath}/resources/images/iconEdit.png' /></a>";
            }

            function editNow(keyval) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/FindpwPolicyMng',
                    data: {customerid: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                        $('#upid').val(data.upid);
                        $('#upminlength').val(data.upminlength);
                        $('#upmaxlength').val(data.upmaxlength);
                        $('#upallowspecialcharacters').val(data.upallowspecialcharacters);
                        $('#upminimumspecialcharacters').val(data.upminimumspecialcharacters);
                        $('#upmaximumspecialcharacters').val(data.upmaximumspecialcharacters);
                        $('#upminimumuppercasecharacters').val(data.upminimumuppercasecharacters);
                        $('#upminimumnumericalcharacters').val(data.upminimumnumericalcharacters);
                        $('#updescription').val(data.updescription);

                        $('#id').val(data.id);
                        $('#minlength').val(data.upminlength);
                        $('#maxlength').val(data.upmaxlength);
                        $('#allowspecialcharacters').val(data.upallowspecialcharacters);
                        $('#minimumspecialcharacters').val(data.upminimumspecialcharacters);
                        $('#maximumspecialcharacters').val(data.upmaximumspecialcharacters);
                        $('#minimumuppercasecharacters').val(data.upminimumuppercasecharacters);
                        $('#minimumnumericalcharacters').val(data.upminimumnumericalcharacters);
                        $('#description').val(data.updescription);


                    },
                    error: function (data) {
                        window.location = "${pageContext.request.contextPath}/LogoutloginCall.blb?";
                    }
                });
            }


            function backToMain() {
                $('#editForm').hide();
                $('#viewForm').show();
                $('#divmsg').empty();
                resetUpdateForm();

            }

            $.subscribe('gotoeditform', function (event, data) {
                $('#editForm').show();
                $('#viewForm').hide();
            });


            //reset Datas

            function resetUpdateForm() {
                var upid = $('#upid').val();
                editNow(upid);
                $('#divmsg').empty();
            }

        </script>
    </head>

    <body style="overflow:hidden" onload="load()">
        <div class="wrapper">

            <div class="body_content" id="includedContent" >

                <div class="watermark"></div>
                <div class="heading">Password Policy</div>
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
                    <s:set id="vupdate"><s:property value="vupdate" default="true"/></s:set>
                    <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>
                    <s:set var="vdownload"><s:property value="vdownload" default="true"/></s:set>
                    <s:set var="vresetpass"><s:property value="vresetpass" default="true"/></s:set>

                        <div class="contentcenter">
                        <s:form id="viewForm" action="" method="post"  theme="simple">         
                            <table class="form_table">      
                                <tr>
                                    <td hidden="true"><s:textfield id="id" name="id" cssClass="textField"/></td>
                                    <td class="formLable">Minimum Length<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="minlength" name="minlength" cssClass="textField" disabled="true"/></td>  
                                </tr>  
                                <tr>
                                    <td class="formLable">Maximum Length<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="maxlength" name="maxlength" cssClass="textField" disabled="true"/></td>      
                                </tr>
                                <tr>
                                    <td class="formLable">Allow Special Characters<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="allowspecialcharacters" name="allowspecialcharacters" cssClass="textField" disabled="true"/></td> 
                                    <td class="formLable">[~@#$&!]</td> 
                                </tr>  
                                <tr>
                                    <td class="formLable">Minimum Special Characters<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="minimumspecialcharacters" name="minimumspecialcharacters" cssClass="textField" disabled="true"/></td>  

                                </tr>
                                <tr>
                                    <td class="formLable">Maximum Special Characters<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="maximumspecialcharacters" name="maximumspecialcharacters" cssClass="textField" disabled="true"/></td>  

                                </tr>
                                <tr>
                                    <td class="formLable">Minimum Uppercase Characters<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="minimumuppercasecharacters" name="minimumuppercasecharacters" cssClass="textField" disabled="true"/></td>  
                                </tr>  
                                <tr>
                                    <td class="formLable">Minimum Numerical Characters<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="minimumnumericalcharacters" name="minimumnumericalcharacters" cssClass="textField" disabled="true"/></td>      
                                </tr>
                                <tr>
                                    <td class="formLable">Description<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="description" name="description" cssClass="textField" disabled="true"/></td> 
                                </tr> 
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory" >*</span></td>
                                </tr>
                            </table><table class="form_table">
                                <tr>                                
                                    <td> 
                                        <sj:a   id="editformid"  button="true"    onClickTopics="gotoeditform"  cssClass="button_asearch"  role="button" aria-disabled="false"  disabled='#vupdate' >Edit</sj:a>
                                        </td>
                                    </tr>
                                </table>
                        </s:form>

                        <s:form id="editForm"  theme="simple" method="post" cssStyle="display:none" >
                            <table class="form_table">      
                                <tr>
                                    <td hidden="true"><s:textfield id="upid" name="upid" cssClass="textField"/></td>
                                    <td class="formLable">Minimum Length<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upminlength" name="upminlength" cssClass="textField"/></td>  
                                </tr>  
                                <tr>
                                    <td class="formLable">Maximum Length<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upmaxlength" name="upmaxlength" cssClass="textField" /></td>      
                                </tr>
                                <tr>
                                    <td class="formLable">Allow Special Characters<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upallowspecialcharacters" name="upallowspecialcharacters" cssClass="textField" /></td>
                                    <td class="formLable">[~@#$&!]</td> 
                                </tr>  
                                <tr>
                                    <td class="formLable">Minimum Special Characters<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upminimumspecialcharacters" name="upminimumspecialcharacters" cssClass="textField" /></td>  
                                </tr>
                                <tr>
                                    <td class="formLable">Maximum Special Characters<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upmaximumspecialcharacters" name="upmaximumspecialcharacters" cssClass="textField" /></td>  

                                </tr>
                                <tr>
                                    <td class="formLable">Minimum Uppercase Characters<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upminimumuppercasecharacters" name="upminimumuppercasecharacters" cssClass="textField"/></td>  
                                </tr>  
                                <tr>
                                    <td class="formLable">Minimum Numerical Characters<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="upminimumnumericalcharacters" name="upminimumnumericalcharacters" cssClass="textField" /></td>      
                                </tr>
                                <tr>
                                    <td class="formLable">Description<span class="mandatory">*</span></td> <td >:</td>
                                    <td><s:textfield id="updescription" name="updescription" cssClass="textField" /></td> 
                                </tr> 
                                <tr>
                                    <td class="content_td formLable" colspan="7"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory" >*</span></td>
                                </tr>
                            </table><table class="form_table">
                                <tr>                                
                                    <td> <s:url var="updateurl" action="UpdatepwPolicyMng"/>                                   
                                        <sj:submit   button="true" href="%{updateurl}" value="Update"   targets="divmsg"  cssClass="button_sadd" disabled="#vupdate"/>
                                        <sj:submit button="true" value="Reset" onClick="resetUpdateForm()" cssClass="button_sreset"/>
                                        <sj:a href="#" name="back" button="true" onclick="backToMain()"  cssClass="button_aback" >Back</sj:a>    
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
