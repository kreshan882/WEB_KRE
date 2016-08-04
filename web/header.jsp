<%-- 
    Document   : header
    Created on : Aug 12, 2014, 6:09:22 PM
    Author     : kreshan
--%>



<%@page import="com.epic.util.ModuleComparator"%>
<%@page import="java.util.TreeSet"%>
<%@page import="com.epic.login.bean.PageBean"%>
<%@page import="com.epic.login.bean.ModuleBean"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<div class="banner1">
    <div class="leftimg"></div>
    <div class="rightimg"></div>
</div><!--end of banner1-->

<div class="ticker">
    <div id="pheader" class="ticker_heading"></div><!--ticker current path-->
    <div class="username "><span class="caps">${SessionObject.username}</span></div> 
    <a href="LogoutloginCall.action">
        
        <div class="logout"><span class="login_data"></span>
        
        </div>
    </a>
                    <!--       <font>Home</font></a>-->
                    <div id="wrap">
		  <ul class="navbar">
			
                      <li><a class="logout-icon" href="#"><span class="fa fa-lock"></span></a>
				<ul>
                                    <li><a class="change_pswd" href="changePassword.action">Change Password</a></li>
				   <li><a href="LogoutloginCall.action">Logout</a></li>
				   
				</ul>         
			 </li>
			 
		  </ul>
</div>
    
</div><!--end of ticker-->    


<div class="left_menu_bar">
    <span class="login_data"><div class="home"><a href="homeCall.action"><img src="${pageContext.request.contextPath}/resources/images/home.png"/></a></div></span>
    <div id="accordian">
        <ul>



            <%

                try {

                    HashMap<ModuleBean, List<PageBean>> sectionPageList = (HashMap<ModuleBean, List<PageBean>>) request.getSession().getAttribute("modulePageList");
//                    String warnmsg = request.getSession().getAttribute(SessionVarlist.MIN_PASSWORD_CHANGE_PERIOD);
            %>



            <%
                    ModuleComparator sec1 = new ModuleComparator();
                    Set<ModuleBean> section = new TreeSet<ModuleBean>(sec1);

                    Set<ModuleBean> section1 = sectionPageList.keySet();
                    for (ModuleBean sec2 : section1) {
                        section.add(sec2);
                    }

                    int secId = 1;
                    int pageId = 1000;

                    out.println("<li class=\'main-navigation-menu\' id=\'treemenu\' >");

                    for (ModuleBean sec : section) {

                        out.println("<h3>" + sec.getMODULE_NAME() + "</h3>");

                        out.println("<ul id=\"" + secId + "\">");

                        //                        out.println("d.add(" + i + "," + 0 + ",\'" + sec.getDescription() + "\');");
                        List<PageBean> pageList = sectionPageList.get(sec);
                        for (PageBean pageBean : pageList) {

                            pageId++;

                            out.println("<li>");

                            out.println("<a id= " + pageId + " href=\'" + request.getContextPath() +"/"+ pageBean.getPAGE_URL() + ".action\'>");
                            out.println("<span class=\'title\'>" + pageBean.getPAGE_NAME() + "</span>");
                            out.println("</a>");

                            out.println("</li>");

//                            out.println("d.add(" + j + "," + i + ",\'" + pageBean.getDescription() + "\'" + ",\' " + request.getContextPath() + pageBean.getUrl() + ".mpi\');");
                        }

                        out.println(" </ul>");

                        secId++;
                    }
                    out.println("</li>");

                } catch (Exception ee) {

                    ee.printStackTrace();
                }
            %>


        </ul>                   

    </div><!--end of accordian_menu_bar-->
</div><!--end of left_menu_bar-->
