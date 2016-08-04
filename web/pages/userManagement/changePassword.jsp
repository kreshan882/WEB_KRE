<%-- 
    Document   : changePassword
    Created on : Aug 6, 2014, 4:08:59 PM
    Author     : kreshan
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Styles.jsp" />
        <script type="text/javascript">
             function resetData(){
                $('#passwordOld').val("");   
                $('#passwordNew1').val("");  
                $('#passwordNew2').val(""); 
            }
            
            $.subscribe('resetButton', function(event, data) {
                $('#divmsg').empty();
                resetData();
            });
        </script>  
    </head>

    <body style="overflow:hidden">

        <div class="wrapper">

            <jsp:include page="../../header.jsp" /> 



            <div class="body_content" id="includedContent">

                
                    <div class="watermark"></div>
                    <div class="heading">Change Password</div>
                    <div class="AddUser_box ">
                    <div class="message" >   
                        <s:div id="divmsg">
                            <i style="color: red">  <s:actionerror theme="jquery"/></i>
                            <i style="color: green"> <s:actionmessage theme="jquery"/></i>
                        </s:div>
                    </div>
                    <div class="contentcenter">           
                        
                        <s:form method="post" name="changePassword" id="changePassword" theme="simple" >         
                            <table class="form_table" border="0px">
                                <tr>
                                    <td class="content_td formLable">User Name</td>
                                    <td class="content_td formLable">:</td>
                                    <td colspan="2"><b>${SessionObject.username}</b></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable">Old Password<span class="mandatory">*</span></td>
                                    <td class="content_td formLable">:</td>
                                    <td colspan="2"><s:password name="passwordOld" id="passwordOld" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable">New Password<span class="mandatory">*</span></td>
                                    <td class="content_td formLable">:</td>
                                    <td colspan="2"><s:password name="passwordNew1" id="passwordNew1" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable">Confirm New Password<span class="mandatory">*</span></td>
                                    <td class="content_td formLable">:</td>
                                    <td colspan="2"><s:password name="passwordNew2" id="passwordNew2" cssClass="textField" /></td>
                                </tr>
                                <tr>
                                    <td class="content_td formLable" colspan="4"><span class="mandatory_text">Mandatory fields are marked with</span><span class="mandatory">*</span></td>
                                </tr>
                                <tr>
                                    <td colspan="4">
                                        <s:url var="changePasswordUrl" action="changePwFunctionchangePassword"/>
                                        <sj:submit 
                                            button="true"
                                            value="Save" 
                                            href="%{changePasswordUrl}"
                                            targets="divmsg"
                                            cssClass="button_sadd"
                                        />
                                        <s:url  var="inserturl"/>
                                        <sj:submit href="%{inserturl}" button="true"  onClickTopics="resetButton" value="Reset" cssClass="button_reset"/>
                                    </td>
                                </tr>

                            </table>
                        </s:form>
                    </div>
                </div>

            </div>



            <jsp:include page="../../footer.jsp" /> 



        </div> <!--end of body_content-->

    </body>
</html>
