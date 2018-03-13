<%@ page import="com.smile.util.*" %>
<%@ page import="java.sql.Connection" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%
        // java code in jsp file
        // do not create a new database connection if connection does not exist for this session
        System.out.println("/songMenu.jsp .....");
        Connection dbConn = JdbcMysql.getStoredConnection(request,false);
        if (dbConn == null) {
            // go to login page (index.jsp)
            RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
            view.forward(request, response);
            return;
        }
    %>

    <script type="text/javascript" src="<%=request.getContextPath()%>/jQuery/jquery-1.11.3.min.js"></script>
    <script type="text/javascript">
        // Using jQuery.
        $(function(){
            // load logout page
            $('#logoutJsp').load("<%=request.getContextPath()%>/logout.jsp");
        });
    </script>
	
    <style>
        body {
            font-family:"New Roman";
            font-size:20px;
            background-color:#d0e4fe;
        }
        h1 {
            color:green;
            text-align:center;
        }
        table {
            border: 2px solid #000000;
            width:80%;
            margin-left: auto;
            margin-right: auto;
        }
        .table label {
            font-family:"New Roman";
            color:blue;
        }
        .table label.radio {
            font-family:"New Roman";
            color:red;
        }
        .table select{
        }
        td,th {
            font-family:"New Roman";
            border: 1px solid #000000;
        }
    </style>
    
    <title>Main Menu</title>
</head>
    
<body>
    <div id="logoutJsp" style="text-align:right;"></div>
    <h1 style="margin:0px 0px 0px 0px;">Songs Data Management System</h1>
    <table>
        <tr>
            <td>
                <!-- cannot be /Song2Servlet.html 
                    <a href="Song2Servlet.html?actFunction=LIST&queryCondition=&byOrder=song2.song_no&pageNo=1">Songs Management</a>
                -->
                <a href="<%=request.getContextPath()%>/song2Web/song2.jsp">Songs Management</a>
            </td>
            <td>
                <a href="<%=request.getContextPath()%>/singerWeb/singers.jsp">Singers Management</a>
            </td>
        </tr>
    </table>
</body>
</html>