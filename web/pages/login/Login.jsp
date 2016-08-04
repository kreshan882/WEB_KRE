<%-- 
    Document   : Login
    Created on : 17/07/2014, 5:15:17 PM
    Author     : kreshan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/resources/css/font-awesome.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/resources/images/favicon.ico" rel="shortcut icon" type="image/ico" />



        <title>Login Page</title>
    </head>
    <body>
        <div class="login_body">

            <div class="header">
                <div class="leftimg"></div>
                <!--<div class="rightimg"></div>-->
                <div class="login_ticker">
                    <div class="login_content"></div>
                </div>

            </div>
            <!--login form-->

            <div class="watermark"></div>
            <div class="content">

                <div id="dialog">

                    <s:form action="loginCheckloginCall" theme="simple">

                        <table>
                            
                            <tr>
                                <td class="lable">Login ID</td>
                                <td class="lable">:</td>
                                <td colspan="2"><s:textfield name="userName"  cssClass="form-field"/></td>
                            </tr>
                            <tr>
                                <td class="lable">Password</td>
                                <td class="lable">:</td>
                                <td colspan="2"><s:password name="password"  cssClass="form-field"/></td>
                            </tr>
                            <tr>  
                                <td colspan="2"></td>
                                <td align="right"><s:submit label="Login" cssClass="login_button" value="Login" /></td>
                                <td align="right"><s:reset label="Reset" cssClass="reset_button" value="Reset" /></td>

                            </tr>


                        </table>
                        <i style="color: red"> <s:actionerror/></i> 
                        <i style="color: green"> <s:actionmessage/></i> 
                    </s:form>
                </div>
            </div>
            <!--end of login form-->


            <div class="footer"><jsp:include page="../../footer.jsp" /></div>
        </div>
    </body>
</html>
