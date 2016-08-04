<%-- 
    Document   : userProfileManagement
    Created on : Feb 29, 2016, 4:52:32 PM
    Author     : kreshan
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
            function statusformatter(cellvalue, options, rowObject) {
                if (cellvalue == '1') {
                    var html = "<img src='${pageContext.request.contextPath}/resources/images/normal_1.png' />";
                } else {
                    var html = "<img src= '${pageContext.request.contextPath}/resources/images/risk_critical_s.gif' />";
                }
                return html;
            }

            function statusFormatter(cellvalue, options, rowObject) {
                if (cellvalue == '1') {
                    var html = "<img src='${pageContext.request.contextPath}/resources/images/normal_1.png' />";
                } else {
                    var html = "<img src= '${pageContext.request.contextPath}/resources/images/risk_critical_s.gif' />";
                }
                return html;
            }


            function grideReload() {
                jQuery("#gridtable1").trigger("reloadGrid");
                jQuery("#gridtable2").trigger("reloadGrid");
                $('.loading').hide();
                setTimeout(grideReload, 5000);
            }

            // boot up the first call
            grideReload();




            //reset Datas

        </script>
    </head>

    <body style="overflow:hidden" onload="load()">
        <div class="wrapper">

            <div class="body_content" id="includedContent" >

                <div class="watermark"></div>
                <div class="heading">Monitor Current Status</div>
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

                        </div>


                        <div class="viewuser_tbl">
                            <div id="tablediv">

                            <s:url var="Lclisturl" action="LcListmonitorstatusMng"/>
                            <s:url var="Eslisturl" action="EsListmonitorstatusMng"/>
                            <sjg:grid
                                id="gridtable1"
                                caption="Channel Monitor"
                                dataType="json"
                                href="%{Lclisturl}"
                                pager="true"
                                gridModel="gridModel"
                                rowList="10,15,20"
                                rowNum="10"
                                autowidth="true"
                                rownumbers="true"
                                onCompleteTopics="completetopics"
                                rowTotal="false"
                                viewrecords="true"
                                shrinkToFit = "true"
                                >
                                <sjg:gridColumn name="lcId" index="ID" title="Channel Name" align="left" width="20" sortable="true"/> 
                                <sjg:gridColumn name="lanSta" index="LAN_STATUS" title="Network Status" align="center" width="10" formatter="statusFormatter" sortable="false"/>
                                <sjg:gridColumn name="connectSta" index="CONNECT_STATUS" title="Service Status" align="center" formatter="statusFormatter" width="10"  sortable="true"/>  
                                <sjg:gridColumn name="type" index="SIGNONSTATUS" title="Sign On Status" align="center" width="10" formatter="statusFormatter" sortable="false"/>  



                            </sjg:grid> 
                            <br>
                            <br>
                            <sjg:grid
                                id="gridtable2"
                                caption="Epic Switch Monitor"
                                dataType="json"
                                href="%{Eslisturl}"
                                pager="true"
                                gridModel="gridModel2"
                                rowList="10,15,20"
                                rowNum="10"
                                autowidth="true"
                                rownumbers="true"
                                onCompleteTopics="completetopics"
                                rowTotal="false"
                                viewrecords="true"
                                shrinkToFit = "false"
                                >
                                <sjg:gridColumn name="status" index="STATUS" title="Switch Status" align="center" width="500" formatter="statusFormatter" sortable="true"/>
                                <sjg:gridColumn name="trafic" index="TRAFFIC" title="Current Traffic" align="left" width="157" sortable="true"/>                     
                                <sjg:gridColumn name="checkout" index="CHECKOUT" title="Checkout Connection(DB)" align="center"  width="157"  sortable="true"/>
                                <sjg:gridColumn name="dbcon" index="DBCON" title="Free Connection(DB)" align="center" width="157"  sortable="true"/>  

                            </sjg:grid> 


                        </div> 



                    </div>


                </div>              

            </div>
        </div>
        <jsp:include page="../../footer.jsp" /> 

    </body>
</html>