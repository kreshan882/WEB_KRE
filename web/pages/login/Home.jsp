<%-- 
    Document   : Home
    Created on : Aug 5, 2014, 4:25:18 PM
    Author     : kreshan
--%>

<%@page import="org.apache.struts2.ServletActionContext"%>
<%@page import="com.epic.login.bean.SessionUserBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Epic CLA</title>
        <jsp:include page="../../Styles.jsp" />



    </head>

    <body style="overflow:hidden">

        <div class="wrapper">

            <jsp:include page="../../header.jsp" /> 



            <div class="body_content" id="includedContent">
                <div class="AddUser_box">
                    <div class="watermark"></div>
                    <div class="contentcenter">
                        <div class="table_center">

                            <table class="form_table" cellspacing="8" align="center">
                               
                                <tr class="home_data">
                                    <td class="content_td homeLable" valign="top">Web Admin Panel Version</td>
                                    <td class="content_td homeLable1">1.03</td>
                                </tr>
                                <tr class="home_data">
                                    <td class="content_td homeLable" valign="top">Epic Switch Version</td>
                                    <td class="content_td homeLable1">1.02</td>
                                </tr>


                            </table>
                        </div>





                    </div>
                </div>
            </div>



            <jsp:include page="../../footer.jsp" /> 



        </div> <!--end of body_content-->

    </body>
</html>

