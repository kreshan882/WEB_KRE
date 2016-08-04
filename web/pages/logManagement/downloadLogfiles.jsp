<%-- 
    Document   : downloadLogsFiles
    Created on : Aug 6, 2014, 4:03:57 PM
    Author     : kreshan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>      
<html>
    <head>
        <title>Download Log Files</title>
        <jsp:include page="/Styles.jsp" />



        <script type="text/javascript" >



            function downloadformatter(cellvalue, options, rowObject) {


                return "<a href='${pageContext.request.contextPath}/DownloadlogDownloadMng?filePath=" + cellvalue + "' ><img src='${pageContext.request.contextPath}/resources/images/download.png'  /></a>"
            }


            function downloadSelected(keyval) {

                $.ajax({
                    url: '${pageContext.request.contextPath}/DownloadlogsDownloadLogsFiles',
                    data: {filePath: keyval},
                    dataType: "json",
                    type: "POST",
                    success: function (data) {
                    }
                });

            }




            $.subscribe('onclickview', function (event, data) {
                $('#message').empty();
                var logFileSelection = $('#logFileSelection').val();

                $("#gridtable").jqGrid('setGridParam', {postData: {logFileSelection: logFileSelection}});
                $("#gridtable").jqGrid('setGridParam', {page: 1});
                jQuery("#gridtable").trigger("reloadGrid");

            });



        </script>

    </head>
    <body style="overflow:hidden">
        <div class="wrapper">

            <jsp:include page="../../header.jsp" /> 
            <div class="body_content" id="includedContent">



                <div class="watermark"></div>
                <div class="heading">Download Log Files</div>
                <div class="AddUser_box ">   


                    <div class="message">
                        <s:div id="message">
                            <i style="color: red">  <s:actionerror theme="jquery"/></i>
                            <i style="color: green"> <s:actionmessage theme="jquery"/></i>
                        </s:div>
                    </div>


                    <s:set id="vadd" var="vadd"><s:property value="vadd" default="true"/></s:set>
                    <s:set var="vupdate"><s:property value="vupdate" default="true"/></s:set>
                    <s:set var="vdelete"><s:property value="vdelete" default="true"/></s:set>
                    <s:set var="vdownload"><s:property value="vdownload" default="true"/></s:set>
                    <s:set var="vresetpass"><s:property value="vresetpass" default="true"/></s:set>


                        <div class="contentcenter">   

                        <s:form action="BackupAndDownloadlogDownloadMng" theme="simple" cssStyle="display:none">         
                            <table class="form_table" border="0">
                                <tr>

                                    <td class="content_td formLable">Log File Type:</td>

                                    <td colspan="3"><s:select  name="logFileSelection" 
                                               listKey="key" listValue="value"
                                               list="%{logFileType}" id="logFileSelection" value="01" cssClass="dropdown" /></td>
                                </tr>
                                <tr>

                                    <td align="center" colspan="0"><sj:a 
                                            id="viewbutt" 
                                            button="true"                                        
                                            onClickTopics="onclickview"  cssClass="button_asave"   role="button" aria-disabled="false"                                     
                                            >View</sj:a></td>
                                    <td align="left" colspan="0" ><s:submit button="true" label="generate" value="Download Logs" cssClass="button_asave" cssStyle="width:140px; padding-left:1px;padding-right:1px; height:23px" hidden="#vdownload"  ></s:submit></td>
                                        <td></td>


                                    </tr>
                                </table>
                        </s:form>

                    </div>                 


                    <div class="viewuser_tbl">
                        <s:url var="listurl" action="ListlogDownloadMng"/>
                        <sjg:grid
                            id="gridtable"
                            caption="Download Log Files"
                            dataType="json"
                            href="%{listurl}"
                            pager="true"
                            gridModel="gridModel"
                            rowList="10,15,20"
                            rowNum="10"
                            autowidth="true"
                            rownumbers="true"
                            onCompleteTopics="completetopics"
                            rowTotal="false"
                            viewrecords="true"

                            >

                            <sjg:gridColumn name="logFileName" index="logFileName" title="Log File Name" align="left" width="20" sortable="true"/>                    
                            <sjg:gridColumn name="date" index="date" title="Date"  align="center" width="20"  sortable="true" hidden="true"/>
                            <sjg:gridColumn name="size" index="size" title="Size" align="center" width="20" sortable="true"/>                    
                            <sjg:gridColumn name="path" index="path" title="Download"  align="center" width="20"  formatter="downloadformatter" hidden="#vdownload" sortable="false"/>
                        </sjg:grid> 


                    </div>

                </div><!--end of body_content-->
            </div>




            <jsp:include page="../../footer.jsp" /> 



        </div> 

    </body>
</html>
