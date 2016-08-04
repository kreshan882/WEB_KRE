<%-- 
    Document   : menu
    Created on : Aug 12, 2014, 6:01:36 PM
    Author     : kreshan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"  %>  
<%@taglib  uri="/struts-jquery-tags" prefix="sj"%>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>  

<html>
    <head>
        
    <sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="resources/template/themes"/>


    <link href="${pageContext.request.contextPath}/resources/images/favicon.ico" rel="shortcut icon" type="image/ico" />  
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resources/css/buttons.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resources/css/familytree.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resources/css/font-awesome.css" rel="stylesheet" type="text/css">
    
    <script src="${pageContext.request.contextPath}/resources/js/TreeMenu.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/div.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.cookie.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/amcharts.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/pie.js" type="text/javascript"></script>

    <script type="text/javascript" >

        $(document).ready(function() {
            var idpage = $.cookie("selectedpage");
            var idpageval = "#" + idpage;
            var idsec = $.cookie("selectedsec");
            var idsecval = "#" + idsec;
            //        $(idsecval).addClass("active");
            $(this).find(idpageval).addClass('active');
            $(idsecval).slideDown();
            //        $(this).siblings('li').find(idsecval).removeClass('active');



            $("#accordian h3").click(function() {
                //slide up all the link lists
                $("#accordian ul ul").slideUp();
                //slide down the link list below the h3 clicked - only if its closed
                if (!$(this).next().is(":visible"))
                {
                    $(this).next().slideDown();
                    $.cookie("selectedsec", $(this).next().attr("id"), {path: "/", expires: 1});
                    //                alert($(this).next().attr("id"));
                }
            })


        })

        $(function() {
            $('#accordian ul ul li').click(function() {
                $.cookie("selectedpage", $(this).find('a').attr("id"), {path: "/", expires: 1});
                //            $(this).find('a').addClass('active');
                //            $(this).siblings('li').find('a').removeClass('active');
            })
        });

    </script>
</head>
</html>

